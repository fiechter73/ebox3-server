package com.ebox3.server.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;

@Entity
@Table(name = "customer")
public class Customer extends AuditModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3977068727069699714L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "anrede")
	private String anrede;

	@Column(name = "name")
	private String name;

	@Column(name = "vorname")
	private String vorname;

	@Column(name = "strasse")
	private String strasse;

	@Column(name = "ort")
	private String ort;

	@Column(name = "plz")
	private String plz;

	@Column(name = "tel1")
	private String tel1;

	@Column(name = "tel2")
	private String tel2;

	@Column(name = "email")
	private String email;

	@Column(name = "useCompanyAddress")
	private boolean useCompanyAddress;

	@Column(name = "firmenName", columnDefinition = "TEXT")
	private String firmenName;

	@Column(name = "firmenAnschrift", columnDefinition = "TEXT")
	private String firmenAnschrift;

	@Column(name = "age")
	private int age;

	@Column(name = "active")
	private boolean active;

	@Column(name = "statusText")
	private String statusText;

	@Column(name = "bemerkungen", columnDefinition = "TEXT")
	private String bemerkungen;

	public Customer() {
	}

	public Customer(String anrede, String name, String vorname, String strasse, String ort, String plz, String tel1,
			String tel2, String email, String firmenName, String rechnungsAnschrift, String firmenAnschrift, int age,
			boolean active, boolean useCompanyAddress, String statusText, String bemerkungen) {
		this.anrede = anrede;
		this.name = name;
		this.vorname = vorname;
		this.strasse = strasse;
		this.ort = ort;
		this.plz = plz;
		this.tel1 = tel1;
		this.tel2 = tel2;
		this.email = email;
		this.firmenName = firmenName;
		this.firmenAnschrift = firmenAnschrift;
		this.age = age;
		this.active = active;
		this.useCompanyAddress = useCompanyAddress;
		this.statusText = statusText;
		this.bemerkungen = bemerkungen;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
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

	public String getTel1() {
		return tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirmenName() {
		return firmenName;
	}

	public void setFirmenName(String firmenName) {
		this.firmenName = firmenName;
	}

	public String getFirmenAnschrift() {
		return firmenAnschrift;
	}

	public void setFirmenAnschrift(String firmenAnschrift) {
		this.firmenAnschrift = firmenAnschrift;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return this.age;
	}

	public boolean isActive() {
		return active;
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

	public String getStatusText() {
		return statusText;
	}

	public String getAnrede() {
		return anrede;
	}

	public void setAnrede(String anrede) {
		this.anrede = anrede;
	}

	public String getBemerkungen() {
		return bemerkungen;
	}

	public void setBemerkungen(String bemerkungen) {
		this.bemerkungen = bemerkungen;
	}

	public Set<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(Set<Contract> contracts) {
		this.contracts = contracts;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", anrede=" + anrede + ", name=" + name + ", vorname=" + vorname + ", strasse="
				+ strasse + ", ort=" + ort + ", plz=" + plz + ", tel1=" + tel1 + ", tel2=" + tel2 + ", email=" + email
				+ ", useCompanyAddress=" + useCompanyAddress + ", firmenName=" + firmenName + ", firmenAnschrift="
				+ firmenAnschrift + ", age=" + age + ", active=" + active + ", statusText=" + statusText
				+ ", bemerkungen=" + bemerkungen + ", contracts=" + contracts + "]";
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
	private Set<Contract> contracts = new HashSet<>();

}
