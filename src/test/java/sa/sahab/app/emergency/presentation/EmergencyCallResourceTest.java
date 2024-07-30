package sa.sahab.app.emergency.presentation;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import sa.sahab.app.emergency.presentation.response.ApiPayload;

import java.time.Instant;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class EmergencyCallResourceTest {
	
	private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"));
	
	@Autowired
	private WebTestClient webTestClient;
	
	@BeforeAll
	static void beforeAll() {
		POSTGRES.start();
	}
	
	@AfterAll
	static void afterAll() {
		POSTGRES.stop();
	}
	
	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
		registry.add("spring.datasource.username", POSTGRES::getUsername);
		registry.add("spring.datasource.password", POSTGRES::getPassword);
	}
	
	@DisplayName("Testing find all emergency calls")
	@Test
	void whenDoFindAll_thenAllEmergencyCallsShouldBeReturned() {
		this.webTestClient
				.get()
				.uri("/ambulances")
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody()
				.jsonPath("$").value(notNullValue(ApiPayload.class))
				.jsonPath("$.timestamp").value(notNullValue(Instant.class))
				.jsonPath("$.status").value(is(ApiPayload.ApiStatus.SUCCESS.name()))
				.jsonPath("$.responseBody").value(notNullValue())
				.jsonPath("$.responseBody").isArray();
	}
	
	/*
	@DisplayName("given a valid ID, test find emergency call by ID")
	@Test
	void givenId_whenDoFindById_thenAnEmergencyCallShouldBeReturned() {
		var validId = "3c4d5e6f-7a8b-9c0d-1e2f-3a4b5c6d7e8f";
		this.webTestClient
				.get()
				.uri("/ambulances/{id}", validId)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody()
				.jsonPath("$").value(notNullValue(ApiPayload.class))
				.jsonPath("$.timestamp").value(notNullValue(Instant.class))
				.jsonPath("$.status").value(is(ApiPayload.ApiStatus.SUCCESS.name()))
				.jsonPath("$.responseBody").value(notNullValue())
				.jsonPath("$.responseBody.id").value(notNullValue(UUID.class))
				.jsonPath("$.responseBody.callerName").isNotEmpty()
				.jsonPath("$.responseBody.callerPhoneNumber").value(notNullValue())
				.jsonPath("$.responseBody.callerPhoneNumber").isNotEmpty()
				.jsonPath("$.responseBody.callTime").value(notNullValue())
				.jsonPath("$.responseBody.ambulance").value(notNullValue())
				.jsonPath("$.responseBody.ambulance.id").value(notNullValue(UUID.class))
				.jsonPath("$.responseBody.driver").value(notNullValue())
				.jsonPath("$.responseBody.driver.id").value(notNullValue(UUID.class));
	}
	*/
	
}



