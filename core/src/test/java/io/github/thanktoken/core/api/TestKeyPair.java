package io.github.thanktoken.core.api;

import java.security.PrivateKey;
import java.security.PublicKey;

import io.github.mmm.binary.Binary;
import io.github.mmm.binary.BinaryType;
import io.github.mmm.crypto.asymmetric.key.AsymmetricKeyPair;
import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.address.ThankAddressHeader;
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
   * @param addressHeader the {@link ThankAddressHeader} for the {@link #getAddress() address}.
   * @param algorithm the {@link ThankAlgorithm} to use.
   */
  public TestKeyPair(ThankAddressHeader addressHeader, ThankAlgorithm algorithm) {

    this(algorithm.getKeyCreator().generateKeyPair(), addressHeader, algorithm);
  }

  /**
   * The constructor.
   *
   * @param keyBase64 the key pair / private key as base64 encoded {@link String}.
   * @param addressHeader the {@link ThankAddressHeader} for the {@link #getAddress() address}.
   * @param algorithm the {@link ThankAlgorithm} to use.
   */
  public TestKeyPair(String keyBase64, ThankAddressHeader addressHeader, ThankAlgorithm algorithm) {

    this(algorithm.getKeyCreator().createKeyPair(BinaryType.parseBase64(keyBase64)), addressHeader, algorithm);
  }

  private TestKeyPair(AsymmetricKeyPair<?, ?> keyPair, ThankAddressHeader addressHeader, ThankAlgorithm algorithm) {

    this(keyPair.getPrivateKey(), keyPair.getPublicKey(), addressHeader, algorithm);
  }

  private TestKeyPair(PrivateKey privateKey, PublicKey publicKey, ThankAddressHeader addressHeader,
      ThankAlgorithm algorithm) {

    super();
    this.algorithm = algorithm;
    this.privateKey = privateKey;
    this.publicKey = publicKey;
    this.address = algorithm.createAddress(publicKey, addressHeader);
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
