/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.token;

import java.util.List;

import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.location.ThankLocation;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * Implementation of {@link ThankToken} as mutable Java bean.
 */
public abstract class AbstractThankToken implements ThankToken {

  @Override
  public ThankVersion getVersion() {

    ThankTokenHeader header = getHeader();
    if (header != null) {
      return header.getVersion();
    }
    return null;
  }

  @Override
  public ThankAlgorithm getAlgorithm() {

    ThankTokenHeader header = getHeader();
    if (header != null) {
      return header.getAlgorithm();
    }
    return null;
  }

  @Override
  public ThankCurrency getCurrency() {

    ThankTokenHeader header = getHeader();
    if (header != null) {
      return header.getCurrency();
    }
    return null;
  }

  @Override
  public ThankLocation getLocation() {

    ThankTokenHeader header = getHeader();
    if (header != null) {
      return header.getLocation();
    }
    return null;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append(getHeader());
    sb.append('#');
    List<? extends ThankTransaction> transactions = getTransactions();
    ;
    int size = transactions.size();
    sb.append(size);
    if (size > 0) {
      sb.append('@');
      sb.append(transactions.get(size - 1).getTimestamp());
    }
    return sb.toString();
  }

}
