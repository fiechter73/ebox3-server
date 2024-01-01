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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ebox")

public class Ebox extends AuditModel  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "boxnumber")
	private Long boxnumber;

	@Column(name = "boxtype")
	private String boxtype;

	@Column(name = "beschreibung")
	private String beschreibung;

	@Column(name = "priceAdapted")
	private boolean priceAdapted;

	@Column(name = "price")
	private Double price;

	@Column(name = "additional")
	private boolean additional;

	@Column(name = "additionalCosts")
	private Double additionalCosts;

	@Column(name = "depot")
	private Double depot;

	@Column(name = "strom")
	private boolean strom;

	@Column(name = "stromPrice")
	private Double stromPrice;

	@Column(name = "wasser")
	private boolean wasser;

	@Column(name = "wasserPrice")
	private Double wasserPrice;

	@Column(name = "heizung")
	private boolean heizung;

	@Column(name = "heizungPrice")
	private Double heizungPrice;

	@Column(name = "internet")
	private boolean internet;

	@Column(name = "internetWifi")
	private boolean internetWifi;

	@Column(name = "internetPrice")
	private Double internetPrice;

	@Column(name = "internetWifiPrice")
	private Double internetWifiPrice;

	@Column(name = "briefkasten")
	private boolean briefkasten;

	@Column(name = "briefkastenPrice")
	private Double briefkastenPrice;

	@Column(name = "totalPriceNetto")
	private Double totalPriceNetto;

	@Column(name = "mwstPrice")
	private Double mwstPrice;

	@Column(name = "mwstSatz")
	private Double mwstSatz;

	@Column(name = "totalPriceBrutto")
	private Double totalPriceBrutto;

	@Column(name = "status")
	private boolean status;

	@Column(name = "statusText")
	private String statusText;

	@Column(name = "startDate")
	private Date startDate;

	@Column(name = "endDate")
	private Date endDate;

	@Column(name = "terminateDate")
	private Date terminateDate;

	@Column(name = "keysPerBox")
	private String keysPerBox;

	@Column(name = "bemerkungen")
	private String bemerkungen;

	public Ebox() {
	}

	public Ebox(Long boxnumber, String boxtype, String beschreibung, Double price, Double additionalCosts,
			boolean additional, Double depot, boolean priceAdapted, boolean strom, Double stromPrice, boolean wasser,
			Double wasserPrice, boolean heizung, Double heizungPrice, boolean internet, Double internetPrice,
			boolean briefkasten, Double briefkastenPrice, Double totalPriceNetto, Double mwstPrice, Double mwstSatz,
			Double totalPriceBrutto, boolean status, String statusText, Date startDate, Date endDate,
			Date terminateDate, String keysPerBox, String bemerkungen, boolean internetWifi, Double internetWifiPrice) {

		this.boxnumber = boxnumber;
		this.boxtype = boxtype;
		this.beschreibung = beschreibung;
		this.price = price;
		this.additional = additional;
		this.additionalCosts = additionalCosts;
		this.depot = depot;
		this.priceAdapted = priceAdapted;
		this.strom = strom;
		this.stromPrice = stromPrice;
		this.wasser = wasser;
		this.wasserPrice = wasserPrice;
		this.heizung = heizung;
		this.heizungPrice = heizungPrice;
		this.internet = internet;
		this.internetPrice = internetPrice;
		this.briefkasten = briefkasten;
		this.briefkastenPrice = briefkastenPrice;
		this.totalPriceNetto = totalPriceBrutto;
		this.mwstPrice = mwstPrice;
		this.mwstSatz = mwstSatz;
		this.totalPriceBrutto = totalPriceBrutto;
		this.status = status;
		this.statusText = statusText;
		this.startDate = startDate;
		this.endDate = endDate;
		this.terminateDate = terminateDate;
		this.keysPerBox = keysPerBox;
		this.internetWifi = internetWifi;
		this.internetWifiPrice = internetWifiPrice;
		this.bemerkungen = bemerkungen;

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

	public Double getTotalPriceBrutto() {
		return totalPriceBrutto;
	}

	public void setTotalPriceBrutto(Double totalPriceBrutto) {
		this.totalPriceBrutto = totalPriceBrutto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBoxNumber() {
		return boxnumber;
	}

	public void setBoxNumber(Long boxnumber) {
		this.boxnumber = boxnumber;
	}

	public String getBoxType() {
		return boxtype;
	}

	public void setBoxType(String boxtype) {
		this.boxtype = boxtype;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public boolean isPriceAdapted() {
		return priceAdapted;
	}

	public void setPriceAdapted(boolean priceAdapted) {
		this.priceAdapted = priceAdapted;
	}

	public Double getDepot() {
		return depot;
	}

	public void setDepot(Double depot) {
		this.depot = depot;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	public Contract getContract() {
		return this.contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public ElectricMeter getElectricMeter() {
		return this.electricMeter;
	}

	public void setElectricMeter(ElectricMeter electricMeter) {
		this.electricMeter = electricMeter;
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

	public Double getMwstSatz() {
		return mwstSatz;
	}

	public void setMwstSatz(Double mwstSatz) {
		this.mwstSatz = mwstSatz;
	}

	public String getKeysPerBox() {
		return keysPerBox;
	}

	public void setKeysPerBox(String keysPerBox) {
		this.keysPerBox = keysPerBox;
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

	@Override
	public String toString() {
		return "Ebox [id=" + id + ", boxnumber=" + boxnumber + ", boxtype=" + boxtype + ", beschreibung=" + beschreibung
				+ ", priceAdapted=" + priceAdapted + ", price=" + price + ", additional=" + additional
				+ ", additionalCosts=" + additionalCosts + ", depot=" + depot + ", strom=" + strom + ", stromPrice="
				+ stromPrice + ", wasser=" + wasser + ", wasserPrice=" + wasserPrice + ", heizung=" + heizung
				+ ", heizungPrice=" + heizungPrice + ", internet=" + internet + ", internetWifi=" + internetWifi
				+ ", internetPrice=" + internetPrice + ", internetWifiPrice=" + internetWifiPrice + ", briefkasten="
				+ briefkasten + ", briefkastenPrice=" + briefkastenPrice + ", totalPriceNetto=" + totalPriceNetto
				+ ", mwstPrice=" + mwstPrice + ", mwstSatz=" + mwstSatz + ", totalPriceBrutto=" + totalPriceBrutto
				+ ", status=" + status + ", statusText=" + statusText + ", startDate=" + startDate + ", endDate="
				+ endDate + ", terminateDate=" + terminateDate + ", keysPerBox=" + keysPerBox + ", bemerkungen="
				+ bemerkungen + ", contract=" + contract + ", electricMeter=" + electricMeter + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Ebox) {
			return ((Ebox) obj).id == this.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.id.intValue();
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contractId_FK", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Contract contract;

	@OneToOne(mappedBy = "ebox")
	@JsonIgnore
	private ElectricMeter electricMeter;


}
