/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.util.Objects;

import io.github.mmm.crypto.crypt.EncryptedData;
import io.github.thanktoken.core.api.data.ThankDataObject;

/**
 * Interface to {@link #getEncryptedPurpose() read} the {@link #getEncryptedPurpose() encrypted purpose}.
 */
public interface AttributeReadEncryptedPurpose extends ThankDataObject {

  /** Name of property {@link #getEncryptedPurpose()}. */
  String PROPERTY_ENCRYPTED_PURPOSE = "encryptedPurpose";

  /**
   * @return the encrypted purpose (only readable by the {@link AttributeReadRecipient#getRecipient() recipient}) of
   *         object or {@code null} if not available.
   *
   * @see io.github.thanktoken.core.api.transaction.ThankTransaction#getEncryptedPurpose()
   */
  EncryptedData getEncryptedPurpose();

  /**
   * @return the required {@link #getEncryptedPurpose() encrypted purpose}.
   */
  default EncryptedData requireEncryptedPurpose() {

    EncryptedData encryptedPurpose = getEncryptedPurpose();
    Objects.requireNonNull(encryptedPurpose, PROPERTY_ENCRYPTED_PURPOSE);
    return encryptedPurpose;
  }
}
