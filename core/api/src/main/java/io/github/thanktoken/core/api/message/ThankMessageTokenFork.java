/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.message;

import java.util.Collections;
import java.util.List;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;

/**
 * {@link ThankMessage} for a <em>fork</em> of a {@link io.github.thanktoken.core.api.ThankToken}. If a
 * {@link io.github.thanktoken.core.api.ThankToken} is forked, it gets invalidated and new
 * {@link io.github.thanktoken.core.api.ThankToken}s are created with a summarized total amount that must be less or
 * equal to the current {@link io.github.thanktoken.core.api.ThankToken#getValue() value} of the forked
 * {@link io.github.thanktoken.core.api.ThankToken}.<br>
 * For invalidation a new {@link io.github.thanktoken.core.api.transaction.ThankTransaction} is
 * {@link io.github.thanktoken.core.api.ThankTokenBean#addTransaction(io.github.thanktoken.core.api.transaction.ThankTransaction)
 * added} that has no {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getRecipient() recipient}, a
 * {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getPublicPurpose() public purpose} of
 * {@link #FORK_TO} and a {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getReferenceList() reference
 * list} containing all the {@link io.github.thanktoken.core.api.reference.ThankTokenReferenceType references} to the
 * created {@link io.github.thanktoken.core.api.ThankToken}s.<br>
 * These created {@link io.github.thanktoken.core.api.ThankToken}s are
 * {@link io.github.thanktoken.core.api.header.ThankHeader#getReference() referencing} the invalidated forked
 * {@link io.github.thanktoken.core.api.ThankToken} and have the same
 * {@link io.github.thanktoken.core.api.header.ThankHeader#getLocation() location} and
 * {@link io.github.thanktoken.core.api.header.ThankHeader#getTarget() target}.
 */
public class ThankMessageTokenFork extends AbstractThankMessage {

  /**
   * The {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getPublicPurpose() public purpose} of the
   * {@link io.github.thanktoken.core.api.transaction.ThankTransaction} of the forked
   * {@link io.github.thanktoken.core.api.ThankToken}.
   */
  public static final String FORK_TO = "forkTo";

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
  public SecurityPublicKey getRecipient() {

    // TODO Auto-generated method stub
    return null;
  }

}
