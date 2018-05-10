package io.github.thanktoken.core.api.datatype;

import java.util.Objects;

/**
 * Abstract base class for {@link String}-based datatypes.
 */
public abstract class StringType {

  private final String value;

  /**
   * The constructor.
   *
   * @param value - see {@link #getValue()}.
   */
  public StringType(String value) {

    super();
    Objects.requireNonNull(value, "value");
    String trimmed = value.trim();
    this.value = trimmed;
  }

  @Override
  public boolean equals(Object obj) {

    if ((obj == null) || (obj.getClass() != getClass())) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    String otherValue = ((StringType) obj).value;
    return Objects.equals(this.value, otherValue);
  }

  @Override
  public int hashCode() {

    return ~this.value.hashCode();
  }

  /**
   * @return the {@link String} value representing this datatype.
   */
  public String getValue() {

    return this.value;
  }

  /**
   * Null-safe access to {@link #getValue()}.
   *
   * @param type is {@link StringType} and may be <code>null</code>.
   * @return {@link #getValue() value}. Will be <code>null</code> if <code>type</code> was <code>null</code>.
   */
  public static String getValue(StringType type) {

    if (type == null) {
      return null;
    }
    return type.value;
  }

  @Override
  public String toString() {

    return this.value;
  }

  /**
   * @return the (maximum) allowed length of this type.
   */
  public abstract int getMaxLength();

}
