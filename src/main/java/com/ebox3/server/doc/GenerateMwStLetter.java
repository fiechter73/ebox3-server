package com.ebox3.server.doc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebox3.server.model.dto.MwstDTO;


public class GenerateMwStLetter {
  
  private Double mwstPriceBox = 0.00;
  private Double mwstPriceStrom = 0.00;
  private Double mwstPriceRechnung = 0.00;
  
  private Double nettoPriceBox = 0.00;
  private Double nettoPriceStrom = 0.00;
  private Double nettoPriceRechnung = 0.00;
  
  private Double bruttoPriceBox = 0.00;
  private Double bruttoPriceStrom = 0.00;
  private Double bruttoPriceRechnung = 0.00;
  
  
  private final Log logger = LogFactory.getLog(getClass());
  
  public boolean generateDocMwstLetter(List<MwstDTO> mwstListEbox,List<MwstDTO> mwstListStrom, List<MwstDTO> mwstListRechnung,  Date from, Date to, InputStream template, OutputStream out) {
    try {
      Context con = buildMwstList(mwstListEbox,mwstListStrom,mwstListRechnung, from, to);
      Stamper stamp = new Stamper();
      stamp.stamp(template, con, out);
      stamp.close();
    } catch (final Exception e) {
      logger.error("Exception: " + e.getMessage());
    } finally {
      try {
        out.close();
        template.close();
        logger.info("KB MwStList: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
      } catch (final IOException e) {
        logger.error("IOException: " + e.getMessage());
      }
    }
    return true;
  }
  
  private Context buildMwstList(List<MwstDTO> mwstDtoListBox, List<MwstDTO> mwstDtoListStrom, List<MwstDTO> mwstDtoListRechnung, Date from, Date to) {
    Context context = new Context();
    ArrayList<LMwst> mwstListB = new ArrayList<LMwst>();
    ArrayList<LMwst> mwstListS = new ArrayList<LMwst>();
    ArrayList<LMwst> mwstListR = new ArrayList<LMwst>();
    mwstDtoListBox.forEach(mwst -> { // Boxen
      final LMwst lMwstB = new LMwst();
      setSumMwstBox(mwst.getMwstPrice());
      setSumNettoBox(mwst.getNettoPrice());
      setSumBruttoBox(mwst.getBruttoPrice());
      lMwstB.setCustomer(mwst.getCustomer());
      lMwstB.setBoxes(mwst.getBoxes());
      lMwstB.setMonth(mwst.getMonth());
      lMwstB.setPayDate(new DateText(mwst.getPayDate()));
      lMwstB.setMwstPrice(new Money(mwst.getMwstPrice()));
      lMwstB.setNettoPrice(new Money(mwst.getNettoPrice()));
      lMwstB.setBruttoPrice(new Money(mwst.getBruttoPrice()));
      mwstListB.add(lMwstB);  
    });
    
    mwstDtoListStrom.forEach(mwst -> { // Strom
      final LMwst lMwstS = new LMwst();
      if (mwst.getMwstPrice()!= null) {
        setSumMwstStrom(mwst.getMwstPrice());
        setSumNettoStrom(mwst.getNettoPrice());
        setSumBruttoStrom(mwst.getBruttoPrice());
        lMwstS.setCustomer(mwst.getCustomer());
        lMwstS.setBoxes(mwst.getBoxes());
        lMwstS.setMonth(mwst.getMonth());
        lMwstS.setPayDate(new DateText(mwst.getPayDate()));
        lMwstS.setMwstPrice(new Money(mwst.getMwstPrice()));
        lMwstS.setNettoPrice(new Money(mwst.getNettoPrice()));
        lMwstS.setBruttoPrice(new Money(mwst.getBruttoPrice()));
        mwstListS.add(lMwstS);        
      }
 
    });
    
    mwstDtoListRechnung.forEach(mwst -> { // Rechnungen
      final LMwst lMwstR = new LMwst();
      setSumMwstRechnung(mwst.getMwstPrice());
      setSumNettoRechnung(mwst.getNettoPrice());
      setSumBruttoRechnung(mwst.getBruttoPrice());
      lMwstR.setCustomer(mwst.getCustomer());
      lMwstR.setBoxes(mwst.getBoxes());
      lMwstR.setMonth(mwst.getMonth());
      lMwstR.setPayDate(new DateText(mwst.getPayDate()));
      lMwstR.setMwstPrice(new Money(mwst.getMwstPrice()));
      lMwstR.setNettoPrice(new Money(mwst.getNettoPrice()));
      lMwstR.setBruttoPrice(new Money(mwst.getBruttoPrice()));
      mwstListR.add(lMwstR);  
    });

    
    context.setCurrentDate(new DateText(Util.getCurrentDate()));
    context.setQuarter("Periode: " + Util.getFormatedDate(from) + " - " + Util.getFormatedDate(to));
    context.setLmwstb(mwstListB);
    context.setLmwsts(mwstListS);
    context.setLmwstr(mwstListR);
    context.setTotMwstPriceBox(new Money(getSumMwstBox()));
    context.setTotMwstPriceStrom(new Money(getSumMwstStrom()));
    context.setTotMwstPriceRechnung(new Money(getSumMwstRechnung()));

    context.setTotNettoPriceBox(new Money(getSumNettoBox()));
    context.setTotNettoPriceStrom(new Money(getSumNettoStrom()));
    context.setTotNettoPriceRechnung(new Money(getSumNettoRechnung()));

    context.setTotBruttoPriceBox(new Money(getSumBruttoBox()));
    context.setTotBruttoPriceStrom(new Money(getSumBruttoStrom()));
    context.setTotBruttoPriceRechnung(new Money(getSumBruttoRechnung()));
     
    return context;
  }
  
 
  private void setSumMwstBox(Double mwstPriceBox) {
    this.mwstPriceBox += mwstPriceBox;
  }
  
  private Double getSumMwstBox() {
    return this.mwstPriceBox;
  }
  
  private void setSumMwstStrom(Double mwstPriceStrom) {
    this.mwstPriceStrom += mwstPriceStrom;
  }
  
  private Double getSumMwstStrom() {
    return this.mwstPriceStrom;
  }
  private void setSumMwstRechnung(Double mwstPriceRechnung) {
    this.mwstPriceRechnung += mwstPriceRechnung;
  }
  
  private Double getSumMwstRechnung() {
    return this.mwstPriceRechnung;
  }
  
  
  private void setSumNettoBox(Double nettoPriceBox) {
    this.nettoPriceBox += nettoPriceBox;
  }
  
  private Double getSumNettoBox() {
    return this.nettoPriceBox;
  }
  
  private void setSumNettoStrom(Double nettoPriceStrom) {
    this.nettoPriceStrom += nettoPriceStrom;
  }
  
  private Double getSumNettoStrom() {
    return this.nettoPriceStrom;
  }
  private void setSumNettoRechnung(Double nettoPriceRechnung) {
    this.nettoPriceRechnung += nettoPriceRechnung;
  }
  
  private Double getSumNettoRechnung() {
    return this.nettoPriceRechnung;
  }
  
  
  private void setSumBruttoBox(Double bruttoPriceBox) {
    this.bruttoPriceBox += bruttoPriceBox;
  }
  
  private Double getSumBruttoBox() {
    return this.bruttoPriceBox;
  }
  
  private void setSumBruttoStrom(Double bruttoPriceStrom) {
    this.bruttoPriceStrom += bruttoPriceStrom;
  }
  
  private Double getSumBruttoStrom() {
    return this.bruttoPriceStrom;
  }
  private void setSumBruttoRechnung(Double bruttoPriceRechnung) {
    this.bruttoPriceRechnung += bruttoPriceRechnung;
  }
  
  private Double getSumBruttoRechnung() {
    return this.bruttoPriceRechnung;
  }

}
