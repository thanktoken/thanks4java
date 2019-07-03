/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.value;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The datatype for a decimal value.
 *
 * @see io.github.thanktoken.core.api.token.header.ThankTokenHeader#getAmount()
 * @see io.github.thanktoken.core.api.token.ThankToken#getValue()
 */
public final class ThankValue extends Number implements Comparable<ThankValue> {

  private static final long serialVersionUID = 1L;

  private static final int SCALE = 8; // unscaledValue = 1 --> 0.00000001

  private static final long FRACTION = 100000000;

  private static final BigDecimal FRACTION_DECIMAL = BigDecimal.valueOf(FRACTION);

  private static final long[] FACTORS = new long[] { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000 };

  /** The value {@code 0}. */
  public static final ThankValue VALUE_0 = new ThankValue(0);

  /** The value {@code 1}. */
  public static final ThankValue VALUE_1 = new ThankValue(100000000);

  /** The value {@code 0.1}. */
  public static final ThankValue VALUE_0_1 = new ThankValue(10000000);

  /** The value {@code 0.2}. */
  public static final ThankValue VALUE_0_2 = new ThankValue(20000000);

  /** The value {@code 0.25}. */
  public static final ThankValue VALUE_0_25 = new ThankValue(25000000);

  /** The value {@code 0.5}. */
  public static final ThankValue VALUE_0_5 = new ThankValue(50000000);

  /** The value {@code 0.01}. */
  public static final ThankValue VALUE_0_01 = new ThankValue(1000000);

  /** The value {@code 0.02}. */
  public static final ThankValue VALUE_0_02 = new ThankValue(2000000);

  /** The value {@code 0.05}. */
  public static final ThankValue VALUE_0_05 = new ThankValue(5000000);

  /**
   * The value {@code 0.00000001} what is the minimum positive value. Every {@link ThankValue} is technically a factor
   * of its {@link #getUnscaledValue() unscaled value} multiplied with this value.
   */
  public static final ThankValue VALUE_0_00000001 = new ThankValue(1);

  /** The value {@code 2}. */
  public static final ThankValue VALUE_2 = new ThankValue(200000000);

  /** The value {@code 4}. */
  public static final ThankValue VALUE_4 = new ThankValue(400000000);

  /** The value {@code 5}. */
  public static final ThankValue VALUE_5 = new ThankValue(500000000);

  /** The value {@code 8}. */
  public static final ThankValue VALUE_8 = new ThankValue(800000000L);

  /** The value {@code 9}. */
  public static final ThankValue VALUE_9 = new ThankValue(900000000L);

  /** The value {@code 10}. */
  public static final ThankValue VALUE_10 = new ThankValue(1000000000);

  /** The value {@code 16}. */
  public static final ThankValue VALUE_16 = new ThankValue(1600000000L);

  /** The value {@code 18}. */
  public static final ThankValue VALUE_18 = new ThankValue(1800000000L);

  /** The value {@code 20}. */
  public static final ThankValue VALUE_20 = new ThankValue(2000000000);

  /** The value {@code 25}. */
  public static final ThankValue VALUE_25 = new ThankValue(2500000000L);

  /** The value {@code 32}. */
  public static final ThankValue VALUE_32 = new ThankValue(3200000000L);

  /** The value {@code 50}. */
  public static final ThankValue VALUE_50 = new ThankValue(5000000000L);

  /** The value {@code 64}. */
  public static final ThankValue VALUE_64 = new ThankValue(6400000000L);

  /** The value {@code 100}. */
  public static final ThankValue VALUE_100 = new ThankValue(10000000000L);

  /** The value {@code 500}. */
  public static final ThankValue VALUE_500 = new ThankValue(50000000000L);

  /** The value {@code 1000}. */
  public static final ThankValue VALUE_1000 = new ThankValue(100000000000L);

  /** The value {@code 33.33333333} what is (very almost) a third of {@code 1000}. */
  public static final ThankValue VALUE_33_33333333 = new ThankValue(3333333333L);

  /** The value {@code 11.11111111} what is exactly a third of {@link #VALUE_33_33333333 33.33333333}. */
  public static final ThankValue VALUE_11_11111111 = new ThankValue(1111111111L);

  /**
   * The maximum value {@code 92233720368.54775807}. While this is only the technical maximum value that can be
   * represented as instance of {@link ThankValue} most {@link io.github.thanktoken.core.api.currency.ThankCurrency
   * currencies} will technically restrict the
   * {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader#getAmount() amount} to a lower value.
   */
  public static final ThankValue MAX_VALUE = new ThankValue(Long.MAX_VALUE);

  /** The value {@code 0} (zero). Same as {@link #VALUE_0}. */
  public static final ThankValue ZERO = VALUE_0;

  /** The value {@code 1} (one). Same as {@link #VALUE_1}. */
  public static final ThankValue ONE = VALUE_1;

  private final long unscaledValue;

  private transient BigDecimal decimal;

  private transient String string;

  /**
   * The constructor.
   *
   * @param unscaledValue the {@link #getUnscaledValue() unscaled value}.
   */
  private ThankValue(long unscaledValue) {

    super();
    if (unscaledValue < 0) {
      throw new IllegalArgumentException("Value (" + unscaledValue + ") may not be negative.");
    }
    this.unscaledValue = unscaledValue;
  }

  /**
   * @return the (fixed) scale.
   * @see BigDecimal#scale()
   */
  public int getScale() {

    return SCALE;
  }

  /**
   * @return the unscaled value in <em>trace</em>. This is technically the value in fraction units of 10 millions so if
   *         {@code 1} is returned, the actual value would be {@code 0.00000001}.
   * @see BigDecimal#unscaledValue()
   */
  public long getUnscaledValue() {

    return this.unscaledValue;
  }

  @Override
  public int intValue() {

    return (int) (this.unscaledValue / FRACTION);
  }

  @Override
  public long longValue() {

    return this.unscaledValue / FRACTION;
  }

  @Override
  public float floatValue() {

    return ((float) this.unscaledValue) / FRACTION;
  }

  @Override
  public double doubleValue() {

    return ((double) this.unscaledValue) / FRACTION;
  }

  /**
   * @return this amount as {@link BigDecimal}.
   */
  public BigDecimal bigDecimalValue() {

    if (this.decimal == null) {
      this.decimal = new BigDecimal(this.unscaledValue).divide(FRACTION_DECIMAL);
    }
    return this.decimal;
  }

  /**
   * @param add the {@link ThankValue} to add to this value.
   * @return the (new) {@link ThankValue} with the sum of {@code this} and the given {@link ThankValue}.
   */
  public ThankValue add(ThankValue add) {

    if (add == ZERO) {
      return this;
    } else if (this == ZERO) {
      return add;
    }
    long unscaled = this.unscaledValue + add.unscaledValue;
    if (unscaled < 0) {
      throw new IllegalArgumentException("Overflow whilst adding " + toString() + " and " + add);
    }
    return ofUnscaled(unscaled);
  }

  @Override
  public int hashCode() {

    return Long.hashCode(this.unscaledValue);
  }

  @Override
  public boolean equals(Object obj) {

    if (obj == this) {
      return true;
    }
    if (!(obj instanceof ThankValue)) {
      return false;
    }
    ThankValue other = (ThankValue) obj;
    return (this.unscaledValue == other.unscaledValue);
  }

  @Override
  public int compareTo(ThankValue other) {

    if (other == null) {
      return -1;
    }
    long delta = this.unscaledValue - other.unscaledValue;
    if (delta < 0) {
      return -1;
    } else if (delta > 0) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  public String toString() {

    if (this.string == null) {
      if (this.unscaledValue == 0) {
        return "0";
      }
      char[] chars = new char[20];
      int charIndex = chars.length;
      long val = this.unscaledValue;
      int scale = SCALE;
      while ((val > 0) || (scale >= 0)) {
        long rest = val % 10;
        if ((rest > 0) || (charIndex < chars.length) || (scale <= 0)) {
          chars[--charIndex] = (char) ('0' + rest);
        }
        val = val / 10;
        scale--;
        if ((scale == 0) && (charIndex < chars.length)) {
          chars[--charIndex] = '.';
        }
      }
      this.string = new String(chars, charIndex, chars.length - charIndex);
    }
    return this.string;
  }

  /**
   * <b>Attention:</b> This method will only accept exact {@link #toString() string representations} of a
   * {@link ThankValue} as the inverse function of {@link #toString()}. Other representations with leading or trailing
   * zeros (such as "01" or "0.10") as well as too large scale, etc. will all produce an
   * {@link IllegalArgumentException}.
   *
   * @param string the {@link #toString() string representation} of the value. May be {@code null}.
   * @return the parsed {@link ThankValue} or {@code null} if the given {@link String} was {@code null}.
   * @throws IllegalArgumentException if the given {@link String} is not a valid {@link #toString() string
   *         representation} of a {@link ThankValue}.
   */
  public static ThankValue of(String string) {

    if (string == null) {
      return null;
    }
    if ("0".equals(string)) {
      return ZERO;
    }
    int length = string.length();
    int scale;
    int fractionIndex = string.indexOf('.');
    if (string.startsWith("0") && (fractionIndex != 1)) { // no leading zeros
      throw new IllegalArgumentException(string);
    }
    if (string.endsWith("0") && (fractionIndex > 0)) { // no trailing zeros
      throw new IllegalArgumentException(string);
    }
    int mag = fractionIndex;
    if (fractionIndex < 0) {
      scale = SCALE;
      mag = length;
    } else if (fractionIndex > 0) {
      int fractionDigitCount = length - 1 - fractionIndex;
      if ((fractionDigitCount <= 0) || (fractionDigitCount > SCALE)) {
        throw new IllegalArgumentException(string);
      }
      scale = SCALE - fractionDigitCount;
    } else {
      throw new IllegalArgumentException(string);
    }
    if (mag > 11) { // 92233720368
      overflow(string);
    }
    long factor = FACTORS[scale];
    long unscaled = 0;

    for (int i = 0; i < length; i++) {
      if (i != fractionIndex) {
        char c = string.charAt(i);
        int digit = c - '0';
        if ((digit < 0) || (digit > 9)) {
          throw new IllegalArgumentException(string);
        }
        unscaled = (unscaled * 10) + digit;
      }
    }
    if (unscaled < 0) {
      overflow(string);
    }
    unscaled = unscaled * factor;
    if (unscaled < 0) {
      overflow(string);
    }
    return ofUnscaled(unscaled);
  }

  private static void overflow(String string) {

    throw new /* NumberFormatException */ IllegalArgumentException(string,
        new IllegalArgumentException("Maximum value 92233720368.54775807 exceeded."));
  }

  private static void underflow(String string) {

    throw new /* NumberFormatException */ IllegalArgumentException(string, new IllegalArgumentException("Value can not be less than 0."));
  }

  /**
   * @param value the {@link #bigDecimalValue() decimal representation} of the value. May be {@code null}.
   * @return the corresponding {@link ThankValue} or {@code null} if the given {@link BigDecimal} was {@code null}.
   */
  public static ThankValue of(BigDecimal value) {

    if (value == null) {
      return null;
    }
    int signum = value.signum();
    if (signum < 0) {
      underflow(value.toPlainString());
    } else if (signum == 0) {
      return ZERO;
    }
    BigDecimal normalized = value.setScale(SCALE, BigDecimal.ROUND_DOWN);
    BigInteger unscaledValue = normalized.unscaledValue();
    if (unscaledValue.bitLength() > 63) {
      overflow(value.toPlainString());
    }
    return ofUnscaled(unscaledValue.longValue());
  }

  /**
   * @param unscaled the {@link #getUnscaledValue() unscaled value}.
   * @return the {@link ThankValue} having the given {@code unscaled} long for {@link #getUnscaledValue()}.
   */
  public static ThankValue ofUnscaled(long unscaled) {

    if (unscaled == 0) {
      return VALUE_0;
    } else if (unscaled <= VALUE_1000.unscaledValue) {
      if (unscaled <= VALUE_1.unscaledValue) {
        if (unscaled == VALUE_1.unscaledValue) {
          return VALUE_1;
        } else if (unscaled == VALUE_0_1.unscaledValue) {
          return VALUE_0_1;
        } else if (unscaled == VALUE_0_2.unscaledValue) {
          return VALUE_0_2;
        } else if (unscaled == VALUE_0_25.unscaledValue) {
          return VALUE_0_25;
        } else if (unscaled == VALUE_0_5.unscaledValue) {
          return VALUE_0_5;
        } else if (unscaled == VALUE_0_01.unscaledValue) {
          return VALUE_0_01;
        } else if (unscaled == VALUE_0_02.unscaledValue) {
          return VALUE_0_02;
        } else if (unscaled == VALUE_0_05.unscaledValue) {
          return VALUE_0_05;
        } else if (unscaled == VALUE_0_00000001.unscaledValue) {
          return VALUE_0_00000001;
        }
      } else {
        if (unscaled == VALUE_2.unscaledValue) {
          return VALUE_2;
        } else if (unscaled == VALUE_5.unscaledValue) {
          return VALUE_5;
        } else if (unscaled == VALUE_10.unscaledValue) {
          return VALUE_10;
        } else if (unscaled == VALUE_20.unscaledValue) {
          return VALUE_20;
        } else if (unscaled == VALUE_25.unscaledValue) {
          return VALUE_25;
        } else if (unscaled == VALUE_50.unscaledValue) {
          return VALUE_50;
        } else if (unscaled == VALUE_100.unscaledValue) {
          return VALUE_100;
        } else if (unscaled == VALUE_500.unscaledValue) {
          return VALUE_500;
        } else if (unscaled == VALUE_1000.unscaledValue) {
          return VALUE_1000;
        } else if (unscaled == VALUE_33_33333333.unscaledValue) {
          return VALUE_33_33333333;
        } else if (unscaled == VALUE_11_11111111.unscaledValue) {
          return VALUE_11_11111111;
        }
      }
    }
    return new ThankValue(unscaled);
  }

}
