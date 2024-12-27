package com.waltmann.waltmann_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class WaltmannApiApplicationTests {

	@Test
	void contextLoads() {
		System.setProperty("AWS_REGION", "us-east-1");
	}

}
