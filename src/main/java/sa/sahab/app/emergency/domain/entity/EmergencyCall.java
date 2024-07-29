package sa.sahab.app.emergency.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "emergency_calls")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EmergencyCall {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(length = 50, nullable = false)
	private String callerName;
	
	@Column(length = 10, nullable = false)
	private String callerPhoneNumber;
	
	private String locationAddress;
	
	private String description;
	
	@Column(nullable = false)
	private LocalDateTime callTime;
	
	@ManyToOne
	@JoinColumn(name = "ambulance_id")
	private Ambulance ambulance;
	
	@ManyToOne
	@JoinColumn(name = "driver_id")
	private Driver driver;
	
}



