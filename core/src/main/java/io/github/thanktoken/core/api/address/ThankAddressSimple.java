package io.github.thanktoken.core.api.address;

import java.security.PublicKey;

import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * {@link ThankAddress} initial version. Later {@link ThankVersion}s or other {@link ThankAlgorithm}s may introduce
 * other implementations.
 *
 * @since 1.0.0
 */
public class ThankAddressSimple extends ThankAddress {

  private ThankAddressType type;

  private PublicKey publicKey;

  /**
   * The constructor.
   *
   * @param data the {@link #getData() binary data}.
   * @param publicKey the {@link PublicKey}.
   */
  public ThankAddressSimple(byte[] data, PublicKey publicKey) {

    super(data);
    this.publicKey = publicKey;
  }

  /**
   * The constructor.
   *
   * @param data the {@link #getData() binary data}.
   */
  public ThankAddressSimple(byte[] data) {

    super(data);
  }

  @Override
  public ThankAddressType getType() {

    if (this.type == null) {
      this.type = ThankAddressType.of(getDataByte(0));
    }
    return this.type;
  }

  /**
   * @return the {@link PublicKey} represented by this address. May be {@code null} if not yet deserialized.
   */
  protected PublicKey getPublicKey() {

    return this.publicKey;
  }

  /**
   * @param publicKey new value of {@link #getPublicKey()}.
   */
  protected void setPublicKey(PublicKey publicKey) {

    if ((this.publicKey != null) && (this.publicKey != publicKey)) {
      throw new IllegalStateException("PublicKey has already been set and can not be changed anymore.");
    }
    this.publicKey = publicKey;
  }

  public static class ThankAddressAccess {

    public PublicKey getPublicKey(ThankAddressSimple address) {

      return address.getPublicKey();
    }

    public void setPublicKey(ThankAddressSimple address, PublicKey key) {

      address.setPublicKey(key);
    }

  }

}
