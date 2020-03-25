package io.github.thanktoken.core.api.validate;

import io.github.thanktoken.core.api.io.ThankNetworkException;
import io.github.thanktoken.core.api.message.ThankMessage;
import io.github.thanktoken.core.api.token.ThankToken;

/**
 * Interface for verifying a {@link ThankToken}.
 */
public interface ThankValidator {

  /**
   * Validates the given {@link ThankToken} with {@link ThankValidationMode#FULL}.
   *
   * @param token the {@link ThankToken} to validate.
   * @return the {@link ThankValidationResult result} of the validation.
   * @throws ThankNetworkException in case of a (temporary) network error. Validation may be retried.
   */
  default ThankValidationResult validate(ThankToken token) throws ThankNetworkException {

    return validate(token, ThankValidationMode.FULL);
  }

  /**
   * Validates the given {@link ThankToken}.
   *
   * @param token the {@link ThankToken} to validate.
   * @param mode the {@link ThankValidationMode} that defines the details how to validate the {@link ThankToken}.
   * @return the {@link ThankValidationResult result} of the validation. May be in{@link ThankValidationResult#isValid()
   *         valid} if {@link ThankValidationMode#getFailureMode()} is {@code false}.
   * @throws ThankValidationException if {@link ThankValidationMode#getFailureMode()} is {@code true} and a
   *         validation error occurred.
   * @throws ThankNetworkException in case of a (temporary) network error. Validation may be retried.
   */
  ThankValidationResult validate(ThankToken token, ThankValidationMode mode) throws ThankValidationException, ThankNetworkException;

  // /**
  // * Validates the given {@link ThankTransaction}.
  // *
  // * @param token the {@link ThankToken} owning the given {@link ThankTransaction}.
  // * @param tx the {@link ThankTransaction} to validate.
  // * @param mode the {@link ThankValidationMode} that defines the details how to validate the {@link
  // ThankTransaction}.
  // * @throws ThankValidationException if the {@link ThankTransaction} is invalid.
  // */
  // void validateTransaction(ThankToken token, ThankTransaction tx, ThankValidationMode mode) throws
  // ThankValidationException;

  /**
   * Validates the given {@link ThankMessage} with {@link ThankValidationMode#FULL}.
   *
   * @param message the {@link ThankMessage} to validate.
   * @return the {@link ThankValidationResult result} of the validation.
   * @throws ThankNetworkException in case of a (temporary) network error. Validation may be retried.
   */
  default ThankValidationResult validateMessage(ThankMessage message) throws ThankNetworkException {

    return validateMessage(message, ThankValidationMode.FULL);
  }

  /**
   * Validates the given {@link ThankMessage}.
   *
   * @param message the {@link ThankMessage} to validate.
   * @param mode the {@link ThankValidationMode} that defines the details how to validate the {@link ThankMessage}.
   *        Specific {@link ThankValidationMode} may typically only be specified for testing.
   * @return the {@link ThankValidationResult result} of the validation. May be in{@link ThankValidationResult#isValid()
   *         valid} if {@link ThankValidationMode#getFailureMode()} is {@code false}.
   * @throws ThankValidationException if {@link ThankValidationMode#getFailureMode()} is {@code true} and a
   *         validation error occurred.
   * @throws ThankNetworkException in case of a (temporary) network error. Validation may be retried.
   */
  ThankValidationResult validateMessage(ThankMessage message, ThankValidationMode mode) throws ThankValidationException, ThankNetworkException;

}
