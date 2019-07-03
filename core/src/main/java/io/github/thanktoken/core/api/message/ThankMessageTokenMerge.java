/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.message;

import java.util.Collections;
import java.util.List;

import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * {@link ThankMessage} for a <em>merge</em> of {@link io.github.thanktoken.core.api.token.ThankToken}s. If
 * {@link io.github.thanktoken.core.api.token.ThankToken}s are merged, they all get invalidated and a new
 * {@link io.github.thanktoken.core.api.token.ThankToken} is created with an
 * {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader#getAmount() amount} that must be less or equal to the
 * summarized current {@link io.github.thanktoken.core.api.token.ThankToken#getValue() values} of the merged and invalidated
 * {@link io.github.thanktoken.core.api.token.ThankToken}s.<br>
 * Every merged {@link ThankToken} needs to add a {@link io.github.thanktoken.core.api.transaction.ThankTransaction}
 * with no {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getRecipient() recipient} and a
 * {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getReference() reference} having the
 * {@link io.github.thanktoken.core.api.reference.ThankTokenReference#getType() type}
 * {@link io.github.thanktoken.core.api.attribute.AttributeReadReferenceType#TYPE_MERGE_WITH} pointing to the next
 * merged token. However, the last merged token, needs to use the
 * {@link io.github.thanktoken.core.api.reference.ThankTokenReference#getType() type}
 * {@link io.github.thanktoken.core.api.attribute.AttributeReadReferenceType#TYPE_MERGE_TO} pointing to the newly
 * created token. That new token has to have a {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader#getReference()
 * reference} with the {@link io.github.thanktoken.core.api.reference.ThankTokenReference#getType() type}
 * {@link io.github.thanktoken.core.api.attribute.AttributeReadReferenceType#TYPE_MERGE_FROM} pointing to the first
 * merged token. This way the tokens build a circular reference what simplifies validation from any point in the chain.
 * <br>
 * Additionally specific rules apply for the tokens to be merged. E.g. it is invalid to merge tokens of different
 * {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader#getTarget() targets} or incompatible
 * {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader#getLocation() locations} unless they have reached their
 * destination.
 */
public class ThankMessageTokenMerge extends AbstractThankMessage {

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
  public ThankAddress getRecipient() {

    return this.token.getHeader().getRecipient();
  }

  /**
   * @return the newly created {@link ThankToken} with the value of the {@link #getTransactions() merged tokens} as
   *         {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader#getAmount() amount}.
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
