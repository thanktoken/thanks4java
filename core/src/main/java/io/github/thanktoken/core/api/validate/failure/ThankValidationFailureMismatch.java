package io.github.thanktoken.core.api.validate.failure;

import java.util.Objects;

import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.field.ThankField;
import io.github.thanktoken.core.api.validate.ThankValidationFailureReceiver;

/**
 * {@link ThankValidationFailure} if a property did not have the expected value or format.
 *
 * @since 1.0.0
 */
public class ThankValidationFailureMismatch extends ThankValidationFailure {

  /** {@link #getId() ID} of this failure. */
  public static final String ID = "MIS";

  /**
   * The constructor.
   *
   * @param property the {@link io.github.thanktoken.core.api.field.ThankField#getName() name} of the affected property.
   * @param value the actual value as {@link String}.
   * @param expectedValueOrFormat the value or format that was expected but is not matched by the actual value.
   *
   * @param data the optional {@link ThankDataObject}.
   */
  public ThankValidationFailureMismatch(String property, String value, String expectedValueOrFormat, ThankDataObject data) {

    super(ID, "Property " + property + " has value " + value + " but expected: " + expectedValueOrFormat, data, null);
  }

  /**
   * @param <T> type of the value to check.
   * @param <D> type of the {@link ThankDataObject} to validate.
   * @param field the {@link ThankField} to check.
   * @param data the {@link ThankDataObject} to validate.
   * @param expectedValue the expected value.
   * @param receiver the {@link ThankValidationFailureReceiver} that collects potential validation failures.
   */
  public static <T, D extends ThankDataObject> void validate(ThankField<T, D, ?> field, D data, T expectedValue,
      ThankValidationFailureReceiver receiver) {

    T value = field.get(data);
    if (!Objects.equals(value, expectedValue)) {
      receiver.add(new ThankValidationFailureMismatch(field.getName(), "" + value, "" + expectedValue, data));
    }
  }

}
