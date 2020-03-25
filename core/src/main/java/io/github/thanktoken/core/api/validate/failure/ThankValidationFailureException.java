package io.github.thanktoken.core.api.validate.failure;

import io.github.thanktoken.core.api.data.ThankDataObject;

/**
 * {@link ThankValidationFailure} for unexpected {@link Throwable exception} that occurred during validation.
 *
 * @since 1.0.0
 */
public class ThankValidationFailureException extends ThankValidationFailure {

  /** {@link #getId() ID} of this failure. */
  public static final String ID = "EXC";

  /**
   * The constructor.
   *
   * @param data the optional {@link ThankDataObject}.
   * @param error the unexpected {@link Throwable exception} that occurred.
   */
  public ThankValidationFailureException(ThankDataObject data, Throwable error) {

    super(ID, "Unexpected error!", data, error);
  }

}
