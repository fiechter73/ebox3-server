package com.ebox3.server.doc;

import java.io.InputStream;
import java.io.OutputStream;

import io.reflectoring.docxstamper.DocxStamper;
import io.reflectoring.docxstamper.DocxStamperConfiguration;



public class Stamper {

  private DocxStamper<Context> stamper;

  @SuppressWarnings("unchecked")
  public void stamp(InputStream template, Context con, OutputStream out) {
    if (this.stamper == null) {
      this.stamper = new DocxStamperConfiguration().addTypeResolver(Money.class, new MoneyTypeResolver())
          .addTypeResolver(DateText.class, new DateTypeResolver()).build();
    }
    this.stamper.stamp(template, con, out);
  }
  
  public void close() {
    this.stamper = null;
    System.gc();
  }
  
}
