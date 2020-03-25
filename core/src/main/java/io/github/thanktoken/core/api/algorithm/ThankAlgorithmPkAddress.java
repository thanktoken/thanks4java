package io.github.thanktoken.core.api.algorithm;

import java.security.PublicKey;

import net.sf.mmm.crypto.CryptoBinaryFormat;
import net.sf.mmm.crypto.asymmetric.access.AsymmetricAccess;
import net.sf.mmm.crypto.asymmetric.sign.SignatureBinary;
import net.sf.mmm.crypto.hash.Hash;

import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.address.ThankAddressHeader;
import io.github.thanktoken.core.api.address.ThankAddressSimple;
import io.github.thanktoken.core.api.address.ThankAddressSimple.ThankAddressAccess;

/**
 * Abstract base implementation of {@link ThankAlgorithm} where the {@link CryptoBinaryFormat#FORMAT_COMPACT compact}
 * {@link PublicKey} is used as address via {@link ThankAddressSimple}.
 *
 * @since 1.0.0
 */
public abstract class ThankAlgorithmPkAddress extends ThankAlgorithm {

  private static final ThankAddressAccess ADDRESS_ACCESS = new ThankAddressAccess();

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() algorithm name}.
   * @param asymetricAccess the {@link AsymmetricAccess} to retrieve all factories from.
   */
  public ThankAlgorithmPkAddress(String value, AsymmetricAccess<?, ?, ?, ?, ?> asymetricAccess) {

    super(value, asymetricAccess);
  }

  @Override
  public ThankAddress createAddress(byte[] data) {

    return new ThankAddressSimple(data);
  }

  @Override
  public ThankAddress createAddress(PublicKey key, ThankAddressHeader header) {

    byte[] data = getKeyCreator().asData(key, CryptoBinaryFormat.FORMAT_COMPACT);
    byte[] address = header.appendBinary(data);
    return new ThankAddressSimple(address, key);
  }

  @Override
  protected PublicKey createPublicKey(ThankAddress address, SignatureBinary signature, Hash messageHash) {

    ThankAddressSimple pkAddress = (ThankAddressSimple) address;
    PublicKey publicKey = ADDRESS_ACCESS.getPublicKey(pkAddress);
    if (publicKey == null) {
      publicKey = getKeyCreator().createPublicKey(address.getData(), CryptoBinaryFormat.FORMAT_COMPACT);
      ADDRESS_ACCESS.setPublicKey(pkAddress, publicKey);
    }
    return publicKey;
  }

}
