package com.ebox3.server.doc;


public class LContract {
  
  private DateText startDate;
  private DateText endDate;
  private DateText openEndedContractDate;
  private String statusText;
  private String openEndedContract;
  private String nutzung;
  private String monatsfrist;
 
  public DateText getStartDate() {
    return this.startDate;
  }
  public void setStartDate(DateText startDate) {
    this.startDate = startDate;
  }
  public DateText getEndDate() {
    return this.endDate;
  }
  public void setEndDate(DateText endDate) {
    this.endDate = endDate;
  }
  public String getStatusText() {
    return statusText;
  }
  public void setStatusText(String statusText) {
    this.statusText = statusText;
  }
  public String getOpenEndedContract() {
    return openEndedContract;
  }
  public void setOpenEndedContract(String openEndedContract) {
    this.openEndedContract = openEndedContract;
  }
  public DateText getOpenEndedContractDate() {
    return openEndedContractDate;
  }
  public void setOpenEndedContractDate(DateText openEndedContractDate) {
    this.openEndedContractDate = openEndedContractDate;
  }
  public String getNutzung() {
    return nutzung;
  }
  public void setNutzung(String nutzung) {
    this.nutzung = nutzung;
  }
  public String getMonatsfrist() {
    return monatsfrist;
  }
  public void setMonatsfrist(String monatsfrist) {
    this.monatsfrist = monatsfrist;
  }
  
  
  
}
