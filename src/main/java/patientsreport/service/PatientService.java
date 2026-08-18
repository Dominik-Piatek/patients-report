package patientsreport.service;

import org.springframework.stereotype.Service;
import java.util.List;

import patientsreport.entity.Patient;
import patientsreport.repository.PatientRepository;

@Service
public class PatientService
{
	private final PatientRepository patientRepository;
	
	public PatientService(PatientRepository patientRepository)
	{
		this.patientRepository = patientRepository;
	}
	
	public List<Patient> getAllPatientsSorted()
	{
		return patientRepository.findAllByOrderByLastNameAscFirstNameAsc();
	}
}