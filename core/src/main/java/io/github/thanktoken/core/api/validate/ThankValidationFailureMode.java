package io.github.thanktoken.core.api.validate;

/**
 * TODO hohwille This type ...
 *
 * @since 1.0.0
 */
public enum ThankValidationFailureMode {

  /** Mode to never throw a {@link ThankValidationException} but return a {@link ThankValidationResult}. */
  RETURN_RESULT,

  /**
   * Mode to throw a {@link ThankValidationException} for the first
   * {@link io.github.thanktoken.core.api.validate.failure.ThankValidationFailure failure} that occurred and stop any
   * further validation ("fail fast").
   */
  THROW_ON_FIRST_FAILURE,

  /**
   * Mode to throw a {@link ThankValidationException} at the end of the validation in case that the
   * {@link ThankValidationResult} is in{@link ThankValidationResult#isValid() valid} ("fail at end").
   */
  THROW_ON_LAST_FAILURE

  ;

  /**
   * @return {@code true} if a {@link io.github.thanktoken.core.api.validate.failure.ThankValidationFailure failure}
   *         will cause a {@link ThankValidationException} to be thrown. In this case the returned
   *         {@link ThankValidationResult} can be ignored, because it can only be {@link ThankValidationResult#isValid()
   *         valid}, {@code false} otherwise (failures are always returned as {@link ThankValidationResult}).
   */
  public boolean isThrowException() {

    return (this != RETURN_RESULT);
  }
}
