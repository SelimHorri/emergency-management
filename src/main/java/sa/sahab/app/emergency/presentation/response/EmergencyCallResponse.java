package sa.sahab.app.emergency.presentation.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record EmergencyCallResponse(UUID id,
									String callerName,
									String callerPhoneNumber,
									String locationAddress,
									String description,
									LocalDateTime callTime,
									AmbulanceResponse ambulance,
									DriverResponse driver) {}



