/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.message;

import java.util.Collections;
import java.util.List;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;

/**
 * {@link ThankMessage} for a simple <em>transfer</em> of a {@link io.github.thanktoken.core.api.ThankToken} from a
 * previous {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getRecipient() recipient} to a new
 * {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getRecipient() recipient} (owner).
 */
public class ThankMessageTokenTransfer extends AbstractThankMessage {

  private final ThankTokenReferenceType reference;

  private final ThankTransaction transaction;

  private List<ThankTransaction> transactions;

  /**
   * The constructor.
   *
   * @param reference the {@link #getReference() reference}.
   * @param transaction the {@link #getTransaction() transaction}.
   */
  public ThankMessageTokenTransfer(ThankTokenReferenceType reference, ThankTransaction transaction) {

    super();
    this.reference = reference;
    this.transaction = transaction;
  }

  @Override
  public ThankMessageType<ThankMessageTokenTransfer> getType() {

    return ThankMessageType.TRANSFER;
  }

  /**
   * @return the {@link ThankTokenReferenceType} pointing to the {@link io.github.thanktoken.core.api.ThankToken} the
   *         {@link #getTransaction() transaction} is created for.
   */
  public ThankTokenReferenceType getReference() {

    return this.reference;
  }

  /**
   * @return the new {@link ThankTransaction}.
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

  @Override
  public List<ThankToken> getTokens() {

    return Collections.emptyList();
  }

  @Override
  public SecurityPublicKey getRecipient() {

    return this.transaction.getRecipient();
  }

}
