package com.ebox3.server.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;

@Entity
@Table(name = "payment")
public class Payment extends AuditModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "jahr")
	private String jahr;

	@Column(name = "status", columnDefinition = "TEXT")
	private String status;

	@Column(name = "aktKautionPrice")
	private Double aktKautionPrice;

	@Column(name = "retourKautionPrice")
	private Double retourKautionPrice;

	@Column(name = "lastKautionPrice")
	private Double lastKautionPrice;

	@Column(name = "aktKautionDate")
	private Date aktKautionDate;

	@Column(name = "retourKautionDate")
	private Date retourKautionDate;

	@Column(name = "sumBruttoPrice")
	private Double sumBruttoPrice;

	@Column(name = "boxNumbers")
	private String boxNumbers;

	@Column(name = "terminateDate")
	private Date terminateDate;

	@Column(name = "aktBruttoPrice")
	private Double aktBruttoPrice;

	@Column(name = "lastBruttoPrice")
	private Double lastBruttoPrice;

	@Column(name = "contractDetails")
	private String contractDetails;

	@Column(name = "copyFlag")
	private boolean copyFlag;

	public Payment() {
	}

	public Payment(Long id, String jahr, String status, Double aktKautionPrice, Double retourKautionPrice,
			Double lastKautionPrice, Date aktKautionDate, Date retourKautionDate, Double sumBruttoPrice,
			String boxNumbers, Date terminateDate, Double aktBruttoPrice, Double lastBruttoPrice,
			String contractDetails, boolean copyFlag, Set<PaymentDatePrice> paymentDatePrice, Contract contract) {
		this.id = id;
		this.jahr = jahr;
		this.status = status;
		this.aktKautionPrice = aktKautionPrice;
		this.retourKautionPrice = retourKautionPrice;
		this.lastKautionPrice = lastKautionPrice;
		this.aktKautionDate = aktKautionDate;
		this.retourKautionDate = retourKautionDate;
		this.sumBruttoPrice = sumBruttoPrice;
		this.boxNumbers = boxNumbers;
		this.terminateDate = terminateDate;
		this.aktBruttoPrice = aktBruttoPrice;
		this.lastBruttoPrice = lastBruttoPrice;
		this.contractDetails = contractDetails;
		this.copyFlag = copyFlag;
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

	public Double getRetourKautionPrice() {
		return retourKautionPrice;
	}

	public void setRetourKautionPrice(Double retourKautionPrice) {
		this.retourKautionPrice = retourKautionPrice;
	}

	public Double getSumBruttoPrice() {
		return sumBruttoPrice;
	}

	public void setSumBruttoPrice(Double sumBruttoPrice) {
		this.sumBruttoPrice = sumBruttoPrice;
	}

	public String getBoxNumbers() {
		return boxNumbers;
	}

	public void setBoxNumbers(String boxNumbers) {
		this.boxNumbers = boxNumbers;
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

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isCopyFlag() {
		return copyFlag;
	}

	public void setCopyFlag(boolean copyFlag) {
		this.copyFlag = copyFlag;
	}

	public Date getAktKautionDate() {
		return aktKautionDate;
	}

	public void setAktKautionDate(Date aktKautionDate) {
		this.aktKautionDate = aktKautionDate;
	}

	public Date getRetourKautionDate() {
		return retourKautionDate;
	}

	public void setRetourKautionDate(Date retourKautionDate) {
		this.retourKautionDate = retourKautionDate;
	}

	public Double getLastKautionPrice() {
		return lastKautionPrice;
	}

	public void setLastKautionPrice(Double lastKautionPrice) {
		this.lastKautionPrice = lastKautionPrice;
	}

	public Set<PaymentDatePrice> getPaymentDatePrice() {
		return paymentDatePrice;
	}

	public void setPaymentDatePrice(Set<PaymentDatePrice> paymentDatePrice) {
		this.paymentDatePrice = paymentDatePrice;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", jahr=" + jahr + ", status=" + status + ", aktKautionPrice=" + aktKautionPrice
				+ ", retourKautionPrice=" + retourKautionPrice + ", lastKautionPrice=" + lastKautionPrice
				+ ", aktKautionDate=" + aktKautionDate + ", retourKautionDate=" + retourKautionDate
				+ ", sumBruttoPrice=" + sumBruttoPrice + ", boxNumbers=" + boxNumbers + ", terminateDate="
				+ terminateDate + ", aktBruttoPrice=" + aktBruttoPrice + ", lastBruttoPrice=" + lastBruttoPrice
				+ ", contractDetails=" + contractDetails + ", copyFlag=" + copyFlag + ", paymentDatePrice="
				+ paymentDatePrice + ", contract=" + contract + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Payment) {
			return ((Payment) obj).id == this.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.id.intValue();
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "payment")
	private Set<PaymentDatePrice> paymentDatePrice = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contractId_FK", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Contract contract;
}
