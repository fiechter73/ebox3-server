package com.ebox3.server.model.dto;

import java.util.Date;
import java.util.List;

public class OverviewDTO {

	private Long customerId;
	private List<Long> boxNumbers;
	private String anrede;
	private String statusTextCustomer;
	private String vorname;
	private String name;
	private String strasse;
	private String ort;
	private String plz;
	private String email;
	private String tel;
	private String statusText;
	private Date startDate;
	private Date endDate;
	private Date terminateDate;
	private String bemerkungen;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<Long> getBoxNumbers() {
		return boxNumbers;
	}

	public void setBoxNumbers(List<Long> boxNumbers) {
		this.boxNumbers = boxNumbers;
	}

	public String getAnrede() {
		return anrede;
	}

	public void setAnrede(String anrede) {
		this.anrede = anrede;
	}

	public String getStatusTextCustomer() {
		return statusTextCustomer;
	}

	public void setStatusTextCustomer(String statusTextCustomer) {
		this.statusTextCustomer = statusTextCustomer;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
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

	public String getBemerkungen() {
		return bemerkungen;
	}

	public void setBemerkungen(String bemerkungen) {
		this.bemerkungen = bemerkungen;
	}

	@Override
	public String toString() {
		return "OverviewDTO [customerId=" + customerId + ", boxNumbers=" + boxNumbers + ", anrede=" + anrede
				+ ", statusTextCustomer=" + statusTextCustomer + ", vorname=" + vorname + ", name=" + name
				+ ", strasse=" + strasse + ", ort=" + ort + ", plz=" + plz + ", email=" + email + ", tel=" + tel
				+ ", statusText=" + statusText + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", terminateDate=" + terminateDate + ", bemerkungen=" + bemerkungen + "]";
	}

}
