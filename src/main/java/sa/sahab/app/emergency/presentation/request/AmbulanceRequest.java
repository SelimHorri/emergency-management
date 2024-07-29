package sa.sahab.app.emergency.presentation.request;

import sa.sahab.app.emergency.domain.entity.Ambulance.AmbulanceStatus;

public record AmbulanceRequest(String licensePlate,
							   AmbulanceStatus status,
							   String location) {}



