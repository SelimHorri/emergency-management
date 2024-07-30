package sa.sahab.app.emergency.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "drivers")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Driver {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	private String name;
	
	private String licenseNumber;
	
	private String phoneNumber;
	
	@OneToMany(mappedBy = "driver")
	private Set<EmergencyCall> emergencyCalls;
	
}



