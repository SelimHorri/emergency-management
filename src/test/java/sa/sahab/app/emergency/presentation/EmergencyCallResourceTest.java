package sa.sahab.app.emergency.presentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import sa.sahab.app.emergency.presentation.response.ApiPayload;

import java.time.Instant;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Testcontainers
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
				.jsonPath("$.responseBody.size()").isEqualTo(9);
	}
	
}



