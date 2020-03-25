/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import io.github.mmm.crypto.hash.Hash;
import io.github.thanktoken.core.api.data.ThankDataObject;

/**
 * Interface to read the {@link #getHash2Sign() hash to sign} and {@link #getHash2Chain() hash to chain} as
 * {@link Hash}.
 */
public interface AttributeReadHash extends ThankDataObject {

  /**
   * @return the {@link Hash} of this {@link ThankDataObject} to {@link AttributeReadSignature#getSignature() sign}.
   *         Will be {@code null} unless initially calculated (see linked {@code createHashes} methods). This hash is
   *         created by hashing all (non-transient) {@link io.github.thanktoken.core.api.field.ThankField fields}
   *         excluding the {@link AttributeReadSignature#getSignature() signature} in
   *         {@link io.github.thanktoken.core.api.field.ThankFieldMap#getAll() well-defined order}. This is a transient
   *         property only used internally to cache intermediate hashes and save performance.
   * @see io.github.thanktoken.core.api.token.header.AbstractThankTokenHeader#createHashes()
   * @see io.github.thanktoken.core.api.transaction.AbstractThankTransaction#createHashes(io.github.thanktoken.core.api.token.ThankToken,
   *      Hash)
   */
  Hash getHash2Sign();

  /**
   * @return the {@link Hash} of this {@link ThankDataObject} to chain. Will be {@code null} unless initially calculated
   *         (see linked {@code createHashes} methods) and the {@link AttributeReadSignature#getSignature() signature}
   *         being present. This hash is created by hashing the {@link #getHash2Sign() hash to sign} together with the
   *         {@link AttributeReadSignature#getSignature() signature}. It is then
   *         {@link io.github.mmm.crypto.hash.HashCreator#update(io.github.mmm.crypto.CryptoBinary) used} by the next
   *         {@link ThankDataObject} ({@link io.github.thanktoken.core.api.transaction.ThankTransaction}) to calculate
   *         the {@link #getHash2Sign() hash to sign}. This creates a chain that can not be manipulated without breaking
   *         the hashes. The {@link AttributeReadSignature#getSignature() signature} is included in this hash to chain
   *         because for multi-signatures multiple values can be valid and it is relevant who signed the transaction.
   *         This is a transient property only used internally to cache intermediate hashes and save performance.
   * @see io.github.thanktoken.core.api.token.header.AbstractThankTokenHeader#createHashes()
   * @see io.github.thanktoken.core.api.transaction.AbstractThankTransaction#createHashes(io.github.thanktoken.core.api.token.ThankToken,
   *      io.github.thanktoken.core.api.transaction.Hash)
   */
  Hash getHash2Chain();

}
