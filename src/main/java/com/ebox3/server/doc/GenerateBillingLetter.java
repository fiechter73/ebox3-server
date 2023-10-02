package com.ebox3.server.doc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebox3.server.model.AdditionalCosts;


public class GenerateBillingLetter extends Document {
  
  private final Log logger = LogFactory.getLog(getClass());
  
  private Double totPriceBox = 0.0;
  
  
  public void addTotPriceBoxes(Double totPriceBox) {
    this.totPriceBox += totPriceBox;
  }
  
  public Double getTotPriceBoxes() {
    return this.totPriceBox;
  }
  
  public boolean generateDocBillingLetter(AdditionalCosts item, InputStream template, OutputStream out ) {
    try { 
      Context con = buildBillingLetter(item);
      Stamper stamp = new Stamper();
      stamp.stamp(template, con, out);
      stamp.close();
    } catch (final Exception e) {
      logger.error("Exception: " + e.getMessage());
    } finally {
      try {
        out.close();
        template.close();
        logger.info("KB BillingLetter: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
      } catch (final IOException e) {
        logger.error("IOException: " + e.getMessage());
      }
    }
    return true;
  }
  
  
  private Context buildBillingLetter(AdditionalCosts item) {
    Context context = new Context();
   
    LCustomer customer = getCustomerByAddress(item.getContract().getCustomer());
    LAdditionalCosts additionalCosts = genAdditionalCosts(item);
           
    
    context.setCurrentDate(new DateText(Util.getCurrentDate()));
    context.setCu(customer);
    context.setAc(additionalCosts);
    context.setCurrentDate(new DateText(Util.getCurrentDate()));
    return context;
  }
    
  private LAdditionalCosts genAdditionalCosts(AdditionalCosts addCosts) {
    
    LAdditionalCosts ac = new LAdditionalCosts();
    final List<ItemObj> itemList = new ArrayList<ItemObj>();
    
    ac.setText(addCosts.getText());
    ac.setTitle(addCosts.getTitle());
        
    if(addCosts.getPositiontext1() != null  && addCosts.getPositiontext1().length() > 0) {
      ItemObj i1 = new ItemObj();
      i1.setPostext(addCosts.getPositiontext1());
      i1.setPosprice(new Money(addCosts.getPositionprice1()));
      itemList.add(i1);
    }  

    
    if(addCosts.getPositiontext2() != null && addCosts.getPositiontext2().length() > 0) {
      ItemObj i2 = new ItemObj();
      i2.setPostext(addCosts.getPositiontext2());
      i2.setPosprice(new Money(addCosts.getPositionprice2()));
      itemList.add(i2);
    }  

    if(addCosts.getPositiontext3() != null && addCosts.getPositiontext3().length() > 0 ) {
      ItemObj i3 = new ItemObj();
      i3.setPostext(addCosts.getPositiontext3());
      i3.setPosprice(new Money(addCosts.getPositionprice3()));
      itemList.add(i3);
    }  

    if(addCosts.getPositiontext4() != null && addCosts.getPositiontext4().length() > 0 ) {
      ItemObj i4 = new ItemObj();
      i4.setPostext(addCosts.getPositiontext4());
      i4.setPosprice(new Money(addCosts.getPositionprice4()));
      itemList.add(i4);
    }
    
    if(addCosts.getPositiontext5() != null && addCosts.getPositiontext5().length() > 0) {
      ItemObj i5 = new ItemObj();
      i5.setPostext(addCosts.getPositiontext5());
      i5.setPosprice(new Money(addCosts.getPositionprice5()));
      itemList.add(i5);
    }
    
    ac.setMwstText("MwSt. " + addCosts.getMwstSatz().toString() + " %");
    ac.setTotSumText("Total:");
    
    ac.setMwstPrice((new Money(addCosts.getMwstPrice())));
    ac.setMwstSatz(addCosts.getMwstSatz());
    ac.setSumBruttoPrice((new Money(addCosts.getSumBruttoPrice())));
    
    ac.setItemList(itemList);
    
    return ac;
  }
  
}
