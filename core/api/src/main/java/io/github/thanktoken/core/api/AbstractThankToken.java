/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api;

import java.util.List;

import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * Implementation of {@link ThankToken} as mutable Java bean.
 */
public abstract class AbstractThankToken implements ThankToken {

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append(getHeader());
    sb.append('#');
    List<? extends ThankTransaction> transactions = getTransactions();;
    int size = transactions.size();
    sb.append(size);
    if (size > 0) {
      sb.append('@');
      sb.append(transactions.get(size - 1).getTimestamp());
    }
    return sb.toString();
  }

}
