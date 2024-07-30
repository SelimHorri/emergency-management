package sa.sahab.app.emergency.presentation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;
import sa.sahab.app.emergency.application.AmbulanceService;
import sa.sahab.app.emergency.presentation.request.AmbulanceRequest;
import sa.sahab.app.emergency.presentation.response.AmbulanceResponse;
import sa.sahab.app.emergency.presentation.response.ApiPayload;

import java.util.List;
import java.util.UUID;

@Tag(name = "Ambulance Management", description = "Ambulance management services")
@RestController
@RequestMapping("/ambulances")
@RequiredArgsConstructor
class AmbulanceResource {
	
	private final AmbulanceService ambulanceService;
	
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ApiPayload.class)),
					description = "Find all ambulances"),
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
	ApiPayload<List<AmbulanceResponse>> findAllAmbulances() {
		return ApiPayload.ofSuccess(this.ambulanceService.findAllAmbulances());
	}
	
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ApiPayload.class)),
					description = "Find a specific ambulance by id"),
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
	ApiPayload<AmbulanceResponse> findAmbulanceById(@PathVariable UUID id) {
		return ApiPayload.ofSuccess(this.ambulanceService.findAmbulanceById(id));
	}
	
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "201",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ApiPayload.class)),
					description = "Create new ambulance"),
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
	ApiPayload<AmbulanceResponse> createAmbulance(@RequestBody AmbulanceRequest ambulanceRequest) {
		return ApiPayload.ofSuccess(this.ambulanceService.createAmbulance(ambulanceRequest));
	}
	
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ApiPayload.class)),
					description = "Update an existing ambulance"),
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
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	ApiPayload<AmbulanceResponse> updateAmbulance(@PathVariable UUID id, @RequestBody AmbulanceRequest ambulanceRequest) {
		return ApiPayload.ofSuccess(this.ambulanceService.updateAmbulance(id, ambulanceRequest));
	}
	
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ApiPayload.class)),
					description = "Delete an existing ambulance by id"),
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
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	ApiPayload<Void> deleteAmbulance(@PathVariable UUID id) {
		this.ambulanceService.deleteAmbulance(id);
		return ApiPayload.ofSuccess(null);
	}
	
}



