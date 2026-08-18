package patientsreport.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Data @NoArgsConstructor @AllArgsConstructor
public class Patient
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;
	
	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;
	
	@Column(name = "date_of_birth", nullable = false)
	private LocalDate dateOfBirth;
	
	@Column(name = "gender", length = 20)
	private String gender;
	
	@Column(name = "pesel", length = 11)
	private String pesel;
	
	@Column(name = "city", length = 100)
	private String city;
	
	@Column(name = "registration_date", nullable = false)
	private LocalDate registrationDate;
}