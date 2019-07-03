/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.message;

import java.util.Collections;
import java.util.List;

import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * {@link ThankMessage} for a <em>fork</em> of a {@link io.github.thanktoken.core.api.token.ThankToken}. If a
 * {@link io.github.thanktoken.core.api.token.ThankToken} is forked, it gets invalidated and new
 * {@link io.github.thanktoken.core.api.token.ThankToken}s are created with a summarized total amount that must be less or
 * equal to the current {@link io.github.thanktoken.core.api.token.ThankToken#getValue() value} of the forked
 * {@link io.github.thanktoken.core.api.token.ThankToken}.<br>
 * For invalidation a new {@link io.github.thanktoken.core.api.transaction.ThankTransaction} is
 * {@link io.github.thanktoken.core.api.token.ThankTokenBean#addTransaction(io.github.thanktoken.core.api.transaction.ThankTransaction)
 * added} that has no {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getRecipient() recipient}, a
 * {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getReference() reference} with the
 * {@link io.github.thanktoken.core.api.reference.ThankTokenReference#getType() type}
 * {@link io.github.thanktoken.core.api.attribute.AttributeReadReferenceType#TYPE_FORK_TO} and that links to the first
 * created {@link io.github.thanktoken.core.api.token.ThankToken}. Each of these created tokens must again have a
 * {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader#getReference() reference} that connect them as a circular
 * chain. Each of them has the {@link io.github.thanktoken.core.api.reference.ThankTokenReference#getType() type}
 * {@link io.github.thanktoken.core.api.attribute.AttributeReadReferenceType#TYPE_FORK_WITH} that connects it with the
 * next created token. However, the last created token has a
 * {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader#getReference() reference} with the
 * {@link io.github.thanktoken.core.api.reference.ThankTokenReference#getType() type}
 * {@link io.github.thanktoken.core.api.attribute.AttributeReadReferenceType#TYPE_FORK_FROM} linking back to the
 * originally forked token.<br>
 * All created {@link io.github.thanktoken.core.api.token.ThankToken}s have the same
 * {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader#getLocation() location} and
 * {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader#getTarget() target} as the forked token.
 */
public class ThankMessageTokenFork extends AbstractThankMessage {

  private final ThankTransaction transaction;

  private final List<ThankToken> tokens;

  private List<ThankTransaction> transactions;

  /**
   * The constructor.
   *
   * @param transaction the {@link #getTransaction() invalidating transaction}.
   * @param tokens the {@link #getTokens() newly created tokens}.
   */
  public ThankMessageTokenFork(ThankTransaction transaction, List<ThankToken> tokens) {

    super();
    this.transaction = transaction;
    this.tokens = tokens;
  }

  @Override
  public ThankMessageType<ThankMessageTokenFork> getType() {

    return ThankMessageType.FORK;
  }

  /**
   * @return the {@link ThankTransaction} that invalidates the forked {@link ThankToken}.
   */
  public ThankTransaction getTransaction() {

    return this.transaction;
  }

  @Override
  public List<ThankTransaction> getTransactions() {

    if (this.transactions == null) {
      this.transactions = Collections.unmodifiableList(Collections.singletonList(this.transaction));
    }
    return this.transactions;
  }

  /**
   * @return the {@link List} of newly created {@link ThankToken}s the {@link #getTransaction() forked token} has been
   *         split into.
   */
  @Override
  public List<ThankToken> getTokens() {

    return this.tokens;
  }

  @Override
  public ThankAddress getRecipient() {

    if (this.tokens.size() > 0) {
      return this.tokens.get(0).getHeader().getRecipient();
    }
    return null;
  }

}
