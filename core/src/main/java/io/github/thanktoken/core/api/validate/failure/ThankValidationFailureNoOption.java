package io.github.thanktoken.core.api.validate.failure;

import java.util.Collection;
import java.util.Set;

import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.field.ThankField;
import io.github.thanktoken.core.api.validate.ThankValidationFailureReceiver;

/**
 * {@link ThankValidationFailure} if a property did not have a value from a given set of options.
 *
 * @since 1.0.0
 */
public class ThankValidationFailureNoOption extends ThankValidationFailure {

  /** {@link #getId() ID} of this failure. */
  public static final String ID = "OPT";

  /**
   * The constructor.
   *
   * @param property the {@link io.github.thanktoken.core.api.field.ThankField#getName() name} of the affected property.
   * @param value the actual value as {@link String}.
   * @param options the {@link Collection} with the valid options for the property value.
   *
   * @param data the optional {@link ThankDataObject}.
   */
  public ThankValidationFailureNoOption(String property, String value, Collection<?> options, ThankDataObject data) {

    super(ID, "Property " + property + " has value " + value + " but expected to be one of: " + options, data, null);
  }

  /**
   * @param <T> type of the value to check.
   * @param <D> type of the {@link ThankDataObject} to validate.
   * @param field the {@link ThankField} to check.
   * @param data the {@link ThankDataObject} to validate.
   * @param options the {@link Collection} with the valid options for the property value.
   * @param receiver the {@link ThankValidationFailureReceiver} that collects potential validation failures.
   */
  public static <T, D extends ThankDataObject> void validate(ThankField<T, D, ?> field, D data, Set<T> options,
      ThankValidationFailureReceiver receiver) {

    T value = field.get(data);
    if (!options.contains(value)) {
      receiver.add(new ThankValidationFailureNoOption(field.getName(), "" + value, options, data));
    }
  }

}
