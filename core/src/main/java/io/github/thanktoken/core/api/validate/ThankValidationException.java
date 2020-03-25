package io.github.thanktoken.core.api.validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.thanktoken.core.api.validate.failure.ThankValidationFailure;

/**
 * An {@link ThankValidationException} is thrown if the
 * {@link io.github.thanktoken.core.api.validate.ThankValidator#validate(io.github.thanktoken.core.api.token.ThankToken)
 * validation} failed.
 *
 * @see io.github.thanktoken.core.api.io.ThankNetworkException
 * @see io.github.thanktoken.core.api.validate.ThankValidator#validate(io.github.thanktoken.core.api.token.ThankToken,
 *      ThankValidationMode)
 */
public class ThankValidationException extends RuntimeException implements ThankValidationResult {

  private static final long serialVersionUID = 1L;

  private final List<ThankValidationFailure> failures;

  /**
   * The constructor.
   *
   * @param result the in{@link ThankValidationResult#isValid() valid} {@link ThankValidationResult} with the
   *        {@link ThankValidationResult#getFailures() failures}.
   * @param cause the {@link #getCause() cause of this exception}. May be {@code null}.
   * @see #of(ThankValidationResult)
   */
  protected ThankValidationException(ThankValidationResult result, Throwable cause) {

    super(createMessage(result), cause);
    assert (!result.isValid());
    this.failures = Collections.unmodifiableList(new ArrayList<>(result.getFailures()));
  }

  private static String createMessage(ThankValidationResult result) {

    StringBuilder sb = new StringBuilder("The validation failed!");
    for (ThankValidationFailure failure : result.getFailures()) {
      sb.append('\n');
      sb.append(failure.toString());
    }
    return sb.toString();
  }

  @Override
  public boolean isValid() {

    return false;
  }

  @Override
  public List<ThankValidationFailure> getFailures() {

    return this.failures;
  }

  /**
   * @param result the {@link ThankValidationResult}.
   * @return the {@link ThankValidationException} for the given {@link ThankValidationResult}.
   */
  public static ThankValidationException of(ThankValidationResult result) {

    List<ThankValidationFailure> failures = result.getFailures();
    if (failures.isEmpty()) {
      return null;
    }
    ThankValidationException exception = null;
    for (ThankValidationFailure failure : failures) {
      Throwable error = failure.getError();
      if (error != null) {
        if (exception == null) {
          exception = new ThankValidationException(result, error);
        } else {
          exception.addSuppressed(error);
        }
      }
    }
    if (exception == null) {
      exception = new ThankValidationException(result, null);
    }
    return exception;
  }

}
