package com.zyj.msp.Controller;

import com.zyj.msp.Entity.Patient;
import com.zyj.msp.Service.PatientService;
import com.zyj.msp.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public Result savePatient(@RequestBody Patient patient) {
        boolean isSave = patientService.savePatient(patient);
        return isSave ? Result.SUCCEED() : Result.FAILED();
    }

    @PutMapping
    public Result updatePatient(@RequestBody Patient patient) {
        boolean isUpdate = patientService.updatePatient(patient);
        return isUpdate ? Result.SUCCEED() : Result.FAILED();
    }

    @DeleteMapping("/{patientId}")
    public Result deletePatient(@PathVariable("patientId") Integer patientId) {
        boolean isDelete = patientService.deletePatient(patientId);
        return isDelete ? Result.SUCCEED() : Result.FAILED();
    }

    @GetMapping("/id/{patientId}")
    public Result getPatient(@PathVariable("patientId") Integer patientId) {
        Patient patient = patientService.getPatient(patientId);
        return patient != null ? Result.SUCCEED(patient) : Result.FAILED();
    }

    @GetMapping("/certNo/{certNo}")
    public Result getPatientByCertNo(@PathVariable("certNo") String certNo) {
        Patient patient = patientService.getPatientByCertNo(certNo);
        return patient != null ? Result.SUCCEED(patient) : Result.FAILED();
    }

    @GetMapping("/list")
    public Result listPatients() {
        List<Patient> patients = patientService.listPatients();
        return Result.SUCCEED(patients);
    }

    @GetMapping("/limit/{pageSize}/{index}")
    public Result limitPatients(@PathVariable("pageSize") Integer pageSize, @PathVariable("index") Integer index) {
        List<Patient> patients = patientService.limitPatients(pageSize, index);
        return Result.SUCCEED(patients);
    }

}
