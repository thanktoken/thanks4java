package io.github.thanktoken.core.api.validate;

import io.github.thanktoken.core.api.ThankToken;

/**
 * An {@link ThankTokenValidationException} is thrown if a {@link ThankToken} is invalid.
 *
 * @see ThankTokenValidator#validate(ThankToken, ThankValidationMode)
 */
public class ThankTokenValidationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   *
   * @param cause is the exception that caused this error.
   * @param token the invalid {@link ThankToken}.
   */
  public ThankTokenValidationException(Throwable cause, ThankToken token) {

    super(createMessage(token, cause), cause);
  }

  private static String createMessage(ThankToken token, Throwable cause) {

    StringBuilder sb = new StringBuilder("Invalid token ");
    sb.append(token);
    String message = null;
    if (cause != null) {
      message = cause.getMessage();
    }
    if (message == null) {
      sb.append('.');
    } else {
      sb.append(": ");
      sb.append(message);
    }
    return sb.toString();
  }

}
