package io.github.thanktoken.core.api.validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.github.thanktoken.core.api.validate.failure.ThankValidationFailure;

/**
 * Implementation of {@link ThankValidationResult}.
 *
 * @since 1.0.0
 */
public class ThankValidationResultImpl implements ThankValidationResult, ThankValidationFailureReceiver {

  private List<ThankValidationFailure> failures;

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
  }

  void makeImmutable() {

    if (this.failures == null) {
      this.failures = Collections.emptyList();
    } else {
      this.failures = Collections.unmodifiableList(this.failures);
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
