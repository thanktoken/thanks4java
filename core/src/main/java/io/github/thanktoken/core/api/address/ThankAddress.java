package io.github.thanktoken.core.api.address;

import net.sf.mmm.crypto.CryptoBinary;

import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;

/**
 * Cryptographic address that acts like a bank account number. It corresponds to a {@link java.security.KeyPair}.
 * Depending on the {@link ThankAlgorithm} this may be a binary representation of the {@link java.security.PublicKey}
 * itself or something derived from it (like a hash) but still sufficient to verify a signature created by signing with
 * the corresponding {@link java.security.PrivateKey}.
 *
 * @since 1.0.0
 */
public abstract class ThankAddress extends CryptoBinary {

  /**
   * The constructor.
   *
   * @param data the {@link #getData() binary data}.
   */
  public ThankAddress(byte[] data) {

    super(data);
  }

  /**
   * @return the {@link ThankAddressType type} of this address. Please note that using the wrong type will render your
   *         tokens, transactions and messages invalid. Official {@link ThankAddress}es have to be certified and
   *         confirmed via the {@link io.github.thanktoken.core.api.identity.ThankIdentity}-directory (see
   *         {@link io.github.thanktoken.core.api.identity.ThankIdentityProvider}).
   */
  public abstract ThankAddressType getType();

  /**
   * @return the ID (hash-code) of this address that may be used for
   *         {@link io.github.thanktoken.core.api.timestamp.ThankTimestamp}s.
   * @see io.github.thanktoken.core.api.timestamp.ThankTimestampFactoryNanoIdRange
   */
  public int getTimestampId() {

    int id = this.data[0];
    for (int i = 1; i < this.data.length; i++) {
      id = 31 * id + this.data[i];
    }
    return id;
  }

}
