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
import sa.sahab.app.emergency.presentation.request.DriverRequest;
import sa.sahab.app.emergency.presentation.response.ApiPayload;
import sa.sahab.app.emergency.presentation.response.DriverResponse;
import sa.sahab.app.emergency.application.DriverService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Driver Management", description = "Driver management services")
@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
class DriverResource {
	
	private final DriverService driverService;
	
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ApiPayload.class)),
					description = "Find all drivers"),
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
	ApiPayload<List<DriverResponse>> findAllDrivers() {
		return ApiPayload.ofSuccess(this.driverService.findAllDrivers());
	}
	
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ApiPayload.class)),
					description = "Find a specific driver by id"),
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
	ApiPayload<DriverResponse> findDriverById(@PathVariable UUID id) {
		return ApiPayload.ofSuccess(this.driverService.findDriverById(id));
	}
	
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "201",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ApiPayload.class)),
					description = "Create new driver"),
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
	ApiPayload<DriverResponse> createDriver(@RequestBody DriverRequest driverRequest) {
		return ApiPayload.ofSuccess(this.driverService.createDriver(driverRequest));
	}
	
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ApiPayload.class)),
					description = "Update an existing driver"),
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
	ApiPayload<DriverResponse> updateDriver(@PathVariable UUID id, @RequestBody DriverRequest driverRequest) {
		return ApiPayload.ofSuccess(this.driverService.updateDriver(id, driverRequest));
	}
	
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ApiPayload.class)),
					description = "Delete an existing driver by id"),
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
	ApiPayload<Void> deleteDriver(@PathVariable UUID id) {
		this.driverService.deleteDriver(id);
		return ApiPayload.ofSuccess(null);
	}
	
}



