package com.vortex.msp.Controller;

import com.vortex.msp.Service.EMICardService;
import com.vortex.msp.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emic")
public class EMICardController {

    private final EMICardService emiCardService;

    @Autowired
    public EMICardController(EMICardService emiCardService) {
        this.emiCardService = emiCardService;
    }


    @GetMapping("/text")
    public Result getEMICardNo() {
        return Result.SUCCEED();
    }

    @GetMapping("/qr")
    public Result getEMICardQr() {
        String base64 = emiCardService.getEMICardQr("411324200312062237");
        return Result.SUCCEED(base64);
    }


}
