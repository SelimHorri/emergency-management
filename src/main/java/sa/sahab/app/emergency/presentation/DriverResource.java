package sa.sahab.app.emergency.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sa.sahab.app.emergency.presentation.request.DriverRequest;
import sa.sahab.app.emergency.presentation.response.ApiPayload;
import sa.sahab.app.emergency.presentation.response.DriverResponse;
import sa.sahab.app.emergency.service.DriverService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
class DriverResource {
	
	private final DriverService driverService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	ApiPayload<List<DriverResponse>> findAllDrivers() {
		return ApiPayload.ofSuccess(this.driverService.findAllDrivers());
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	ApiPayload<DriverResponse> findDriverById(@PathVariable UUID id) {
		return ApiPayload.ofSuccess(this.driverService.findDriverById(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	ApiPayload<DriverResponse> createDriver(@RequestBody DriverRequest driverRequest) {
		return ApiPayload.ofSuccess(this.driverService.createDriver(driverRequest));
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	ApiPayload<DriverResponse> updateDriver(@PathVariable UUID id, @RequestBody DriverRequest driverRequest) {
		return ApiPayload.ofSuccess(this.driverService.updateDriver(id, driverRequest));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	ApiPayload<Void> deleteDriver(@PathVariable UUID id) {
		this.driverService.deleteDriver(id);
		return ApiPayload.ofSuccess(null);
	}
	
}



