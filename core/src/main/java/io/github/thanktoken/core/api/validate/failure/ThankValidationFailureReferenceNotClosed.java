package io.github.thanktoken.core.api.validate.failure;

import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.reference.ThankTokenReference;
import io.github.thanktoken.core.api.token.ThankToken;

/**
 * {@link ThankValidationFailure} if a {@link ThankTokenReference} could not be resolved as the referenced
 * {@link ThankToken} could not be found.
 *
 * @since 1.0.0
 */
public class ThankValidationFailureReferenceNotClosed extends ThankValidationFailureReference {

  /** {@link #getId() ID} of this failure. */
  public static final String ID = "RFCYC";

  /**
   * The constructor.
   *
   * @param reference the invalid {@link ThankTokenReference}.
   * @param data the optional {@link ThankDataObject}.
   */
  public ThankValidationFailureReferenceNotClosed(ThankTokenReference reference, ThankToken data) {

    this(reference, data, null);
  }

  /**
   * The constructor.
   *
   * @param reference the invalid {@link ThankTokenReference}.
   * @param data the optional {@link ThankDataObject}.
   * @param error the optional {@link Throwable}.
   */
  public ThankValidationFailureReferenceNotClosed(ThankTokenReference reference, ThankToken data, Throwable error) {

    super(ID, "Token for reference " + reference + " shoud point back to the start of the cycle at "
        + data.getHeader().getId(), reference, data, error);
  }

}
