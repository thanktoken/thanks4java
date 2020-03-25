package io.github.thanktoken.core.api.validate.failure;

import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.reference.ThankTokenReference;

/**
 * {@link ThankValidationFailure} if a date property lies in the future.
 *
 * @since 1.0.0
 */
public abstract class ThankValidationFailureReference extends ThankValidationFailure {

  /**
   * The constructor.
   *
   * @param id the {@link #getId() ID}.
   * @param message the {@link #getMessage() message}.
   * @param reference the invalid {@link ThankTokenReference}.
   * @param data the optional {@link ThankDataObject}.
   * @param error the optional {@link Throwable}.
   */
  protected ThankValidationFailureReference(String id, String message, ThankTokenReference reference, ThankDataObject data, Throwable error) {

    super(id, message, data, error);
  }

}
