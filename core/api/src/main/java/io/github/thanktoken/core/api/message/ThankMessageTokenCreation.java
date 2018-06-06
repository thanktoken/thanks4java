/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.message;

import java.util.Collections;
import java.util.List;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;

/**
 * {@link ThankMessage} for a regular <em>creation</em> of {@link io.github.thanktoken.core.api.ThankToken}s. Each
 * {@link io.github.thanktoken.core.api.identity.ThankIdentityType#NATURAL_PERSON natural person} can create a fixed and
 * pre-defined set of {@link io.github.thanktoken.core.api.ThankToken}s per day (what may also be done by a banking
 * service for that person). The details of creation depend on the used
 * {@link io.github.thanktoken.core.api.currency.ThankCurrency}. For
 * {@link io.github.thanktoken.core.api.currency.ThankSustainableCurrency sustainable currencies} like
 * {@link io.github.thanktoken.core.api.currency.Thanks} each creation consists of one
 * {@link io.github.thanktoken.core.api.ThankToken} for each of the following
 * {@link io.github.thanktoken.core.api.header.ThankTarget}s:
 * <ul>
 * <li>{@link io.github.thanktoken.core.api.header.ThankTarget#PERSON_INCOME}</li>
 * <li>{@link io.github.thanktoken.core.api.header.ThankTarget#COMMUNITY_COUNTRY}</li>
 * <li>{@link io.github.thanktoken.core.api.header.ThankTarget#COMMUNITY_STATE}</li>
 * <li>{@link io.github.thanktoken.core.api.header.ThankTarget#COMMUNITY_REGION}</li>
 * <li>{@link io.github.thanktoken.core.api.header.ThankTarget#SUSTAINABILITY_COUNTRY}</li>
 * <li>{@link io.github.thanktoken.core.api.header.ThankTarget#SUSTAINABILITY_STATE}</li>
 * <li>{@link io.github.thanktoken.core.api.header.ThankTarget#SUSTAINABILITY_REGION}</li>
 * </ul>
 * All created {@link io.github.thanktoken.core.api.ThankToken}s except the
 * {@link io.github.thanktoken.core.api.header.ThankTarget#PERSON_INCOME} have to be created with a third of the
 * {@link io.github.thanktoken.core.api.header.ThankHeader#getAmount() amount} and need a
 * {@link io.github.thanktoken.core.api.transaction.ThankTransaction} transferring the
 * {@link io.github.thanktoken.core.api.ThankToken} to the "government" corresponding to the
 * {@link io.github.thanktoken.core.api.header.ThankTarget#getLevel() level} of the
 * {@link io.github.thanktoken.core.api.header.ThankLocation}.
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
  public SecurityPublicKey getRecipient() {

    SecurityPublicKey recipient = null;
    for (ThankToken token : this.tokens) {
      SecurityPublicKey rcp = token.getHeader().getRecipient();
      if (recipient == null) {
        rcp = recipient;
      } else if (!recipient.equals(rcp)) {
        throw new IllegalStateException("Illegal token creation with multiple recipients.");
      }
    }
    return recipient;
  }

}
