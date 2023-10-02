package com.ebox3.server.doc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Util {
  
  static Double sumPrice = 0.00;
  
  public static String getFormatedDate(Date date) {
    String pattern = "dd.MM.yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    return simpleDateFormat.format(date).toString();
  }
  
  public static Date possibleTerminationDate(Date contractStartDate, Long minDuration) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(contractStartDate);
    int duration = minDuration!= null ? minDuration.intValue() - 1:5;
    calendar.add(Calendar.MONTH, duration);
    int lastDateOfThisMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    calendar.set(year,month, lastDateOfThisMonth);
    return calendar.getTime();
    
  }
  
  public static Double sumPriceBrutto(Double price) {
    sumPrice = sumPrice + price;
    return sumPrice;
  }
  
  public static void clearSumPriceBrutto() {
    sumPrice = 0.00;
  }
  
  public static Date getCurrentDate() {
    Calendar calendar = Calendar.getInstance();    
    return calendar.getTime();
  }
  
  
  public static String getYearByString(Date date) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy");
    return dateFormat.format(date);
  }
  
  public static Date get01_01_(String jahr) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, Integer.parseInt(jahr));
    cal.set(Calendar.MONTH, 0); // 0= jan
    cal.set(Calendar.DAY_OF_MONTH, 1);
    Date start = cal.getTime();
    return start;
  }
    
  public static Date get31_03_(String jahr) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, Integer.parseInt(jahr));
    cal.set(Calendar.MONTH, 2); // 2 = märz
    cal.set(Calendar.DAY_OF_MONTH, 31);
    Date start = cal.getTime();
    return start;
  }
  
  public static Date get01_04_(String jahr) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, Integer.parseInt(jahr));
    cal.set(Calendar.MONTH, 3); // 3 = april
    cal.set(Calendar.DAY_OF_MONTH, 1);
    Date start = cal.getTime();
    return start;
  }
  
  public static Date get30_06_(String jahr) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, Integer.parseInt(jahr));
    cal.set(Calendar.MONTH, 5); // 5 = june
    cal.set(Calendar.DAY_OF_MONTH, 30);
    Date start = cal.getTime();
    return start;
  }

  public static Date get01_07_(String jahr) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, Integer.parseInt(jahr));
    cal.set(Calendar.MONTH, 6); // 6 = jul
    cal.set(Calendar.DAY_OF_MONTH, 1);
    Date start = cal.getTime();
    return start;
  }
  
  public static Date get30_09_(String jahr) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, Integer.parseInt(jahr));
    cal.set(Calendar.MONTH, 8); // 8 = sep
    cal.set(Calendar.DAY_OF_MONTH, 30);
    Date start = cal.getTime();
    return start;
  }
  
  public static Date get01_10_(String jahr) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, Integer.parseInt(jahr));
    cal.set(Calendar.MONTH, 9); // 9 = okt
    cal.set(Calendar.DAY_OF_MONTH, 1);
    Date start = cal.getTime();
    return start;
  }
  
  public static Date get_31_12_(String jahr) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, Integer.parseInt(jahr));
    cal.set(Calendar.MONTH, 11); // 11 = december
    cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve
    Date end = cal.getTime();
    return end;
  }
  
  
  
   
}
