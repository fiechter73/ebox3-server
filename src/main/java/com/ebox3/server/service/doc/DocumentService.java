package com.ebox3.server.service.doc;

import java.io.IOException;
import java.util.Date;

import jakarta.servlet.http.HttpServletResponse;

public interface DocumentService {

	public void downloadContractWordResource(final Long id, final HttpServletResponse response) throws IOException;

	public void downloadCustomerLetterWordResource(final String type, final Long id, final HttpServletResponse response)
			throws IOException;

	public void downloadCustomerWordResource(final String type, final String id, final HttpServletResponse response)
			throws IOException;

	public void downloadElectricBillWordResource(final String ids, final HttpServletResponse response)
			throws IOException;

	public void downloadCustomerSalesListWordResource(final String jahr, final HttpServletResponse response)
			throws IOException;
	
	public void downloadCustomerSalesListExcelResource(final String jahr, final HttpServletResponse response)
			throws IOException;

	public void downloadCustomerBillWordResource(final String jahr, HttpServletResponse response) throws IOException;

	public void downloadCustomerBillExcelResource(final String jahr, HttpServletResponse response) throws IOException;

	public void downloaElectricOverwievBillingWordResource(final String jahr, final String status,
			final HttpServletResponse response) throws IOException;

	public void downloadElectricOverviewBillingExcelResource(final String jahr, final String status,
			final HttpServletResponse response) throws IOException;

	public void downloadBillingLetterWordResource(final Long id, final HttpServletResponse response) throws IOException;

	public void downloadMwstWordResource(final String year, final Date from, final Date to,
			final HttpServletResponse response) throws IOException;

	public void downloadMwstExcelResource(final String year, final Date from, final Date to,
			final HttpServletResponse response) throws IOException;
}
