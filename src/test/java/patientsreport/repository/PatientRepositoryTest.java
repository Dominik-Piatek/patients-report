package patientsreport.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.Collator;
import java.util.List;
import java.util.Locale;
import static org.assertj.core.api.Assertions.assertThat;

import patientsreport.PostgresContainerTest;
import patientsreport.entity.Patient;

class PatientRepositoryTest extends PostgresContainerTest
{
	@Autowired
	private PatientRepository patientRepository;
	
	@Test
	void findAllByOrderByLastNameAscFirstNameAsc_returnsPatientsSortedCorrectly()
	{
		List<Patient> patients = patientRepository.findAllByOrderByLastNameAscFirstNameAsc();
		assertThat(patients).isNotEmpty();
		
		Collator polishCollator = Collator.getInstance(new Locale("pl", "PL"));
		
		for (int i = 0; i < patients.size() - 1; i++)
		{
			Patient current = patients.get(i);
			Patient next = patients.get(i + 1);
			
			int lastNameComparison = polishCollator.compare(current.getLastName(), next.getLastName());
			assertThat(lastNameComparison <= 0).isTrue();
			
			if (lastNameComparison == 0)
				assertThat(polishCollator.compare(current.getFirstName(), next.getFirstName()) <= 0).isTrue();
		}
	}
}