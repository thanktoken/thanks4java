package io.github.thanktoken.core.api.validate;

import java.util.List;

import io.github.thanktoken.core.api.validate.failure.ThankValidationFailure;

/**
 * Interface for the result of a {@link ThankValidator#validate(io.github.thanktoken.core.api.token.ThankToken)
 * validation}.
 *
 * @since 1.0.0
 */
public interface ThankValidationResult {

  /**
   * @return the immutable {@link List} of {@link ThankValidationFailure}s. Has to be {@link List#isEmpty() empty} if
   *         {@link #isValid() valid} and not empty otherwise.
   */
  List<ThankValidationFailure> getFailures();

  /**
   * @return {@code true} if the validation was successful, {@code false} otherwise (the data is invalid).
   */
  default boolean isValid() {

    return getFailures().isEmpty();
  }

}
