package io.github.thanktoken.core.base.datatype;

import net.sf.mmm.cryptoooo.asymmetric.crypt.ec.Secp256k1;
import net.sf.mmm.cryptoooo.hash.SecurityHashConfigSha256;

import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;

/**
 * {@link ThankAlgorithm} using ECDSA and ECIES with {@link Secp256k1} parameters.
 */
public class ThankAlgorithmSecp256k1 extends ThankAlgorithm {

  /** The {@link #getValue() algorithm name}. */
  public static final String NAME = "s256k1";

  private static final ThankAlgorithm INSTANCE = new ThankAlgorithmSecp256k1();

  /**
   * The constructor.
   */
  public ThankAlgorithmSecp256k1() {

    super(NAME, Secp256k1.create().withHash(SecurityHashConfigSha256.SHA_256_2X).getFactories());
  }

  /**
   * @return this {@link ThankAlgorithm}.
   */
  public static ThankAlgorithm get() {

    return INSTANCE;
  }

}
