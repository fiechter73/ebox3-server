package com.ebox3.server.doc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebox3.server.model.Payment;
import com.ebox3.server.model.PaymentDatePrice;

public class GenerateTenantList {

  private final Log logger = LogFactory.getLog(getClass());

  public boolean generateDocTenantList(final List<PaymentDatePrice> paymentDatePriceList, List<Payment> paymentList,
      InputStream template, OutputStream out) {
    try {
      final Context con = buildTenantBill(paymentDatePriceList, paymentList);
      Stamper stamp = new Stamper();
      stamp.stamp(template, con, out);
      stamp.close();
    } catch (final Exception e) {
      logger.error("Exception: " + e.getMessage());
    } finally {
      try {
        out.close();
        template.close();
        logger.info("KB TenantList: "
            + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
      } catch (final IOException e) {
        logger.error("IOException: " + e.getMessage());
      }
    }
    return true;
  }

  private Context buildTenantBill(final List<PaymentDatePrice> paymentDatePriceList, final List<Payment> paymentList) {
    final LPayment lPayment = new LPayment();
    final Context context = new Context();
    context.setTb(addJahrPayment(paymentDatePriceList));
    context.setJahr(paymentDatePriceList.get(0).getPayment().getJahr());
    lPayment.setSumJan(getSumJan(paymentDatePriceList) != null ? getSumJan(paymentDatePriceList) : new Money(0.00));
    lPayment.setSumFeb(getSumFeb(paymentDatePriceList) != null ? getSumFeb(paymentDatePriceList) : new Money(0.00));
    lPayment.setSumMar(getSumMar(paymentDatePriceList) != null ? getSumMar(paymentDatePriceList) : new Money(0.00));
    lPayment.setSumApr(getSumApr(paymentDatePriceList) != null ? getSumApr(paymentDatePriceList) : new Money(0.00));
    lPayment.setSumMai(getSumMai(paymentDatePriceList) != null ? getSumMai(paymentDatePriceList) : new Money(0.00));
    lPayment.setSumJun(getSumJun(paymentDatePriceList) != null ? getSumJun(paymentDatePriceList) : new Money(0.00));
    lPayment.setSumJul(getSumJul(paymentDatePriceList) != null ? getSumJul(paymentDatePriceList) : new Money(0.00));
    lPayment.setSumAug(getSumAug(paymentDatePriceList) != null ? getSumAug(paymentDatePriceList) : new Money(0.00));
    lPayment.setSumSep(getSumSep(paymentDatePriceList) != null ? getSumSep(paymentDatePriceList) : new Money(0.00));
    lPayment.setSumOct(getSumOct(paymentDatePriceList) != null ? getSumOct(paymentDatePriceList) : new Money(0.00));
    lPayment.setSumNov(getSumNov(paymentDatePriceList) != null ? getSumNov(paymentDatePriceList) : new Money(0.00));
    lPayment.setSumDec(getSumDec(paymentDatePriceList) != null ? getSumDec(paymentDatePriceList) : new Money(0.00));
    lPayment.setSumTotMon(getSumTotMon(paymentList) != null ? getSumTotMon(paymentList) : new Money(0.00));
    lPayment.setSumTotKaut(getSumTotKaut(paymentList) != null ? getSumTotKaut(paymentList) : new Money(0.00));

    context.setCal(lPayment);
    return context;
  }

  private List<TenantBilling> addJahrPayment(final List<PaymentDatePrice> paymentDatePriceList) {

    final ArrayList<TenantBilling> tbList = new ArrayList<TenantBilling>();

    String pattern = "dd.MM.yy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    paymentDatePriceList.forEach(element -> {
      final TenantBilling tb = new TenantBilling();
      tb.setJan(element.getBruttoJanPrice() != null ? new Money(element.getBruttoJanPrice()) : new Money(0.00));
      tb.setJand(
          element.getBruttoJanDate() != null ? new String(simpleDateFormat.format(element.getBruttoJanDate())) : "");
      tb.setFeb(element.getBruttoFebPrice() != null ? new Money(element.getBruttoFebPrice()) : new Money(0.00));
      tb.setFebd(
          element.getBruttoFebDate() != null ? new String(simpleDateFormat.format(element.getBruttoFebDate())) : "");
      tb.setMar(element.getBruttoMarPrice() != null ? new Money(element.getBruttoMarPrice()) : new Money(0.00));
      tb.setMard(
          element.getBruttoMarDate() != null ? new String(simpleDateFormat.format(element.getBruttoMarDate())) : "");
      tb.setApr(element.getBruttoAprPrice() != null ? new Money(element.getBruttoAprPrice()) : new Money(0.00));
      tb.setAprd(
          element.getBruttoAprDate() != null ? new String(simpleDateFormat.format(element.getBruttoAprDate())) : "");
      tb.setMai(element.getBruttoMaiPrice() != null ? new Money(element.getBruttoMaiPrice()) : new Money(0.00));
      tb.setMaid(
          element.getBruttoMaiDate() != null ? new String(simpleDateFormat.format(element.getBruttoMaiDate())) : "");
      tb.setJun(element.getBruttoJunPrice() != null ? new Money(element.getBruttoJunPrice()) : new Money(0.00));
      tb.setJund(
          element.getBruttoJunDate() != null ? new String(simpleDateFormat.format(element.getBruttoJunDate())) : "");
      tb.setJul(element.getBruttoJulPrice() != null ? new Money(element.getBruttoJulPrice()) : new Money(0.00));
      tb.setJuld(
          element.getBruttoJulDate() != null ? new String(simpleDateFormat.format(element.getBruttoJulDate())) : "");
      tb.setAug(element.getBruttoAugPrice() != null ? new Money(element.getBruttoAugPrice()) : new Money(0.00));
      tb.setAugd(
          element.getBruttoAugDate() != null ? new String(simpleDateFormat.format(element.getBruttoAugDate())) : "");
      tb.setSep(element.getBruttoSepPrice() != null ? new Money(element.getBruttoSepPrice()) : new Money(0.00));
      tb.setSepd(
          element.getBruttoSepDate() != null ? new String(simpleDateFormat.format(element.getBruttoSepDate())) : "");
      tb.setOct(element.getBruttoOctPrice() != null ? new Money(element.getBruttoOctPrice()) : new Money(0.00));
      tb.setOctd(
          element.getBruttoOctDate() != null ? new String(simpleDateFormat.format(element.getBruttoOctDate())) : "");
      tb.setNov(element.getBruttoNovPrice() != null ? new Money(element.getBruttoNovPrice()) : new Money(0.00));
      tb.setNovd(
          element.getBruttoNovDate() != null ? new String(simpleDateFormat.format(element.getBruttoNovDate())) : "");
      tb.setDec(element.getBruttoDecPrice() != null ? new Money(element.getBruttoDecPrice()) : new Money(0.00));
      tb.setDecd(
          element.getBruttoDecDate() != null ? new String(simpleDateFormat.format(element.getBruttoDecDate())) : "");
      tb.setKau(element.getPayment().getAktKautionPrice() != null ? new Money(element.getPayment().getAktKautionPrice())
          : new Money(0.00));
      tb.setKaud(element.getPayment().getAktKautionDate() != null
          ? new String(simpleDateFormat.format(element.getPayment().getAktKautionDate()))
          : "-");
      if (element.getPayment().getRetourKautionDate() != null) {
        tb.setKauR("Rück: " + new String(simpleDateFormat.format(element.getPayment().getRetourKautionDate()))+ "  ");
        tb.setKauRP( element.getPayment().getRetourKautionPrice() != null 
                ? new Money(element.getPayment().getRetourKautionPrice()):new Money(0.00));
      } else {
    	  tb.setKauR("-                      ");
    	  tb.setKauRP(new Money(0.00));
      }

      tb.setLastName(element.getPayment().getContract().getCustomer().getName());

      if (element.getPayment().getContract().getCustomer().isUseCompanyAddress()
          && element.getPayment().getContract().getCustomer().getFirmenAnschrift() != null) {
        String firmenAdr = element.getPayment().getContract().getCustomer().getAnrede() + " "
            + element.getPayment().getContract().getCustomer().getVorname() + " "
            + element.getPayment().getContract().getCustomer().getName() + " "
            + element.getPayment().getContract().getCustomer().getFirmenName() + " "
            + element.getPayment().getContract().getCustomer().getFirmenAnschrift() + " "
            + element.getPayment().getContract().getCustomer().getTel1();
        tb.setCustomer(firmenAdr);
      } else {
        String privateAdr = element.getPayment().getContract().getCustomer().getAnrede() + " "
            + element.getPayment().getContract().getCustomer().getVorname() + " "
            + element.getPayment().getContract().getCustomer().getName() + " "
            + element.getPayment().getContract().getCustomer().getStrasse() + " "
            + element.getPayment().getContract().getCustomer().getPlz() + " "
            + element.getPayment().getContract().getCustomer().getOrt();
        tb.setCustomer(privateAdr);
      }

      tb.setMon(new Money(element.getPayment().getAktBruttoPrice()));
      tb.setBox(element.getPayment().getContractDetails().trim());
      tb.setStatus(element.getPayment().getStatus());
      tb.setTot(getTot(element));
      tbList.add(tb);
    });

    List<TenantBilling> itemList = tbList.stream().sorted(Comparator.comparing(TenantBilling::getLastName))
        .collect(Collectors.toList());

    return itemList;
  }



  private Money getTot(final PaymentDatePrice element) {
    resetSum();
    addSum(element.getBruttoJanPrice() != null ? element.getBruttoJanPrice() : 0.00);
    addSum(element.getBruttoFebPrice() != null ? element.getBruttoFebPrice() : 0.00);
    addSum(element.getBruttoMarPrice() != null ? element.getBruttoMarPrice() : 0.00);
    addSum(element.getBruttoAprPrice() != null ? element.getBruttoAprPrice() : 0.00);
    addSum(element.getBruttoMaiPrice() != null ? element.getBruttoMaiPrice() : 0.00);
    addSum(element.getBruttoJunPrice() != null ? element.getBruttoJunPrice() : 0.00);
    addSum(element.getBruttoJulPrice() != null ? element.getBruttoJulPrice() : 0.00);
    addSum(element.getBruttoAugPrice() != null ? element.getBruttoAugPrice() : 0.00);
    addSum(element.getBruttoSepPrice() != null ? element.getBruttoSepPrice() : 0.00);
    addSum(element.getBruttoOctPrice() != null ? element.getBruttoOctPrice() : 0.00);
    addSum(element.getBruttoNovPrice() != null ? element.getBruttoNovPrice() : 0.00);
    addSum(element.getBruttoDecPrice() != null ? element.getBruttoDecPrice() : 0.00);

    return new Money(getSum());
  }

  Double sum = 0.00;
  
  private void resetSum() {
    sum = 0.00;
  }

  private void addSum(Double pSum) {
    sum += pSum;
  }

  private Double getSum() {
    return sum;
  }

  private Money getSumJan(final List<PaymentDatePrice> paymentDatePriceList) {
    return new Money(paymentDatePriceList.stream() // Jan.
        .filter(pay -> pay != null && pay.getBruttoJanPrice() != null).mapToDouble(PaymentDatePrice::getBruttoJanPrice)
        .sum());
  }

  private Money getSumFeb(final List<PaymentDatePrice> paymentDatePriceList) {
    return new Money(paymentDatePriceList.stream() // Feb.
        .filter(pay -> pay != null && pay.getBruttoFebPrice() != null).mapToDouble(PaymentDatePrice::getBruttoFebPrice)
        .sum());
  }

  private Money getSumMar(final List<PaymentDatePrice> paymentDatePriceList) {
    return new Money(paymentDatePriceList.stream() // Mar.
        .filter(pay -> pay != null && pay.getBruttoMarPrice() != null).mapToDouble(PaymentDatePrice::getBruttoMarPrice)
        .sum());
  }

  private Money getSumApr(final List<PaymentDatePrice> paymentDatePriceList) {
    return new Money(paymentDatePriceList.stream() // Apr.
        .filter(pay -> pay != null && pay.getBruttoAprPrice() != null).mapToDouble(PaymentDatePrice::getBruttoAprPrice)
        .sum());
  }

  private Money getSumMai(final List<PaymentDatePrice> paymentDatePriceList) {
    return new Money(paymentDatePriceList.stream() // Mai.
        .filter(pay -> pay != null && pay.getBruttoMaiPrice() != null).mapToDouble(PaymentDatePrice::getBruttoMaiPrice)
        .sum());
  }

  private Money getSumJun(final List<PaymentDatePrice> paymentDatePriceList) {
    return new Money(paymentDatePriceList.stream() // Jun.
        .filter(pay -> pay != null && pay.getBruttoJunPrice() != null).mapToDouble(PaymentDatePrice::getBruttoJunPrice)
        .sum());
  }

  private Money getSumJul(final List<PaymentDatePrice> paymentDatePriceList) {
    return new Money(paymentDatePriceList.stream() // Jul.
        .filter(pay -> pay != null && pay.getBruttoJulPrice() != null).mapToDouble(PaymentDatePrice::getBruttoJulPrice)
        .sum());
  }

  private Money getSumAug(final List<PaymentDatePrice> paymentDatePriceList) {
    return new Money(paymentDatePriceList.stream() // Aug.
        .filter(pay -> pay != null && pay.getBruttoAugPrice() != null).mapToDouble(PaymentDatePrice::getBruttoAugPrice)
        .sum());
  }

  private Money getSumSep(final List<PaymentDatePrice> paymentDatePriceList) {
    return new Money(paymentDatePriceList.stream() // Sep.
        .filter(pay -> pay != null && pay.getBruttoSepPrice() != null).mapToDouble(PaymentDatePrice::getBruttoSepPrice)
        .sum());
  }

  private Money getSumOct(final List<PaymentDatePrice> paymentDatePriceList) {
    return new Money(paymentDatePriceList.stream() // Oct.
        .filter(pay -> pay != null && pay.getBruttoOctPrice() != null).mapToDouble(PaymentDatePrice::getBruttoOctPrice)
        .sum());
  }

  private Money getSumNov(final List<PaymentDatePrice> paymentDatePriceList) {
    return new Money(paymentDatePriceList.stream() // Nov.
        .filter(pay -> pay != null && pay.getBruttoNovPrice() != null).mapToDouble(PaymentDatePrice::getBruttoNovPrice)
        .sum());
  }

  private Money getSumDec(final List<PaymentDatePrice> paymentDatePriceList) {
    return new Money(paymentDatePriceList.stream() // Dec.
        .filter(pay -> pay != null && pay.getBruttoDecPrice() != null).mapToDouble(PaymentDatePrice::getBruttoDecPrice)
        .sum());
  }

  private Money getSumTotMon(final List<Payment> paymentList) {
    return new Money(paymentList.stream() // Summe Brutto Preis
        .filter(pay -> pay != null && pay.getSumBruttoPrice() != null).mapToDouble(Payment::getSumBruttoPrice).sum());
  }

  private Money getSumTotKaut(final List<Payment> paymentList) {
    return new Money(paymentList.stream() // Summe der Kaution
        .filter(pay -> pay != null && pay.getAktKautionPrice() != null).mapToDouble(Payment::getAktKautionPrice).sum());
  }

}
