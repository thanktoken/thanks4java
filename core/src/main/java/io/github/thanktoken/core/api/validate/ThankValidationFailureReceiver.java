package io.github.thanktoken.core.api.validate;

import io.github.thanktoken.core.api.validate.failure.ThankValidationFailure;

/**
 * Interface for an object {@link #add(ThankValidationFailure) collecting} {@link ThankValidationFailure}s.
 *
 * @since 1.0.0
 */
public interface ThankValidationFailureReceiver {

  /**
   * @param failure the {@link ThankValidationFailure} to receive.
   */
  void add(ThankValidationFailure failure);

}
