package sa.sahab.app.emergency.presentation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;
import sa.sahab.app.emergency.presentation.request.EmergencyCallRequest;
import sa.sahab.app.emergency.presentation.response.ApiPayload;
import sa.sahab.app.emergency.presentation.response.EmergencyCallResponse;
import sa.sahab.app.emergency.application.EmergencyCallService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Emergency calls Management", description = "Emergency calls management services")
@RestController
@RequestMapping("/emergency-calls")
@RequiredArgsConstructor
class EmergencyCallResource {
	
	private final EmergencyCallService emergencyCallService;
	
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ApiPayload.class)),
					description = "Find all emergency calls"),
			@ApiResponse(
					responseCode = "404",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ProblemDetail.class))),
			@ApiResponse(
					responseCode = "500",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ProblemDetail.class)))
	})
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	ApiPayload<List<EmergencyCallResponse>> findAllEmergencyCalls() {
		return ApiPayload.ofSuccess(this.emergencyCallService.findAllEmergencyCalls());
	}
	
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ApiPayload.class)),
					description = "Find a specific emergency call by id"),
			@ApiResponse(
					responseCode = "404",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ProblemDetail.class))),
			@ApiResponse(
					responseCode = "500",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ProblemDetail.class)))
	})
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	ApiPayload<EmergencyCallResponse> findEmergencyCallById(@PathVariable UUID id) {
		return ApiPayload.ofSuccess(this.emergencyCallService.findEmergencyCallById(id));
	}
	
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "201",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ApiPayload.class)),
					description = "Log a new emergency call"),
			@ApiResponse(
					responseCode = "404",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ProblemDetail.class))),
			@ApiResponse(
					responseCode = "500",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ProblemDetail.class)))
	})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	ApiPayload<EmergencyCallResponse> createEmergencyCall(@RequestBody @Valid EmergencyCallRequest emergencyCallRequest) {
		return ApiPayload.ofSuccess(this.emergencyCallService.createEmergencyCall(emergencyCallRequest));
	}
	
}



