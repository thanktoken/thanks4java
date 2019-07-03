package io.github.thanktoken.core.api.algorithm;

import net.sf.mmm.crypto.asymmetric.access.ec.bc.Secp256k1;
import net.sf.mmm.crypto.hash.sha2.Sha256;

/**
 * {@link ThankAlgorithm} based on {@link Secp256k1 Secp256k1}.
 *
 * @since 1.0.0
 */
public class ThankAlgorithmSecp256k1 extends ThankAlgorithmPkSignature {

  /** The singleton instance. */
  private static final ThankAlgorithmSecp256k1 INSTANCE = new ThankAlgorithmSecp256k1();

  /**
   * The constructor.
   */
  private ThankAlgorithmSecp256k1() {

    super("secp256k1", Secp256k1.ofRecoveryId(Sha256.ALGORITHM_SHA_256));
  }

  /**
   * @return the singleton instance.
   */
  public static ThankAlgorithmSecp256k1 get() {

    return INSTANCE;
  }

}
