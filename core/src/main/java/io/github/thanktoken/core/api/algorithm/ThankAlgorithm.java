package io.github.thanktoken.core.api.algorithm;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.crypto.asymmetric.access.AsymmetricAccess;
import net.sf.mmm.crypto.asymmetric.crypt.AsymmetricCryptorFactory;
import net.sf.mmm.crypto.asymmetric.key.AsymmetricKeyCreator;
import net.sf.mmm.crypto.asymmetric.sign.SignatureBinary;
import net.sf.mmm.crypto.asymmetric.sign.SignatureProcessorFactory;
import net.sf.mmm.crypto.hash.Hash;
import net.sf.mmm.crypto.hash.HashCreator;
import net.sf.mmm.crypto.hash.HashFactory;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;

import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.address.ThankAddressHeader;
import io.github.thanktoken.core.api.datatype.StringType;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * The underlying algorithm(s) used for signing, encryption and hashing.
 *
 * @see io.github.thanktoken.core.api.token.header.ThankTokenHeader#getAlgorithm()
 */
public abstract class ThankAlgorithm extends StringType {

  private static final Map<String, ThankAlgorithm> INSTANCE_MAP = new HashMap<>();

  private final HashFactory hashFactory;

  private final AsymmetricCryptorFactory<?, ?> cryptorFactory;

  private final AsymmetricKeyCreator<?, ?, ?> keyCreator;

  private final SignatureProcessorFactory<?, ?, ?> signatureFactory;

  static {

    // initialize standard algorithms
    ThankAlgorithmRsa4096.get();
    ThankAlgorithmSecp256k1.get();
  }

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() algorithm name}.
   * @param hashFactory the {@link HashFactory} used to create hashes.
   * @param cryptorFactory the {@link AsymmetricCryptorFactory} used for encryption.
   * @param keyCreator the {@link AsymmetricKeyCreator} used to create public and private keys.
   * @param signatureFactory the {@link SignatureProcessorFactory} used for signing.
   */
  public ThankAlgorithm(String value, HashFactory hashFactory, AsymmetricCryptorFactory<?, ?> cryptorFactory,
      AsymmetricKeyCreator<?, ?, ?> keyCreator, SignatureProcessorFactory<?, ?, ?> signatureFactory) {

    super(value);
    this.hashFactory = hashFactory;
    this.cryptorFactory = cryptorFactory;
    this.keyCreator = keyCreator;
    this.signatureFactory = signatureFactory;
    if (INSTANCE_MAP.containsKey(value)) {
      throw new DuplicateObjectException(ThankAlgorithm.class, value);
    }
    INSTANCE_MAP.put(value, this);
  }

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() algorithm name}.
   * @param asymetricAccess the {@link AsymmetricAccess} to retrieve all factories from.
   */
  public ThankAlgorithm(String value, AsymmetricAccess<?, ?, ?, ?, ?> asymetricAccess) {

    this(value, asymetricAccess, asymetricAccess, asymetricAccess.newKeyCreator(),
        asymetricAccess.getSignatureFactoryWithoutHash());
  }

  /**
   * @return the {@link HashCreator} used to calculate hash values of the {@link ThankToken} data, esp. for
   *         {@link ThankTransaction#getSignature()}.
   */
  public HashFactory getHashFactory() {

    return this.hashFactory;
  }

  /**
   * @return the {@link AsymmetricCryptorFactory} used to encrypt and decrypt data.
   */
  @SuppressWarnings("rawtypes")
  public AsymmetricCryptorFactory getCryptorFactory() {

    return this.cryptorFactory;
  }

  /**
   * @return the {@link AsymmetricKeyCreator} used to deal with asymmetric keys.
   */
  @SuppressWarnings("rawtypes")
  public AsymmetricKeyCreator getKeyCreator() {

    return this.keyCreator;
  }

  /**
   * @return the {@link SignatureProcessorFactory} used for signing and verifying.
   */
  @SuppressWarnings("rawtypes")
  public SignatureProcessorFactory getSignatureFactory() {

    return this.signatureFactory;
  }

  /**
   * @param data the {@link ThankAddress#getData() raw binary data}.
   * @return the converted {@link ThankAddress}.
   */
  public abstract ThankAddress createAddress(byte[] data);

  /**
   * @param key the {@link PublicKey}.
   * @param header the {@link ThankAddressHeader header} of the {@link ThankAddress} to create.
   * @return the {@link ThankAddress} for the given {@link PublicKey}.
   */
  public abstract ThankAddress createAddress(PublicKey key, ThankAddressHeader header);

  @Override
  public int getMaxLength() {

    return 10;
  }

  /**
   * @param value the {@link #toString() string representation} of the algorithm.
   * @return the {@link ThankAlgorithm} instance for the given {@code value}.
   */
  public static ThankAlgorithm of(String value) {

    if ((value == null) || value.isEmpty()) {
      return null;
    }
    ThankAlgorithm algorithm = INSTANCE_MAP.get(value);
    if (algorithm == null) {
      throw new ObjectNotFoundException(ThankAlgorithm.class, value);
    }
    return algorithm;
  }

  /**
   * @param signature the {@link SignatureBinary}.
   * @param address the {@link ThankAddress}.
   * @param messageHash the raw {@link Hash} that was signed resulting in the given {@link SignatureBinary}.
   * @throws RuntimeException if the verification failed (signature is invalid).
   */
  public void verifySignature(SignatureBinary signature, ThankAddress address, Hash messageHash) {

    PublicKey publicKey = createPublicKey(address, signature, messageHash);
    boolean valid = getSignatureFactory().newVerifierUnsafe(publicKey).verify(messageHash, signature);
    if (!valid) {
      throw new IllegalArgumentException("Invalid signature!");
    }
  }

  /**
   * @param address the {@link ThankAddress}.
   * @param signature the {@link SignatureBinary}.
   * @param messageHash the raw {@link Hash} that was signed resulting in the given {@link SignatureBinary}.
   * @return the {@link PublicKey} corresponding to the given {@link ThankAddress}.
   */
  abstract PublicKey createPublicKey(ThankAddress address, SignatureBinary signature, Hash messageHash);
}
