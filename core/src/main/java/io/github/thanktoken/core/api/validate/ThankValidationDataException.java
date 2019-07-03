package io.github.thanktoken.core.api.validate;

import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.token.ThankToken;

/**
 * An {@link ThankValidationDataException} is thrown if the {@link ThankDataObject} to validate is invalid. This is a
 * deterministic result and should not be retried. The invalid data has to be rejected.
 *
 * @see ThankValidator#validate(ThankToken, ThankValidationMode)
 */
public class ThankValidationDataException extends ThankValidationException {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   *
   * @param cause is the exception that caused this error.
   * @param data the invalid {@link ThankDataObject}.
   */
  public ThankValidationDataException(Throwable cause, ThankDataObject data) {

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
