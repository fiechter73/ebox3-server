package com.ebox3.server.doc;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebox3.server.model.AdditionalCosts;

public class GenerateBillingList extends Document {

  private final Log logger = LogFactory.getLog(getClass());

  public boolean generateCustomerSalesLetter(List<AdditionalCosts> addtionalCosts, InputStream template,
      OutputStream out) {
    try {
      Context con = buildCustomerSalesList(addtionalCosts);
      Stamper stamp = new Stamper();
      stamp.stamp(template, con, out);
      stamp.close();;
    } catch (final Exception e) {
      logger.error("Exception: " + e.getMessage());
    } finally {
      try {
        out.close();
        template.close();
        logger.info("KB BillingList: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
      } catch (final IOException e) {
        logger.error("IOException: " + e.getMessage());
      }
    }
    return true;
  }

  private Context buildCustomerSalesList(List<AdditionalCosts> addtionalCosts) {
    Context context = new Context();
    ArrayList<LAdditionalCosts> laddCosts = new ArrayList<LAdditionalCosts>();

    addtionalCosts.forEach(additionalCost -> {
      final LAdditionalCosts lAdditionalCosts = new LAdditionalCosts();
      LCustomer lCust = getCustomerByAddress(additionalCost.getContract().getCustomer());
      String firma = lCust.getFirmenName() != null ? lCust.getFirmenName() : " ";
      String anschrift = lCust.getAnrede() + " " + lCust.getName();
      String tel = lCust.getTel() != null ? lCust.getTel(): "" ; 
      String email = lCust.getEmail()!= null ? lCust.getEmail(): "" ;
      lAdditionalCosts.setAnschrift(anschrift +" " + tel + " " + email +" " + firma);

      lAdditionalCosts.setCreatedDate(new DateText(additionalCost.getCreatedAt()));
      lAdditionalCosts.setRechnungsart(additionalCost.getRechnungsart());
      lAdditionalCosts.setTitle(additionalCost.getTitle());
      lAdditionalCosts.setSumBruttoPrice(new Money(additionalCost.getSumBruttoPrice()));
      lAdditionalCosts.setStatusText(additionalCost.getStatusText());
      laddCosts.add(lAdditionalCosts);
      
    });
    context.setCurrentDate(new DateText(Util.getCurrentDate()));
    context.setLac(laddCosts);
    return context;
  }

}
