package io.github.thanktoken.core.api.validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.github.thanktoken.core.api.validate.failure.ThankValidationFailure;
import io.github.thanktoken.core.api.validate.failure.ThankValidationFailureException;

/**
 * Implementation of {@link ThankValidationResult}.
 *
 * @since 1.0.0
 */
public class ThankValidationResultImpl implements ThankValidationResult, ThankValidationFailureReceiver {

  private final ThankValidationFailureMode failureMode;

  private List<ThankValidationFailure> failures;

  /**
   * The constructor.
   *
   * @param failureMode the {@link ThankValidationFailureMode}.
   */
  public ThankValidationResultImpl(ThankValidationFailureMode failureMode) {

    super();
    this.failureMode = failureMode;
  }

  @Override
  public List<ThankValidationFailure> getFailures() {

    if (this.failures == null) {
      return Collections.emptyList();
    }
    return this.failures;
  }

  @Override
  public void add(ThankValidationFailure failure) {

    Objects.requireNonNull(failure, "failure");
    if (this.failures == null) {
      this.failures = new ArrayList<>();
    }
    this.failures.add(failure);
    if (this.failureMode == ThankValidationFailureMode.THROW_ON_FIRST_FAILURE) {
      Throwable error = null;
      if (failure instanceof ThankValidationFailureException) {
        error = failure.getError();
      }
      throw new ThankValidationException(this, error);
    }
  }

  void complete() {

    if (this.failures == null) {
      this.failures = Collections.emptyList();
    } else {
      throwIfInvalid();
      this.failures = Collections.unmodifiableList(this.failures);
    }
  }

  void throwIfInvalid() {

    if (this.failureMode.isThrowException()) {
      ThankValidationException exception = ThankValidationException.of(this);
      if (exception != null) {
        throw exception;
      }
    }
  }

  @Override
  public String toString() {

    int failureCount = getFailures().size();
    if (failureCount == 0) {
      return "valid";
    } else {
      StringBuilder sb = new StringBuilder(failureCount * 16);
      sb.append("invalid with ");
      sb.append(failureCount);
      sb.append(" failure(s)");
      for (ThankValidationFailure failure : this.failures) {
        sb.append('\n');
        failure.toString(sb);
      }
      return sb.toString();

    }
  }

}
