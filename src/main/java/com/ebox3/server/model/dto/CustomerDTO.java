package com.ebox3.server.model.dto;

import java.util.List;


public class CustomerDTO {

	private Long id;
	private String anrede;
	private String name;
	private String vorname;
	private String strasse;
	private String ort;
	private String plz;
	private String tel1;
	private String tel2;
	private String email;
	private String firmenName;
	private boolean useCompanyAddress;
	private String firmenAnschrift;
	private boolean active;
	private String statusText;
	private String bemerkungen;
	private Long countedContract;
	private Double sumBoxPrice;
	private Long countedBox;
	private List<ContractDTO> contracts;

	public CustomerDTO() {
	}

	public Long getId() {
		return id;
	}

	public String getAnrede() {
		return anrede;
	}

	public String getName() {
		return name;
	}

	public String getVorname() {
		return vorname;
	}

	public String getStrasse() {
		return strasse;
	}

	public String getOrt() {
		return ort;
	}

	public String getPlz() {
		return plz;
	}

	public String getTel1() {
		return tel1;
	}

	public String getTel2() {
		return tel2;
	}

	public String getEmail() {
		return email;
	}

	public String getFirmenName() {
		return firmenName;
	}

	public String getFirmenAnschrift() {
		return firmenAnschrift;
	}

	public boolean isActive() {
		return active;
	}

	public String getStatusText() {
		return statusText;
	}

	public String getBemerkungen() {
		return bemerkungen;
	}

	public Long getCountedContract() {
		return countedContract;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAnrede(String anrede) {
		this.anrede = anrede;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirmenName(String firmenName) {
		this.firmenName = firmenName;
	}

	public void setFirmenAnschrift(String firmenAnschrift) {
		this.firmenAnschrift = firmenAnschrift;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isUseCompanyAddress() {
		return useCompanyAddress;
	}

	public void setUseCompanyAddress(boolean useCompanyAddress) {
		this.useCompanyAddress = useCompanyAddress;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public void setBemerkungen(String bemerkungen) {
		this.bemerkungen = bemerkungen;
	}

	public void setCountedContract(Long countedContract) {
		this.countedContract = countedContract;
	}

	public List<ContractDTO> getContracts() {
		return contracts;
	}

	public void setContracts(List<ContractDTO> contracts) {
		this.contracts = contracts;
	}

	public Double getSumBoxPrice() {
		return sumBoxPrice;
	}

	public void setSumBoxPrice(Double sumBoxPrice) {
		this.sumBoxPrice = sumBoxPrice;
	}

	public Long getCountedBox() {
		return countedBox;
	}

	public void setCountedBox(Long countedBox) {
		this.countedBox = countedBox;
	}

	@Override
	public String toString() {
		return "CustomerDTO [id=" + id + ", anrede=" + anrede + ", name=" + name + ", vorname=" + vorname + ", strasse="
				+ strasse + ", ort=" + ort + ", plz=" + plz + ", tel1=" + tel1 + ", tel2=" + tel2 + ", email=" + email
				+ ", firmenName=" + firmenName + ", useCompanyAddress=" + useCompanyAddress + ", firmenAnschrift="
				+ firmenAnschrift + ", active=" + active + ", statusText=" + statusText + ", bemerkungen=" + bemerkungen
				+ ", countedContract=" + countedContract + ", sumBoxPrice=" + sumBoxPrice + ", countedBox=" + countedBox
				+ ", contracts=" + contracts + "]";
	}

	@Override
	public int hashCode() {
		return this.id.intValue();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EboxDTO) {
			return ((CustomerDTO) obj).id == this.id;
		}
		return false;
	}

}
