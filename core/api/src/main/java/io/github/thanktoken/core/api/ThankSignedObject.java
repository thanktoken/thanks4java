/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api;

import io.github.thanktoken.core.api.attribute.AttributeReadRecipient;
import io.github.thanktoken.core.api.attribute.AttributeReadSignature;
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
public abstract interface ThankSignedObject extends AttributeReadSignature, AttributeReadRecipient {

  @Override
  SecurityPublicKey getRecipient();

  @Override
  SecuritySignature getSignature();

}
