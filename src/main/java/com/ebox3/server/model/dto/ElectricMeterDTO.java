package com.ebox3.server.model.dto;

import com.ebox3.server.model.Ebox;

public class ElectricMeterDTO {

	private Long id;
	private String eboxBezeichnung;
	private String anschriftMieter;
	private String anrede;
	private String name;
	private boolean useCompanyAddress;
	private String anschriftMieterAdr;
	private String anschriftMieterOrt;
	private String firma;
	private String anschriftMieterFirma;
	private String vertragsdetails;
	private Long electricMeterNumber;
	private String electricMeterName;
	private String electricMeterBemerkungen;
	private Double stromPrice;
	private Long customerId;
	private Long eboxId;
	private Ebox ebox;

	public ElectricMeterDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEboxBezeichnung() {
		return eboxBezeichnung;
	}

	public void setEboxBezeichnung(String eboxBezeichnung) {
		this.eboxBezeichnung = eboxBezeichnung;
	}

	public void setAnschriftMieter(String anschriftMieter) {
		this.anschriftMieter = anschriftMieter;
	}

	public String getAnschriftMieter() {
		return anschriftMieter;
	}

	public void setAschriftMieter(String anschriftMieter) {
		this.anschriftMieter = anschriftMieter;
	}

	public String getAnschriftMieterAdr() {
		return anschriftMieterAdr;
	}

	public void setAnschriftMieterAdr(String anschriftMieterAdr) {
		this.anschriftMieterAdr = anschriftMieterAdr;
	}

	public String getAnschriftMieterOrt() {
		return anschriftMieterOrt;
	}

	public void setAnschriftMieterOrt(String anschriftMieterOrt) {
		this.anschriftMieterOrt = anschriftMieterOrt;
	}

	public String getAnschriftMieterFirma() {
		return anschriftMieterFirma;
	}

	public String getAnrede() {
		return anrede;
	}

	public void setAnrede(String anrede) {
		this.anrede = anrede;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAnschriftMieterFirma(String anschriftMieterFirma) {
		this.anschriftMieterFirma = anschriftMieterFirma;
	}

	public String getVertragsdetails() {
		return vertragsdetails;
	}

	public void setVertragsdetails(String vertragsdetails) {
		this.vertragsdetails = vertragsdetails;
	}

	public Long getElectricMeterNumber() {
		return electricMeterNumber;
	}

	public void setElectricMeterNumber(Long electricMeterNumber) {
		this.electricMeterNumber = electricMeterNumber;
	}

	public String getElectricMeterName() {
		return electricMeterName;
	}

	public void setElectricMeterName(String electricMeterName) {
		this.electricMeterName = electricMeterName;
	}

	public Double getStromPrice() {
		return stromPrice;
	}

	public void setStromPrice(Double stromPrice) {
		this.stromPrice = stromPrice;
	}

	public boolean isUseCompanyAddress() {
		return useCompanyAddress;
	}

	public void setUseCompanyAddress(boolean useCompanyAddress) {
		this.useCompanyAddress = useCompanyAddress;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getElectricMeterBemerkungen() {
		return electricMeterBemerkungen;
	}

	public void setElectricMeterBemerkungen(String electricMeterBemerkungen) {
		this.electricMeterBemerkungen = electricMeterBemerkungen;
	}

	public Ebox getEbox() {
		return ebox;
	}

	public void setEbox(Ebox ebox) {
		this.ebox = ebox;
	}
	

	public Long getEboxId() {
		return eboxId;
	}

	public void setEboxId(Long eboxId) {
		this.eboxId = eboxId;
	}

	@Override
	public String toString() {
		return "ElectricMeterDTO [id=" + id + ", eboxBezeichnung=" + eboxBezeichnung + ", anschriftMieter="
				+ anschriftMieter + ", anrede=" + anrede + ", name=" + name + ", useCompanyAddress=" + useCompanyAddress
				+ ", anschriftMieterAdr=" + anschriftMieterAdr + ", anschriftMieterOrt=" + anschriftMieterOrt
				+ ", firma=" + firma + ", anschriftMieterFirma=" + anschriftMieterFirma + ", vertragsdetails="
				+ vertragsdetails + ", electricMeterNumber=" + electricMeterNumber + ", electricMeterName="
				+ electricMeterName + ", electricMeterBemerkungen=" + electricMeterBemerkungen + ", stromPrice="
				+ stromPrice + ", customerId=" + customerId + ", eboxId=" + eboxId + ", ebox=" + ebox + "]";
	}



}
