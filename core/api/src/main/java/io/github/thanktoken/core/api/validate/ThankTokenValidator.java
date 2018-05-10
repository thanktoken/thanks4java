package io.github.thanktoken.core.api.validate;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * Interface for verifying a {@link ThankToken}.
 */
public interface ThankTokenValidator {

  /**
   * Validates the given {@link ThankToken}.
   *
   * @param token the {@link ThankToken} to validate.
   * @throws ThankTokenValidationException if the {@link ThankToken} is invalid.
   */
  default void validate(ThankToken token) throws ThankTokenValidationException {

    validate(token, ThankValidationMode.FULL);
  }

  /**
   * Validates the given {@link ThankToken}.
   *
   * @param token the {@link ThankToken} to validate.
   * @param mode the {@link ThankValidationMode} that defines the details how to validate the {@link ThankToken}.
   * @throws ThankTokenValidationException if the {@link ThankToken} is invalid.
   */
  void validate(ThankToken token, ThankValidationMode mode) throws ThankTokenValidationException;

  /**
   * Validates the given {@link ThankToken#getHeader() header} of the given {@link ThankToken}.
   *
   * @param token the {@link ThankToken} with the {@link ThankToken#getHeader() header} to validate.
   * @param mode the {@link ThankValidationMode} that defines the details how to validate the {@link ThankTransaction}.
   * @throws ThankTokenValidationException if the {@link ThankTransaction} is invalid.
   */
  void validateHeader(ThankToken token, ThankValidationMode mode) throws ThankTokenValidationException;

  /**
   * Validates the given {@link ThankTransaction}.
   *
   * @param token the {@link ThankToken} owning the given {@link ThankTransaction}.
   * @param tx the {@link ThankTransaction} to validate.
   * @param mode the {@link ThankValidationMode} that defines the details how to validate the {@link ThankTransaction}.
   * @throws ThankTokenValidationException if the {@link ThankTransaction} is invalid.
   */
  void validateTransaction(ThankToken token, ThankTransaction tx, ThankValidationMode mode) throws ThankTokenValidationException;

}
