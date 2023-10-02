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
@Table(name = "additionalcosts")
public class AdditionalCosts extends AuditModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "rechnungsart")
	private String rechnungsart;

	@Column(name = "statusText")
	private String statusText;

	@Column(name = "billingDate")
	private Date billingDate;

	@Column(name = "title", columnDefinition = "TEXT")
	private String title;

	@Column(name = "text", columnDefinition = "TEXT")
	private String text;

	@Column(name = "positiontext1", columnDefinition = "TEXT")
	private String positiontext1;

	@Column(name = "positionprice1")
	private Double positionprice1;

	@Column(name = "positiontext2", columnDefinition = "TEXT")
	private String positiontext2;

	@Column(name = "positionprice2")
	private Double positionprice2;

	@Column(name = "positiontext3", columnDefinition = "TEXT")
	private String positiontext3;

	@Column(name = "positionprice3")
	private Double positionprice3;

	@Column(name = "positiontext4", columnDefinition = "TEXT")
	private String positiontext4;

	@Column(name = "positionprice4")
	private Double positionprice4;

	@Column(name = "positiontext5", columnDefinition = "TEXT")
	private String positiontext5;

	@Column(name = "positionprice5")
	private Double positionprice5;

	@Column(name = "mwstPrice")
	private Double mwstPrice;

	@Column(name = "mwstSatz")
	private Double mwstSatz;

	@Column(name = "sumBruttoPrice")
	private Double sumBruttoPrice;

	public AdditionalCosts() {
	}

	public AdditionalCosts(Long id, String rechnungsart, String statusText, Date billingDate, String title, String text,
			String positiontext1, Double positionprice1, String positiontext2, Double positionprice2,
			String positiontext3, Double positionprice3, String positiontext4, Double positionprice4,
			String positiontext5, Double positionprice5, Double mwstPrice, Double mwstSatz, Double sumBruttoPrice,
			Contract contract) {
		this.id = id;
		this.rechnungsart = rechnungsart;
		this.statusText = statusText;
		this.billingDate = billingDate;
		this.title = title;
		this.text = text;
		this.positiontext1 = positiontext1;
		this.positionprice1 = positionprice1;
		this.positiontext2 = positiontext2;
		this.positionprice2 = positionprice2;
		this.positiontext3 = positiontext3;
		this.positionprice3 = positionprice3;
		this.positiontext4 = positiontext4;
		this.positionprice4 = positionprice4;
		this.positiontext5 = positiontext5;
		this.positionprice5 = positionprice5;
		this.mwstPrice = mwstPrice;
		this.mwstSatz = mwstSatz;
		this.sumBruttoPrice = sumBruttoPrice;

	}

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

	public Double getMwstSatz() {
		return mwstSatz;
	}

	public void setMwstSatz(Double mwstSatz) {
		this.mwstSatz = mwstSatz;
	}

	public Double getSumBruttoPrice() {
		return sumBruttoPrice;
	}

	public void setSumBruttoPrice(Double sumBruttoPrice) {
		this.sumBruttoPrice = sumBruttoPrice;
	}

	public Contract getContract() {
		return this.contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
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

	public Date getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}

	@Override
	public String toString() {
		return "AdditionalCosts [id=" + id + ", rechnungsart=" + rechnungsart + ", statusText=" + statusText
				+ ", billingDate=" + billingDate + ", title=" + title + ", text=" + text + ", positiontext1="
				+ positiontext1 + ", positionprice1=" + positionprice1 + ", positiontext2=" + positiontext2
				+ ", positionprice2=" + positionprice2 + ", positiontext3=" + positiontext3 + ", positionprice3="
				+ positionprice3 + ", positiontext4=" + positiontext4 + ", positionprice4=" + positionprice4
				+ ", positiontext5=" + positiontext5 + ", positionprice5=" + positionprice5 + ", mwstPrice=" + mwstPrice
				+ ", mwstSatz=" + mwstSatz + ", sumBruttoPrice=" + sumBruttoPrice + ", contract=" + contract + "]";
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contractId_FK", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Contract contract;

}
