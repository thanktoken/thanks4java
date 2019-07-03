package io.github.thanktoken.core.base.datatype;

import net.sf.mmm.crypto.asymmetric.access.ec.bc.Curve25519;
import net.sf.mmm.cryptooo.hash.SecurityHashConfigSha256;

import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;

/**
 * {@link ThankAlgorithm} using ECDSA and ECIES with {@link Curve25519} parameters.
 */
public class ThankAlgorithmCurve25519 extends ThankAlgorithm {

  /** The {@link #getValue() algorithm name}. */
  public static final String NAME = "c25519";

  private static final ThankAlgorithm INSTANCE = new ThankAlgorithmCurve25519();

  /**
   * The constructor.
   */
  public ThankAlgorithmCurve25519() {

    super(NAME, Curve25519.create().withHash(SecurityHashConfigSha256.SHA_256_2X).getFactories());
  }

  /**
   * @return this {@link ThankAlgorithm}.
   */
  public static ThankAlgorithm get() {

    return INSTANCE;
  }

}
