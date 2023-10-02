package com.ebox3.server.doc;
import java.util.List;


public class Context {

  private LCustomer cu;
  private LContract co;
  private List<LEbox> bx;
  private List<LEbox> bb;
  private List<LEbox> bp;
  private List <LElectricPeriod> ep;
  private List <TenantBilling> tb;
  private List <LCustomer> cul;
  private EBillDetails eb;
  private String listBoxNumber="";
  private String listStandplatzNumber="";
  private DateText currentDate;
  private String jahr;
  private String bemerkungen;
  private LPayment cal;
  private LAdditionalCosts ac;
  private List<LAdditionalCosts> lac;
  private List<LMwst>lmwstb;
  private List<LMwst>lmwsts;
  private List<LMwst>lmwstr;
  private String quarter;
  private Money totMwstPriceBox;
  private Money totMwstPriceStrom;
  private Money totMwstPriceRechnung;
  
  private Money totNettoPriceStrom;
  private Money totNettoPriceBox;
  private Money totNettoPriceRechnung;
  
  private Money totBruttoPriceStrom;
  private Money totBruttoPriceBox;
  private Money totBruttoPriceRechnung;
 
  
  public String getJahr() {
    return jahr;
  }
  public void setJahr(String jahr) {
    this.jahr = jahr;
  }
  public Money getSumPriceBrutto() {
    return sumPriceBrutto;
  }
  public void setSumPriceBrutto(Money sumPriceBrutto) {
    this.sumPriceBrutto = sumPriceBrutto;
  }

  public String getBemerkungen() {
    return bemerkungen;
  }
  public void setBemerkungen(String bemerkungen) {
    this.bemerkungen = bemerkungen;
  }

  private Money sumPriceBrutto;

  public LCustomer getCu() {
    return cu;
  }
  public void setCu(LCustomer cu) {
    this.cu = cu;
  }
  public LContract getCo() {
    return co;
  }
  public void setCo(LContract lcontract) {
    this.co = lcontract;
  }
  public List<LEbox> getBx() {
    return bx;
  }
  public void setBx(List<LEbox> bx) {
    this.bx = bx;
  }
  public List<LEbox> getBp() {
    return bp;
  }
  public void setBp(List<LEbox> bp) {
    this.bp = bp;
  }
  public List<LEbox> getBb() {
    return bb;
  }
  public void setBb(List<LEbox> bb) {
    this.bb = bb;
  }
  public List<LElectricPeriod> getEp() {
    return ep;
  }
  
  public void setEp(List<LElectricPeriod> ep) {
    this.ep = ep;
  }
  
  public EBillDetails getEb() {
    return eb;
  }
  public void setEb(EBillDetails eb) {
    this.eb = eb;
  }
  
  public List <TenantBilling> getTb() {
    return tb;
  }
  
  public void setTb(List<TenantBilling> tb) {
    this.tb = tb;
  }
  
  public String getListBoxNumber() {
    return this.listBoxNumber;
  }
  
  public String getListStandplatzNumber() {
    return this.listStandplatzNumber;
  }
  
  public void setListBoxNumber(String listBoxNumber) {
    this.listBoxNumber = listBoxNumber;
  }
  
  public void setListStandplatzNumber(String listStandplatzNumber) {
    this.listStandplatzNumber = listStandplatzNumber;
  }
    
  public DateText getCurrentDate() {
    return currentDate;
  }
  public void setCurrentDate(DateText currentDate) {
    this.currentDate = currentDate;
  }
  public List<LCustomer> getCul() {
    return cul;
  }
  public void setCul(List<LCustomer> cul) {
    this.cul = cul;
  }
  public LPayment getCal() {
    return cal;
  }
  public void setCal(LPayment cal) {
    this.cal = cal;
  }
  public LAdditionalCosts getAc() {
    return ac;
  }
  public void setAc(LAdditionalCosts ac) {
    this.ac = ac;
  }
  public List<LAdditionalCosts> getLac() {
    return lac;
  }
  public void setLac(List<LAdditionalCosts> lac) {
    this.lac = lac;
  }
  public List<LMwst> getLmwstb() {
    return lmwstb;
  }
  public void setLmwstb(List<LMwst> lmwstb) {
    this.lmwstb = lmwstb;
  }
  public List<LMwst> getLmwsts() {
    return lmwsts;
  }
  public void setLmwsts(List<LMwst> lmwsts) {
    this.lmwsts = lmwsts;
  }
  public List<LMwst> getLmwstr() {
    return lmwstr;
  }
  public void setLmwstr(List<LMwst> lmwstr) {
    this.lmwstr = lmwstr;
  }
  public String getQuarter() {
    return quarter;
  }
  public void setQuarter(String quarter) {
    this.quarter = quarter;
  }
  public Money getTotMwstPriceBox() {
    return totMwstPriceBox;
  }
  public void setTotMwstPriceBox(Money totMwstPriceBox) {
    this.totMwstPriceBox = totMwstPriceBox;
  }
  public Money getTotMwstPriceStrom() {
    return totMwstPriceStrom;
  }
  public void setTotMwstPriceStrom(Money totMwstPriceStrom) {
    this.totMwstPriceStrom = totMwstPriceStrom;
  }
  public Money getTotMwstPriceRechnung() {
    return totMwstPriceRechnung;
  }
  public void setTotMwstPriceRechnung(Money totMwstPriceRechnung) {
    this.totMwstPriceRechnung = totMwstPriceRechnung;
  }
  public Money getTotNettoPriceStrom() {
    return totNettoPriceStrom;
  }
  public void setTotNettoPriceStrom(Money totNettoPriceStrom) {
    this.totNettoPriceStrom = totNettoPriceStrom;
  }
  public Money getTotNettoPriceBox() {
    return totNettoPriceBox;
  }
  public void setTotNettoPriceBox(Money totNettoPriceBox) {
    this.totNettoPriceBox = totNettoPriceBox;
  }
  public Money getTotNettoPriceRechnung() {
    return totNettoPriceRechnung;
  }
  public void setTotNettoPriceRechnung(Money totNettoPriceRechnung) {
    this.totNettoPriceRechnung = totNettoPriceRechnung;
  }
  public Money getTotBruttoPriceStrom() {
    return totBruttoPriceStrom;
  }
  public void setTotBruttoPriceStrom(Money totBruttoPriceStrom) {
    this.totBruttoPriceStrom = totBruttoPriceStrom;
  }
  public Money getTotBruttoPriceBox() {
    return totBruttoPriceBox;
  }
  public void setTotBruttoPriceBox(Money totBruttoPriceBox) {
    this.totBruttoPriceBox = totBruttoPriceBox;
  }
  public Money getTotBruttoPriceRechnung() {
    return totBruttoPriceRechnung;
  }
  public void setTotBruttoPriceRechnung(Money totBruttoPriceRechnung) {
    this.totBruttoPriceRechnung = totBruttoPriceRechnung;
  }
}
