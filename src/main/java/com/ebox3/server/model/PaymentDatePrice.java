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
@Table(name = "paymentdateprice")
public class PaymentDatePrice extends AuditModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "bruttoJanPrice")
	private Double bruttoJanPrice;

	@Column(name = "bruttoFebPrice")
	private Double bruttoFebPrice;

	@Column(name = "bruttoMarPrice")
	private Double bruttoMarPrice;

	@Column(name = "bruttoAprPrice")
	private Double bruttoAprPrice;

	@Column(name = "bruttoMaiPrice")
	private Double bruttoMaiPrice;

	@Column(name = "bruttoJunPrice")
	private Double bruttoJunPrice;

	@Column(name = "bruttoJulPrice")
	private Double bruttoJulPrice;

	@Column(name = "bruttoAugPrice")
	private Double bruttoAugPrice;

	@Column(name = "bruttoSepPrice")
	private Double bruttoSepPrice;

	@Column(name = "bruttoOctPrice")
	private Double bruttoOctPrice;

	@Column(name = "bruttoNovPrice")
	private Double bruttoNovPrice;

	@Column(name = "bruttoDecPrice")
	private Double bruttoDecPrice;

	@Column(name = "bruttoJanDate")
	private Date bruttoJanDate;

	@Column(name = "bruttoFebDate")
	private Date bruttoFebDate;

	@Column(name = "bruttoMarDate")
	private Date bruttoMarDate;

	@Column(name = "bruttoAprDate")
	private Date bruttoAprDate;

	@Column(name = "bruttoMaiDate")
	private Date bruttoMaiDate;

	@Column(name = "bruttoJunDate")
	private Date bruttoJunDate;

	@Column(name = "bruttoJulDate")
	private Date bruttoJulDate;

	@Column(name = "bruttoAugDate")
	private Date bruttoAugDate;

	@Column(name = "bruttoSepDate")
	private Date bruttoSepDate;

	@Column(name = "bruttoOctDate")
	private Date bruttoOctDate;

	@Column(name = "bruttoNovDate")
	private Date bruttoNovDate;

	@Column(name = "bruttoDecDate")
	private Date bruttoDecDate;

	public PaymentDatePrice() {
	}

	public PaymentDatePrice(Long id, Double bruttoJanPrice, Double bruttoFebPrice, Double bruttoMarPrice,
			Double bruttoAprPrice, Double bruttoMaiPrice, Double bruttoJunPrice, Double bruttoJulPrice,
			Double bruttoAugPrice, Double bruttoSepPrice, Double bruttoOctPrice, Double bruttoNovPrice,
			Double bruttoDecPrice, Date bruttoJanDate, Date bruttoFebDate, Date bruttoMarDate, Date bruttoAprDate,
			Date bruttoMaiDate, Date bruttoJunDate, Date bruttoJulDate, Date bruttoAugDate, Date bruttoSepDate,
			Date bruttoOctDate, Date bruttoNovDate, Date bruttoDecDate, Payment payment) {
		this.id = id;
		this.bruttoJanPrice = bruttoJanPrice;
		this.bruttoFebPrice = bruttoFebPrice;
		this.bruttoMarPrice = bruttoMarPrice;
		this.bruttoAprPrice = bruttoAprPrice;
		this.bruttoMaiPrice = bruttoMaiPrice;
		this.bruttoJunPrice = bruttoJunPrice;
		this.bruttoJulPrice = bruttoJulPrice;
		this.bruttoAugPrice = bruttoAugPrice;
		this.bruttoSepPrice = bruttoSepPrice;
		this.bruttoOctPrice = bruttoOctPrice;
		this.bruttoNovPrice = bruttoNovPrice;
		this.bruttoDecPrice = bruttoDecPrice;
		this.bruttoJanDate = bruttoJanDate;
		this.bruttoFebDate = bruttoFebDate;
		this.bruttoMarDate = bruttoMarDate;
		this.bruttoAprDate = bruttoAprDate;
		this.bruttoMaiDate = bruttoMaiDate;
		this.bruttoJunDate = bruttoJunDate;
		this.bruttoJulDate = bruttoJulDate;
		this.bruttoAugDate = bruttoAugDate;
		this.bruttoSepDate = bruttoSepDate;
		this.bruttoOctDate = bruttoOctDate;
		this.bruttoNovDate = bruttoNovDate;
		this.bruttoDecDate = bruttoDecDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Payment getPayment() {
		return this.payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	@Override
	public String toString() {
		return "PaymentDatePrice [id=" + id + ", bruttoJanPrice=" + bruttoJanPrice + ", bruttoFebPrice="
				+ bruttoFebPrice + ", bruttoMarPrice=" + bruttoMarPrice + ", bruttoAprPrice=" + bruttoAprPrice
				+ ", bruttoMaiPrice=" + bruttoMaiPrice + ", bruttoJunPrice=" + bruttoJunPrice + ", bruttoJulPrice="
				+ bruttoJulPrice + ", bruttoAugPrice=" + bruttoAugPrice + ", bruttoSepPrice=" + bruttoSepPrice
				+ ", bruttoOctPrice=" + bruttoOctPrice + ", bruttoNovPrice=" + bruttoNovPrice + ", bruttoDecPrice="
				+ bruttoDecPrice + ", bruttoJanDate=" + bruttoJanDate + ", bruttoFebDate=" + bruttoFebDate
				+ ", bruttoMarDate=" + bruttoMarDate + ", bruttoAprDate=" + bruttoAprDate + ", bruttoMaiDate="
				+ bruttoMaiDate + ", bruttoJunDate=" + bruttoJunDate + ", bruttoJulDate=" + bruttoJulDate
				+ ", bruttoAugDate=" + bruttoAugDate + ", bruttoSepDate=" + bruttoSepDate + ", bruttoOctDate="
				+ bruttoOctDate + ", bruttoNovDate=" + bruttoNovDate + ", bruttoDecDate=" + bruttoDecDate + ", payment="
				+ payment + "]";
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paymentId_FK", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Payment payment;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PaymentDatePrice) {
			return ((PaymentDatePrice) obj).id == this.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.id.intValue();
	}

}
