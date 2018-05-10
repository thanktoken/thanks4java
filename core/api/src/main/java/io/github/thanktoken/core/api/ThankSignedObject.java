/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api;

import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;
import net.sf.mmm.security.api.sign.SecuritySignature;

/**
 * Super-interface of an object that has a {@link #getRecipient() recipient} and is {@link #getSignature() signed}
 * accordingly.
 *
 * @see ThankHeader
 * @see ThankTransaction
 */
public abstract interface ThankSignedObject {

  /**
   * @return the {@link SecurityPublicKey} of the recipient (current owner) of the {@link ThankToken} at the time when
   *         this object has been created and verified.
   */
  SecurityPublicKey getRecipient();

  /**
   * @return the {@link SecuritySignature} as its hash (excluding this {@link SecuritySignature}) signed using the
   *         private key of the (previous) {@link #getRecipient() recipient} (owner). So this signature can be validated
   *         using the {@link #getRecipient() recipient} of the {@link ThankToken#getTransactions() previous}
   *         {@link ThankTransaction}.
   */
  SecuritySignature getSignature();

}
