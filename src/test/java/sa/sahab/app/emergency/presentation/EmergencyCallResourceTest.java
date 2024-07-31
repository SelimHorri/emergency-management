package sa.sahab.app.emergency.presentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import sa.sahab.app.AppConstants;
import sa.sahab.app.emergency.presentation.request.EmergencyCallRequest;
import sa.sahab.app.emergency.presentation.response.ApiPayload;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Testcontainers
@Rollback
class EmergencyCallResourceTest {
	
	@Container
	@ServiceConnection
	private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"));
	
	@Autowired
	private WebTestClient webTestClient;
	
	@DisplayName("Testing find all emergency calls")
	@Test
	void whenDoFindAll_thenAllEmergencyCallsShouldBeReturned() {
		this.webTestClient
				.get()
				.uri("/emergency-calls")
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody()
				.jsonPath("$").value(notNullValue(ApiPayload.class))
				.jsonPath("$.timestamp").value(notNullValue(Instant.class))
				.jsonPath("$.status").value(is(ApiPayload.ApiStatus.SUCCESS.name()))
				.jsonPath("$.responseBody").value(notNullValue())
				.jsonPath("$.responseBody").isArray()
				.jsonPath("$.responseBody.size()").isEqualTo(10);
	}
	
	@DisplayName("given a valid ID, test find emergency call by ID")
	@Test
	void givenValidId_whenDoFindById_thenAnEmergencyCallShouldBeReturned() {
		var validId = UUID.fromString("3c4d5e6f-7a8b-9c0d-1e2f-3a4b5c6d7e8f");
		this.webTestClient
				.get()
				.uri("/emergency-calls/{id}", validId)
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
	
	@DisplayName("given a invalid ID, test find emergency call by ID")
	@Test
	void givenInvalidId_whenDoFindById_thenAnEmergencyCallShouldBeReturned() {
		var invalidId = UUID.fromString("3c4d5e6f-7a8b-9c0d-1e2f-3a4b5c6d7e8e");
		this.webTestClient
				.get()
				.uri("/emergency-calls/{id}", invalidId)
				.exchange()
				.expectStatus()
				.isNotFound()
				.expectBody()
				.jsonPath("$").value(notNullValue(ApiPayload.class))
				.jsonPath("$.timestamp").value(notNullValue(Instant.class))
				.jsonPath("$.status").value(is(ApiPayload.ApiStatus.FAILURE.name()))
				.jsonPath("$.responseBody").value(notNullValue())
				.jsonPath("$.responseBody.type").value(notNullValue())
				.jsonPath("$.responseBody.type").value(notNullValue())
				.jsonPath("$.responseBody.title").isNotEmpty()
				.jsonPath("$.responseBody.status").value(notNullValue())
				.jsonPath("$.responseBody.detail").isNotEmpty()
				.jsonPath("$.responseBody.instance").value(notNullValue());
	}
	
	@DisplayName("given a request body, test create emergency call")
	@Test
	void givenValidRequestBody_whenDoCreateEmergencyCall_thenAnEmergencyCallShouldBeReturned() {
		this.webTestClient
				.post()
				.uri("/emergency-calls")
				.bodyValue(EmergencyCallRequest.builder()
						.callerName("John Smith")
						.callerPhoneNumber("1234567890")
						.locationAddress("123 Main Street")
						.description("Medical emergency")
						.callTime(LocalDateTime.parse(
								"15-10-2022 08:00:00",
								DateTimeFormatter.ofPattern(AppConstants.LOCAL_DATE_TIME_FORMAT)))
						.ambulanceId(UUID.fromString("b2e4d6c8-5e4f-4a2b-b1d3-c6e8a4f7b9d1"))
						.driverId(UUID.fromString("b2c3d4e5-f6a7-8b9c-0d1e-f2a3b4c5d6e7"))
						.build())
				.exchange()
				.expectStatus()
				.isCreated()
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
	
}



