package io.github.thanktoken.core.api.algorithm;

import java.security.PublicKey;

import net.sf.mmm.crypto.CryptoBinaryFormat;
import net.sf.mmm.crypto.asymmetric.access.AsymmetricAccess;
import net.sf.mmm.crypto.asymmetric.sign.SignatureBinary;
import net.sf.mmm.crypto.asymmetric.sign.SignatureWithPublicKeyRecovery;
import net.sf.mmm.crypto.hash.Hash;
import net.sf.mmm.crypto.hash.HashCreator;

import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.address.ThankAddressSimple;
import io.github.thanktoken.core.api.address.ThankAddressType;

/**
 * Abstract base implementation of {@link ThankAlgorithm} where {@link PublicKey} can be restored from
 * {@link SignatureBinary} and {@link ThankAddress} can be a irreversable and small transformation of the
 * {@link PublicKey} such as a hash.
 *
 * @since 1.0.0
 */
public abstract class ThankAlgorithmPkSignature extends ThankAlgorithm {

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() algorithm name}.
   * @param asymetricAccess the {@link AsymmetricAccess} to retrieve all factories from.
   */
  public ThankAlgorithmPkSignature(String value, AsymmetricAccess<?, ?, ?, ?, ?> asymetricAccess) {

    super(value, asymetricAccess);
  }

  @Override
  public ThankAddress createAddress(byte[] data) {

    return new ThankAddressSimple(data);
  }

  @Override
  public ThankAddress createAddress(PublicKey key, ThankAddressType type) {

    byte[] raw = getKeyCreator().asData(key, CryptoBinaryFormat.FORMAT_COMPACT);
    HashCreator hashCreator = getHashFactory().newHashCreator();
    byte[] hash = hashCreator.hash(raw, true);
    byte[] data = type.appendBinary(hash);
    return new ThankAddressSimple(data);
  }

  @Override
  protected PublicKey createPublicKey(ThankAddress address, SignatureBinary signature, Hash messageHash) {

    PublicKey publicKey = ((SignatureWithPublicKeyRecovery) signature).recoverPublicKey(messageHash.getData());
    ThankAddress pkAddress = createAddress(publicKey, address.getType());
    if (!pkAddress.equals(address)) {
      throw new IllegalStateException("Address does not match to signature!");
    }
    return publicKey;
  }

}
