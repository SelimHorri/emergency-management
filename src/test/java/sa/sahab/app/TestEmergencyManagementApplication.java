package sa.sahab.app;

import org.springframework.boot.SpringApplication;

public class TestEmergencyManagementApplication {
	
	public static void main(String[] args) {
		SpringApplication.from(EmergencyManagementApplication::main)
				.with(TestcontainersConfiguration.class)
				.run(args);
	}
	
}



