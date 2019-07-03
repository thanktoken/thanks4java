/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.token;

import java.util.ArrayList;
import java.util.List;

import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.token.header.ThankTokenHeaderType;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * Implementation of {@link ThankToken} as mutable Java bean.
 */
public class ThankTokenBean extends AbstractThankToken {

  private ThankTokenHeader header;

  private List<ThankTransaction> transactions;

  /**
   * The constructor.
   */
  public ThankTokenBean() {

    super();
  }

  /**
   * The constructor.
   *
   * @param header the fixed {@link #getHeader() header}.
   */
  public ThankTokenBean(ThankTokenHeader header) {

    super();
    this.header = header;
    this.transactions = new ArrayList<>();
  }

  /**
   * The constructor.
   *
   * @param token the {@link ThankToken} to copy.
   */
  public ThankTokenBean(ThankToken token) {

    super();
    this.header = new ThankTokenHeaderType(token.getHeader());
    this.transactions = new ArrayList<>(token.getTransactions());
  }

  @Override
  public ThankTokenHeader getHeader() {

    return this.header;
  }

  /**
   * @param header the new value of {@link #getHeader()}.
   * @return this
   */
  public ThankTokenBean setHeader(ThankTokenHeader header) {

    this.header = header;
    return this;
  }

  @Override
  public List<? extends ThankTransaction> getTransactions() {

    return this.transactions;
  }

  /**
   * @param transactions the new value of {@link #getTransactions()}.
   * @return this
   */
  public ThankTokenBean setTransactions(List<ThankTransaction> transactions) {

    this.transactions = transactions;
    return this;
  }

  /**
   * @param tx the {@link ThankTransaction} to add to {@link #getTransactions()}.
   * @return this.
   */
  public ThankTokenBean addTransaction(ThankTransaction tx) {

    this.transactions.add(tx);
    return this;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append(this.header);
    sb.append('#');
    int size = this.transactions.size();
    sb.append(size);
    if (size > 0) {
      sb.append('@');
      sb.append(this.transactions.get(size - 1).getTimestamp());
    }
    return sb.toString();
  }

}
