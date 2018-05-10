package io.github.thanktoken.core.api.datatype;

/**
 * Abstract base class for {@link String}-based datatypes.
 */
public abstract class IntegerType {

  private final int value;

  /**
   * The constructor.
   *
   * @param value - see {@link #getValue()}.
   */
  public IntegerType(int value) {

    super();
    this.value = value;
  }

  @Override
  public boolean equals(Object obj) {

    if ((obj == null) || (obj.getClass() != getClass())) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    IntegerType other = (IntegerType) obj;
    if (this.value != other.value) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {

    return ~this.value;
  }

  /**
   * @return the {@code int} value of this datatype.
   */
  public int getValue() {

    return this.value;
  }

  @Override
  public String toString() {

    return Integer.toString(this.value);
  }

}
