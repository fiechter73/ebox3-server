package com.ebox3.server.doc;


public class LMwst {

  private String customer;
  private String month;
  private DateText   payDate;
  private Money nettoPrice;
  private Money bruttoPrice;
  private Money mwstPrice;
  private String boxes;
  
  
  
  public String getCustomer() {
    return customer;
  }
  public void setCustomer(String customer) {
    this.customer = customer;
  }
  public String getMonth() {
    return month;
  }
  public void setMonth(String month) {
    this.month = month;
  }
  public DateText getPayDate() {
    return payDate;
  }
  public void setPayDate(DateText payDate) {
    this.payDate = payDate;
  }
  public Money getNettoPrice() {
    return nettoPrice;
  }
  public void setNettoPrice(Money nettoPrice) {
    this.nettoPrice = nettoPrice;
  }
  public Money getBruttoPrice() {
    return bruttoPrice;
  }
  public void setBruttoPrice(Money bruttoPrice) {
    this.bruttoPrice = bruttoPrice;
  }
  public Money getMwstPrice() {
    return mwstPrice;
  }
  public void setMwstPrice(Money mwstPrice) {
    this.mwstPrice = mwstPrice;
  }
  public String getBoxes() {
    return boxes;
  }
  public void setBoxes(String boxes) {
    this.boxes = boxes;
  }

}
