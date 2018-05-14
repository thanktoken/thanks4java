/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.datatype;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Helper class to deal with {@link BigDecimal} amounts.
 *
 * @see io.github.thanktoken.core.api.header.ThankHeader#getAmount()
 * @see io.github.thanktoken.core.api.ThankToken#getValue()
 */
public class AmountHelper {

  private static final DecimalFormat FORMAT = new DecimalFormat("#.####", new DecimalFormatSymbols(Locale.US));

  /**
   * The maximum {@link BigDecimal#scale() scale} of an
   * {@link io.github.thanktoken.core.api.header.ThankHeader#getAmount() amount}.
   */
  public static final int MAX_SCALE = 8; // 0.00000001

  /**
   * @param amount the {@link BigDecimal} to truncate if it has {@link BigDecimal#stripTrailingZeros() trailing zeros}
   *        or {@link #MAX_SCALE too large} {@link BigDecimal#scale() scale}.
   * @return the truncated {@link BigDecimal} or the given {@code amount} if already truncated.
   */
  public static BigDecimal truncate(BigDecimal amount) {

    if (amount == null) {
      return null;
    }
    if (amount.scale() <= MAX_SCALE) {
      BigInteger unscaledValue = amount.unscaledValue();
      if (!unscaledValue.testBit(0) && unscaledValue.mod(BigInteger.TEN).signum() == 0) {
        BigDecimal stripped = amount.stripTrailingZeros();
        if (stripped.scale() < 0) {
          stripped = stripped.setScale(0);
        }
        return stripped;
      }
      return amount;
    }
    return amount.setScale(MAX_SCALE, BigDecimal.ROUND_DOWN).stripTrailingZeros();
  }

  /**
   * @param value the {@link BigDecimal} value.
   * @return the {@code value} formatted as {@link String}.
   */
  public static String format(BigDecimal value) {

    if (value == null) {
      return null;
    }
    return value.toPlainString();
    // return FORMAT.format(value);
  }

  /**
   * @param value the {@link BigDecimal} as{@link String}.
   * @return the given {@code value} parsed as {@link BigDecimal}.
   */
  public static BigDecimal parse(String value) {

    if (value == null) {
      return null;
    }
    return new BigDecimal(value);
  }

}
