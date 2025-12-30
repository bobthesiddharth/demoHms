package com.HMSApp.Hospital.Management.System.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.management.AttributeNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HMSApp.Hospital.Management.System.entity.Patient;
import com.HMSApp.Hospital.Management.System.repository.PatientRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class PatientController {
	

	private PatientRepository patientRepository;
	
	public PatientController (PatientRepository patientRepository) {
		super() ;
		this.patientRepository = patientRepository;
	}
	
	@GetMapping("/patients-list")
	public List<Patient> getAllPatients(){
		return patientRepository.findAll();
	}
	
	
	@PostMapping("/patients-list")
	public Patient createPatient(@RequestBody Patient patient) {
		return patientRepository.save(patient);
	}
	
	@DeleteMapping("/patients-list/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePatient(@PathVariable Integer id) throws AttributeNotFoundException {
        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new AttributeNotFoundException("Patient not found with id: " + id));
        
        patientRepository.delete(patient);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        
        return ResponseEntity.ok(response);
    }
	
	@GetMapping("/patients-list/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable int id) throws AttributeNotFoundException {
	    Patient patient = patientRepository.findById(id)
	        .orElseThrow(() -> new AttributeNotFoundException("Patient not found with the id: " + id));
	    return ResponseEntity.ok(patient);
	}
	
	
	@PutMapping("/patients-list/{id}")
	public ResponseEntity<Patient> updatePatientById(@PathVariable int id, @RequestBody Patient patientDetails) throws AttributeNotFoundException {
	    Patient existingPatient = patientRepository.findById(id)
	        .orElseThrow(() -> new AttributeNotFoundException("Patient not found with id: " + id));

	    existingPatient.setAge(patientDetails.getAge());
	    existingPatient.setName(patientDetails.getName());
	    existingPatient.setBlood(patientDetails.getBlood());
	    existingPatient.setDose(patientDetails.getDose());
	    existingPatient.setFees(patientDetails.getFees());
	    existingPatient.setPrescription(patientDetails.getPrescription());
 	    existingPatient.setUrgency(patientDetails.getUrgency());


	    Patient savedPatient = patientRepository.save(existingPatient);
	    return ResponseEntity.ok(savedPatient);
	}
}
