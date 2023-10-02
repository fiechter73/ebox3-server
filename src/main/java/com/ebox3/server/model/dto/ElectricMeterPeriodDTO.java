package com.ebox3.server.model.dto;

import java.util.List;


public class ElectricMeterPeriodDTO {

  private Long id;
  private Long electricMeterNumber;
  private String electricMeterName;
  private String electricMeterBemerkungen;
  private List<ElectricPeriodDTO> electricPeriodDTO;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public List<ElectricPeriodDTO> getElectricPeriodDTO() {
    return electricPeriodDTO;
  }

  public void setElectricPeriodDTO(List<ElectricPeriodDTO> electricPeriodDTO) {
    this.electricPeriodDTO = electricPeriodDTO;
  }
  

  public String getElectricMeterBemerkungen() {
    return electricMeterBemerkungen;
  }

  public void setElectricMeterBemerkungen(String electricMeterBemerkungen) {
    this.electricMeterBemerkungen = electricMeterBemerkungen;
  }
  

  @Override
public String toString() {
	return "ElectricMeterPeriodDTO [id=" + id + ", electricMeterNumber=" + electricMeterNumber + ", electricMeterName="
			+ electricMeterName + ", electricMeterBemerkungen=" + electricMeterBemerkungen + ", electricPeriodDTO="
			+ electricPeriodDTO + "]";
}

@Override
  public boolean equals(Object obj) {
    if (obj instanceof ElectricMeterPeriodDTO) {
      return ((ElectricMeterPeriodDTO) obj).id == this.id;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return this.id.intValue();
  }


}
