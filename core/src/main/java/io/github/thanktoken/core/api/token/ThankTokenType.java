/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.token.header.ThankTokenHeaderType;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * Implementation of {@link ThankToken} as mutable Java bean.
 */
public class ThankTokenType extends AbstractThankToken {

  private final ThankTokenHeader header;

  private final List<ThankTransaction> transactionsMutable;

  private final List<ThankTransaction> transactions;

  /**
   * The constructor.
   *
   * @param header the fixed {@link #getHeader() header}.
   */
  public ThankTokenType(ThankTokenHeader header) {

    super();
    this.header = header;
    this.transactionsMutable = new ArrayList<>();
    this.transactions = Collections.unmodifiableList(this.transactionsMutable);
  }

  /**
   * The constructor.
   *
   * @param token the {@link ThankToken} to copy.
   */
  public ThankTokenType(ThankToken token) {

    super();
    this.header = new ThankTokenHeaderType(token.getHeader());
    this.transactionsMutable = new ArrayList<>(token.getTransactions());
    this.transactions = Collections.unmodifiableList(this.transactionsMutable);
  }

  @Override
  public ThankTokenHeader getHeader() {

    return this.header;
  }

  @Override
  public List<? extends ThankTransaction> getTransactions() {

    return this.transactions;
  }

  /**
   * @param tx the {@link ThankTransaction} to add to {@link #getTransactions()}.
   * @return this.
   */
  public ThankTokenType addTransaction(ThankTransaction tx) {

    this.transactionsMutable.add(tx);
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
