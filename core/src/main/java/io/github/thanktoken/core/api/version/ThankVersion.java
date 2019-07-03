package io.github.thanktoken.core.api.version;

import net.sf.mmm.binary.api.codec.Base64;
import net.sf.mmm.binary.api.codec.BinaryCodec;

import io.github.thanktoken.core.api.datatype.IntegerType;

/**
 * {@link ThankVersion} represents the version of the semantic structure and the binary serialization format of a
 * {@link io.github.thanktoken.core.api.token.ThankToken}.
 *
 * @see io.github.thanktoken.core.api.token.header.ThankTokenHeader#getVersion()
 */
public class ThankVersion extends IntegerType implements Comparable<ThankVersion> {

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() value}.
   */
  public ThankVersion(int value) {

    super(value);
    if (value <= 0) {
      throw new IllegalArgumentException(Integer.toString(value));
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
   * @return the {@link BinaryCodec} to encode and decode binary data.
   */
  public BinaryCodec getCodec() {

    return Base64.DEFAULT;
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
