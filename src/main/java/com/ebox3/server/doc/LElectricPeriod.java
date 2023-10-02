package com.ebox3.server.doc;

public class LElectricPeriod {
  
  private Long boxNumber;
  private String boxNumberText;
  private Double startkWh;
  private Double endkWh;
  private Money pricekWh;
  private Double diffkWh;
  private Money sumDiffkWh;
  private String monVorZahlung;
  private Money guthabenKunde;
  private String guthabenKundeText;
  private Money vorZahl;
  private Money vorZahlSum;
  private Money totPriceBox;
  private DateText fromDate;
  private DateText toDate;
  private Money priceBearbeitung;
  private String bemerkung;
  private String elektroZaehlerNr;
  private String anschrift;
  private String statusText;
  private String zahlungEingegangen;
  
  public Long getBoxNumber() {
    return boxNumber;
  }
  public void setBoxNumber(Long boxNumber) {
    this.boxNumber = boxNumber;
  }
  public Double getStartkWh() {
    return startkWh;
  }
  public void setStartkWh(Double startkWh) {
    this.startkWh = startkWh;
  }
  public Double getEndkWh() {
    return endkWh;
  }
  public void setEndkWh(Double endkWh) {
    this.endkWh = endkWh;
  }
  public Money getPricekWh() {
    return pricekWh;
  }
  public void setPricekWh(Money pricekWh) {
    this.pricekWh = pricekWh;
  }
  public Double getDiffkWh() {
    return diffkWh;
  }
  public void setDiffkWh(Double diffkWh) {
    this.diffkWh = diffkWh;
  }
  public Money getSumDiffkWh() {
    return sumDiffkWh;
  }
  public void setSumDiffkWh(Money sumDiffkWh) {
    this.sumDiffkWh = sumDiffkWh;
  }
  public String getMonVorZahlung() {
    return monVorZahlung;
  }
  public void setMonVorZahlung(String monVorZahlung) {
    this.monVorZahlung = monVorZahlung;
  }
  public Money getVorZahl() {
    return vorZahl;
  }
  public void setVorZahl(Money vorZahl) {
    this.vorZahl = vorZahl;
  }
  public Money getVorZahlSum() {
    return vorZahlSum;
  }
  public void setVorZahlSum(Money vorZahlSum) {
    this.vorZahlSum = vorZahlSum;
  }
  public Money getTotPriceBox() {
    return totPriceBox;
  }
  public void setTotPriceBox(Money totPriceBox) {
    this.totPriceBox = totPriceBox;
  }
  
  public void setFromDate(DateText fromDate) {
    this.fromDate = fromDate;
  }
  
  public DateText getFromDate() {
    return this.fromDate; 
  }
  
  public void setToDate(DateText toDate) {
    this.toDate = toDate;
  }
  
  public DateText getToDate() {
    return this.toDate;
  }
  public Money getPriceBearbeitung() {
    return priceBearbeitung;
  }
  public void setPriceBearbeitung(Money priceBearbeitung) {
    this.priceBearbeitung = priceBearbeitung;
  }
  public Money getGuthabenKunde() {
    return guthabenKunde;
  }
  public void setGuthabenKunde(Money guthabenKunde) {
    this.guthabenKunde = guthabenKunde;
  }
  public String getGuthabenKundeText() {
    return guthabenKundeText;
  }
  public void setGuthabenKundeText(String guthabenKundeText) {
    this.guthabenKundeText = guthabenKundeText;
  }
  public String getBemerkung() {
    return bemerkung;
  }
  public void setBemerkung(String bemerkung) {
    this.bemerkung = bemerkung;
  }
  public String getElektroZaehlerNr() {
    return elektroZaehlerNr;
  }
  public void setElektroZaehlerNr(String elektroZaehlerNr) {
    this.elektroZaehlerNr = elektroZaehlerNr;
  }
  public String getAnschrift() {
    return anschrift;
  }
  public void setAnschrift(String anschrift) {
    this.anschrift = anschrift;
  }
  public String getStatusText() {
    return statusText;
  }
  public void setStatusText(String statusText) {
    this.statusText = statusText;
  }
  public String getBoxNumberText() {
    return this.boxNumberText;
  }
  public void setBoxNumberText(String boxNumberText) {
    this.boxNumberText = boxNumberText;
  }
  public String getZahlungEingegangen() {
    return zahlungEingegangen;
  }
  public void setZahlungEingegangen(String zahlungEingegangen) {
    this.zahlungEingegangen = zahlungEingegangen;
  }
  @Override
  public boolean equals(Object obj) {
      if (obj instanceof LElectricPeriod) {
          return ((LElectricPeriod) obj).boxNumber == this.boxNumber;
      }
      return false;
  }

  @Override
  public int hashCode() {
    return this.boxNumber.intValue();
  }   

  
  
}
