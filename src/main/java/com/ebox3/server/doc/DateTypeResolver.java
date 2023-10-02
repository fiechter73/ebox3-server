package com.ebox3.server.doc;

import java.text.SimpleDateFormat;

import io.reflectoring.docxstamper.replace.typeresolver.AbstractToTextResolver;



public class DateTypeResolver extends AbstractToTextResolver<DateText> {

    protected String resolveStringForObject(DateText dateText) {    
      String pattern = "dd.MM.yyyy";
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
      String x = simpleDateFormat.format(dateText.getDateText());
      return x;
    }  
}
