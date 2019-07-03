package io.github.thanktoken.core.api.token.header;

import net.sf.mmm.crypto.hash.HashCreator;

import io.github.thanktoken.core.api.attribute.AttributeReadSignature;
import io.github.thanktoken.core.api.context.ThankTokenContext;
import io.github.thanktoken.core.api.data.AbstractThankDataObjectWithHash;
import io.github.thanktoken.core.api.id.ThankTokenIdType;

/**
 * The abstract base implementation of {@link ThankTokenHeader}.
 */
public abstract class AbstractThankTokenHeader extends AbstractThankDataObjectWithHash implements ThankTokenHeader {

  private transient ThankTokenIdType id;

  @Override
  public ThankTokenIdType getId() {

    if (this.id == null) {
      this.id = ThankTokenIdType.of(this);
    }
    return this.id;
  }

  /**
   * Calculates the {@link #getHash2Sign() hash to sign} and if the {@link AttributeReadSignature#getSignature()
   * signature} is present also the {@link #getHash2Chain() hash to chain}.
   *
   * @throws RuntimeException if ({@link io.github.thanktoken.core.api.field.ThankField#isMandatory() mandatory})
   *         {@link io.github.thanktoken.core.api.field.ThankField fields} of this object (except potentially the
   *         {@link AttributeReadSignature#getSignature() signature}) or the given {@link ThankTokenContext} are not set
   *         ({@code null}).
   */
  public void createHashes() {

    calculateHashes(this, null);
  }

  @Override
  protected byte[] createHash2Sign(HashCreator hashCreator) {

    return ThankTokenHeaderField.getFields().hash2Sign(this, hashCreator);
  }

  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder(32);
    buffer.append(getAmount());
    buffer.append(getCurrency());
    buffer.append('@');
    buffer.append(getTimestamp());
    return buffer.toString();
  }

}
