package io.github.thanktoken.core.api.header;

import io.github.thanktoken.core.api.datatype.IntegerType;
import net.sf.mmm.util.exception.api.ValueOutOfRangeException;

/**
 * {@link ThankVersion} represents the version of the binary serialization format as well as the cryptographic design of
 * a {@link io.github.thanktoken.core.api.ThankToken}.
 *
 * @see io.github.thanktoken.core.api.header.ThankHeader#getVersion()
 */
public class ThankVersion extends IntegerType implements Comparable<ThankVersion> {

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() value}.
   */
  @SuppressWarnings("boxing")
  public ThankVersion(int value) {

    super(value);
    if (value <= 0) {
      throw new ValueOutOfRangeException(value, 1, Integer.MAX_VALUE);
    }
  }

  @Override
  public int compareTo(ThankVersion other) {

    if (other == null) {
      return -1;
    }
    return getValue() - other.getValue();
  }

  /**
   * @param other the {@link ThankVersion} to compare.
   * @return {@code true} if this {@link ThankVersion} is newer than the given {@link ThankVersion}, {@code false}
   *         otherwise.
   */
  public boolean isNewer(ThankVersion other) {

    return compareTo(other) > 0;
  }

  /**
   * @param other the {@link ThankVersion} to compare.
   * @return {@code true} if this {@link ThankVersion} is older than the given {@link ThankVersion}, {@code false}
   *         otherwise.
   */
  public boolean isOlder(ThankVersion other) {

    return compareTo(other) < 0;
  }

  /**
   * @param value - see {@link #getValue()}.
   * @return the {@link ThankVersion}.
   */
  public static ThankVersion of(int value) {

    return new ThankVersion(value);
  }

  /**
   * @param value - see {@link #getValue()}.
   * @return the {@link ThankVersion} or {@code null} if the given {@code value} is {@code null}.
   */
  public static ThankVersion of(String value) {

    if (value == null) {
      return null;
    }
    try {
      return new ThankVersion(Integer.parseInt(value));
    } catch (NumberFormatException e) {
      throw new IllegalStateException("Invalid version '" + value + "'.", e);
    }
  }

}
