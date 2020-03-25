/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.data;

import io.github.mmm.crypto.asymmetric.sign.SignatureBinary;
import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.attribute.AttributeReadRecipient;
import io.github.thanktoken.core.api.attribute.AttributeReadSignature;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * Super-interface of an object that has a {@link #getRecipient() recipient} and is {@link #getSignature() signed}
 * accordingly.
 *
 * @see ThankTokenHeader
 * @see ThankTransaction
 */
public abstract interface ThankSignedObject extends AttributeReadSignature, AttributeReadRecipient {

  @Override
  ThankAddress getRecipient();

  @Override
  SignatureBinary getSignature();

}
