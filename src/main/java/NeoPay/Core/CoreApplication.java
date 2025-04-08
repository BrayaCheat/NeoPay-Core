package NeoPay.Core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

}
