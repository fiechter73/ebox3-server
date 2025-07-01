package com.ebox3.server.model.dto;

import java.util.Date;

public class EboxDTO {

	private Long id;
	private String beschreibung;
	private Double price;
	private boolean priceAdapted;
	private boolean additional;
	private Double additionalCosts;
	private boolean strom;
	private Double stromPrice;
	private boolean wasser;
	private Double wasserPrice;
	private boolean heizung;
	private Double heizungPrice;
	private boolean internet;
	private Double internetPrice;
	private boolean internetWifi;
	private Double internetWifiPrice;
	private String bemerkungen;
	private boolean briefkasten;
	private Double briefkastenPrice;
	private Double totalPriceNetto;
	private Double mwstPrice;
	private Double mwstSatz;
	private Double totalPriceBrutto;
	private Double depot;
	private String keysPerBox;
	private boolean status;
	private String statusText;

	private Long boxNumber;
	private String boxType;
	private String anschrift;
	private Long contractId;
	private Date startDate;
	private Date endDate;
	private Date terminateDate;
	private Date lastRentDate;
	private String laufzeit;
	private boolean publicOnWeb;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public boolean isPriceAdapted() {
		return priceAdapted;
	}

	public void setPriceAdapted(boolean priceAdapted) {
		this.priceAdapted = priceAdapted;
	}

	public boolean isAdditional() {
		return additional;
	}

	public void setAdditional(boolean additional) {
		this.additional = additional;
	}

	public Double getAdditionalCosts() {
		return additionalCosts;
	}

	public void setAdditionalCosts(Double additionalCosts) {
		this.additionalCosts = additionalCosts;
	}

	public boolean isStrom() {
		return strom;
	}

	public void setStrom(boolean strom) {
		this.strom = strom;
	}

	public Double getStromPrice() {
		return stromPrice;
	}

	public void setStromPrice(Double stromPrice) {
		this.stromPrice = stromPrice;
	}

	public boolean isWasser() {
		return wasser;
	}

	public void setWasser(boolean wasser) {
		this.wasser = wasser;
	}

	public Double getWasserPrice() {
		return wasserPrice;
	}

	public void setWasserPrice(Double wasserPrice) {
		this.wasserPrice = wasserPrice;
	}

	public boolean isHeizung() {
		return heizung;
	}

	public void setHeizung(boolean heizung) {
		this.heizung = heizung;
	}

	public Double getHeizungPrice() {
		return heizungPrice;
	}

	public void setHeizungPrice(Double heizungPrice) {
		this.heizungPrice = heizungPrice;
	}

	public boolean isInternet() {
		return internet;
	}

	public void setInternet(boolean internet) {
		this.internet = internet;
	}

	public Double getInternetPrice() {
		return internetPrice;
	}

	public void setInternetPrice(Double internetPrice) {
		this.internetPrice = internetPrice;
	}

	public boolean isBriefkasten() {
		return briefkasten;
	}

	public void setBriefkasten(boolean briefkasten) {
		this.briefkasten = briefkasten;
	}

	public Double getBriefkastenPrice() {
		return briefkastenPrice;
	}

	public void setBriefkastenPrice(Double briefkastenPrice) {
		this.briefkastenPrice = briefkastenPrice;
	}

	public Double getTotalPriceNetto() {
		return totalPriceNetto;
	}

	public void setTotalPriceNetto(Double totalPriceNetto) {
		this.totalPriceNetto = totalPriceNetto;
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

	public Double getTotalPriceBrutto() {
		return totalPriceBrutto;
	}

	public void setTotalPriceBrutto(Double totalPriceBrutto) {
		this.totalPriceBrutto = totalPriceBrutto;
	}

	public Double getDepot() {
		return depot;
	}

	public void setDepot(Double depot) {
		this.depot = depot;
	}

	public String getKeysPerBox() {
		return keysPerBox;
	}

	public void setKeysPerBox(String keysPerBox) {
		this.keysPerBox = keysPerBox;
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

	public Long getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(Long boxNumber) {
		this.boxNumber = boxNumber;
	}

	public String getBoxType() {
		return boxType;
	}

	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}

	public String getAnschrift() {
		return anschrift;
	}

	public void setAnschrift(String anschrift) {
		this.anschrift = anschrift;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
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

	public boolean isInternetWifi() {
		return internetWifi;
	}

	public void setInternetWifi(boolean internetWifi) {
		this.internetWifi = internetWifi;
	}

	public Double getInternetWifiPrice() {
		return internetWifiPrice;
	}

	public void setInternetWifiPrice(Double internetWifiPrice) {
		this.internetWifiPrice = internetWifiPrice;
	}

	public String getBemerkungen() {
		return bemerkungen;
	}

	public void setBemerkungen(String bemerkungen) {
		this.bemerkungen = bemerkungen;
	}

	public Date getLastRentDate() {
		return lastRentDate;
	}

	public void setLastRentDate(Date lastRentDate) {
		this.lastRentDate = lastRentDate;
	}

	public String getLaufzeit() {
		return laufzeit;
	}

	public void setLaufzeit(String laufzeit) {
		this.laufzeit = laufzeit;
	}

	public boolean isPublicOnWeb() {
		return publicOnWeb;
	}

	public void setPublicOnWeb(boolean publicOnWeb) {
		this.publicOnWeb = publicOnWeb;
	}

	@Override
	public String toString() {
		return "EboxDTO [id=" + id + ", beschreibung=" + beschreibung + ", price=" + price + ", priceAdapted="
				+ priceAdapted + ", additional=" + additional + ", additionalCosts=" + additionalCosts + ", strom="
				+ strom + ", stromPrice=" + stromPrice + ", wasser=" + wasser + ", wasserPrice=" + wasserPrice
				+ ", heizung=" + heizung + ", heizungPrice=" + heizungPrice + ", internet=" + internet
				+ ", internetPrice=" + internetPrice + ", internetWifi=" + internetWifi + ", internetWifiPrice="
				+ internetWifiPrice + ", bemerkungen=" + bemerkungen + ", briefkasten=" + briefkasten
				+ ", briefkastenPrice=" + briefkastenPrice + ", totalPriceNetto=" + totalPriceNetto + ", mwstPrice="
				+ mwstPrice + ", mwstSatz=" + mwstSatz + ", totalPriceBrutto=" + totalPriceBrutto + ", depot=" + depot
				+ ", keysPerBox=" + keysPerBox + ", status=" + status + ", statusText=" + statusText + ", boxNumber="
				+ boxNumber + ", boxType=" + boxType + ", anschrift=" + anschrift + ", contractId=" + contractId
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", terminateDate=" + terminateDate
				+ ", lastRentDate=" + lastRentDate + ", laufzeit=" + laufzeit + ", publicOnWeb=" + publicOnWeb + "]";
	}

	@Override
	public int hashCode() {
		return this.boxNumber.intValue();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EboxDTO) {
			return ((EboxDTO) obj).boxNumber == this.boxNumber;
		}
		return false;
	}

}
