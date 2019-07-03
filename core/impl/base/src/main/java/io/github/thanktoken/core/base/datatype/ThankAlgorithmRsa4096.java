package io.github.thanktoken.core.base.datatype;

import net.sf.mmm.crypto.asymmetric.access.rsa.Rsa;
import net.sf.mmm.crypto.hash.SecurityHashConfigSha256;

import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;

/**
 * {@link ThankAlgorithm} using RSA with 4096 bit key-length. Mainly available as proof-of-concept. Keys are too long
 * leading to overhead and waste.
 */
public class ThankAlgorithmRsa4096 extends ThankAlgorithm {

  /** The {@link #getValue() algorithm name}. */
  public static final String NAME = "rsa4k";

  private static final ThankAlgorithm INSTANCE = new ThankAlgorithmRsa4096();

  /**
   * The constructor.
   */
  public ThankAlgorithmRsa4096() {

    super(NAME, Rsa.keyLength4096().withHash(SecurityHashConfigSha256.SHA_256_2X).getFactories());
  }

  /**
   * @return this {@link ThankAlgorithm}.
   */
  public static ThankAlgorithm get() {

    return INSTANCE;
  }

}
