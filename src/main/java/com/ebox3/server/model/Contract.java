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
@Table(name = "contract")
public class Contract extends AuditModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4082770122099555633L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "startDate")
	private Date startDate;

	@Column(name = "endDate")
	private Date endDate;

	@Column(name = "terminateDate")
	private Date terminateDate;

	@Column(name = "checkOutDate")
	private Date checkOutDate;

	@Column(name = "active")
	private boolean active;

	@Column(name = "statusText")
	private String statusText;

	@Column(name = "guthaben")
	private Double guthaben;

	@Column(name = "frist")
	private String frist;

	@Column(name = "minDuration")
	private Long minDuration;

	@Column(name = "bemerkungen", columnDefinition = "TEXT")
	private String bemerkungen;

	@Column(name = "qrReferenceCodeRent")
	private String qrReferenceCodeRent;

	@Column(name = "qrReferenceCodeDeposit")
	private String qrReferenceCodeDeposit;

	public Contract() {
	}

	public Contract(Date startDate, Date endDate, Date terminateDate, Date checkOutDate, boolean active,
			String statusText, Double guthaben, String frist, String bemerkungen, Long minDuration,
			String qrReferenceCodeRent, String qrReferenceCodeDeposit) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.terminateDate = terminateDate;
		this.checkOutDate = checkOutDate;
		this.active = active;
		this.statusText = statusText;
		this.guthaben = guthaben;
		this.frist = frist;
		this.bemerkungen = bemerkungen;
		this.minDuration = minDuration;
		this.qrReferenceCodeRent = qrReferenceCodeRent;
		this.qrReferenceCodeDeposit = qrReferenceCodeDeposit;

	}

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

	public void setTerminateDate(Date terminateDate) {
		this.terminateDate = terminateDate;
	}

	public Date getTerminateDate() {
		return terminateDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getStatusText() {
		return this.statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<Ebox> getEboxs() {
		return eboxs;
	}

	public void setEboxs(Set<Ebox> eboxs) {
		this.eboxs = eboxs;
	}

	public Set<Payment> getPayment() {
		return payment;
	}

	public void setPayment(Set<Payment> payment) {
		this.payment = payment;
	}

	public Set<DBFile> getDBFile() {
		return dbfile;
	}

	public void setDBFile(Set<DBFile> dbfile) {
		this.dbfile = dbfile;
	}

	public Set<ContractHistory> getContractHistory() {
		return contractHistory;
	}

	public void setContractHistory(Set<ContractHistory> contractHistory) {
		this.contractHistory = contractHistory;
	}

	public Set<AdditionalCosts> getAdditionalCosts() {
		return additionalCosts;
	}

	public void setAdditionalCosts(Set<AdditionalCosts> additionalCosts) {
		this.additionalCosts = additionalCosts;
	}

	public Double getGuthaben() {
		return guthaben;
	}

	public void setGuthaben(Double guthaben) {
		this.guthaben = guthaben;
	}

	public String getFrist() {
		return frist;
	}

	public void setFrist(String frist) {
		this.frist = frist;
	}

	public String getBemerkungen() {
		return bemerkungen;
	}

	public void setBemerkungen(String bemerkungen) {
		this.bemerkungen = bemerkungen;
	}

	public Long getMinDuration() {
		return minDuration;
	}

	public void setMinDuration(Long minDuration) {
		this.minDuration = minDuration;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	@Override
	public String toString() {
		return "Contract [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", terminateDate="
				+ terminateDate + ", checkOutDate=" + checkOutDate + ", active=" + active + ", statusText=" + statusText
				+ ", guthaben=" + guthaben + ", frist=" + frist + ", minDuration=" + minDuration + ", bemerkungen="
				+ bemerkungen + ", qrReferenceCodeRent=" + qrReferenceCodeRent + ", qrReferenceCodeDeposit="
				+ qrReferenceCodeDeposit + ", contractHistory=" + contractHistory + ", payment=" + payment + ", eboxs="
				+ eboxs + ", additionalCosts=" + additionalCosts + ", dbfile=" + dbfile + ", customer=" + customer
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Contract) {
			return ((Contract) obj).id == this.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.id.intValue();
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "contract")
	private Set<ContractHistory> contractHistory = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "contract")
	private Set<Payment> payment = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "contract")
	private Set<Ebox> eboxs = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "contract")
	private Set<AdditionalCosts> additionalCosts = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "contract")
	private Set<DBFile> dbfile = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customerId_FK", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Customer customer;

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

}
