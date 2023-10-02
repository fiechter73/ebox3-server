package com.ebox3.server.doc;

import io.reflectoring.docxstamper.replace.typeresolver.AbstractToTextResolver;

public class MoneyTypeResolver extends AbstractToTextResolver<Money> {
    protected String resolveStringForObject(Money money) {
      return String.format("%.2f",money.getMoney());
    }
}