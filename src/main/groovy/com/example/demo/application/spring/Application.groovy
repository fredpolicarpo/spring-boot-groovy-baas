package com.example.demo.application.spring


import com.example.demo.business.Interactor
import com.example.demo.business.ports.AccountRepository
import com.example.demo.business.ports.Timer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@SpringBootApplication
class Application {
	static void main(String[] args) {
		SpringApplication.run(Application, args)
	}

	@Bean
	Interactor interactor(final AccountRepository accountRepositoryJpa, final Timer defaultTimer) {
		return new Interactor(accountRepositoryJpa, defaultTimer)
	}
}
