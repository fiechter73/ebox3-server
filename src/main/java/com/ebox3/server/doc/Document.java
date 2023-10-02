package com.ebox3.server.doc;

import java.util.ArrayList;
import java.util.List;

import com.ebox3.server.model.Customer;
import com.ebox3.server.model.ElectricPeriod;

public class Document {

  protected LCustomer getCustomerByAddress(Customer customer) {
    LCustomer cu = new LCustomer();
    if (customer != null && customer.isUseCompanyAddress() && customer.getFirmenAnschrift() != null) {
      cu.setUseCompanyAddress("Ja");
      if (customer.getFirmenAnschrift() != null) {
        String[] arrayOfStr = customer.getFirmenAnschrift().split(",");
        if (arrayOfStr.length > 1) {
          cu.setFirmenName(customer.getFirmenName());
          cu.setStrasse(arrayOfStr[0] != null ? arrayOfStr[0].trim() : "");
          cu.setOrt(arrayOfStr[1] != null ? arrayOfStr[1].trim() : "");
        } else {
          cu.setFirmenName(customer.getFirmenName() != null ? customer.getFirmenName() : "");
          cu.setStrasse("n/a");
          cu.setOrt("n/a");
        }
        cu.setAnrede(customer.getAnrede());
        cu.setName(customer.getVorname() + " " + customer.getName());
      } else {
        cu.setAnrede(customer.getAnrede());
        cu.setName(customer.getVorname() + " " + customer.getName());
        cu.setFirmenAnschrift("n/a");
      }

    } else {
      cu.setUseCompanyAddress("Nein");
      cu.setAnrede(customer.getAnrede());
      cu.setName(customer.getVorname() + " " + customer.getName());
      cu.setStrasse(customer.getStrasse() != null ? customer.getStrasse() : "");
      String plz = customer.getPlz() != null ? customer.getPlz() : "";
      String ort = customer.getOrt() != null ? customer.getOrt() : "";
      cu.setOrt(plz + " " + ort);
    }
    cu.setStatusText(customer.getStatusText());
    cu.setTel(customer.getTel1() != null ? customer.getTel1() : "");
    cu.setEmail(customer.getEmail() != null ? customer.getEmail() : "");
    return cu;
  }

  protected List<LCustomer> getCustomerListByAddress(List<Customer> customers) {
    List<LCustomer> list = new ArrayList<LCustomer>();
    customers.forEach(customer -> {
      LCustomer cu = new LCustomer();
      cu.setFirmenName(customer.getFirmenName() != null ? customer.getFirmenName() : "");
      cu.setFirmenAnschrift(customer.getFirmenAnschrift() != null ? customer.getFirmenAnschrift() : "");
      cu.setAnrede(customer.getAnrede() != null ? customer.getAnrede() : "");
      String vorname = customer.getVorname() != null ? customer.getVorname() : "";
      String name = customer.getName() != null ? customer.getName() : "";
      cu.setName(vorname + " " + name);
      cu.setStrasse(customer.getStrasse() != null ? customer.getStrasse() : "");
      cu.setPlz(customer.getPlz() != null ? customer.getPlz() : "");
      cu.setOrt(customer.getOrt() != null ? customer.getOrt() : "");
      cu.setBemerkungen(customer.getBemerkungen() != null ? customer.getBemerkungen() : "");
      cu.setStatusText(customer.getStatusText());
      cu.setTel(customer.getTel1() != null ? customer.getTel1() : "");
      cu.setTel1(customer.getTel2() != null ? customer.getTel2() : "");
      cu.setEmail(customer.getEmail() != null ? customer.getEmail() : "");
      list.add(cu);
    });
    return list;
  }


  protected List<LElectricPeriod> getElectricPeriodByCustomerId(Long id, List<ElectricPeriod> epList) {

    List<LElectricPeriod> list = new ArrayList<LElectricPeriod>();
    epList.forEach(ep -> {
      if (ep.getId().equals(id)) {
        LElectricPeriod elPeriod = new LElectricPeriod();
        elPeriod.setBemerkung(ep.getBemerkungen());
        elPeriod.setTotPriceBox(new Money(ep.getStromPriceBrutto()));
        elPeriod.setFromDate(new DateText(ep.getZaehlerFromPeriode()));
        elPeriod.setToDate(new DateText(ep.getZaehlerToPeriode()));
        elPeriod.setStatusText(ep.getStatusText());
        list.add(elPeriod);
      }
    });
    return list;
  }
}
