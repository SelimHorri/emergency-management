package sa.sahab.app.emergency.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sa.sahab.app.emergency.presentation.request.AmbulanceRequest;
import sa.sahab.app.emergency.presentation.response.AmbulanceResponse;
import sa.sahab.app.emergency.presentation.response.ApiPayload;
import sa.sahab.app.emergency.service.AmbulanceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ambulances")
@RequiredArgsConstructor
class AmbulanceResource {
	
	private final AmbulanceService ambulanceService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	ApiPayload<List<AmbulanceResponse>> findAllAmbulances() {
		return ApiPayload.ofSuccess(this.ambulanceService.findAllAmbulances());
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	ApiPayload<AmbulanceResponse> findAmbulanceById(@PathVariable UUID id) {
		return ApiPayload.ofSuccess(this.ambulanceService.findAmbulanceById(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	ApiPayload<AmbulanceResponse> createAmbulance(@RequestBody AmbulanceRequest ambulanceRequest) {
		return ApiPayload.ofSuccess(this.ambulanceService.createAmbulance(ambulanceRequest));
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	ApiPayload<AmbulanceResponse> updateAmbulance(@PathVariable UUID id, @RequestBody AmbulanceRequest ambulanceRequest) {
		return ApiPayload.ofSuccess(this.ambulanceService.updateAmbulance(id, ambulanceRequest));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	ApiPayload<Void> deleteAmbulance(@PathVariable UUID id) {
		this.ambulanceService.deleteAmbulance(id);
		return ApiPayload.ofSuccess(null);
	}
	
}



