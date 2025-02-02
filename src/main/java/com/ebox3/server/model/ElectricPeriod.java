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
@Table(name = "electricPeriod")
public class ElectricPeriod {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "zaehlerStand")
	private Double zaehlerStand;

	@Column(name = "zaehlerFromPeriode")
	private Date zaehlerFromPeriode;

	@Column(name = "zaehlerToPeriode")
	private Date zaehlerToPeriode;

	@Column(name = "stromPriceBrutto")
	private Double stromPriceBrutto;

	@Column(name = "stromPriceNetto")
	private Double stromPriceNetto;

	@Column(name = "status")
	private boolean status;

	@Column(name = "statusText")
	private String statusText;

	@Column(name = "marginStart")
	private String marginStart;

	@Column(name = "marginStop")
	private String marginStop;

	@Column(name = "stromAkontoMonat")
	private Double stromAkontoMonat;

	@Column(name = "preiskW")
	private Double preiskW;

	@Column(name = "bemerkungen", columnDefinition = "TEXT")
	private String bemerkungen;

	@Column(name = "zahlungEingegangen")
	private Date zahlungEingegangen;

	@Column(name = "preisBearbeitungsgebuehr")
	private Double preisBearbeitungsgebuehr;

	@Column(name = "kundenguthaben")
	private Double kundenGuthaben;

	@Column(name = "mwstSatz")
	private Double mwstSatz;

	@Column(name = "mwstPrice")
	private Double mwstPrice;

	@Column(name = "info")
	private String info;

	@Column(name = "printDate")
	private Date printDate;

	@Column(name = "customerId")
	private Long customerId;

	@Column(name = "printAnschrift")
	private String printAnschrift;

	@Column(name = "diffkwatt")
	private Double diffKwatt;
	
	@Column(name = "qrReferenceCode")
	private String qrReferenceCode;

	public ElectricPeriod() {
	}

	public ElectricPeriod(Long id, Double zaehlerStand, Date zaehlerFromPeriode, Date zaehlerToPeriode,
			Double stromPriceBrutto, Double stromPriceNetto, boolean status, String statusText, String marginStart,
			String marginStop, Double stromAkontoMonat, Double preiskW, String bemerkungen, Date zahlungEingegangen,
			Double preisBearbeitungsgebuehr, Double kundenGuthaben, Double mwstSatz, Double mwstPrice, String info,
			Date printDate, Long customerId, String printAnschrift, Double diffKwatt, String qrReferenceCode,  ElectricMeter electricMeter) {
		this.id = id;
		this.zaehlerStand = zaehlerStand;
		this.zaehlerFromPeriode = zaehlerFromPeriode;
		this.zaehlerToPeriode = zaehlerToPeriode;
		this.stromPriceBrutto = stromPriceBrutto;
		this.stromPriceNetto = stromPriceNetto;
		this.status = status;
		this.statusText = statusText;
		this.marginStart = marginStart;
		this.marginStop = marginStop;
		this.stromAkontoMonat = stromAkontoMonat;
		this.preiskW = preiskW;
		this.bemerkungen = bemerkungen;
		this.zahlungEingegangen = zahlungEingegangen;
		this.preisBearbeitungsgebuehr = preisBearbeitungsgebuehr;
		this.kundenGuthaben = kundenGuthaben;
		this.mwstSatz = mwstSatz;
		this.mwstPrice = mwstPrice;
		this.info = info;
		this.printDate = printDate;
		this.customerId = customerId;
		this.printAnschrift = printAnschrift;
		this.diffKwatt = diffKwatt;
		this.qrReferenceCode = qrReferenceCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getZaehlerStand() {
		return zaehlerStand;
	}

	public void setZaehlerStand(Double zaehlerStand) {
		this.zaehlerStand = zaehlerStand;
	}

	public Date getZaehlerFromPeriode() {
		return zaehlerFromPeriode;
	}

	public void setZaehlerFromPeriode(Date zaehlerFromPeriode) {
		this.zaehlerFromPeriode = zaehlerFromPeriode;
	}

	public Date getZaehlerToPeriode() {
		return zaehlerToPeriode;
	}

	public void setZaehlerToPeriode(Date zaehlerToPeriode) {
		this.zaehlerToPeriode = zaehlerToPeriode;
	}

	public String getMarginStart() {
		return marginStart;
	}

	public void setMarginStart(String marginStart) {
		this.marginStart = marginStart;
	}

	public String getMarginStop() {
		return marginStop;
	}

	public void setMarginStop(String marginStop) {
		this.marginStop = marginStop;
	}

	public String getBemerkungen() {
		return bemerkungen;
	}

	public void setBemerkungen(String bemerkungen) {
		this.bemerkungen = bemerkungen;
	}

	public ElectricMeter getElectricMeter() {
		return electricMeter;
	}

	public void setElectricMeter(ElectricMeter electricMeter) {
		this.electricMeter = electricMeter;
	}

	public Double getPreiskW() {
		return preiskW;
	}

	public void setPreiskW(Double preiskW) {
		this.preiskW = preiskW;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public Double getStromAkontoMonat() {
		return stromAkontoMonat;
	}

	public void setStromAkontoMonat(Double stromAkontoMonat) {
		this.stromAkontoMonat = stromAkontoMonat;
	}

	public Double getPreisBearbeitungsgebuehr() {
		return preisBearbeitungsgebuehr;
	}

	public void setPreisBearbeitungsgebuehr(Double preisBearbeitungsgebuehr) {
		this.preisBearbeitungsgebuehr = preisBearbeitungsgebuehr;
	}

	public Double getMwstSatz() {
		return mwstSatz;
	}

	public void setMwstSatz(Double mwstSatz) {
		this.mwstSatz = mwstSatz;
	}

	public Double getMwstPrice() {
		return mwstPrice;
	}

	public void setMwstPrice(Double mwstPrice) {
		this.mwstPrice = mwstPrice;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Date getPrintDate() {
		return printDate;
	}

	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Double getStromPriceBrutto() {
		return stromPriceBrutto;
	}

	public void setStromPriceBrutto(Double stromPriceBrutto) {
		this.stromPriceBrutto = stromPriceBrutto;
	}

	public Double getStromPriceNetto() {
		return stromPriceNetto;
	}

	public void setStromPriceNetto(Double stromPriceNetto) {
		this.stromPriceNetto = stromPriceNetto;
	}

	public Double getKundenGuthaben() {
		return kundenGuthaben;
	}

	public void setKundenGuthaben(Double kundenGuthaben) {
		this.kundenGuthaben = kundenGuthaben;
	}

	public String getPrintAnschrift() {
		return printAnschrift;
	}

	public void setPrintAnschrift(String printAnschrift) {
		this.printAnschrift = printAnschrift;
	}

	public Double getDiffKwatt() {
		return diffKwatt;
	}

	public void setDiffKwatt(Double diffKwatt) {
		this.diffKwatt = diffKwatt;
	}

	public Date getZahlungEingegangen() {
		return zahlungEingegangen;
	}

	public void setZahlungEingegangen(Date zahlungEingegangen) {
		this.zahlungEingegangen = zahlungEingegangen;
	}
	
	public String getQrReferenceCode() {
		return qrReferenceCode;
	}

	public void setQrReferenceCode(String qrReferenceCode) {
		this.qrReferenceCode = qrReferenceCode;
	}


	@Override
	public String toString() {
		return "ElectricPeriod [id=" + id + ", zaehlerStand=" + zaehlerStand + ", zaehlerFromPeriode="
				+ zaehlerFromPeriode + ", zaehlerToPeriode=" + zaehlerToPeriode + ", stromPriceBrutto="
				+ stromPriceBrutto + ", stromPriceNetto=" + stromPriceNetto + ", status=" + status + ", statusText="
				+ statusText + ", marginStart=" + marginStart + ", marginStop=" + marginStop + ", stromAkontoMonat="
				+ stromAkontoMonat + ", preiskW=" + preiskW + ", bemerkungen=" + bemerkungen + ", zahlungEingegangen="
				+ zahlungEingegangen + ", preisBearbeitungsgebuehr=" + preisBearbeitungsgebuehr + ", kundenGuthaben="
				+ kundenGuthaben + ", mwstSatz=" + mwstSatz + ", mwstPrice=" + mwstPrice + ", info=" + info
				+ ", printDate=" + printDate + ", customerId=" + customerId + ", printAnschrift=" + printAnschrift
				+ ", diffKwatt=" + diffKwatt + ", qrReferenceCode=" + qrReferenceCode + ", electricMeter=" + electricMeter
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ElectricPeriod) {
			return ((ElectricPeriod) obj).zaehlerStand == this.zaehlerStand;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.zaehlerStand.intValue();
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "electricMeterId_FK", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private ElectricMeter electricMeter;

}
