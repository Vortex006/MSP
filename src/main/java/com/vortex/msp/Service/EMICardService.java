package com.vortex.msp.Service;

public interface EMICardService {

    String getEMICardNo(String certNo);

    String getEMICardQr(String certNo);
}
