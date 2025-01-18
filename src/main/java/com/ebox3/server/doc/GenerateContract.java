package com.ebox3.server.doc;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.ebox3.server.model.Contract;



public class GenerateContract  extends Document {
  
  private final Log logger = LogFactory.getLog(getClass());
  
  
  public boolean generateDocOffering(Contract contract, InputStream template, OutputStream out) {
    try {
      Context con = buildContract(contract);
      Stamper stamp = new Stamper();
      stamp.stamp(template, con, out);
      stamp.close();
    } catch (final Exception e) {
      logger.error("Exception: " + e.getMessage());
    } finally {
      try {
        out.close();
        template.close();
        logger.info("KB Offerte: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
      } catch (final IOException e) {
        logger.error("IOException: " + e.getMessage());
      }
    }
    return true;
  }

  
  private Context buildContract(Contract contract) {
    Context context = new Context();
    
    LCustomer lcustomer = getCustomerByAddress(contract.getCustomer());
    LContract lcontract = pContract(contract);
    
    ArrayList<LEbox> list = new ArrayList<LEbox>();
    List<Long> boxNr = new ArrayList<Long>();
    List<Long> parkplatzNr = new ArrayList<Long>();
    contract.getEboxs().forEach( ebox -> {
      LEbox bx = new LEbox();
      bx.setBeschreibung(ebox.getBeschreibung());
      bx.setBoxType(ebox.getBoxType().toString());
      bx.setBoxNumber(ebox.getBoxNumber().toString());   
      bx.setBoxNumberSort(ebox.getBoxNumber());
      bx.setPrice(new Money(ebox.getPrice()));
      bx.setGebaeude("A");
      bx.setAdditional(ebox.isAdditional()?"Ja":"Nein");
      bx.setBriefkasten(ebox.isBriefkasten()?"Ja":"Nein");
      bx.setHeizung(ebox.isHeizung()?"Ja":"Nein");
      bx.setInternet(ebox.isInternet()?"Ja": ebox.isInternetWifi()?"Ja":"Nein");
      if (ebox.isInternetWifi()) {
        bx.setWifi("Wifi");
      } else {
        bx.setWifi("");
      }
      bx.setStrom(ebox.isStrom()?"Ja":"Nein");
      bx.setWasser(ebox.isWasser()?"Ja":"Nein");
      bx.setPriceHeizung(new Money(ebox.getHeizungPrice()));
      if(ebox.isInternet()) {
        bx.setPriceInternet(new Money(ebox.getInternetPrice()));
      } else if (ebox.isInternetWifi()) {
        bx.setPriceInternet(new Money(ebox.getInternetWifiPrice()));
      } else {
        bx.setPriceInternet(new Money(0.00)); 
      }
      bx.setPriceStrom(new Money(ebox.getStromPrice()));
      bx.setPriceWasser(new Money(ebox.getWasserPrice()));
      bx.setPriceBriefkasten(new Money(ebox.getBriefkastenPrice()));
      bx.setMwStSatz(new Money(ebox.getMwstSatz()));
      bx.setMwStPrice(new Money(ebox.getMwstPrice()));
      bx.setTotalPriceNetto(new Money(ebox.getTotalPriceNetto()));
      bx.setTotalPriceBrutto(new Money(ebox.getTotalPriceBrutto()));
      bx.setAdditonalCosts(new Money(ebox.getAdditionalCosts()));
      bx.setDepot(new Money(ebox.getDepot()));
      bx.setKeys(ebox.getKeysPerBox());
      list.add(bx);      
      if(ebox.getBoxType().toString().equals("Parkplatz")) {
        parkplatzNr.add(ebox.getBoxNumber());
      } else { 
        boxNr.add(ebox.getBoxNumber());
      }
      context.setSumPriceBrutto(new Money(Util.sumPriceBrutto(ebox.getTotalPriceBrutto())));      
    });
    
    if (! boxNr.isEmpty()) {
      Collections.sort(boxNr);
      context.setListBoxNumber(boxNr.stream().map(String:: valueOf).collect(Collectors.joining(", ")));
    } else {
      context.setListBoxNumber("-");
    }  
    
    if(! parkplatzNr.isEmpty()) {
       Collections.sort(parkplatzNr);
       context.setListStandplatzNumber(parkplatzNr.stream().map(String:: valueOf).collect(Collectors.joining(", ")));
    } else {
      context.setListStandplatzNumber("-");
    }
          
    List <LEbox> sortedListBox  = list.stream()
        .filter(bx -> !"Parkplatz".contentEquals(bx.getBoxType()) && !"Briefkasten".contentEquals(bx.getBoxType())   )
        .sorted(Comparator.comparingLong(LEbox::getBoxNumberSort))
        .collect(Collectors.toList());
    context.setBx(sortedListBox);
    
    List <LEbox> sortedListPark  = list.stream()
        .filter(bp -> "Parkplatz".contentEquals(bp.getBoxType()))
        .sorted(Comparator.comparingLong(LEbox::getBoxNumberSort))
        .collect(Collectors.toList());
    context.setBp(sortedListPark);

    List <LEbox> sortedListBrief  = list.stream()
        .filter(bb -> "Briefkasten".contentEquals(bb.getBoxType()))
        .sorted(Comparator.comparingLong(LEbox::getBoxNumberSort))
        .collect(Collectors.toList());
    context.setBb(sortedListBrief);
    
       Util.clearSumPriceBrutto();
       context.setCurrentDate(new DateText(Util.getCurrentDate()));
       context.setBemerkungen(contract.getBemerkungen()!= null ?contract.getBemerkungen() : null );
        context.setCo(lcontract);
    context.setCu(lcustomer);
    return context;  
  }
  
  
  private LContract pContract(Contract contract) {
    LContract co = new LContract(); // Contract
    
    
    if(contract.getCustomer().isUseCompanyAddress()) {
      co.setNutzung("Gewerbezwecke");
    } else {
      co.setNutzung("Ausschliesslich private hobbymässige Nutzung");
    }
    co.setMonatsfrist(contract.getFrist());
    co.setStartDate(new DateText(contract.getStartDate()));
    co.setEndDate(new DateText(contract.getEndDate()));
    co.setStatusText(contract.getStatusText());
    if(Util.getFormatedDate(contract.getEndDate()).equals("31.12.2100")) {      
      Date date = Util.possibleTerminationDate(contract.getStartDate(), contract.getMinDuration());
      co.setOpenEndedContractDate(new DateText(date));
      co.setOpenEndedContract("Ja");
    } else {
      co.setOpenEndedContract("Nein");
    }
    return co;
  }
  
}
