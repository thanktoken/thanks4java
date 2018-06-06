/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import io.github.thanktoken.core.api.ThankDataObject;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;

/**
 * Interface to {@link #getRecipient() read} the {@link #getRecipient() recipient} as {@link SecurityPublicKey}.
 */
public interface AttributeReadRecipient extends ThankDataObject {

  /**
   * @return the {@link SecurityPublicKey} representing the
   *         {@link io.github.thanktoken.core.api.header.ThankHeader#getRecipient() recipient} of this object. The
   *         recipient is the current owner at the time when this object (or in case of a
   *         {@link io.github.thanktoken.core.api.reference.ThankTokenReferenceType reference} the creator of the
   *         referenced {@link io.github.thanktoken.core.api.ThankToken}) was created and verified.
   * @see io.github.thanktoken.core.api.reference.ThankTokenReference#getRecipient()
   * @see io.github.thanktoken.core.api.header.ThankHeader#getRecipient()
   * @see io.github.thanktoken.core.api.transaction.ThankTransaction#getRecipient()
   * @see io.github.thanktoken.core.api.message.ThankMessage#getRecipient()
   */
  SecurityPublicKey getRecipient();
}
