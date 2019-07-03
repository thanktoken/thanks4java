package io.github.thanktoken.core.api.validate;

import io.github.thanktoken.core.api.io.ThankNetworkException;

/**
 * An {@link ThankValidationException} is thrown if the
 * {@link io.github.thanktoken.core.api.validate.ThankValidator#validate(io.github.thanktoken.core.api.token.ThankToken)
 * validation} failed. <br>
 * <b>ATTENTION:</b><br>
 * There are two major reasons to distinguish why the validation failed:
 * <ul>
 * <li>{@link ThankValidationDataException} is thrown if the data ({@link io.github.thanktoken.core.api.token.ThankToken}) is
 * invalid.</li>
 * <li>{@link ThankNetworkException} is thrown if additional resources had to be received from the network but
 * were not available (temporary error, validation should be retried).</li>
 * </ul>
 *
 * @see io.github.thanktoken.core.api.validate.ThankValidator#validate(io.github.thanktoken.core.api.token.ThankToken,
 *      ThankValidationMode)
 */
public abstract class ThankValidationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   *
   * @param message the {@link #getMessage() message} describing the error.
   * @param cause the {@link #getCause() cause of this exception}. May be {@code null}.
   */
  public ThankValidationException(String message, Throwable cause) {

    super(message, cause);
  }

}
