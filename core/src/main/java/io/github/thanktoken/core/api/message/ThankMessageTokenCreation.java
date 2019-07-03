/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.message;

import java.util.Collections;
import java.util.List;

import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * {@link ThankMessage} for a regular <em>creation</em> of {@link io.github.thanktoken.core.api.token.ThankToken}s. Each
 * {@link io.github.thanktoken.core.api.identity.ThankIdentityType#NATURAL_PERSON natural person} can create a fixed and
 * pre-defined set of {@link io.github.thanktoken.core.api.token.ThankToken}s per day (what may also be done by a banking
 * service for that person). The details of creation depend on the used
 * {@link io.github.thanktoken.core.api.currency.ThankCurrency}. For
 * {@link io.github.thanktoken.core.api.currency.ThankSustainableCurrency sustainable currencies} like
 * {@link io.github.thanktoken.core.api.currency.Thanks} each creation consists of one
 * {@link io.github.thanktoken.core.api.token.ThankToken} for each of the following
 * {@link io.github.thanktoken.core.api.target.ThankTarget}s:
 * <ul>
 * <li>{@link io.github.thanktoken.core.api.target.ThankTarget#PERSON_INCOME}</li>
 * <li>{@link io.github.thanktoken.core.api.target.ThankTarget#COMMUNITY_WORLD}</li>
 * <li>{@link io.github.thanktoken.core.api.target.ThankTarget#COMMUNITY_SECTOR}</li>
 * <li>{@link io.github.thanktoken.core.api.target.ThankTarget#COMMUNITY_AREA}</li>
 * <li>{@link io.github.thanktoken.core.api.target.ThankTarget#COMMUNITY_REGION}</li>
 * <li>{@link io.github.thanktoken.core.api.target.ThankTarget#SUSTAINABILITY_WORLD}</li>
 * <li>{@link io.github.thanktoken.core.api.target.ThankTarget#SUSTAINABILITY_SECTOR}</li>
 * <li>{@link io.github.thanktoken.core.api.target.ThankTarget#SUSTAINABILITY_AREA}</li>
 * <li>{@link io.github.thanktoken.core.api.target.ThankTarget#SUSTAINABILITY_REGION}</li>
 * </ul>
 * All created {@link io.github.thanktoken.core.api.token.ThankToken}s except the
 * {@link io.github.thanktoken.core.api.target.ThankTarget#PERSON_INCOME} have to be created with a third of the
 * {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader#getAmount() amount} and need a
 * {@link io.github.thanktoken.core.api.transaction.ThankTransaction} transferring the
 * {@link io.github.thanktoken.core.api.token.ThankToken} to the "government" corresponding to the
 * {@link io.github.thanktoken.core.api.target.ThankTarget#getLevel() level} of the
 * {@link io.github.thanktoken.core.api.location.ThankLocation}.
 */
public class ThankMessageTokenCreation extends AbstractThankMessage {

  private final List<ThankToken> tokens;

  /**
   * The constructor.
   *
   * @param tokens the {@link #getTokens() newly created tokens}.
   */
  public ThankMessageTokenCreation(List<ThankToken> tokens) {

    super();
    this.tokens = tokens;
  }

  @Override
  public List<ThankToken> getTokens() {

    return this.tokens;
  }

  @Override
  public List<ThankTransaction> getTransactions() {

    return Collections.emptyList();
  }

  @Override
  public ThankMessageType<ThankMessageTokenCreation> getType() {

    return ThankMessageType.CREATION;
  }

  @Override
  public ThankAddress getRecipient() {

    ThankAddress recipient = null;
    for (ThankToken token : this.tokens) {
      ThankAddress rcp = token.getHeader().getRecipient();
      if (recipient == null) {
        rcp = recipient;
      } else if (!recipient.equals(rcp)) {
        throw new IllegalStateException("Illegal token creation with multiple recipients.");
      }
    }
    return recipient;
  }

}
