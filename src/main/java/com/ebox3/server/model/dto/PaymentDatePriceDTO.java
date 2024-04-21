package com.ebox3.server.model.dto;

import java.util.Date;


public class PaymentDatePriceDTO {

	private Long id;
	private String jahr;
	private String status;
	private Double aktKautionPrice;
	private Double retourKautionPrice;
	private Double lastKautionPrice;
	private Date aktKautionDate;
	private Date retourKautionDate;
	private Double sumBruttoPrice;
	private String lastName;
	private String customerName;
	private String customerAddress;
	private String customerCompany;
	private String customerCompanyAddress;
	private Date terminateDate;
	private Double aktBruttoPrice;
	private Double lastBruttoPrice;
	private String contractDetails;
	private String boxNumbers;
	private Boolean copyFlag;

	private Double bruttoJanPrice;
	private Double bruttoFebPrice;
	private Double bruttoMarPrice;
	private Double bruttoAprPrice;
	private Double bruttoMaiPrice;
	private Double bruttoJunPrice;
	private Double bruttoJulPrice;
	private Double bruttoAugPrice;
	private Double bruttoSepPrice;
	private Double bruttoOctPrice;
	private Double bruttoNovPrice;
	private Double bruttoDecPrice;
	private Date bruttoJanDate;
	private Date bruttoFebDate;
	private Date bruttoMarDate;
	private Date bruttoAprDate;
	private Date bruttoMaiDate;
	private Date bruttoJunDate;
	private Date bruttoJulDate;
	private Date bruttoAugDate;
	private Date bruttoSepDate;
	private Date bruttoOctDate;
	private Date bruttoNovDate;
	private Date bruttoDecDate;
	private boolean excludeInPaymentList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJahr() {
		return jahr;
	}

	public void setJahr(String jahr) {
		this.jahr = jahr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getAktKautionPrice() {
		return aktKautionPrice;
	}

	public void setAktKautionPrice(Double aktKautionPrice) {
		this.aktKautionPrice = aktKautionPrice;
	}

	public Double getLastKautionPrice() {
		return lastKautionPrice;
	}

	public void setLastKautionPrice(Double lastKautionPrice) {
		this.lastKautionPrice = lastKautionPrice;
	}

	public Date getAktKautionDate() {
		return aktKautionDate;
	}

	public void setAktKautionDate(Date aktKautionDate) {
		this.aktKautionDate = aktKautionDate;
	}

	public Double getSumBruttoPrice() {
		return sumBruttoPrice;
	}

	public void setSumBruttoPrice(Double sumBruttoPrice) {
		this.sumBruttoPrice = sumBruttoPrice;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerCompany() {
		return customerCompany;
	}

	public void setCustomerCompany(String customerCompany) {
		this.customerCompany = customerCompany;
	}

	public String getCustomerCompanyAddress() {
		return customerCompanyAddress;
	}

	public void setCustomerCompanyAddress(String customerCompanyAddress) {
		this.customerCompanyAddress = customerCompanyAddress;
	}

	public Date getTerminateDate() {
		return terminateDate;
	}

	public void setTerminateDate(Date terminateDate) {
		this.terminateDate = terminateDate;
	}

	public Double getAktBruttoPrice() {
		return aktBruttoPrice;
	}

	public void setAktBruttoPrice(Double aktBruttoPrice) {
		this.aktBruttoPrice = aktBruttoPrice;
	}

	public Double getLastBruttoPrice() {
		return lastBruttoPrice;
	}

	public void setLastBruttoPrice(Double lastBruttoPrice) {
		this.lastBruttoPrice = lastBruttoPrice;
	}

	public String getContractDetails() {
		return contractDetails;
	}

	public void setContractDetails(String contractDetails) {
		this.contractDetails = contractDetails;
	}

	public String getBoxNumbers() {
		return boxNumbers;
	}

	public void setBoxNumbers(String boxNumbers) {
		this.boxNumbers = boxNumbers;
	}

	public Boolean getCopyFlag() {
		return copyFlag;
	}

	public void setCopyFlag(Boolean copyFlag) {
		this.copyFlag = copyFlag;
	}

	public Double getBruttoJanPrice() {
		return bruttoJanPrice;
	}

	public void setBruttoJanPrice(Double bruttoJanPrice) {
		this.bruttoJanPrice = bruttoJanPrice;
	}

	public Double getBruttoFebPrice() {
		return bruttoFebPrice;
	}

	public void setBruttoFebPrice(Double bruttoFebPrice) {
		this.bruttoFebPrice = bruttoFebPrice;
	}

	public Double getBruttoMarPrice() {
		return bruttoMarPrice;
	}

	public void setBruttoMarPrice(Double bruttoMarPrice) {
		this.bruttoMarPrice = bruttoMarPrice;
	}

	public Double getBruttoAprPrice() {
		return bruttoAprPrice;
	}

	public void setBruttoAprPrice(Double bruttoAprPrice) {
		this.bruttoAprPrice = bruttoAprPrice;
	}

	public Double getBruttoMaiPrice() {
		return bruttoMaiPrice;
	}

	public void setBruttoMaiPrice(Double bruttoMaiPrice) {
		this.bruttoMaiPrice = bruttoMaiPrice;
	}

	public Double getBruttoJunPrice() {
		return bruttoJunPrice;
	}

	public void setBruttoJunPrice(Double bruttoJunPrice) {
		this.bruttoJunPrice = bruttoJunPrice;
	}

	public Double getBruttoJulPrice() {
		return bruttoJulPrice;
	}

	public void setBruttoJulPrice(Double bruttoJulPrice) {
		this.bruttoJulPrice = bruttoJulPrice;
	}

	public Double getBruttoAugPrice() {
		return bruttoAugPrice;
	}

	public void setBruttoAugPrice(Double bruttoAugPrice) {
		this.bruttoAugPrice = bruttoAugPrice;
	}

	public Double getBruttoSepPrice() {
		return bruttoSepPrice;
	}

	public void setBruttoSepPrice(Double bruttoSepPrice) {
		this.bruttoSepPrice = bruttoSepPrice;
	}

	public Double getBruttoOctPrice() {
		return bruttoOctPrice;
	}

	public void setBruttoOctPrice(Double bruttoOctPrice) {
		this.bruttoOctPrice = bruttoOctPrice;
	}

	public Double getBruttoNovPrice() {
		return bruttoNovPrice;
	}

	public void setBruttoNovPrice(Double bruttoNovPrice) {
		this.bruttoNovPrice = bruttoNovPrice;
	}

	public Double getBruttoDecPrice() {
		return bruttoDecPrice;
	}

	public void setBruttoDecPrice(Double bruttoDecPrice) {
		this.bruttoDecPrice = bruttoDecPrice;
	}

	public Date getBruttoJanDate() {
		return bruttoJanDate;
	}

	public void setBruttoJanDate(Date bruttoJanDate) {
		this.bruttoJanDate = bruttoJanDate;
	}

	public Date getBruttoFebDate() {
		return bruttoFebDate;
	}

	public void setBruttoFebDate(Date bruttoFebDate) {
		this.bruttoFebDate = bruttoFebDate;
	}

	public Date getBruttoMarDate() {
		return bruttoMarDate;
	}

	public void setBruttoMarDate(Date bruttoMarDate) {
		this.bruttoMarDate = bruttoMarDate;
	}

	public Date getBruttoAprDate() {
		return bruttoAprDate;
	}

	public void setBruttoAprDate(Date bruttoAprDate) {
		this.bruttoAprDate = bruttoAprDate;
	}

	public Date getBruttoMaiDate() {
		return bruttoMaiDate;
	}

	public void setBruttoMaiDate(Date bruttoMaiDate) {
		this.bruttoMaiDate = bruttoMaiDate;
	}

	public Date getBruttoJunDate() {
		return bruttoJunDate;
	}

	public void setBruttoJunDate(Date bruttoJunDate) {
		this.bruttoJunDate = bruttoJunDate;
	}

	public Date getBruttoJulDate() {
		return bruttoJulDate;
	}

	public void setBruttoJulDate(Date bruttoJulDate) {
		this.bruttoJulDate = bruttoJulDate;
	}

	public Date getBruttoAugDate() {
		return bruttoAugDate;
	}

	public void setBruttoAugDate(Date bruttoAugDate) {
		this.bruttoAugDate = bruttoAugDate;
	}

	public Date getBruttoSepDate() {
		return bruttoSepDate;
	}

	public void setBruttoSepDate(Date bruttoSepDate) {
		this.bruttoSepDate = bruttoSepDate;
	}

	public Date getBruttoOctDate() {
		return bruttoOctDate;
	}

	public void setBruttoOctDate(Date bruttoOctDate) {
		this.bruttoOctDate = bruttoOctDate;
	}

	public Date getBruttoNovDate() {
		return bruttoNovDate;
	}

	public void setBruttoNovDate(Date bruttoNovDate) {
		this.bruttoNovDate = bruttoNovDate;
	}

	public Date getBruttoDecDate() {
		return bruttoDecDate;
	}

	public void setBruttoDecDate(Date bruttoDecDate) {
		this.bruttoDecDate = bruttoDecDate;
	}
	
	public Double getRetourKautionPrice() {
		return retourKautionPrice;
	}

	public void setRetourKautionPrice(Double retourKautionPrice) {
		this.retourKautionPrice = retourKautionPrice;
	}

	public Date getRetourKautionDate() {
		return retourKautionDate;
	}

	public void setRetourKautionDate(Date retourKautionDate) {
		this.retourKautionDate = retourKautionDate;
	}

	public boolean isExcludeInPaymentList() {
		return excludeInPaymentList;
	}

	public void setExcludeInPaymentList(boolean excludeInPaymentList) {
		this.excludeInPaymentList = excludeInPaymentList;
	}

	@Override
	public String toString() {
		return "PaymentDatePriceDTO [id=" + id + ", jahr=" + jahr + ", status=" + status + ", aktKautionPrice="
				+ aktKautionPrice + ", retourKautionPrice=" + retourKautionPrice + ", lastKautionPrice="
				+ lastKautionPrice + ", aktKautionDate=" + aktKautionDate + ", retourKautionDate=" + retourKautionDate
				+ ", sumBruttoPrice=" + sumBruttoPrice + ", lastName=" + lastName + ", customerName=" + customerName
				+ ", customerAddress=" + customerAddress + ", customerCompany=" + customerCompany
				+ ", customerCompanyAddress=" + customerCompanyAddress + ", terminateDate=" + terminateDate
				+ ", aktBruttoPrice=" + aktBruttoPrice + ", lastBruttoPrice=" + lastBruttoPrice + ", contractDetails="
				+ contractDetails + ", boxNumbers=" + boxNumbers + ", copyFlag=" + copyFlag + ", bruttoJanPrice="
				+ bruttoJanPrice + ", bruttoFebPrice=" + bruttoFebPrice + ", bruttoMarPrice=" + bruttoMarPrice
				+ ", bruttoAprPrice=" + bruttoAprPrice + ", bruttoMaiPrice=" + bruttoMaiPrice + ", bruttoJunPrice="
				+ bruttoJunPrice + ", bruttoJulPrice=" + bruttoJulPrice + ", bruttoAugPrice=" + bruttoAugPrice
				+ ", bruttoSepPrice=" + bruttoSepPrice + ", bruttoOctPrice=" + bruttoOctPrice + ", bruttoNovPrice="
				+ bruttoNovPrice + ", bruttoDecPrice=" + bruttoDecPrice + ", bruttoJanDate=" + bruttoJanDate
				+ ", bruttoFebDate=" + bruttoFebDate + ", bruttoMarDate=" + bruttoMarDate + ", bruttoAprDate="
				+ bruttoAprDate + ", bruttoMaiDate=" + bruttoMaiDate + ", bruttoJunDate=" + bruttoJunDate
				+ ", bruttoJulDate=" + bruttoJulDate + ", bruttoAugDate=" + bruttoAugDate + ", bruttoSepDate="
				+ bruttoSepDate + ", bruttoOctDate=" + bruttoOctDate + ", bruttoNovDate=" + bruttoNovDate
				+ ", bruttoDecDate=" + bruttoDecDate + ", excludeInPaymentList=" + excludeInPaymentList + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PaymentDatePriceDTO) {
			return ((PaymentDatePriceDTO) obj).id == this.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.id.intValue();
	}

}
