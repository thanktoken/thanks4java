package io.github.thanktoken.core.api.address;

import io.github.mmm.binary.Binary;

/**
 * The header of a {@link ThankAddress}. Contains the {@link #getType() type} of the identitiy and an optional
 * {@link #getDetail() detail}.
 *
 * @see ThankAddress#getHeader()
 */
public class ThankAddressHeader {

  private final ThankAddressType type;

  private final byte detail;

  private ThankAddressHeader(ThankAddressType type, int detail) {

    super();
    byte detailByte = (byte) detail;
    if (type.isNaturalPerson()) {
      if (detail > 100) {
        detailByte = -1;
      }
    } else {
      assert (detail == 0);
    }
    if (detailByte != detail) {
      throw new IllegalArgumentException(type + ":" + detail);
    }
    this.type = type;
    this.detail = detailByte;
  }

  /**
   * @return the {@link ThankAddressType} representing the type of the identity behind the address.
   */
  public ThankAddressType getType() {

    return this.type;
  }

  /**
   * @return the optional detail.
   */
  public int getDetail() {

    return this.detail;
  }

  /**
   * This is an internal method that may change in future versions.
   *
   * @param blob the {@link Binary} to append.
   * @return the {@code byte[]} with this type followed by the given BLOB.
   */
  public byte[] appendBinary(Binary blob) {

    byte[] data = new byte[blob.getLength() + 2];
    data[0] = this.type.getValue();
    data[1] = this.detail;
    blob.getData(data, 2);
    return data;
  }

  /**
   * This is an internal method that may change in future versions.
   *
   * @param blob the {@code byte[]} to append.
   * @return the {@code byte[]} with this type followed by the given BLOB.
   */
  public byte[] appendBinary(byte[] blob) {

    byte[] data = new byte[blob.length + 2];
    data[0] = this.type.getValue();
    data[1] = this.detail;
    System.arraycopy(blob, 0, data, 2, blob.length);
    return data;
  }

  /**
   * @param type the {@link ThankAddressType#getValue()}.
   * @param detail the {@link #getDetail()}.
   * @return the {@link ThankAddressHeader}.
   */
  static ThankAddressHeader of(byte type, int detail) {

    return new ThankAddressHeader(ThankAddressType.of(type), detail);
  }

  /**
   * @param type the {@link ThankAddressType}.
   * @param detail the {@link #getDetail()}.
   * @return the {@link ThankAddressHeader}.
   */
  static ThankAddressHeader of(ThankAddressType type, int detail) {

    return new ThankAddressHeader(type, detail);
  }

  /**
   * @param belonging the belonging (e.g. 10 for 10% income, and 100 for 100%).
   * @return the {@link ThankAddressHeader}.
   */
  public static ThankAddressHeader ofNaturalPerson(int belonging) {

    return new ThankAddressHeader(ThankAddressType.NATURAL_PERSON, belonging);
  }

}
