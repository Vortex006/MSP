package com.vortex.msp.Controller;

import com.vortex.msp.Entity.Patient;
import com.vortex.msp.Entity.REQ.REQ_GetPatientInfo;
import com.vortex.msp.Entity.RESP.RESP_GetPatientInfo;
import com.vortex.msp.Service.PatientService;
import com.vortex.msp.Utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
@Tag(name = "患者模块")
public class PatientController {

    private final PatientService patientService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @Operation(summary = "新增患者信息")
    @PostMapping
    public Result savePatient(@RequestBody Patient patient) {
        boolean isSave = patientService.savePatient(patient);
        return isSave ? Result.SUCCEED() : Result.FAILED();
    }

    @Operation(summary = "更新患者信息")
    @PutMapping
    public Result updatePatient(@RequestBody Patient patient) {
        boolean isUpdate = patientService.updatePatient(patient);
        return isUpdate ? Result.SUCCEED() : Result.FAILED();
    }

    @Operation(summary = "根据患者ID删除患者信息")
    @Parameters(@Parameter(name = "patientId", description = "患者ID", in = ParameterIn.PATH))
    @DeleteMapping("/{patientId}")
    public Result deletePatient(@PathVariable("patientId") Integer patientId) {
        boolean isDelete = patientService.deletePatient(patientId);
        return isDelete ? Result.SUCCEED() : Result.FAILED();
    }


    @Operation(summary = "根据患者ID获取患者信息")
    @Parameters(@Parameter(name = "patientId", description = "患者ID", in = ParameterIn.PATH))
    @GetMapping("/id/{patientId}")
    public Result getPatient(@PathVariable("patientId") Integer patientId) {
        Patient patient = patientService.getPatient(patientId);
        return patient != null ? Result.SUCCEED(patient) : Result.FAILED();
    }

    @Operation(summary = "根据身份证号获取患者信息")
    @Parameters(@Parameter(name = "certNo", description = "身份证号", in = ParameterIn.PATH))
    @GetMapping("/certNo/{certNo}")
    public Result getPatientByCertNo(@PathVariable("certNo") String certNo) {
        Patient patient = patientService.getPatientByCertNo(certNo);
        return patient != null ? Result.SUCCEED(patient) : Result.FAILED();
    }

    @Operation(summary = "获取所有患者信息")
    @GetMapping("/list")
    public Result listPatients() {
        List<Patient> patients = patientService.listPatients();
        return Result.SUCCEED(patients);
    }

    @Operation(summary = "分页获取患者信息")
    @Parameters({
            @Parameter(name = "pageSize", description = "每页条数", in = ParameterIn.PATH),
            @Parameter(name = "index", description = "当前页码", in = ParameterIn.PATH)
    })
    @GetMapping("/limit/{pageSize}/{index}")
    public Result limitPatients(@PathVariable("pageSize") Integer pageSize, @PathVariable("index") Integer index) {
        List<Patient> patients = patientService.limitPatients(pageSize, index);
        return Result.SUCCEED(patients);
    }

    @Operation(summary = "终端获取患者信息")
    @PostMapping("/terminal/GetPatientInfo")
    public Result getPatientInfoByTerminal(@Validated @RequestBody REQ_GetPatientInfo request) {
        Integer cardType = Integer.parseInt(request.getCardType());
        String cardNo = request.getCardNo();
        RESP_GetPatientInfo patientInfo = patientService.getPatientInfoByTerminal(cardType, cardNo);
        if (ObjectUtils.isEmpty(patientInfo)) {
            return Result.FAILED("未查询到有效的患者信息");
        } else {
            patientInfo.setTraceId(request.getTraceId());
            patientInfo.setTime(request.getTime());
            return Result.SUCCEED(patientInfo);
        }
    }

}
