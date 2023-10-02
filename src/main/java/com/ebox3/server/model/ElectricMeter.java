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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
@Table(name = "electricMeter")
public class ElectricMeter {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "electricMeterNumber")
	private Long electricMeterNumber;

	@Column(name = "electricMeterName")
	private String electricMeterName;

	@Column(name = "electricMeterBemerkungen")
	private String electricMeterBemerkungen;

	public ElectricMeter() {
	}

	public ElectricMeter(Long id, Long electricMeterNumber, String electricMeterName, String electricMeterBemerkungen,
			Ebox ebox, Set<ElectricPeriod> electricPeriods) {
		this.id = id;
		this.electricMeterNumber = electricMeterNumber;
		this.electricMeterName = electricMeterName;
		this.electricMeterBemerkungen = electricMeterBemerkungen;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getElectricMeterName() {
		return electricMeterName;
	}

	public void setElectricMeterName(String electricMeterName) {
		this.electricMeterName = electricMeterName;
	}

	public Long getElectricMeterNumber() {
		return electricMeterNumber;
	}

	public void setElectricMeterNumber(Long electricMeterNumber) {
		this.electricMeterNumber = electricMeterNumber;
	}

	public Ebox getEbox() {
		return ebox;
	}

	public void setEbox(Ebox ebox) {
		this.ebox = ebox;
	}

	public Set<ElectricPeriod> getElectricPeriods() {
		return electricPeriods;
	}

	public void setElectricPeriods(Set<ElectricPeriod> electricPeriods) {
		this.electricPeriods = electricPeriods;
	}

	public String getElectricMeterBemerkungen() {
		return electricMeterBemerkungen;
	}

	public void setElectricMeterBemerkungen(String electricMeterBemerkungen) {
		this.electricMeterBemerkungen = electricMeterBemerkungen;
	}

	@Override
	public String toString() {
		return "ElectricMeter [id=" + id + ", electricMeterNumber=" + electricMeterNumber + ", electricMeterName="
				+ electricMeterName + ", electricMeterBemerkungen=" + electricMeterBemerkungen + ", ebox=" + ebox
				+ ", electricPeriods=" + electricPeriods + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ElectricMeter) {
			return ((ElectricMeter) obj).id == this.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.id.intValue();
	}

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "eboxId_FK", referencedColumnName = "id")
	private Ebox ebox;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "electricMeter")
	private Set<ElectricPeriod> electricPeriods = new HashSet<>();

}
