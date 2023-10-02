package com.ebox3.server.doc;

public class EBillDetails {
  
  private Money pricePorto;
  private Money totPriceBoxes;
  private Money priceMwst;
  private Double MwSt;
  private Money totGutEbox;
  private Money totGutMieter;
  private String guthaben;
  
  public Money getPricePorto() {
    return pricePorto;
  }
  public void setPricePorto(Money pricePorto) {
    this.pricePorto = pricePorto;
  }
  public Money getTotPriceBoxes() {
    return totPriceBoxes;
  }
  public void setTotPriceBoxes(Money totPriceBoxes) {
    this.totPriceBoxes = totPriceBoxes;
  }
  public Money getPriceMwst() {
    return priceMwst;
  }
  public void setPriceMwst(Money priceMwst) {
    this.priceMwst = priceMwst;
  }
  public Double getMwSt() {
    return MwSt;
  }
  public void setMwSt(Double mwSt) {
    MwSt = mwSt;
  }
  public Money getTotGutEbox() {
    return totGutEbox;
  }
  public void setTotGutEbox(Money totGutEbox) {
    this.totGutEbox = totGutEbox;
  }
  public Money getTotGutMieter() {
    return totGutMieter;
  }
  public void setTotGutMieter(Money totGutMieter) {
    this.totGutMieter = totGutMieter;
  }
  public String getGuthaben() {
    return guthaben;
  }
  public void setGuthaben(String guthaben) {
    this.guthaben = guthaben;
  }
}
