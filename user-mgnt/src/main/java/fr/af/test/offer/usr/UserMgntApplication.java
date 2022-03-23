package fr.af.test.offer.usr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("fr.af.test.offer.usr.mapper")
@EnableAspectJAutoProxy
public class UserMgntApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMgntApplication.class, args);
	}

}
