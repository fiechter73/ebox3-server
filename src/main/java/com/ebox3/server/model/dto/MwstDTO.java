package com.ebox3.server.model.dto;

import java.util.Date;

public class MwstDTO {
  
  private String customer;
  private String month;
  private Date   payDate;
  private Double mwstPrice;
  private Double nettoPrice;
  private Double bruttoPrice;
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
  public Date getPayDate() {
    return payDate;
  }
  public void setPayDate(Date payDate) {
    this.payDate = payDate;
  }
  public Double getMwstPrice() {
    return mwstPrice;
  }
  public void setMwstPrice(Double mwstPrice) {
    this.mwstPrice = mwstPrice;
  }
  public Double getNettoPrice() {
    return nettoPrice;
  }
  public void setNettoPrice(Double nettoPrice) {
    this.nettoPrice = nettoPrice;
  }
  public Double getBruttoPrice() {
    return bruttoPrice;
  }
  public void setBruttoPrice(Double bruttoPrice) {
    this.bruttoPrice = bruttoPrice;
  }
  public String getBoxes() {
    return boxes;
  }
  public void setBoxes(String boxes) {
    this.boxes = boxes;
  }
   
}
