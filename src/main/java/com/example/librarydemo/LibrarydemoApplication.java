package com.example.librarydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LibrarydemoApplication {

	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LibrarydemoApplication.class, args);

        // Get the bean
        ResetBooksDB resetBooksDB = context.getBean(ResetBooksDB.class);

        // Call the reset method
        resetBooksDB.resetDB();
	}
}
