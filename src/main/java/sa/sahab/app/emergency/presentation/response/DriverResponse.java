package sa.sahab.app.emergency.presentation.response;

import sa.sahab.app.emergency.domain.entity.Driver;

import java.util.UUID;

public record DriverResponse(UUID id,
							 String name,
							 String licenseNumber,
							 String phoneNumber) {
	
	public static DriverResponse from(Driver driver) {
		return new DriverResponse(
				driver.getId(),
				driver.getName(),
				driver.getLicenseNumber(),
				driver.getPhoneNumber());
	}
	
}



