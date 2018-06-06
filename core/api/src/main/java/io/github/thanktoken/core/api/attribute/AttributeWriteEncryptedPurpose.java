/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import net.sf.mmm.security.api.crypt.SecurityEncryptedData;

/**
 * Interface to {@link #getEncryptedPurpose() read} and {@link #setEncryptedPurpose(SecurityEncryptedData) write} the
 * {@link #getEncryptedPurpose() encrypted purpose}.
 *
 * @param <SELF> this type itself.
 */
public interface AttributeWriteEncryptedPurpose<SELF extends AttributeWriteEncryptedPurpose<SELF>> extends AttributeReadEncryptedPurpose {

  /**
   * @param encryptedPurpose the new value of {@link #getEncryptedPurpose()}.
   * @return this
   */
  SELF setEncryptedPurpose(SecurityEncryptedData encryptedPurpose);

}
