package sa.sahab.app.emergency.presentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import sa.sahab.app.AppConstants;
import sa.sahab.app.emergency.application.EmergencyCallService;
import sa.sahab.app.emergency.infrastructure.exception.ObjectNotFoundException;
import sa.sahab.app.emergency.presentation.request.EmergencyCallRequest;
import sa.sahab.app.emergency.presentation.response.AmbulanceResponse;
import sa.sahab.app.emergency.presentation.response.ApiPayload;
import sa.sahab.app.emergency.presentation.response.DriverResponse;
import sa.sahab.app.emergency.presentation.response.EmergencyCallResponse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = EmergencyCallResource.class)
@AutoConfigureWebTestClient
class EmergencyCallResourceTest {
	
	@Autowired
	private WebTestClient webTestClient;
	
	@MockBean
	private EmergencyCallService emergencyCallService;
	
	@DisplayName("Testing find all emergency calls")
	@Test
	void whenDoFindAll_thenAllEmergencyCallsShouldBeReturned() {
		when(this.emergencyCallService.findAllEmergencyCalls())
			.thenReturn(List.of(EmergencyCallResponse.builder().build(), EmergencyCallResponse.builder().build()));
		
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
				.jsonPath("$.responseBody.size()").isEqualTo(2);
	}
	
	@DisplayName("given a valid ID, test find emergency call by ID")
	@Test
	void givenValidId_whenDoFindById_thenAnEmergencyCallShouldBeReturned() {
		var validId = UUID.fromString("3c4d5e6f-7a8b-9c0d-1e2f-3a4b5c6d7e8f");
		
		when(this.emergencyCallService.findEmergencyCallById(validId))
			.thenReturn(EmergencyCallResponse.builder()
					.id(validId)
					.callerName("callerNameMock")
					.callerPhoneNumber("callerPhoneNumberMock")
					.callTime(LocalDateTime.now())
					.ambulance(AmbulanceResponse.builder().id(UUID.randomUUID()).build())
					.driver(DriverResponse.builder().id(UUID.randomUUID()).build())
					.build());
		
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
	void givenInvalidId_whenDoFindById_thenExceptionShouldBeThrown() {
		var invalidId = UUID.fromString("3c4d5e6f-7a8b-9c0d-1e2f-3a4b5c6d7e8e");
		var e = new ObjectNotFoundException("Emergency call with id: %s is not found".formatted(invalidId.toString()));
		
		when(this.emergencyCallService.findEmergencyCallById(invalidId))
			.thenThrow(e);
		
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
				.jsonPath("$.responseBody.title").isNotEmpty()
				.jsonPath("$.responseBody.status").value(notNullValue())
				.jsonPath("$.responseBody.detail").isNotEmpty()
				.jsonPath("$.responseBody.detail").isEqualTo(e.getMessage())
				.jsonPath("$.responseBody.instance").value(notNullValue());
	}
	
	@DisplayName("given a request body, test create emergency call")
	@Test
	void givenValidRequestBody_whenDoCreateEmergencyCall_thenAnEmergencyCallShouldBeReturned() {
		var eRequest = EmergencyCallRequest.builder()
				.callerName("John Smith")
				.callerPhoneNumber("1234567890")
				.locationAddress("123 Main Street")
				.description("Medical emergency")
				.callTime(LocalDateTime.parse(
						"15-10-2022 08:00:00",
						DateTimeFormatter.ofPattern(AppConstants.LOCAL_DATE_TIME_FORMAT)))
				.ambulanceId(UUID.fromString("b2e4d6c8-5e4f-4a2b-b1d3-c6e8a4f7b9d1"))
				.driverId(UUID.fromString("b2c3d4e5-f6a7-8b9c-0d1e-f2a3b4c5d6e7"))
				.build();
		var eResponse = EmergencyCallResponse.builder()
				.id(UUID.randomUUID())
				.callerName("John Smith")
				.callerPhoneNumber("1234567890")
				.locationAddress("123 Main Street")
				.description("Medical emergency")
				.callTime(eRequest.callTime())
				.ambulance(AmbulanceResponse.builder().id(UUID.fromString("b2e4d6c8-5e4f-4a2b-b1d3-c6e8a4f7b9d1")).build())
				.driver(DriverResponse.builder().id(UUID.fromString("b2c3d4e5-f6a7-8b9c-0d1e-f2a3b4c5d6e7")).build())
				.build();
		when(this.emergencyCallService.createEmergencyCall(eRequest))
			.thenReturn(eResponse);
		
		this.webTestClient
				.post()
				.uri("/emergency-calls")
				.bodyValue(eRequest)
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
				.jsonPath("$.responseBody.callerName").isEqualTo(eResponse.callerName())
				.jsonPath("$.responseBody.callerPhoneNumber").value(notNullValue())
				.jsonPath("$.responseBody.callerPhoneNumber").isNotEmpty()
				.jsonPath("$.responseBody.callerPhoneNumber").isEqualTo(eResponse.callerPhoneNumber())
				.jsonPath("$.responseBody.callTime").value(notNullValue(LocalDateTime.class))
				.jsonPath("$.responseBody.ambulance").value(notNullValue(AmbulanceResponse.class))
				.jsonPath("$.responseBody.ambulance.id").value(notNullValue(UUID.class))
				.jsonPath("$.responseBody.ambulance.id").isEqualTo(eResponse.ambulance().id().toString())
				.jsonPath("$.responseBody.driver").value(notNullValue(DriverResponse.class))
				.jsonPath("$.responseBody.driver.id").value(notNullValue(UUID.class))
				.jsonPath("$.responseBody.driver.id").isEqualTo(eResponse.driver().id().toString());
	}
	
}



