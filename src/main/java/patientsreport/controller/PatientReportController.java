package patientsreport.controller;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import patientsreport.entity.Patient;
import patientsreport.service.PatientReportService;
import patientsreport.service.PatientService;

@RestController
@RequestMapping("/api/reports")
public class PatientReportController
{
	private final PatientService patientService;
	private final PatientReportService patientReportService;
	
	public PatientReportController(PatientService patientService, PatientReportService patientReportService)
	{
		this.patientService = patientService;
		this.patientReportService = patientReportService;
	}
	
	@GetMapping("/patients")
	public ResponseEntity<?> getPatientsReport(@RequestParam(defaultValue = "pdf") String format)
	{
		List<Patient> patients = patientService.getAllPatientsSorted();
		
		if (patients.isEmpty())
		{
			return ResponseEntity.ok("no patients");
		}
		
		switch (format)
		{
			case "pdf":
				byte[] pdfBytes = patientReportService.generatePatientsPdf(patients);
				
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_PDF);
				headers.setContentDisposition(ContentDisposition.attachment().filename("spis-pacjentow.pdf").build());
				
				return ResponseEntity.ok().headers(headers).body(pdfBytes);
				
			default:
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("unsupported format");
		}
	}
}