package com.ebox3.server.model.dto;

public class OutstandPaymentDTO {
  
  private Long customerId;
  private Long contractId;
  private String customer;
  private String contractDuration;
  private String month;
  private Double payment;
  private String boxNumbers;
  private Long payememtDatePriceId;
  
  
  public Long getCustomerId() {
    return customerId;
  }
  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }
  public Long getContractId() {
    return contractId;
  }
  public void setContractId(Long contractId) {
    this.contractId = contractId;
  }
  public String getCustomer() {
    return customer;
  }
  public void setCustomer(String customer) {
    this.customer = customer;
  }
  public String getContractDuration() {
    return contractDuration;
  }
  public void setContractDuration(String contractDuration) {
    this.contractDuration = contractDuration;
  }
  public String getMonth() {
    return month;
  }
  public void setMonth(String month) {
    this.month = month;
  }
  public Double getPayment() {
    return payment;
  }
  public void setPayment(Double payment) {
    this.payment = payment;
  }
  public String getBoxNumbers() {
    return boxNumbers;
  }
  public void setBoxNumbers(String boxNumbers) {
    this.boxNumbers = boxNumbers;
  }
  public Long getPayememtDatePriceId() {
    return payememtDatePriceId;
  }
  public void setPayememtDatePriceId(Long payememtDatePriceId) {
    this.payememtDatePriceId = payememtDatePriceId;
  }
  

}