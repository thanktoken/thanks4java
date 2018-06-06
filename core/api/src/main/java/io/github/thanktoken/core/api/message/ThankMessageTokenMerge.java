/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.message;

import java.util.Collections;
import java.util.List;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;

/**
 * {@link ThankMessage} for a <em>merge</em> of {@link io.github.thanktoken.core.api.ThankToken}s. If
 * {@link io.github.thanktoken.core.api.ThankToken}s are merged, they all get invalidated and a new
 * {@link io.github.thanktoken.core.api.ThankToken} is created with an amount
 * {@link io.github.thanktoken.core.api.header.ThankHeader#getAmount() amount} that must be less or equal to the
 * summarized current {@link io.github.thanktoken.core.api.ThankToken#getValue() values} of the merged and invalidated
 * {@link io.github.thanktoken.core.api.ThankToken}s. The newly created current
 * {@link io.github.thanktoken.core.api.ThankToken} has to current
 * {@link io.github.thanktoken.core.api.header.ThankHeader#getReference() reference} the "first" of the merged and
 * invalidated {@link io.github.thanktoken.core.api.ThankToken}. For each of these merged
 * {@link io.github.thanktoken.core.api.ThankToken}s a
 * {@link io.github.thanktoken.core.api.transaction.ThankTransaction} is
 * {@link io.github.thanktoken.core.api.ThankTokenBean#addTransaction(io.github.thanktoken.core.api.transaction.ThankTransaction)
 * added} that has no {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getRecipient() recipient}, a
 * {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getPublicPurpose() public purpose} of
 * {@link #MERGE_WITH} and a {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getReference() reference}
 * pointing to the next merged {@link io.github.thanktoken.core.api.ThankToken}. However, the "last" merged
 * {@link io.github.thanktoken.core.api.ThankToken} is invalidated with a
 * {@link io.github.thanktoken.core.api.transaction.ThankTransaction} that has a
 * {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getPublicPurpose() public purpose} of
 * {@link #MERGE_TO} and a {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getReference() reference}
 * pointing to the newly created {@link io.github.thanktoken.core.api.ThankToken} replacing all the merged and
 * invalidated {@link io.github.thanktoken.core.api.ThankToken}s.
 */
public class ThankMessageTokenMerge extends AbstractThankMessage {

  /**
   * The {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getPublicPurpose() public purpose} of the
   * {@link io.github.thanktoken.core.api.transaction.ThankTransaction} of the merged
   * {@link io.github.thanktoken.core.api.ThankToken}s (except for the {@link #MERGE_TO last} one).
   */
  public static final String MERGE_WITH = "mergeWith";

  /**
   * The {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getPublicPurpose() public purpose} of the
   * {@link io.github.thanktoken.core.api.transaction.ThankTransaction} of the "last" merged
   * {@link io.github.thanktoken.core.api.ThankToken}.
   */
  public static final String MERGE_TO = "mergeTo";

  private final ThankToken token;

  private List<ThankToken> tokens;

  private final List<ThankTransaction> transactions;

  /**
   * The constructor.
   *
   * @param token the newly created {@link #getToken() token} with the merged value.
   * @param transactions the {@link ThankTransaction}s with the merge invalidation.
   */
  public ThankMessageTokenMerge(ThankToken token, List<ThankTransaction> transactions) {

    super();
    this.token = token;
    this.transactions = transactions;
  }

  @Override
  public ThankMessageType<ThankMessageTokenMerge> getType() {

    return ThankMessageType.MERGE;
  }

  @Override
  public SecurityPublicKey getRecipient() {

    return this.token.getHeader().getRecipient();
  }

  /**
   * @return the newly created {@link ThankToken} with the value of the {@link #getTransactions() merged tokens} as
   *         {@link io.github.thanktoken.core.api.header.ThankHeader#getAmount() amount}.
   */
  public ThankToken getToken() {

    return this.token;
  }

  @Override
  public List<ThankTransaction> getTransactions() {

    return this.transactions;
  }

  @Override
  public List<ThankToken> getTokens() {

    if (this.tokens == null) {
      this.tokens = Collections.unmodifiableList(Collections.singletonList(this.token));
    }
    return this.tokens;
  }

}
