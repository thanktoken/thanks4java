/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import io.github.thanktoken.core.api.ThankDataObject;
import net.sf.mmm.security.api.crypt.SecurityEncryptedData;

/**
 * Interface to {@link #getEncryptedPurpose() read} the {@link #getEncryptedPurpose() encrypted purpose}.
 */
public interface AttributeReadEncryptedPurpose extends ThankDataObject {

  /**
   * @return the encrypted purpose (only readable by the {@link AttributeReadRecipient#getRecipient() recipient}) of
   *         object or {@code null} if not available.
   *
   * @see io.github.thanktoken.core.api.transaction.ThankTransaction#getEncryptedPurpose()
   */
  SecurityEncryptedData getEncryptedPurpose();
}
