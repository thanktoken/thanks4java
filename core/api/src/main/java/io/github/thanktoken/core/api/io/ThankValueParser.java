/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.io;

import java.time.Instant;

import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.datatype.ThankValue;
import io.github.thanktoken.core.api.datatype.TimestampHelper;
import io.github.thanktoken.core.api.header.ThankLocation;
import io.github.thanktoken.core.api.header.ThankTarget;
import io.github.thanktoken.core.api.header.ThankVersion;
import io.github.thanktoken.core.api.reference.ThankTokenReference;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;
import net.sf.mmm.security.api.crypt.SecurityEncryptedData;
import net.sf.mmm.security.api.sign.SecuritySignature;

/**
 * Interface to {@link #parse(String, Class) parse} a simple value.
 */
public interface ThankValueParser {

  /**
   * @param <T> generic type of the value.
   * @param value the value as {@link String}.
   * @param type the {@link Class} reflecting the requested value type.
   * @return the given {@code value} converted to the requested {@code type}.
   */
  @SuppressWarnings("unchecked")
  default <T> T parse(String value, Class<T> type) {

    Object result;
    if (type == String.class) {
      result = value;
    } else if (type == ThankVersion.class) {
      result = ThankVersion.of(value);
    } else if (type == ThankCurrency.class) {
      result = ThankCurrency.of(value);
    } else if (type == ThankValue.class) {
      result = ThankValue.of(value);
    } else if (type == ThankLocation.class) {
      result = ThankLocation.of(value);
    } else if (type == ThankTarget.class) {
      result = ThankTarget.of(value);
    } else if (type == Instant.class) {
      result = TimestampHelper.parse(value);
    } else if (type == SecurityEncryptedData.class) {
      result = SecurityEncryptedData.ofBase64(value);
    } else if (type == SecuritySignature.class) {
      result = SecuritySignature.ofBase64(value);
    } else if ((type == ThankTokenReferenceType.class) || (type == ThankTokenReference.class)) {
      result = ThankTokenReferenceType.of(value, this);
    } else {
      throw new IllegalStateException(type.getName());
    }
    return (T) result;
  }

}
