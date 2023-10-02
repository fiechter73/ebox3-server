package com.ebox3.server.model.dto;

import java.util.Date;
import java.util.List;

public class PaymentDTO {

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

	private Double sumJanBruttoPrice = 0D;
	private Double sumFebBruttoPrice = 0D;
	private Double sumMarBruttoPrice = 0D;
	private Double sumAprBruttoPrice = 0D;
	private Double sumMaiBruttoPrice = 0D;
	private Double sumJunBruttoPrice = 0D;
	private Double sumJulBruttoPrice = 0D;
	private Double sumAugBruttoPrice = 0D;
	private Double sumSepBruttoPrice = 0D;
	private Double sumOctBruttoPrice = 0D;
	private Double sumNovBruttoPrice = 0D;
	private Double sumDecBruttoPrice = 0D;

	private List<PaymentDatePriceDTO> paymentDatePriceDTO;

	public void setSumJanBrutto(Double janBruttoPrice) {
		this.sumJanBruttoPrice += janBruttoPrice;
	}

	public Double getSumJanBrutto() {
		return this.sumJanBruttoPrice;
	}

	public void setSumFebBrutto(Double febBruttoPrice) {
		this.sumFebBruttoPrice += febBruttoPrice;
	}

	public Double getSumFebBrutto() {
		return this.sumFebBruttoPrice;
	}

	public void setSumMarBrutto(Double marBruttoPrice) {
		this.sumMarBruttoPrice += marBruttoPrice;
	}

	public Double getSumMarBrutto() {
		return this.sumMarBruttoPrice;
	}

	public void setSumAprBrutto(Double aprBruttoPrice) {
		this.sumAprBruttoPrice += aprBruttoPrice;
	}

	public Double getSumAprBrutto() {
		return this.sumAprBruttoPrice;
	}

	public void setSumMaiBrutto(Double maiBruttoPrice) {
		this.sumMaiBruttoPrice += maiBruttoPrice;
	}

	public Double getSumMaiBrutto() {
		return this.sumMaiBruttoPrice;
	}

	public void setSumJunBrutto(Double junBruttoPrice) {
		this.sumJunBruttoPrice += junBruttoPrice;
	}

	public Double getSumJunBrutto() {
		return this.sumJunBruttoPrice;
	}

	public void setSumJulBrutto(Double julBruttoPrice) {
		this.sumJulBruttoPrice += julBruttoPrice;
	}

	public Double getSumJulBrutto() {
		return this.sumJulBruttoPrice;
	}

	public void setSumAugBrutto(Double augBruttoPrice) {
		this.sumAugBruttoPrice += augBruttoPrice;
	}

	public Double getSumAugBrutto() {
		return this.sumAugBruttoPrice;
	}

	public void setSumSepBrutto(Double sepBruttoPrice) {
		this.sumSepBruttoPrice += sepBruttoPrice;
	}

	public Double getSumSepBrutto() {
		return this.sumSepBruttoPrice;
	}

	public void setSumOctBrutto(Double octBruttoPrice) {
		this.sumOctBruttoPrice += octBruttoPrice;
	}

	public Double getSumOctBrutto() {
		return this.sumOctBruttoPrice;
	}

	public void setSumNovBrutto(Double novBruttoPrice) {
		this.sumNovBruttoPrice += novBruttoPrice;
	}

	public Double getSumNovBrutto() {
		return this.sumNovBruttoPrice;
	}

	public void setSumDecBrutto(Double decBruttoPrice) {
		this.sumDecBruttoPrice += decBruttoPrice;
	}

	public Double getSumDecBrutto() {
		return this.sumDecBruttoPrice;
	}

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

	public Double getSumBruttoPrice() {
		return sumBruttoPrice;
	}

	public void setSumBruttoPrice(Double sumBruttoPrice) {
		this.sumBruttoPrice = sumBruttoPrice;
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getAktKautionDate() {
		return aktKautionDate;
	}

	public void setAktKautionDate(Date aktKautionDate) {
		this.aktKautionDate = aktKautionDate;
	}

	public Double getLastKautionPrice() {
		return lastKautionPrice;
	}

	public void setLastKautionPrice(Double lastKautionPrice) {
		this.lastKautionPrice = lastKautionPrice;
	}

	public List<PaymentDatePriceDTO> getPaymentDatePrice() {
		return paymentDatePriceDTO;
	}

	public void setPaymentDatePrice(List<PaymentDatePriceDTO> paymentDatePriceDTO) {
		this.paymentDatePriceDTO = paymentDatePriceDTO;
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


	@Override
	public String toString() {
		return "PaymentDTO [id=" + id + ", jahr=" + jahr + ", status=" + status + ", aktKautionPrice=" + aktKautionPrice
				+ ", retourKautionPrice=" + retourKautionPrice + ", lastKautionPrice=" + lastKautionPrice
				+ ", aktKautionDate=" + aktKautionDate + ", retourKautionDate=" + retourKautionDate
				+ ", sumBruttoPrice=" + sumBruttoPrice + ", lastName=" + lastName + ", customerName=" + customerName
				+ ", customerAddress=" + customerAddress + ", customerCompany=" + customerCompany
				+ ", customerCompanyAddress=" + customerCompanyAddress + ", terminateDate=" + terminateDate
				+ ", aktBruttoPrice=" + aktBruttoPrice + ", lastBruttoPrice=" + lastBruttoPrice + ", contractDetails="
				+ contractDetails + ", boxNumbers=" + boxNumbers + ", copyFlag=" + copyFlag + ", sumJanBruttoPrice="
				+ sumJanBruttoPrice + ", sumFebBruttoPrice=" + sumFebBruttoPrice + ", sumMarBruttoPrice="
				+ sumMarBruttoPrice + ", sumAprBruttoPrice=" + sumAprBruttoPrice + ", sumMaiBruttoPrice="
				+ sumMaiBruttoPrice + ", sumJunBruttoPrice=" + sumJunBruttoPrice + ", sumJulBruttoPrice="
				+ sumJulBruttoPrice + ", sumAugBruttoPrice=" + sumAugBruttoPrice + ", sumSepBruttoPrice="
				+ sumSepBruttoPrice + ", sumOctBruttoPrice=" + sumOctBruttoPrice + ", sumNovBruttoPrice="
				+ sumNovBruttoPrice + ", sumDecBruttoPrice=" + sumDecBruttoPrice + ", paymentDatePrice="
				+ paymentDatePriceDTO + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PaymentDTO) {
			return ((PaymentDTO) obj).id == this.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.id.intValue();
	}

}
