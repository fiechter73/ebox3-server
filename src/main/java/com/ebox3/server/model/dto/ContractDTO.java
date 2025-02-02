package com.ebox3.server.model.dto;

import java.util.Date;
import java.util.List;

public class ContractDTO {

	private Long id;
	private Date startDate;
	private Date endDate;
	private Date terminateDate;
	private Date checkOutDate;
	private boolean active;
	private String statusText;
	private Long countedEbox;
	private Double sumEbox;
	private String frist;
	private Date fristDate;
	private List<Long> eBoxNrs;
	private List<Long> eBoxIds;
	private Long custId;
	private String custName;
	private String custAnschrift;
	private Double guthaben;
	private String bemerkungen;
	private List<EboxDTO> eboxs;
	private List<AdditionalCostsDTO> additionalCosts;
	private List<ContractHistoryDTO> contractHistory;
	private List<PaymentDTO> payment;
	private Long minDuration;
	private Date minDurationDate;
	private Double retKaution;
	private String qrReferenceCodeRent;
	private String qrReferenceCodeDeposit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public Long getCountedEbox() {
		return countedEbox;
	}

	public void setCountedEbox(Long countedEbox) {
		this.countedEbox = countedEbox;
	}

	public List<Long> geteBoxNrs() {
		return eBoxNrs;
	}

	public void seteBoxNrs(List<Long> eBoxNrs) {
		this.eBoxNrs = eBoxNrs;
	}

	public List<Long> geteBoxIds() {
		return eBoxIds;
	}

	public void seteBoxIds(List<Long> eBoxIds) {
		this.eBoxIds = eBoxIds;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustAnschrift() {
		return custAnschrift;
	}

	public void setCustAnschrift(String custAnschrift) {
		this.custAnschrift = custAnschrift;
	}

	public Double getGuthaben() {
		return guthaben;
	}

	public void setGuthaben(Double guthaben) {
		this.guthaben = guthaben;
	}

	public Double getRetKaution() {
		return retKaution;
	}

	public void setRetKaution(Double retKaution) {
		this.retKaution = retKaution;
	}

	public String getFrist() {
		return frist;
	}

	public void setFrist(String frist) {
		this.frist = frist;
	}

	public Date getFristDate() {
		return fristDate;
	}

	public void setFristDate(Date frist) {
		this.fristDate = frist;
	}

	public String getBemerkungen() {
		return bemerkungen;
	}

	public void setBemerkungen(String bemerkungen) {
		this.bemerkungen = bemerkungen;
	}

	public List<AdditionalCostsDTO> getAdditionalCosts() {
		return additionalCosts;
	}

	public void setAdditionalCosts(List<AdditionalCostsDTO> additionalCosts) {
		this.additionalCosts = additionalCosts;
	}

	public List<ContractHistoryDTO> getContractHistory() {
		return contractHistory;
	}

	public void setContractHistory(List<ContractHistoryDTO> contractHistory) {
		this.contractHistory = contractHistory;
	}

	public List<PaymentDTO> getPayment() {
		return payment;
	}

	public void setPayment(List<PaymentDTO> payment) {
		this.payment = payment;
	}

	public List<EboxDTO> getEboxs() {
		return eboxs;
	}

	public void setEboxs(List<EboxDTO> eboxs) {
		this.eboxs = eboxs;
	}

	public Double getSumEbox() {
		return sumEbox;
	}

	public void setSumEbox(Double sumEbox) {
		this.sumEbox = sumEbox;
	}

	public Long getMinDuration() {
		return minDuration;
	}

	public void setMinDuration(Long minDuration) {
		this.minDuration = minDuration;
	}

	public Date getMinDurationDate() {
		return minDurationDate;
	}

	public void setMinDurationDate(Date minDurationDate) {
		this.minDurationDate = minDurationDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getQrReferenceCodeRent() {
		return qrReferenceCodeRent;
	}

	public void setQrReferenceCodeRent(String qrReferenceCodeRent) {
		this.qrReferenceCodeRent = qrReferenceCodeRent;
	}

	public String getQrReferenceCodeDeposit() {
		return qrReferenceCodeDeposit;
	}

	public void setQrReferenceCodeDeposit(String qrReferenceCodeDeposit) {
		this.qrReferenceCodeDeposit = qrReferenceCodeDeposit;
	}

	@Override
	public String toString() {
		return "ContractDTO [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", terminateDate="
				+ terminateDate + ", checkOutDate=" + checkOutDate + ", active=" + active + ", statusText=" + statusText
				+ ", countedEbox=" + countedEbox + ", sumEbox=" + sumEbox + ", frist=" + frist + ", fristDate="
				+ fristDate + ", eBoxNrs=" + eBoxNrs + ", eBoxIds=" + eBoxIds + ", custId=" + custId + ", custName="
				+ custName + ", custAnschrift=" + custAnschrift + ", guthaben=" + guthaben + ", bemerkungen="
				+ bemerkungen + ", eboxs=" + eboxs + ", additionalCosts=" + additionalCosts + ", contractHistory="
				+ contractHistory + ", payment=" + payment + ", minDuration=" + minDuration + ", minDurationDate="
				+ minDurationDate + ", retKaution=" + retKaution + ", qrReferenceCodeRent=" + qrReferenceCodeRent
				+ ", qrReferenceCodeDeposit=" + qrReferenceCodeDeposit + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ContractDTO) {
			return ((ContractDTO) obj).id == this.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.id.intValue();
	}

}
