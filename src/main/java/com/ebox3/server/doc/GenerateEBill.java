package com.ebox3.server.doc;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import com.ebox3.server.model.Customer;
import com.ebox3.server.model.ElectricPeriod;

public class GenerateEBill {

  private final Log logger = LogFactory.getLog(getClass());

  private Double totPriceBox = 0.0;

  private String diff;
  
  private Double diffkWatt;
  
  private String printAnschrift;

  private ElectricPeriod ePeriod;

  private Date dateFrom;

  public Date getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(Date dateFrom) {
    this.dateFrom = dateFrom;
  }

  public Double getTotPriceBoxNetto() {
    return totPriceBox;
  }

  public void setTotPriceBoxNetto(Double totPriceBox) {
    this.totPriceBox = totPriceBox;
  }

  public String getDiff() {
    return diff;
  }

  public void setDiff(String diff) {
    this.diff = diff;
  }
  
  private void setElectricPeriod(ElectricPeriod ePeriod) {
    this.ePeriod = ePeriod;
  }

  public ElectricPeriod getElectricPeriod() {
    return this.ePeriod;
  }
  
  public Double getDiffkWatt() {
    return diffkWatt;
  }

  public void setDiffkWatt(Double diffkWatt) {
    this.diffkWatt = diffkWatt;
  }
  
  public String getPrintAnschrift() {
    return printAnschrift;
  }

  public void setPrintAnschrift(String printAnschrift) {
    this.printAnschrift = printAnschrift;
  }

  public boolean generateDocEBill(Customer customer, List<ElectricPeriod> electricPeriodList, InputStream template, OutputStream out) {
    try {
      Context con = buildEBill(customer, electricPeriodList);
      Stamper stamp = new Stamper();
      stamp.stamp(template, con, out);
      stamp.close();
    } catch (final Exception e) {
      logger.error("Exception: " + e.getMessage());
    } finally {
      try {
        out.close();
        template.close();
        logger.info("KB EBill: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
      } catch (final IOException e) {
        logger.error("IOException: " + e.getMessage());
      }
    }
    return true;
  }

  private Context buildEBill(Customer customer, List<ElectricPeriod> electricPeriodList) {
    Context context = new Context();

    LCustomer lCustomer = genAddress(customer);
    
    String anschrift = lCustomer.getAnrede() + " " + lCustomer.getName();
    String firmenName = lCustomer.getFirmenName() != null ? lCustomer.getFirmenName() : "";
    
    setPrintAnschrift(anschrift + " "+ firmenName);

    List<LElectricPeriod> leList = genElectricPeriod(groupBox(electricPeriodList));

    EBillDetails ebillDetails = genEBillDetails(electricPeriodList);

    context.setCurrentDate(new DateText(Util.getCurrentDate()));
    context.setListBoxNumber(getListOfBoxNumbers(electricPeriodList));
    context.setCu(lCustomer);
    context.setEp(leList);
    context.setEb(ebillDetails);
    return context;
  }

  private String getListOfBoxNumbers(List<ElectricPeriod> electricPeriodList) {

    List<Long> list = new ArrayList<Long>();
    electricPeriodList.forEach(element -> {

      if (element.getStatusText().equals("bezahlt") && element.getMarginStart().equals("start")) {               
        list.add(element.getElectricMeter().getEbox().getBoxNumber());
      }
    });

    list.sort(Comparator.naturalOrder());
    return list.toString();
  }
  
  private Double calcMwst(Double sum, Double mwstSatz) {
    Double mwst = (sum / 100) * mwstSatz;
    return kaufRunden(mwst);
  }

  private Double kaufRunden(Double x) { // Runde auf 5 Rappen genau!
    return Math.round(x / 0.05) * 0.05;
  }

  public EBillDetails genEBillDetails(List<ElectricPeriod> electricPeriodList) {

    EBillDetails eb = new EBillDetails();
    Optional<ElectricPeriod> eP = electricPeriodList.stream()
        .filter(electricPeriod -> electricPeriod.getStatusText().contentEquals("offen")).findFirst();

    if (eP.isPresent()) {
      ElectricPeriod e = eP.get();
      Double gebuehr = e.getPreisBearbeitungsgebuehr() != null ? e.getPreisBearbeitungsgebuehr(): 0D;
      eb.setPricePorto(new Money(gebuehr));
      Double sumTotPrice = gebuehr + getTotPriceBoxNetto();
      eb.setTotPriceBoxes(new Money(kaufRunden(getTotPriceBoxNetto())));
      eb.setMwSt(e.getMwstSatz());
      Double mwstPrice = calcMwst(sumTotPrice, e.getMwstSatz());
      eb.setPriceMwst(new Money(mwstPrice));
      Double sum = gebuehr + getTotPriceBoxNetto() + mwstPrice;
      e.setStromPriceBrutto(kaufRunden(sum));
      e.setStromPriceNetto(kaufRunden(getTotPriceBoxNetto()));
      e.setMwstPrice(kaufRunden(mwstPrice));
      e.setInfo(getDiff().toString());
      e.setZaehlerFromPeriode(getDateFrom());
      e.setDiffKwatt(getDiffkWatt());
      e.setPrintAnschrift(getPrintAnschrift());
      if (sum > 0) {
        eb.setGuthaben("JA");
        eb.setTotGutEbox(new Money(kaufRunden(sum)));
      } else {
        eb.setGuthaben("NEIN");
        eb.setTotGutMieter(new Money(kaufRunden(sum)));
      }
      setElectricPeriod(e);
    }
    return eb;
  }

  class StartPeriod {
    Double zaehler;
    Date endDateOfPeriod;

    StartPeriod(Double pZaehler, Date pEndDateOfPeriod) {
      zaehler = pZaehler;
      endDateOfPeriod = pEndDateOfPeriod;
    }

    private Double getZaehler() {
      return zaehler;
    }

    private Date getEndDateOfPeriod() {
      return endDateOfPeriod;
    }

  }

  private List<LElectricPeriod> genElectricPeriod(List<ElectricPeriod> electricPeriodList) {

    HashMap<Long, StartPeriod> startZaehler = new HashMap<Long, StartPeriod>();

    electricPeriodList.forEach(element -> {
      if (element.getStatusText().equals("bezahlt") && element.getMarginStart().equals("start")) {
        logger.debug(element.getZaehlerToPeriode());
        startZaehler.put(element.getElectricMeter().getEbox().getBoxNumber(),
            new StartPeriod(element.getZaehlerStand(), element.getZaehlerToPeriode()));
      }
    });

    List<LElectricPeriod> list = new ArrayList<LElectricPeriod>();
    electricPeriodList.forEach(elPeriod -> {
      if (elPeriod.getMarginStop() != null) {
        if (elPeriod.getStatusText().equals("offen") && elPeriod.getMarginStop().equals("add")) {
          LElectricPeriod lel = new LElectricPeriod();          
          
          if (elPeriod.getElectricMeter().getElectricMeterBemerkungen() != null &&
              elPeriod.getElectricMeter().getElectricMeterBemerkungen().length() > 0)  {
            
            lel.setBoxNumberText(elPeriod.getElectricMeter().getElectricMeterBemerkungen());
            
          } else {
            lel.setBoxNumberText(elPeriod.getElectricMeter().getEbox().getBoxNumber().toString());
          }
          
          StartPeriod per = startZaehler.get(elPeriod.getElectricMeter().getEbox().getBoxNumber());

          lel.setStartkWh(per.getZaehler());
          if (lel.getEndkWh() == null) {
            Date day = addOneDay(per.getEndDateOfPeriod());
            lel.setFromDate(new DateText(day));
            lel.setToDate(new DateText(elPeriod.getZaehlerToPeriode()));
            lel.setEndkWh(elPeriod.getZaehlerStand());
            lel.setPricekWh(new Money(elPeriod.getPreiskW()));
            Double diffRound =  Math.round((lel.getEndkWh() - lel.getStartkWh())*100.0) / 100.0;
            lel.setDiffkWh(diffRound);
            Double sumDiff = lel.getDiffkWh() * elPeriod.getPreiskW();
            lel.setSumDiffkWh(new Money(sumDiff));
            lel.setGuthabenKundeText("Sonstige Guthaben");
            Double gutKunde = 0D;
            if (elPeriod.getKundenGuthaben() != null ) {
              gutKunde = elPeriod.getKundenGuthaben();
              lel.setGuthabenKunde(new Money(gutKunde));              
            } else {
              gutKunde = 0D;
              lel.setGuthabenKunde(new Money (0D));
            }
            int month = calculateMonth(per.getEndDateOfPeriod(), elPeriod.getZaehlerToPeriode());
            lel.setMonVorZahlung(String.valueOf(month));
            lel.setVorZahl(new Money(elPeriod.getStromAkontoMonat()));
            Double vorZahlSum = month * elPeriod.getStromAkontoMonat() * (-1);
            lel.setVorZahlSum(new Money(vorZahlSum));
            Double sum = sumDiff + vorZahlSum + gutKunde;
            setTotPriceBoxNetto(sum); // Bilde die Summe für die Endberechnung
            setDiff("Z.Start: " + lel.getStartkWh() + " " + "Z.Stop: " + lel.getEndkWh() + " Diff: " + lel.getDiffkWh()
                + " Anzahl Monate: " + month);
            setDiffkWatt(diffRound);
            setDateFrom(day); // From Datum ist ein Tag später als das ursprüngliche From Datum
            lel.setTotPriceBox(new Money(sum));
          }
          list.add(lel);
        }
      }
    });

    List<LElectricPeriod> newList = list.stream().sorted(Comparator.comparing(LElectricPeriod::getBoxNumber))
        .collect(Collectors.toList());

    return newList;

  }

  private Date addOneDay(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.DATE, 1);
    return c.getTime();

  }

  private int calculateMonth(Date start, Date end) {

    LocalDate pStart = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    LocalDate pEnd = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    Period diff = Period.between(pStart, pEnd);

    int m = 0;
    int m1 = 0;
    int m2 = 0;

    if (diff.getYears() > 0) {
      m = diff.getYears() * 12;
    }

    if (diff.getMonths() > 0) {
      m1 = diff.getMonths();
    }

    if (diff.getDays() > 3) { // Diese Berechnung berücksichtigt den Februar 28/29 Tage mit
    	m2 = 1;               // Im Grundsatz sollten die Tage gar nicht mit brücksichtigt werden, es zählen nur die Monate! 
    	                      // Mieterwechsel im Mitte des Monats --> Ablesen des Zählers per Ende Monat bzw. Mieter zahlt noch den laufenden Monat.      
    }

    return m + m1 + m2;
  }

  private LCustomer genAddress(Customer customer) {

    LCustomer cu = new LCustomer();
    if (customer.isUseCompanyAddress() && customer.getFirmenAnschrift() != null) { // Geschäftsadresse
      cu.setUseCompanyAddress("JA");
      String[] arrayOfStr = customer.getFirmenAnschrift().split(",");
      if(arrayOfStr.length > 1) {
        cu.setFirmenName(customer.getFirmenName());
        cu.setAnrede(customer.getAnrede());
        cu.setName(customer.getName() + " " + customer.getVorname());
        cu.setStrasse(arrayOfStr[0] != null ? arrayOfStr[0].trim() : "");
        cu.setOrt(arrayOfStr[1] != null ? arrayOfStr[1].trim() : "");
      } else {
        cu.setFirmenName(customer.getFirmenName());
        cu.setAnrede(customer.getAnrede());
        cu.setName(customer.getName() + " " + customer.getVorname());
        cu.setStrasse("n/a");
        cu.setOrt("n/a");
      }

    } else {
      cu.setUseCompanyAddress("NEIN");
      cu.setAnrede(customer.getAnrede());
      cu.setName(customer.getName() + " " + customer.getVorname());
      cu.setStrasse(customer.getStrasse());
      cu.setOrt(customer.getPlz() + " " + customer.getOrt());
    }
    return cu;
  }

  private List<ElectricPeriod> groupBox(List<ElectricPeriod> electricPeriodList) {

    List<ElectricPeriod> list = new ArrayList<ElectricPeriod>();

    Map<Long, Set<ElectricPeriod>> elm = electricPeriodList.stream().distinct().collect(Collectors.groupingBy(
        ep -> ep.getElectricMeter().getElectricMeterNumber(), Collectors.mapping(ep -> ep, Collectors.toSet())));

    electricPeriodList.forEach(electricPeriod -> {

      if (electricPeriod.getStatusText().equals("bezahlt")) {
        if (elm.get(electricPeriod.getElectricMeter().getElectricMeterNumber()) != null) {
          list.addAll(elm.get(electricPeriod.getElectricMeter().getElectricMeterNumber()));
        }
      }
    });

    list.forEach(e -> {
      logger.debug(e.getElectricMeter().getElectricMeterNumber() + ":" /* + e.getAnschriftMieter()+ ":" + */ + e.getId()
          + ":" + e.getElectricMeter().getEbox().getBoxNumber() + ":" + e.getZaehlerStand() + ":" + e.getStatusText());
    });
    return list;
  }

}
