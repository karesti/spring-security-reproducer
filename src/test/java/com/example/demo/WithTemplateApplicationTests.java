package com.example.demo;

import config.OldWebSecurityConfig;
import config.WebSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(WebSecurityConfig.class)
public class WithTemplateApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	public void accessSecuredResourceAuthenticatedThenOk() throws Exception {
		HttpHeaders httpHeaders = testRestTemplate.withBasicAuth("user", "password")
				.getRestTemplate().headForHeaders(getTestURL());
		// With WebSecurityConfig works
		// The status is Forbidden, it does not execute the method
		assertThat(httpHeaders).isNotNull();
		assertThat(httpHeaders.get("hello")).isNotNull();
		assertThat(httpHeaders.get("hello")).isNotEmpty();
		assertThat(httpHeaders.get("hello").get(0)).isEqualTo("world");
	}

	@Test
	public void accessSecuredResourceNotAuthenticated() throws Exception {
		HttpHeaders httpHeaders = testRestTemplate.withBasicAuth("pepe", "lolo")
				.getRestTemplate().headForHeaders(getTestURL());
		// The status is 403, does not execute the method
		assertThat(httpHeaders).isNotNull();
		assertThat(httpHeaders.get("hello")).isNull();
	}


	private String getTestURL() {
		return "http://localhost:" + port + "/test";
	}


}
