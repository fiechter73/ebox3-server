package com.ebox3.server.doc;

public class TenantBilling {

  private Money jan;
  private Money feb;
  private Money mar;
  private Money apr;
  private Money mai;
  private Money jun;
  private Money jul;
  private Money aug;
  private Money sep;
  private Money oct;
  private Money nov;
  private Money dec;
  private Money kau;
  private String jand;
  private String febd;
  private String mard;
  private String aprd;
  private String maid;
  private String jund;
  private String juld;
  private String augd;
  private String sepd;
  private String octd;
  private String novd;
  private String decd;
  private String kaud;
  private Money tot;
  private String customer;
  private String buiding;
  private String jahr;
  private Money mon;
  private String box;
  private String status;
  private String lastName;
  private String kauR;
  private Money kauRP;
  
  
  public Money getMon() {
    return mon;
  }
  public void setMon(Money mon) {
    this.mon = mon;
  }
  public String getBox() {
    return box;
  }
  public void setBox(String box) {
    this.box = box;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public Money getJan() {
    return jan;
  }
  public void setJan(Money jan) {
    this.jan = jan;
  }
  public Money getFeb() {
    return feb;
  }
  public void setFeb(Money feb) {
    this.feb = feb;
  }
  public Money getMar() {
    return mar;
  }
  public void setMar(Money mar) {
    this.mar = mar;
  }
  
  public Money getApr() {
    return apr;
  }
  public void setApr(Money apr) {
    this.apr = apr;
  }
  public Money getMai() {
    return mai;
  }
  public void setMai(Money mai) {
    this.mai = mai;
  }
  public Money getJun() {
    return jun;
  }
  public void setJun(Money jun) {
    this.jun = jun;
  }
  public Money getJul() {
    return jul;
  }
  public void setJul(Money jul) {
    this.jul = jul;
  }
  public Money getAug() {
    return aug;
  }
  public void setAug(Money aug) {
    this.aug = aug;
  }
  public Money getSep() {
    return sep;
  }
  public void setSep(Money sep) {
    this.sep = sep;
  }
  public Money getOct() {
    return oct;
  }
  public void setOct(Money oct) {
    this.oct = oct;
  }
  public Money getNov() {
    return nov;
  }
  public void setNov(Money nov) {
    this.nov = nov;
  }
  public Money getDec() {
    return dec;
  }
  public void setDec(Money dec) {
    this.dec = dec;
  }
  
  public Money getTot() {
    return tot;
  }
  public void setTot(Money tot) {
    this.tot = tot;
  }
  public String getCustomer() {
    return customer;
  }
  public void setCustomer(String customer) {
    this.customer = customer;
  }
  public String getBuiding() {
    return buiding;
  }
  public void setBuiding(String buiding) {
    this.buiding = buiding;
  }
  public String getJahr() {
    return jahr;
  }
  public void setJahr(String jahr) {
    this.jahr = jahr;
  }
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getJand() {
    return jand;
  }
  public void setJand(String jand) {
    this.jand = jand;
  }
  public String getFebd() {
    return febd;
  }
  public void setFebd(String febd) {
    this.febd = febd;
  }
  public String getMard() {
    return mard;
  }
  public void setMard(String mard) {
    this.mard = mard;
  }
  public String getAprd() {
    return aprd;
  }
  public void setAprd(String aprd) {
    this.aprd = aprd;
  }
  public String getMaid() {
    return maid;
  }
  public void setMaid(String maid) {
    this.maid = maid;
  }
  public String getJund() {
    return jund;
  }
  public void setJund(String jund) {
    this.jund = jund;
  }
  public String getJuld() {
    return juld;
  }
  public void setJuld(String juld) {
    this.juld = juld;
  }
  public String getAugd() {
    return augd;
  }
  public void setAugd(String augd) {
    this.augd = augd;
  }
  public String getSepd() {
    return sepd;
  }
  public void setSepd(String sepd) {
    this.sepd = sepd;
  }
  public String getOctd() {
    return octd;
  }
  public void setOctd(String octd) {
    this.octd = octd;
  }
  public String getNovd() {
    return novd;
  }
  public void setNovd(String novd) {
    this.novd = novd;
  }
  public String getDecd() {
    return decd;
  }
  public void setDecd(String decd) {
    this.decd = decd;
  }
  public Money getKau() {
    return kau;
  }
  public void setKau(Money kau) {
    this.kau = kau;
  }
  public String getKaud() {
    return kaud;
  }
  public void setKaud(String kaud) {
    this.kaud = kaud;
  }
  public String getKauR() {
    return kauR;
  }
  public void setKauR(String kauR) {
    this.kauR = kauR;
  }
  
  public Money getKauRP() {
	return kauRP;
  }
  public void setKauRP(Money kauRP) {
	this.kauRP = kauRP;
  }
@Override
public String toString() {
	return "TenantBilling [jan=" + jan + ", feb=" + feb + ", mar=" + mar + ", apr=" + apr + ", mai=" + mai + ", jun="
			+ jun + ", jul=" + jul + ", aug=" + aug + ", sep=" + sep + ", oct=" + oct + ", nov=" + nov + ", dec=" + dec
			+ ", kau=" + kau + ", jand=" + jand + ", febd=" + febd + ", mard=" + mard + ", aprd=" + aprd + ", maid="
			+ maid + ", jund=" + jund + ", juld=" + juld + ", augd=" + augd + ", sepd=" + sepd + ", octd=" + octd
			+ ", novd=" + novd + ", decd=" + decd + ", kaud=" + kaud + ", tot=" + tot + ", customer=" + customer
			+ ", buiding=" + buiding + ", jahr=" + jahr + ", mon=" + mon + ", box=" + box + ", status=" + status
			+ ", lastName=" + lastName + ", kauR=" + kauR + ", kauRP=" + kauRP + "]";
}
  
  

}
