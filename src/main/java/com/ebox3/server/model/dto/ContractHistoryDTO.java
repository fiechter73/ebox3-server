package com.ebox3.server.model.dto;

import java.util.Date;

public class ContractHistoryDTO {

	private Long id;

	private String contractDetails;

	private String objekteDetails;

	private Double totalSumPriceBrutto;

	private String statusText;

	private Date startDate;

	private Date endDate;

	private Date terminateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContractDetails() {
		return contractDetails;
	}

	public void setContractDetails(String contractDetails) {
		this.contractDetails = contractDetails;
	}

	public String getObjekteDetails() {
		return objekteDetails;
	}

	public void setObjekteDetails(String objekteDetails) {
		this.objekteDetails = objekteDetails;
	}

	public Double getTotalSumPriceBrutto() {
		return totalSumPriceBrutto;
	}

	public void setTotalSumPriceBrutto(Double totalSumPriceBrutto) {
		this.totalSumPriceBrutto = totalSumPriceBrutto;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getTerminateDate() {
		return terminateDate;
	}

	public void setTerminateDate(Date terminateDate) {
		this.terminateDate = terminateDate;
	}

	@Override
	public String toString() {
		return "ContractHistoryDTO [id=" + id + ", contractDetails=" + contractDetails + ", objekteDetails="
				+ objekteDetails + ", totalSumPriceBrutto=" + totalSumPriceBrutto + ", statusText=" + statusText
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", terminateDate=" + terminateDate + "]";
	}

}
