package com.ebox3.server.doc;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebox3.server.model.ElectricPeriod;

public class GenerateEBillOverviewList {

  private final Log logger = LogFactory.getLog(getClass());

  public boolean generateEbillList(List<ElectricPeriod> electricPeriodList, InputStream template, OutputStream out) {

    try {
      Context con = buildList(electricPeriodList);
      Stamper stamp = new Stamper();
      stamp.stamp(template, con, out);
      stamp.close();
    } catch (final Exception e) {
      logger.error("Exception: " + e.getMessage());
    } finally {
      try {
        out.close();
        template.close();
        logger.info("KB EBillList: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
      } catch (final IOException e) {
        logger.error("IOException: " + e.getMessage());
      }
    }
    return true;
  }
  
  private Context buildList(List<ElectricPeriod> electricPeriodList) {
    Context context = new Context();
    context.setCurrentDate(new DateText(Util.getCurrentDate()));
    context.setEp(lElectricPeriod(electricPeriodList));
    return context;
  }

  private List<LElectricPeriod> lElectricPeriod(List<ElectricPeriod> electricPeriodList) {
    List<LElectricPeriod> lPeriodList = new ArrayList<LElectricPeriod>();

    String pattern = "dd.MM.yy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    electricPeriodList.forEach(electricPeriod -> {
      LElectricPeriod lelectricPeriod = new LElectricPeriod();
      String electicMeterNr = electricPeriod.getElectricMeter().getElectricMeterNumber() != null
          ? electricPeriod.getElectricMeter().getElectricMeterNumber().toString()
          : "";
      String electricMeterBemerkungen = electricPeriod.getElectricMeter().getElectricMeterBemerkungen() != null
          ? electricPeriod.getElectricMeter().getElectricMeterBemerkungen()
          : "";
      lelectricPeriod.setElektroZaehlerNr(electicMeterNr + " " + electricMeterBemerkungen);
      lelectricPeriod
          .setAnschrift(electricPeriod.getPrintAnschrift() != null ? electricPeriod.getPrintAnschrift() : "-");
      lelectricPeriod.setDiffkWh(electricPeriod.getDiffKwatt() != null ? electricPeriod.getDiffKwatt() : 0);
      lelectricPeriod.setBemerkung(electricPeriod.getBemerkungen() != null ? electricPeriod.getBemerkungen() : "");
      lelectricPeriod.setZahlungEingegangen(electricPeriod.getZahlungEingegangen() != null
          ? simpleDateFormat.format(electricPeriod.getZahlungEingegangen())
          : "");
      if (electricPeriod.getZaehlerFromPeriode() != null) {
        lelectricPeriod.setFromDate(new DateText(electricPeriod.getZaehlerFromPeriode()));
      } else {
        lelectricPeriod.setFromDate(new DateText(electricPeriod.getZaehlerToPeriode()));
      }
      if (electricPeriod.getZaehlerToPeriode() != null) {
        lelectricPeriod.setToDate(new DateText(electricPeriod.getZaehlerToPeriode()));
      }
      if (electricPeriod.getStromPriceBrutto() != null) {
        lelectricPeriod.setTotPriceBox(new Money(electricPeriod.getStromPriceBrutto()));
      } else {
        lelectricPeriod.setTotPriceBox(new Money(0.00));
      }
      lelectricPeriod.setStatusText(electricPeriod.getStatusText() != null ? electricPeriod.getStatusText() : "");
      lPeriodList.add(lelectricPeriod);

    });
    return lPeriodList;
  }

}
