package com.ebox3.server.service;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;

public interface QrcodeService {

	public void generateQrBillCode(final Long idModul, final Long idCustomer, final Double amount,
			final String uMessage, final String mode, final String modul, final HttpServletResponse response)
			throws IOException;

	public void generateElectricRentDepositQrBillCode(final Long id, final String mode,
			final HttpServletResponse response) throws IOException;

}
