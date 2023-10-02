package com.ebox3.server.model.dto;

import java.util.Date;


public class ElectricPeriodDTO {

	private Long id;
	private Double zaehlerStand;
	private Date zaehlerFromPeriode;
	private Date zaehlerToPeriode;
	private Double stromPriceBrutto;
	private Double stromPriceNetto;
	private boolean status;
	private String statusText;
	private String marginStart;
	private String marginStop;
	private Double stromAkontoMonat;
	private Double preiskW;
	private String bemerkungen;
	private Date zahlungEingegangen;
	private Double preisBearbeitungsgebuehr;
	private Double kundenGuthaben;
	private Double mwstSatz;
	private Double mwstPrice;
	private String info;
	private Date printDate;
	private String printAnschrift;
	private Long customerId;
	private String customerInformation;
	private Long  idElectricMeter;
	private String gebaeudeInfos;
	private String bemerkungenElektroMeter;
	private Long electricMeterNumber;
	private Double diffKwatt;


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

	public Double getStromAkontoMonat() {
		return stromAkontoMonat;
	}

	public void setStromAkontoMonat(Double stromAkontoMonat) {
		this.stromAkontoMonat = stromAkontoMonat;
	}

	public Double getPreiskW() {
		return preiskW;
	}

	public void setPreiskW(Double preiskW) {
		this.preiskW = preiskW;
	}

	public String getBemerkungen() {
		return bemerkungen;
	}

	public void setBemerkungen(String bemerkungen) {
		this.bemerkungen = bemerkungen;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerInformation() {
		return customerInformation;
	}

	public void setCustomerInformation(String customerInformation) {
		this.customerInformation = customerInformation;
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

	public Date getZahlungEingegangen() {
		return zahlungEingegangen;
	}

	public void setZahlungEingegangen(Date zahlungEingegangen) {
		this.zahlungEingegangen = zahlungEingegangen;
	}

	public Long getIdElectricMeter() {
		return idElectricMeter;
	}

	public void setIdElectricMeter(Long idElectricMeter) {
		this.idElectricMeter = idElectricMeter;
	}

	public String getGebaeudeInfos() {
		return gebaeudeInfos;
	}

	public void setGebaeudeInfos(String gebaeudeInfos) {
		this.gebaeudeInfos = gebaeudeInfos;
	}
	
	public String getBemerkungenElektroMeter() {
		return bemerkungenElektroMeter;
	}

	public void setBemerkungenElektroMeter(String bemerkungenElektroMeter) {
		this.bemerkungenElektroMeter = bemerkungenElektroMeter;
	}
	
	public Long getElectricMeterNumber() {
		return electricMeterNumber;
	}

	public void setElectricMeterNumber(Long electricMeterNumber) {
		this.electricMeterNumber = electricMeterNumber;
	}
	
	public Double getDiffKwatt() {
		return diffKwatt;
	}

	public void setDiffKwatt(Double diffKwatt) {
		this.diffKwatt = diffKwatt;
	}

	public String getPrintAnschrift() {
		return printAnschrift;
	}

	public void setPrintAnschrift(String printAnschrift) {
		this.printAnschrift = printAnschrift;
	}

	@Override
	public String toString() {
		return "ElectricPeriodDTO [id=" + id + ", zaehlerStand=" + zaehlerStand + ", zaehlerFromPeriode="
				+ zaehlerFromPeriode + ", zaehlerToPeriode=" + zaehlerToPeriode + ", stromPriceBrutto="
				+ stromPriceBrutto + ", stromPriceNetto=" + stromPriceNetto + ", status=" + status + ", statusText="
				+ statusText + ", marginStart=" + marginStart + ", marginStop=" + marginStop + ", stromAkontoMonat="
				+ stromAkontoMonat + ", preiskW=" + preiskW + ", bemerkungen=" + bemerkungen + ", zahlungEingegangen="
				+ zahlungEingegangen + ", preisBearbeitungsgebuehr=" + preisBearbeitungsgebuehr + ", kundenGuthaben="
				+ kundenGuthaben + ", mwstSatz=" + mwstSatz + ", mwstPrice=" + mwstPrice + ", info=" + info
				+ ", printDate=" + printDate + ", printAnschrift=" + printAnschrift + ", customerId=" + customerId
				+ ", customerInformation=" + customerInformation + ", idElectricMeter=" + idElectricMeter
				+ ", gebaeudeInfos=" + gebaeudeInfos + ", bemerkungenElektroMeter=" + bemerkungenElektroMeter
				+ ", electricMeterNumber=" + electricMeterNumber + ", diffKwatt=" + diffKwatt + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ElectricPeriodDTO) {
			return ((ElectricPeriodDTO) obj).zaehlerStand == this.zaehlerStand;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.zaehlerStand.intValue();
	}
}
