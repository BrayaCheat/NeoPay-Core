package NeoPay.Core.Services.ServiceImpl;

import NeoPay.Core.DTO.Mapper.AccountMapper;
import NeoPay.Core.DTO.Mapper.TransactionMapper;
import NeoPay.Core.DTO.Request.TransactionRequest;
import NeoPay.Core.DTO.Response.TransactionResponse;
import NeoPay.Core.Exceptions.NotFoundException;
import NeoPay.Core.Models.Account;
import NeoPay.Core.Models.Transaction;
import NeoPay.Core.Models.User;
import NeoPay.Core.Repositories.AccountRepository;
import NeoPay.Core.Repositories.TransactionRepository;
import NeoPay.Core.Services.TransactionService;
import NeoPay.Core.Utilities.TransactionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final TransactionUtils transactionUtils;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper, AccountRepository accountRepository, AccountMapper accountMapper, TransactionUtils transactionUtils) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.transactionUtils = transactionUtils;
    }

    @Transactional
    @Override
    public TransactionResponse createTransaction(TransactionRequest dto) {
        if (dto.getType().toString().isEmpty()) {
            throw new RuntimeException("Invalid transaction type!");
        }
        return switch (dto.getType()) {
            case DEPOSIT -> handleDeposit(dto);
            case WITHDRAW -> handleWithdraw(dto);
            case TRANSFER -> handleTransfer(dto);
        };
    }

    public TransactionResponse handleDeposit(TransactionRequest dto) {
        Account receiver = accountRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new NotFoundException("Receiver id: " + dto.getReceiverId() + " not found!"));

        receiver.setBalance(receiver.getBalance().add(dto.getAmount()));
        accountRepository.save(receiver);

        Transaction transaction = Transaction.builder()
                .receiver(receiver)
                .amount(dto.getAmount())
                .currency("USD")
                .remark(dto.getRemark())
                .type(Transaction.TransactionType.DEPOSIT)
                .status(Transaction.TransactionStatus.SUCCESS)
                .reference(TransactionUtils.generateTransactionReferenceId())
                .build();

        return transactionMapper.toDTO(transactionRepository.save(transaction));
    }

    public TransactionResponse handleWithdraw(TransactionRequest dto) {
        Account sender = accountRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new NotFoundException("Sender id: " + dto.getSenderId() + " not found!"));

        if (sender.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance for withdrawal!");
        }

        sender.setBalance(sender.getBalance().subtract(dto.getAmount()));
        accountRepository.save(sender);

        Transaction transaction = Transaction.builder()
                .sender(sender)
                .amount(dto.getAmount())
                .currency("USD")
                .remark(dto.getRemark())
                .type(Transaction.TransactionType.WITHDRAW)
                .status(Transaction.TransactionStatus.SUCCESS)
                .reference(TransactionUtils.generateTransactionReferenceId())
                .build();

        return transactionMapper.toDTO(transactionRepository.save(transaction));
    }

    public TransactionResponse handleTransfer(TransactionRequest dto) {
        Account sender = accountRepository.findById(dto.getSenderId()).orElseThrow(() -> new NotFoundException("Sender id: " + dto.getSenderId() + " not found!"));
        Account receiver = accountRepository.findById(dto.getReceiverId()).orElseThrow(() -> new NotFoundException("Receiver id: " + dto.getReceiverId() + " not found!"));

        if (sender.getBalance().compareTo(dto.getAmount()) < 0) {
            Transaction transaction = Transaction.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .amount(dto.getAmount())
                    .currency("USD")
                    .reference(TransactionUtils.generateTransactionReferenceId())
                    .remark("Insufficient balance!")
                    .type(dto.getType())
                    .status(Transaction.TransactionStatus.FAILED)
                    .build();
            transactionUtils.saveFailedTransaction(transaction);
            throw new RuntimeException("Insufficient balance! You only have: " + sender.getBalance());
        }

        sender.setBalance(sender.getBalance().subtract(dto.getAmount()));
        receiver.setBalance(receiver.getBalance().add(dto.getAmount()));
        accountRepository.save(sender);
        accountRepository.save(receiver);

        Transaction transaction = Transaction.builder()
                .sender(sender)
                .receiver(receiver)
                .amount(dto.getAmount())
                .currency("USD")
                .reference(TransactionUtils.generateTransactionReferenceId())
                .remark(dto.getRemark())
                .type(dto.getType())
                .status(Transaction.TransactionStatus.SUCCESS)
                .build();
        return transactionMapper.toDTO(transactionRepository.save(transaction));
    }

    @Override
    public List<TransactionResponse> getTransaction(Long accountId, Pageable pageable) {
        Specification<Transaction> spec = TransactionUtils.sentOrReceived(accountId);
        return transactionRepository.findAll(spec, pageable).stream().map(transactionMapper::toDTO).toList();
    }

    @Override
    public Page<TransactionResponse> getReceiveTransactions(Long accountId, Pageable pageable, String senderAccountNumber, String receiverAccountNumber) {
        Specification<Transaction> spec = TransactionUtils.buildTransactionSearchSpec(accountId, true, senderAccountNumber, receiverAccountNumber);
        return transactionRepository.findAll(spec, pageable).map(transactionMapper::toDTO);
    }

    @Override
    public Page<TransactionResponse> getSenderTransactions(Long accountId, Pageable pageable, String senderAccountNumber, String receiverAccountNumber) {
        Specification<Transaction> spec = TransactionUtils.buildTransactionSearchSpec(accountId, false, senderAccountNumber, receiverAccountNumber);
        return transactionRepository.findAll(spec, pageable).map(transactionMapper::toDTO);
    }

    @Override
    public Long transactionCount(Long userId) {
        return transactionRepository.transactionCount(userId);
    }
}
