package kr.codesqaud.cafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import kr.codesqaud.cafe.controller.CafeController;
import kr.codesqaud.cafe.domain.MemoryUserRepository;
import kr.codesqaud.cafe.domain.UserRepository;

@SpringBootApplication
public class CafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafeApplication.class, args);
	}

}
