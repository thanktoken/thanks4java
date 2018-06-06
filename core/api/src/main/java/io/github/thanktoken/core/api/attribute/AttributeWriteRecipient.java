/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;

/**
 * Interface to {@link #getRecipient() read} and {@link #setRecipient(SecurityPublicKey) write} the
 * {@link #getRecipient() recipient} as {@link SecurityPublicKey}.
 *
 * @param <SELF> this type itself.
 */
public interface AttributeWriteRecipient<SELF extends AttributeWriteRecipient<SELF>> extends AttributeReadRecipient {

  /**
   * @param recipient the new value of {@link #getRecipient()}.
   * @return this
   */
  SELF setRecipient(SecurityPublicKey recipient);

}
