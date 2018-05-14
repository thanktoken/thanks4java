/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.datatype;

import java.time.Instant;

import io.github.thanktoken.core.api.ThankConstants;

/**
 * Helper class to deal with {@link Instant} values.
 */
public class TimestampHelper {

  /**
   * @param value the {@link Instant} value.
   * @return the {@code value} formatted as {@link String}.
   */
  public static String format(Instant value) {

    if (value == null) {
      return null;
    }
    return ThankConstants.TIMESTAMP_FORMATTER.format(value);
  }

  /**
   * @param value the {@link Instant} {@link #format(Instant) formatted} as {@link String}.
   * @return the given {@code value} parsed as {@link Instant}.
   */
  public static Instant parse(String value) {

    if (value == null) {
      return null;
    }
    return Instant.from(ThankConstants.TIMESTAMP_FORMATTER.parse(value));
  }

}
