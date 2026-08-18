package patientsreport.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import patientsreport.PostgresContainerTest;
import patientsreport.repository.PatientRepository;

@AutoConfigureMockMvc
class PatientReportControllerTest extends PostgresContainerTest
{
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Test
	void getPatientsReport_withPatientsInDatabase_returnsPdf() throws Exception
	{
		mockMvc.perform(get("/api/reports/patients").param("format", "pdf")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_PDF));
	}
	
	@Test
	void getPatientsReport_withUnsupportedFormat_returnsBadRequest() throws Exception
	{
		mockMvc.perform(get("/api/reports/patients").param("format", "xml")).andExpect(status().isBadRequest());
	}
	
	@Test
	@Transactional
	void getPatientsReport_withNoPatients_returnsOkWithMessage() throws Exception
	{
		patientRepository.deleteAll();
		mockMvc.perform(get("/api/reports/patients").param("format", "pdf")).andExpect(status().isOk()).andExpect(content().string("no patients"));
	}
}