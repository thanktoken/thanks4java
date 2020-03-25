package io.github.thanktoken.core.api.validate.failure;

import io.github.mmm.crypto.asymmetric.sign.SignatureBinary;
import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.attribute.AttributeReadHash;
import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.validate.ThankValidationFailureReceiver;

/**
 * {@link ThankValidationFailure} if a signature was invalid (not a valid signature signed by the recipient).
 *
 * @since 1.0.0
 */
public class ThankValidationFailureSignature extends ThankValidationFailure {

  /** {@link #getId() ID} of this failure. */
  public static final String ID = "SGN";

  /**
   * The constructor.
   *
   * @param signature the invalid {@link SignatureBinary}.
   * @param address the expected recipient / {@link ThankAddress address}.
   * @param data the optional {@link ThankDataObject}.
   * @param error the optional {@link Throwable}.
   */
  public ThankValidationFailureSignature(SignatureBinary signature, ThankAddress address, ThankDataObject data,
      Throwable error) {

    super(ID, "Not a valid signature for the recipient/address.", data, error);
  }

  /**
   * @param signature the {@link SignatureBinary} to verify.
   * @param address the expected recipient / {@link ThankAddress address}.
   * @param algorithm the underlying {@link ThankAlgorithm}.
   * @param data the {@link ThankDataObject} to validate as {@link AttributeReadHash}.
   * @param receiver the {@link ThankValidationFailureReceiver} that collects potential validation failures.
   */
  public static void validate(SignatureBinary signature, ThankAddress address, ThankAlgorithm algorithm,
      AttributeReadHash data, ThankValidationFailureReceiver receiver) {

    try {
      algorithm.verifySignature(signature, address, data.getHash2Sign());
    } catch (Exception e) {
      receiver.add(new ThankValidationFailureSignature(signature, address, data, e));
    }
  }

}
