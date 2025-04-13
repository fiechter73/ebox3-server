package com.ebox3.server.model.dto;

import java.util.Date;

public class AdditionalCostsDTO {

	private Long id;
	private String rechnungsart;
	private String statusText;
	private Date billingDate;
	private String title;
	private String text;
	private String positiontext1;
	private Double positionprice1;
	private String positiontext2;
	private Double positionprice2;
	private String positiontext3;
	private Double positionprice3;
	private String positiontext4;
	private Double positionprice4;
	private String positiontext5;
	private Double positionprice5;
	private Double mwstPrice;
	private Double mwstSatz;
	private Double sumBruttoPrice;
	private Date createdDate;
	private Date updatedDate;
	private Long contractId;
	private Date contractStartDate;
	private Date contractEndDate;
	private String contractStatusText;
	private String contractQuitPeriode;
	private Long customerId;
	private String customerAndrede;
	private String customerVornameName;
	private String customerStrasse;
	private String customerOrtPlz;
	private String qrReferenceCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRechnungsart() {
		return rechnungsart;
	}

	public void setRechnungsart(String rechnungsart) {
		this.rechnungsart = rechnungsart;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public String getPositiontext1() {
		return positiontext1;
	}

	public void setPositiontext1(String positiontext1) {
		this.positiontext1 = positiontext1;
	}

	public Double getPositionprice1() {
		return positionprice1;
	}

	public void setPositionprice1(Double positionprice1) {
		this.positionprice1 = positionprice1;
	}

	public String getPositiontext2() {
		return positiontext2;
	}

	public void setPositiontext2(String positiontext2) {
		this.positiontext2 = positiontext2;
	}

	public Double getPositionprice2() {
		return positionprice2;
	}

	public void setPositionprice2(Double positionprice2) {
		this.positionprice2 = positionprice2;
	}

	public String getPositiontext3() {
		return positiontext3;
	}

	public void setPositiontext3(String positiontext3) {
		this.positiontext3 = positiontext3;
	}

	public Double getPositionprice3() {
		return positionprice3;
	}

	public void setPositionprice3(Double positionprice3) {
		this.positionprice3 = positionprice3;
	}

	public String getPositiontext4() {
		return positiontext4;
	}

	public void setPositiontext4(String positiontext4) {
		this.positiontext4 = positiontext4;
	}

	public Double getPositionprice4() {
		return positionprice4;
	}

	public void setPositionprice4(Double positionprice4) {
		this.positionprice4 = positionprice4;
	}

	public String getPositiontext5() {
		return positiontext5;
	}

	public void setPositiontext5(String positiontext5) {
		this.positiontext5 = positiontext5;
	}

	public Double getPositionprice5() {
		return positionprice5;
	}

	public void setPositionprice5(Double positionprice5) {
		this.positionprice5 = positionprice5;
	}

	public Double getMwstPrice() {
		return mwstPrice;
	}

	public void setMwstPrice(Double mwstPrice) {
		this.mwstPrice = mwstPrice;
	}

	public Double getSumBruttoPrice() {
		return sumBruttoPrice;
	}

	public void setSumBruttoPrice(Double sumBruttoPrice) {
		this.sumBruttoPrice = sumBruttoPrice;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public Date getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public Date getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public String getContractStatusText() {
		return contractStatusText;
	}

	public void setContractStatusText(String contractStatusText) {
		this.contractStatusText = contractStatusText;
	}

	public String getContractQuitPeriode() {
		return contractQuitPeriode;
	}

	public void setContractQuitPeriode(String contractQuitPeriode) {
		this.contractQuitPeriode = contractQuitPeriode;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerAndrede() {
		return customerAndrede;
	}

	public void setCustomerAndrede(String customerAndrede) {
		this.customerAndrede = customerAndrede;
	}

	public String getCustomerVornameName() {
		return customerVornameName;
	}

	public void setCustomerVornameName(String customerVornameName) {
		this.customerVornameName = customerVornameName;
	}

	public String getCustomerStrasse() {
		return customerStrasse;
	}

	public void setCustomerStrasse(String customerStrasse) {
		this.customerStrasse = customerStrasse;
	}

	public String getCustomerOrtPlz() {
		return customerOrtPlz;
	}

	public void setCustomerOrtPlz(String customerOrtPlz) {
		this.customerOrtPlz = customerOrtPlz;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Double getMwstSatz() {
		return mwstSatz;
	}

	public void setMwstSatz(Double mwstSatz) {
		this.mwstSatz = mwstSatz;
	}

	public Date getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}

	public String getQrReferenceCode() {
		return qrReferenceCode;
	}

	public void setQrReferenceCode(String qrReferenceCode) {
		this.qrReferenceCode = qrReferenceCode;
	}

	@Override
	public String toString() {
		return "AdditionalCostsDTO [id=" + id + ", rechnungsart=" + rechnungsart + ", statusText=" + statusText
				+ ", billingDate=" + billingDate + ", title=" + title + ", text=" + text + ", positiontext1="
				+ positiontext1 + ", positionprice1=" + positionprice1 + ", positiontext2=" + positiontext2
				+ ", positionprice2=" + positionprice2 + ", positiontext3=" + positiontext3 + ", positionprice3="
				+ positionprice3 + ", positiontext4=" + positiontext4 + ", positionprice4=" + positionprice4
				+ ", positiontext5=" + positiontext5 + ", positionprice5=" + positionprice5 + ", mwstPrice=" + mwstPrice
				+ ", mwstSatz=" + mwstSatz + ", sumBruttoPrice=" + sumBruttoPrice + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + ", contractId=" + contractId + ", contractStartDate="
				+ contractStartDate + ", contractEndDate=" + contractEndDate + ", contractStatusText="
				+ contractStatusText + ", contractQuitPeriode=" + contractQuitPeriode + ", customerId=" + customerId
				+ ", customerAndrede=" + customerAndrede + ", customerVornameName=" + customerVornameName
				+ ", customerStrasse=" + customerStrasse + ", customerOrtPlz=" + customerOrtPlz + ", qrReferenceCode="
				+ qrReferenceCode + "]";
	}


}
