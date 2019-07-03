/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.util.Objects;

import net.sf.mmm.crypto.asymmetric.sign.SignatureBinary;

import io.github.thanktoken.core.api.data.ThankDataObject;

/**
 * Interface to {@link #getSignature() read} the {@link SignatureBinary}.
 */
public interface AttributeReadSignature extends ThankDataObject {

  /** Name of property {@link #getSignature()}. */
  String PROPERTY_SIGNATURE = "signature";

  /**
   * @return the {@link SignatureBinary} of this object that was signed using the private key of the (previous)
   *         {@link AttributeReadRecipient#getRecipient() recipient} (owner). So this signature can be validated using
   *         the previous {@link AttributeReadRecipient#getRecipient() recipient} (owner). The {@link SignatureBinary}
   *         is build from the hash of the fields of this object excluding this {@link SignatureBinary}.
   * @see io.github.thanktoken.core.api.data.ThankSignedObject#getSignature()
   * @see io.github.thanktoken.core.api.token.header.ThankTokenHeader#getSignature()
   * @see io.github.thanktoken.core.api.transaction.ThankTransaction#getSignature()
   * @see io.github.thanktoken.core.api.message.ThankMessage#getSignature()
   */
  SignatureBinary getSignature();

  /**
   * @return the required {@link #getSignature() signature}.
   */
  default SignatureBinary requireSignature() {

    SignatureBinary signature = getSignature();
    Objects.requireNonNull(signature, PROPERTY_SIGNATURE);
    return signature;
  }
}
