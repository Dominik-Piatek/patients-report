package patientsreport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

import patientsreport.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>
{
	@Query(value = "SELECT * FROM patients ORDER BY last_name COLLATE \"pl-PL-x-icu\", first_name COLLATE \"pl-PL-x-icu\"", nativeQuery = true)
	List<Patient> findAllByOrderByLastNameAscFirstNameAsc();
}