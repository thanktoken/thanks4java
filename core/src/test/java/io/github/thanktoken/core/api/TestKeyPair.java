package io.github.thanktoken.core.api;

import java.security.PrivateKey;
import java.security.PublicKey;

import net.sf.mmm.binary.api.Binary;
import net.sf.mmm.binary.api.BinaryType;
import net.sf.mmm.crypto.asymmetric.key.AsymmetricKeyPair;

import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.address.ThankAddressType;
import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.identity.ThankIdentity;
import io.github.thanktoken.core.api.identity.ThankIdentityBean;

/**
 * {@link AsymmetricKeyPair} for testing.
 */
public class TestKeyPair {

  private final ThankAlgorithm algorithm;

  private final PrivateKey privateKey;

  private final PublicKey publicKey;

  private final ThankAddress address;

  private final ThankIdentity identity;

  /**
   * The constructor.
   *
   * @param addressType the {@link ThankAddressType} for the {@link #getAddress() address}.
   * @param algorithm the {@link ThankAlgorithm} to use.
   */
  public TestKeyPair(ThankAddressType addressType, ThankAlgorithm algorithm) {

    this(algorithm.getKeyCreator().generateKeyPair(), addressType, algorithm);
  }

  /**
   * The constructor.
   *
   * @param keyBase64 the key pair / private key as base64 encoded {@link String}.
   * @param addressType the {@link ThankAddressType} for the {@link #getAddress() address}.
   * @param algorithm the {@link ThankAlgorithm} to use.
   */
  public TestKeyPair(String keyBase64, ThankAddressType addressType, ThankAlgorithm algorithm) {

    this(algorithm.getKeyCreator().createKeyPair(BinaryType.parseBase64(keyBase64)), addressType, algorithm);
  }

  private TestKeyPair(AsymmetricKeyPair<?, ?> keyPair, ThankAddressType addressType, ThankAlgorithm algorithm) {

    this(keyPair.getPrivateKey(), keyPair.getPublicKey(), addressType, algorithm);
  }

  private TestKeyPair(PrivateKey privateKey, PublicKey publicKey, ThankAddressType addressType, ThankAlgorithm algorithm) {

    super();
    this.algorithm = algorithm;
    this.privateKey = privateKey;
    this.publicKey = publicKey;
    this.address = algorithm.createAddress(publicKey, addressType);
    ThankIdentityBean bean = new ThankIdentityBean();
    bean.setLatestAddress(this.address);
    bean.setLocation(TestData.TEST_LOCATION);
    // bean.setSignatures(signatures)
    this.identity = bean;
  }

  /**
   * @return the {@link PrivateKey}.
   */
  public PrivateKey getPrivateKey() {

    return this.privateKey;
  }

  /**
   * @return {@link #getPrivateKey() private key} as {@link Binary}.
   */
  public Binary getPrivateKeyBinary() {

    return this.algorithm.getKeyCreator().asBinary(this.privateKey);
  }

  /**
   * @return the {@link PublicKey}.
   */
  public PublicKey getPublicKey() {

    return this.publicKey;
  }

  /**
   * @return {@link #getPublicKey() public key} as {@link Binary}.
   */
  public Binary getPublicKeyBinary() {

    return this.algorithm.getKeyCreator().asBinary(this.publicKey);
  }

  /**
   * @return the {@link ThankAddress}.
   */
  public ThankAddress getAddress() {

    return this.address;
  }

  /**
   * @return the {@link ThankIdentity} for this key pair.
   */
  public ThankIdentity getIdentity() {

    return this.identity;
  }

}
