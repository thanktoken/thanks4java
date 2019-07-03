/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.io;

import net.sf.mmm.crypto.asymmetric.sign.SignatureBinary;
import net.sf.mmm.crypto.crypt.EncryptedData;

import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.context.ThankTokenContext;
import io.github.thanktoken.core.api.context.ThankTokenContextHelper;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.location.ThankLocation;
import io.github.thanktoken.core.api.location.ThankLocationOlc;
import io.github.thanktoken.core.api.target.ThankTarget;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;
import io.github.thanktoken.core.api.value.ThankValue;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * Interface to {@link #parse(String, Class, ThankTokenContext) parse} a simple value.
 *
 * @see ThankValueParserDefault#get()
 */
public interface ThankValueParser {

  /**
   * @param <T> generic type of the value.
   * @param value the value as {@link String}.
   * @param type the {@link Class} reflecting the requested value type.
   * @param context the {@link ThankTokenContext} required for parsing context-sensitive fields.
   * @return the given {@code value} converted to the requested {@code type}.
   */
  @SuppressWarnings("unchecked")
  default <T> T parse(String value, Class<T> type, ThankTokenContext context) {

    Object result;
    if (type == String.class) {
      result = value;
    } else if (type == ThankVersion.class) {
      result = ThankVersion.of(value);
    } else if (type == ThankCurrency.class) {
      result = ThankCurrency.of(value);
    } else if (type == ThankTimestamp.class) {
      result = ThankTimestamp.of(value);
    } else if (type == ThankValue.class) {
      result = ThankValue.of(value);
    } else if (type == ThankAlgorithm.class) {
      result = ThankAlgorithm.of(value);
    } else {
      if (context == null) {
        throw new IllegalArgumentException("Context is required to parse value of type '" + type + "' but context is null.");
      }
      ThankCurrency currency = context.getCurrency();
      if (currency == null) {
        throw new IllegalStateException("Currency is required to parse value of type '" + type
            + "' but context has no currency. Please ensure that fields of token data are given in correct order.");
      }
      return currency.parse(this, value, type, context);
    }
    return (T) result;
  }

  /**
   * @param <T> generic type of the value.
   * @param value the value as {@link String}.
   * @param type the {@link Class} reflecting the requested value type.
   * @param context the {@link ThankTokenContext} required for parsing context-sensitive fields.
   * @return the given {@code value} converted to the requested {@code type}.
   */
  @SuppressWarnings("unchecked")
  default <T> T parseDefault(String value, Class<T> type, ThankTokenContext context) {

    Object result;
    if (type == ThankLocation.class) {
      result = ThankLocationOlc.of(value);
    } else if (type == ThankTarget.class) {
      result = ThankTarget.of(value);
    } else if (type == EncryptedData.class) {
      result = EncryptedData.ofBase64(value);
    } else if (type == SignatureBinary.class) {
      byte[] data = context.getVersion().getCodec().decode(value);
      result = ThankTokenContextHelper.requireAlgorithm(context).getSignatureFactory().createSignature(data);
    } else if (type == ThankAddress.class) {
      byte[] data = context.getVersion().getCodec().decode(value);
      result = ThankTokenContextHelper.requireAlgorithm(context).createAddress(data);
    } else {
      throw new IllegalStateException(type.getName());
    }
    return (T) result;
  }

}
