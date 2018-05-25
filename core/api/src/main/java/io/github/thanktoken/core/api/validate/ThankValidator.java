package io.github.thanktoken.core.api.validate;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * Interface for verifying a {@link ThankToken}.
 */
public interface ThankValidator {

  /**
   * Validates the given {@link ThankToken}.
   *
   * @param token the {@link ThankToken} to validate.
   * @throws ThankValidationException if the {@link ThankToken} is invalid.
   */
  default void validate(ThankToken token) throws ThankValidationException {

    validate(token, ThankValidationMode.FULL);
  }

  /**
   * Validates the given {@link ThankToken}.
   *
   * @param token the {@link ThankToken} to validate.
   * @param mode the {@link ThankValidationMode} that defines the details how to validate the {@link ThankToken}.
   * @throws ThankValidationException if the {@link ThankToken} is invalid.
   */
  void validate(ThankToken token, ThankValidationMode mode) throws ThankValidationException;

  /**
   * Validates the given {@link ThankToken#getHeader() header} of the given {@link ThankToken}.
   *
   * @param token the {@link ThankToken} with the {@link ThankToken#getHeader() header} to validate.
   * @param mode the {@link ThankValidationMode} that defines the details how to validate the {@link ThankTransaction}.
   * @throws ThankValidationException if the {@link ThankTransaction} is invalid.
   */
  void validateHeader(ThankToken token, ThankValidationMode mode) throws ThankValidationException;

  /**
   * Validates the given {@link ThankTransaction}.
   *
   * @param token the {@link ThankToken} owning the given {@link ThankTransaction}.
   * @param tx the {@link ThankTransaction} to validate.
   * @param mode the {@link ThankValidationMode} that defines the details how to validate the {@link ThankTransaction}.
   * @throws ThankValidationException if the {@link ThankTransaction} is invalid.
   */
  void validateTransaction(ThankToken token, ThankTransaction tx, ThankValidationMode mode) throws ThankValidationException;

}
