package sa.sahab.app.emergency.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "ambulances")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Ambulance {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	private String licensePlate;
	
	@Enumerated(EnumType.STRING)
	private AmbulanceStatus status;
	
	private String location;
	
	@OneToMany(mappedBy = "ambulance")
	private Set<EmergencyCall> emergencyCalls;
	
	public enum AmbulanceStatus {
		AVAILABLE, BUSY, OUT_OF_SERVICE
	}
	
}



