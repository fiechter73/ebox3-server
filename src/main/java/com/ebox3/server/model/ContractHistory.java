package com.ebox3.server.model;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contracthistory")
public class ContractHistory extends AuditModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2686119939804371598L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "contractDetails")
	private String contractDetails;

	@Column(name = "objekteDetails")
	private String objekteDetails;

	@Column(name = "totalSumPriceBrutto")
	private Double totalSumPriceBrutto;

	@Column(name = "statusText")
	private String statusText;

	@Column(name = "startDate")
	private Date startDate;

	@Column(name = "endDate")
	private Date endDate;

	@Column(name = "terminateDate")
	private Date terminateDate;

	public ContractHistory() {
	}

	public ContractHistory(String customerDetails, String contractDetails, String objekteDetails,
			Double totalSumPriceBrutto, String statusText, Date startDate, Date endDate, Date terminateDate) {

		this.contractDetails = contractDetails;
		this.objekteDetails = objekteDetails;
		this.totalSumPriceBrutto = totalSumPriceBrutto;
		this.startDate = startDate;
		this.endDate = endDate;
		this.terminateDate = terminateDate;
	}

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

	public Contract getContract() {
		return this.contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	@Override
	public String toString() {
		return "HistoryObject [id=" + id + "," + " contractdetails=" + contractDetails + "," + " objekteDetails="
				+ objekteDetails + "," + " statusText=" + statusText + ", startDate=" + startDate + "," + " endDate="
				+ endDate + "," + " terminateDate=" + terminateDate + ", totalPriceBrutto=" + totalSumPriceBrutto + "]";
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contractId_FK", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Contract contract;
}
