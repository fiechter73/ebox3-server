package com.ebox3.server.doc;

import java.util.List;

public class LAdditionalCosts  {
  
  
  private String rechnungsart;
  private String statusText;
  private String title;
  private String text;
  private Money mwstPrice;
  private Double mwstSatz;
  private Money sumBruttoPrice;
  private String mwstText;
  private String totSumText;
  private List<ItemObj> itemList;
  private DateText   createdDate;
  private DateText   updatedDate;
  private DateText contractStartDate;
  private DateText contractEndDate;
  private String contractStatusText;
  private String contractQuitPeriode;
  private String anschrift;
  
  
  
  public String getRechnungsart() {
    return rechnungsart;
  }
  public void setRechnungsart(String rechnungsart) {
    this.rechnungsart = rechnungsart;
  }
  public String getStatusText() {
    return statusText;
  }
  public void setStatusText(String statusText) {
    this.statusText = statusText;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getText() {
    return text;
  }
  public void setText(String text) {
    this.text = text;
  }
  public List<ItemObj> getItemList() {
    return itemList;
  }
  public void setItemList(List<ItemObj> itemList) {
    this.itemList = itemList;
  }
  public Money getMwstPrice() {
    return mwstPrice;
  }
  public void setMwstPrice(Money mwstPrice) {
    this.mwstPrice = mwstPrice;
  }
  public Money getSumBruttoPrice() {
    return sumBruttoPrice;
  }
  public void setSumBruttoPrice(Money sumBruttoPrice) {
    this.sumBruttoPrice = sumBruttoPrice;
  }
  public DateText getCreatedDate() {
    return createdDate;
  }
  public void setCreatedDate(DateText createdDate) {
    this.createdDate = createdDate;
  }
  public DateText getUpdatedDate() {
    return updatedDate;
  }
  public void setUpdatedDate(DateText updatedDate) {
    this.updatedDate = updatedDate;
  }
  public DateText getContractStartDate() {
    return contractStartDate;
  }
  public void setContractStartDate(DateText contractStartDate) {
    this.contractStartDate = contractStartDate;
  }
  public DateText getContractEndDate() {
    return contractEndDate;
  }
  public void setContractEndDate(DateText contractEndDate) {
    this.contractEndDate = contractEndDate;
  }
  public String getContractStatusText() {
    return contractStatusText;
  }
  public void setContractStatusText(String contractStatusText) {
    this.contractStatusText = contractStatusText;
  }
  public String getContractQuitPeriode() {
    return contractQuitPeriode;
  }
  public void setContractQuitPeriode(String contractQuitPeriode) {
    this.contractQuitPeriode = contractQuitPeriode;
  }
  public String getMwstText() {
    return mwstText;
  }
  public void setMwstText(String mwStText) {
    this.mwstText = mwStText;
  }
  public String getTotSumText() {
    return totSumText;
  }
  public void setTotSumText(String totSumText) {
    this.totSumText = totSumText;
  }
  public Double getMwstSatz() {
    return mwstSatz;
  }
  public void setMwstSatz(Double mwstSatz) {
    this.mwstSatz = mwstSatz;
  }
  public String getAnschrift() {
    return anschrift;
  }
  public void setAnschrift(String anschrift) {
    this.anschrift = anschrift;
  }
  
}
