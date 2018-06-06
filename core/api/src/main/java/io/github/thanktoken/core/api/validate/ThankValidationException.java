package io.github.thanktoken.core.api.validate;

import io.github.thanktoken.core.api.ThankDataObject;
import io.github.thanktoken.core.api.ThankToken;

/**
 * An {@link ThankValidationException} is thrown if a {@link ThankToken} is invalid.
 *
 * @see ThankValidator#validate(ThankToken, ThankValidationMode)
 */
public class ThankValidationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   *
   * @param cause is the exception that caused this error.
   * @param data the invalid {@link ThankDataObject}.
   */
  public ThankValidationException(Throwable cause, ThankDataObject data) {

    super(createMessage(data, cause), cause);
  }

  private static String createMessage(ThankDataObject data, Throwable cause) {

    StringBuilder sb = new StringBuilder("Invalid data ");
    sb.append(data);
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
