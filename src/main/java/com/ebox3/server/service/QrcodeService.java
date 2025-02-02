package com.ebox3.server.service;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;

public interface QrcodeService {
	
    public void generateElectricQrBillCode(final Long idElectricMeter, final Long id, final Double amount, final String uMessage, final String mode,  final HttpServletResponse response) 
    		throws IOException;
    
    public void generateElectricRentDepositQrBillCode(final Long id, final String mode, final HttpServletResponse response) throws IOException;
    

}
