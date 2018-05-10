/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.header.ThankHeaderType;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * Implementation of {@link ThankToken} as mutable Java bean.
 */
public class ThankTokenBean implements ThankToken {

  private final ThankHeader header;

  private final List<ThankTransaction> transactionsMutable;

  private final List<ThankTransaction> transactions;

  /**
   * The constructor.
   *
   * @param header the fixed {@link #getHeader() header}.
   */
  public ThankTokenBean(ThankHeader header) {

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
  public ThankTokenBean(ThankToken token) {

    super();
    this.header = new ThankHeaderType(token.getHeader());
    this.transactionsMutable = new ArrayList<>(token.getTransactions());
    this.transactions = Collections.unmodifiableList(this.transactionsMutable);
  }

  @Override
  public ThankHeader getHeader() {

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
  public ThankTokenBean addTransaction(ThankTransaction tx) {

    this.transactionsMutable.add(tx);
    return this;
  }

}
