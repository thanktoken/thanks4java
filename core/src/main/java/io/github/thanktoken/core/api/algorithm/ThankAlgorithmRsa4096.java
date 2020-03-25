package io.github.thanktoken.core.api.algorithm;

import io.github.mmm.crypto.asymmetric.access.rsa.Rsa;
import io.github.mmm.crypto.hash.sha2.Sha256;

/**
 * {@link ThankAlgorithm} based on {@link Rsa RSA}.
 *
 * @since 1.0.0
 */
public class ThankAlgorithmRsa4096 extends ThankAlgorithmPkAddress {

  /** The singleton instance. */
  private static final ThankAlgorithmRsa4096 INSTANCE = new ThankAlgorithmRsa4096();

  /**
   * The constructor.
   */
  private ThankAlgorithmRsa4096() {

    super("rsa4k", Rsa.of4096(Sha256.ALGORITHM_SHA_256));
  }

  /**
   * @return the singleton instance.
   */
  public static ThankAlgorithmRsa4096 get() {

    return INSTANCE;
  }

}
