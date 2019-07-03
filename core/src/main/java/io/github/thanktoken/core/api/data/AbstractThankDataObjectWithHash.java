/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.data;

import java.util.Objects;

import net.sf.mmm.crypto.asymmetric.sign.SignatureBinary;
import net.sf.mmm.crypto.hash.Hash;
import net.sf.mmm.crypto.hash.HashCreator;

import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.attribute.AttributeReadHash;
import io.github.thanktoken.core.api.attribute.AttributeReadSignature;
import io.github.thanktoken.core.api.context.ThankTokenContext;

/**
 * Abstract base implementation of {@link AttributeReadHash}.
 */
public abstract class AbstractThankDataObjectWithHash implements AttributeReadHash, AttributeReadSignature {

  private transient Hash hash2Sign;

  private transient Hash hash2Chain;

  @Override
  public Hash getHash2Sign() {

    return this.hash2Sign;
  }

  @Override
  public Hash getHash2Chain() {

    return this.hash2Chain;
  }

  /**
   * Calculates the {@link #getHash2Sign() hash to sign} and if the {@link AttributeReadSignature#getSignature()
   * signature} is present also the {@link #getHash2Chain() hash to chain}.
   *
   * @param context the {@link ThankTokenContext} e.g. required to determine the hash algorithm.
   * @param predecessorHash the {@link #getHash2Chain() hash to chain} of the predecessor object or {@code null} for
   *        header.
   * @throws RuntimeException if ({@link io.github.thanktoken.core.api.field.ThankField#isMandatory() mandatory})
   *         {@link io.github.thanktoken.core.api.field.ThankField fields} of this object (except potentially the
   *         {@link AttributeReadSignature#getSignature() signature}) or the given {@link ThankTokenContext} are not set
   *         ({@code null}).
   */
  protected void calculateHashes(ThankTokenContext context, Hash predecessorHash) {

    Objects.requireNonNull(context, "context");
    if (this.hash2Sign == null) {
      ThankAlgorithm algorithm = context.requireAlgorithm();
      HashCreator hashCreator = algorithm.getHashFactory().newHashCreator();
      if (predecessorHash != null) {
        hashCreator.update(predecessorHash);
      }
      byte[] hashData = createHash2Sign(hashCreator);
      this.hash2Sign = new Hash(hashData);
    }
    if (this.hash2Chain == null) {
      SignatureBinary signature = getSignature();
      if (signature != null) {
        ThankAlgorithm algorithm = context.requireAlgorithm();
        HashCreator hashCreator = algorithm.getHashFactory().newHashCreator();
        hashCreator.update(this.hash2Sign);
        hashCreator.update(signature);
        byte[] hashData = hashCreator.hash();
        this.hash2Chain = new Hash(hashData);
      }
    }
  }

  /**
   * @param hashCreator the {@link HashCreator}.
   * @return the raw hash data.
   */
  protected abstract byte[] createHash2Sign(HashCreator hashCreator);

}
