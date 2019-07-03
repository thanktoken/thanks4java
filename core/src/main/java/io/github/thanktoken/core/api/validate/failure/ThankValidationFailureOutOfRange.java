package io.github.thanktoken.core.api.validate.failure;

import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.field.ThankField;
import io.github.thanktoken.core.api.validate.ThankValidationFailureReceiver;

/**
 * {@link ThankValidationFailure} if a property did not have a value from a given set of options.
 *
 * @since 1.0.0
 */
public class ThankValidationFailureOutOfRange extends ThankValidationFailure {

  /** {@link #getId() ID} of this failure. */
  public static final String ID = "OOR";

  /**
   * The constructor.
   *
   * @param property the {@link io.github.thanktoken.core.api.field.ThankField#getName() name} of the affected property.
   * @param value the actual value as {@link String}.
   * @param min the minimum allowed value.
   * @param max the maximum allowed value.
   * @param data the optional {@link ThankDataObject}.
   */
  public ThankValidationFailureOutOfRange(String property, String value, String min, String max, ThankDataObject data) {

    super(ID, "Property " + property + " has value " + value + " but expected to be in the range from " + min + " to " + max, data, null);
  }

  /**
   * @param <T> type of the value to check.
   * @param <D> type of the {@link ThankDataObject} to validate.
   * @param field the {@link ThankField} to check.
   * @param data the {@link ThankDataObject} to validate.
   * @param min the minimum allowed value.
   * @param max the maximum allowed value.
   * @param receiver the {@link ThankValidationFailureReceiver} that collects potential validation failures.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static <T extends Comparable, D extends ThankDataObject> void validate(ThankField<T, D, ?> field, D data, T min, T max,
      ThankValidationFailureReceiver receiver) {

    T value = field.get(data);
    if ((value.compareTo(min) < 0) || (value.compareTo(max) > 0)) {
      receiver.add(new ThankValidationFailureOutOfRange(field.getName(), "" + value, "" + min, "" + max, data));
    }
  }

}
