package com.example.demo;

import config.OldWebSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(OldWebSecurityConfig.class)
public class OldWithWebMockDemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithAnonymousUser
	public void accessAnonymous() throws Exception {
		// With old config is unauthorized
		mockMvc.perform(get("/test"))
				.andExpectAll(status().isUnauthorized()).andReturn().getResponse();
	}

	@Test
	@WithMockUser
	public void accessSecuredResourceAuthenticatedThenOk() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/test"))
				.andExpectAll(status().isOk())
				.andReturn();

		List<String> headers = mvcResult.getResponse().getHeaders("hello");
		assertThat(headers).isNotNull();
		assertThat(headers).isNotEmpty();
		assertThat(headers).first().isEqualTo("world");
	}

}
