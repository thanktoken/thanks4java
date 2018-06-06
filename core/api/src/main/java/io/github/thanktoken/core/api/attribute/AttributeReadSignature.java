/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import io.github.thanktoken.core.api.ThankDataObject;
import net.sf.mmm.security.api.sign.SecuritySignature;

/**
 * Interface to {@link #getSignature() read} the {@link SecuritySignature}.
 */
public interface AttributeReadSignature extends ThankDataObject {

  /**
   * @return the {@link SecuritySignature} of this object that was signed using the private key of the (previous)
   *         {@link AttributeReadRecipient#getRecipient() recipient} (owner). So this signature can be validated using
   *         the previous {@link AttributeReadRecipient#getRecipient() recipient} (owner). The {@link SecuritySignature}
   *         is build from the hash of the fields of this object excluding this {@link SecuritySignature}.
   * @see io.github.thanktoken.core.api.ThankSignedObject#getSignature()
   * @see io.github.thanktoken.core.api.header.ThankHeader#getSignature()
   * @see io.github.thanktoken.core.api.transaction.ThankTransaction#getSignature()
   * @see io.github.thanktoken.core.api.message.ThankMessage#getSignature()
   */
  SecuritySignature getSignature();
}
