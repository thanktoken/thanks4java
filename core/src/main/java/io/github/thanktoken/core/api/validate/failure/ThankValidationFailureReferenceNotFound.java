package io.github.thanktoken.core.api.validate.failure;

import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.reference.ThankTokenReference;

/**
 * {@link ThankValidationFailure} if a {@link ThankTokenReference} cycle is not properly closed, so the last reference
 * does not point back to the initial token.
 *
 * @since 1.0.0
 */
public class ThankValidationFailureReferenceNotFound extends ThankValidationFailureReference {

  /** {@link #getId() ID} of this failure. */
  public static final String ID = "RF404";

  /**
   * The constructor.
   *
   * @param reference the invalid {@link ThankTokenReference}.
   * @param data the optional {@link ThankDataObject}.
   */
  public ThankValidationFailureReferenceNotFound(ThankTokenReference reference, ThankDataObject data) {

    this(reference, data, null);
  }

  /**
   * The constructor.
   *
   * @param reference the invalid {@link ThankTokenReference}.
   * @param data the optional {@link ThankDataObject}.
   * @param error the optional {@link Throwable}.
   */
  public ThankValidationFailureReferenceNotFound(ThankTokenReference reference, ThankDataObject data, Throwable error) {

    super(ID, "Token for reference " + reference + " could not be found.", reference, data, error);
  }

}
