package com.ebox3.server.doc;


public class LPayment {
  
  private Money sumJan;
  private Money sumFeb;
  private Money sumMar;
  private Money sumApr;
  private Money sumMai;
  private Money sumJun;
  private Money sumJul;
  private Money sumAug;
  private Money sumSep;
  private Money sumOct;
  private Money sumNov;
  private Money sumDec;
  private Money sumTotMon;
  private Money sumTotKaut;
  private String jahr;

  public Money getSumJan() {
    return sumJan;
  }
  public void setSumJan(Money sumJan) {
    this.sumJan = sumJan;
  }
  public Money getSumFeb() {
    return sumFeb;
  }
  public void setSumFeb(Money sumFeb) {
    this.sumFeb = sumFeb;
  }
  public Money getSumMar() {
    return sumMar;
  }
  public void setSumMar(Money sumMar) {
    this.sumMar = sumMar;
  }
  public Money getSumApr() {
    return sumApr;
  }
  public void setSumApr(Money sumApr) {
    this.sumApr = sumApr;
  }
  public Money getSumMai() {
    return sumMai;
  }
  public void setSumMai(Money sumMai) {
    this.sumMai = sumMai;
  }
  public Money getSumJun() {
    return sumJun;
  }
  public void setSumJun(Money sumJun) {
    this.sumJun = sumJun;
  }
  public Money getSumJul() {
    return sumJul;
  }
  public void setSumJul(Money sumJul) {
    this.sumJul = sumJul;
  }
  public Money getSumAug() {
    return sumAug;
  }
  public void setSumAug(Money sumAug) {
    this.sumAug = sumAug;
  }
  public Money getSumSep() {
    return sumSep;
  }
  public void setSumSep(Money sumSep) {
    this.sumSep = sumSep;
  }
  public Money getSumOct() {
    return sumOct;
  }
  public void setSumOct(Money sumOct) {
    this.sumOct = sumOct;
  }
  public Money getSumNov() {
    return sumNov;
  }
  public void setSumNov(Money sumNov) {
    this.sumNov = sumNov;
  }
  public Money getSumDec() {
    return sumDec;
  }
  public void setSumDec(Money sumDec) {
    this.sumDec = sumDec;
  }
  public Money getSumTotMon() {
    return sumTotMon;
  }
  public void setSumTotMon(Money sumTotMon) {
    this.sumTotMon = sumTotMon;
  }
  public Money getSumTotKaut() {
    return sumTotKaut;
  }
  public void setSumTotKaut(Money sumTotKaut) {
    this.sumTotKaut = sumTotKaut;
  }
  public String getJahr() {
    return jahr;
  }
  public void setJahr(String jahr) {
    this.jahr = jahr;
  }
  @Override
  public String toString() {
    return "LPayment [sumJan=" + sumJan + ", sumFeb=" + sumFeb + ", sumMar=" + sumMar + ", sumApr=" + sumApr
        + ", sumMai=" + sumMai + ", sumJun=" + sumJun + ", sumJul=" + sumJul + ", sumAug=" + sumAug + ", sumSep="
        + sumSep + ", sumOct=" + sumOct + ", sumNov=" + sumNov + ", sumDec=" + sumDec + ", sumTotMon=" + sumTotMon
        + ", sumTotKaut=" + sumTotKaut + ", jahr=" + jahr + "]";
  }
}
