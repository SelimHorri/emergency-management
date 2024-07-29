package sa.sahab.app.emergency.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sa.sahab.app.emergency.presentation.request.EmergencyCallRequest;
import sa.sahab.app.emergency.presentation.response.ApiPayload;
import sa.sahab.app.emergency.presentation.response.EmergencyCallResponse;
import sa.sahab.app.emergency.service.EmergencyCallService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/emergency-calls")
@RequiredArgsConstructor
class EmergencyCallResource {
	
	private final EmergencyCallService emergencyCallService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	ApiPayload<List<EmergencyCallResponse>> findAllEmergencyCalls() {
		return ApiPayload.ofSuccess(this.emergencyCallService.findAllEmergencyCalls());
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	ApiPayload<EmergencyCallResponse> findEmergencyCallById(@PathVariable UUID id) {
		return ApiPayload.ofSuccess(this.emergencyCallService.findEmergencyCallById(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	ApiPayload<EmergencyCallResponse> createEmergencyCall(@RequestBody @Valid EmergencyCallRequest emergencyCallRequest) {
		return ApiPayload.ofSuccess(this.emergencyCallService.createEmergencyCall(emergencyCallRequest));
	}
	
}



