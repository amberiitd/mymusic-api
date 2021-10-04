package com.namber.mymusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication( exclude = {
})
public class MymusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(MymusicApplication.class, args);
	}

}
