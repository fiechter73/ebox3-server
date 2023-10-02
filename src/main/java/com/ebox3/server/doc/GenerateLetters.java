package com.ebox3.server.doc;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.ebox3.server.model.Customer;

public class GenerateLetters extends Document {

  private final Log logger = LogFactory.getLog(getClass());

  public boolean generateLetterMahung(Customer customer, InputStream template, OutputStream out) {
    try {
      Context con = buildLetter(customer);
      Stamper stamp = new Stamper();
      stamp.stamp(template, con, out);
      stamp.close();
    } catch (final Exception e) {
      logger.error("Exception: " + e.getMessage());
    } finally {
      try {
        out.close();
        template.close();
        logger.info("KB Letter: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
      } catch (final IOException e) {
        logger.error("IOException: " + e.getMessage());
      }
    }
    return true;
  }

  private Context buildLetter(Customer customer) {
    Context context = new Context();
    LCustomer lcustomer = getCustomerByAddress(customer);
    context.setCurrentDate(new DateText(Util.getCurrentDate()));
    context.setCu(lcustomer);
    return context;
  }

}
