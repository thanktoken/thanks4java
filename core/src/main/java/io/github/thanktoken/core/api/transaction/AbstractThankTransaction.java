package io.github.thanktoken.core.api.transaction;

import io.github.mmm.crypto.hash.Hash;
import io.github.mmm.crypto.hash.HashCreator;
import io.github.thanktoken.core.api.attribute.AttributeReadSignature;
import io.github.thanktoken.core.api.context.ThankTokenContext;
import io.github.thanktoken.core.api.data.AbstractThankDataObjectWithHash;
import io.github.thanktoken.core.api.token.ThankToken;

/**
 * This is the abstract base implementation of {@link ThankTransaction}.
 */
public abstract class AbstractThankTransaction extends AbstractThankDataObjectWithHash implements ThankTransaction {

  /**
   * The constructor.
   */
  public AbstractThankTransaction() {

    super();
  }

  /**
   * Calculates the {@link #getHash2Sign() hash to sign} and if the {@link AttributeReadSignature#getSignature()
   * signature} is present also the {@link #getHash2Chain() hash to chain}.
   *
   * @param token the {@link ThankToken} owning this transaction.
   * @param predecessorHash the {@link #getHash2Chain() hash to chain} of the predecessor object (previous transaction
   *        or header for first transaction).
   * @throws RuntimeException if ({@link io.github.thanktoken.core.api.field.ThankField#isMandatory() mandatory})
   *         {@link io.github.thanktoken.core.api.field.ThankField fields} of this object (except potentially the
   *         {@link AttributeReadSignature#getSignature() signature}) or the given {@link ThankTokenContext} are not set
   *         ({@code null}).
   */
  public void createHashes(ThankToken token, Hash predecessorHash) {

    calculateHashes(token, predecessorHash);
  }

  @Override
  protected byte[] createHash2Sign(HashCreator hashCreator) {

    return ThankTransactionField.getFields().hash2Sign(this, hashCreator);
  }

  @Override
  public String toString() {

    return "TX@" + getTimestamp();
  }

}
