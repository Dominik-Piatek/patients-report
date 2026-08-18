package patientsreport.service;

import com.stimulsoft.base.exception.StiException;
import com.stimulsoft.base.serializing.StiDeserializationException;
import com.stimulsoft.report.StiReport;
import com.stimulsoft.report.StiExportManager;
import com.stimulsoft.report.StiSerializeManager;
import com.stimulsoft.report.dictionary.databases.StiJsonDatabase;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import tools.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import patientsreport.entity.Patient;
import patientsreport.exception.ReportGenerationException;

@Service
public class PatientReportService
{
	private static final String PATIENTS_TEMPLATE_PATH = "reports/patients-report.mrt";
	
	public byte[] generatePatientsPdf(List<Patient> patients)
	{
		try
		{
			File templateFile = new ClassPathResource(PATIENTS_TEMPLATE_PATH).getFile();
			StiReport report = StiSerializeManager.deserializeReport(templateFile);
			
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonData = objectMapper.writeValueAsString(Map.of("patients", patients));
			
			report.getDictionary().getDatabases().clear();
			
			StiJsonDatabase jsonDatabase = new StiJsonDatabase("patients", new ByteArrayInputStream(jsonData.getBytes(StandardCharsets.UTF_8)));
			report.getDictionary().getDatabases().add(jsonDatabase);
			report.getDictionary().synchronize();
			
			report.render();
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			StiExportManager.exportPdf(report, outputStream);
			
			return outputStream.toByteArray();
		}
		
		catch (IOException e)
		{
			throw new ReportGenerationException("failed to load report template", e);
		}
		
		catch (StiDeserializationException | SAXException e)
		{
			throw new ReportGenerationException("failed to generate report from template", e);
		}
		
		catch (StiException e)
		{
			throw new ReportGenerationException("failed to export report to pdf", e);
		}
	}
}