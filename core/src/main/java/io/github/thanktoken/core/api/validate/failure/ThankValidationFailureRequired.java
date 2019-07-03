package io.github.thanktoken.core.api.validate.failure;

import java.util.Collection;

import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.field.ThankField;
import io.github.thanktoken.core.api.field.ThankFieldMap;
import io.github.thanktoken.core.api.validate.ThankValidationFailureReceiver;

/**
 * {@link ThankValidationFailure} if an object or property is mandatory but was {@code null} or empty.
 *
 * @since 1.0.0
 */
public class ThankValidationFailureRequired extends ThankValidationFailure {

  /** {@link #getId() ID} of this failure. */
  public static final String ID = "REQ";

  /**
   * The constructor.
   *
   * @param name the name of the mandatory object or property that is {@code null} or empty.
   * @param data the optional {@link ThankDataObject}.
   */
  public ThankValidationFailureRequired(String name, ThankDataObject data) {

    super(ID, "Required value " + name + " is missing.", data, null);
  }

  /**
   * @param <D> type of the {@link ThankDataObject} to validate.
   * @param fieldMap the {@link ThankFieldMap} with the fields of the object to validate.
   * @param data the {@link ThankDataObject} to validate.
   * @param receiver the {@link ThankValidationFailureReceiver} that collects potential validation failures.
   */
  public static <D extends ThankDataObject> void validateAll(ThankFieldMap<D, ?, ?> fieldMap, D data,
      ThankValidationFailureReceiver receiver) {

    for (ThankField<?, D, ?> field : fieldMap) {
      if (field.isMandatory()) {
        validate(field, data, receiver);
      }
    }
  }

  /**
   * @param <T> type of the value to check.
   * @param <D> type of the {@link ThankDataObject} to validate.
   * @param field the {@link ThankField} to check.
   * @param data the {@link ThankDataObject} to validate.
   * @param receiver the {@link ThankValidationFailureReceiver} that collects potential validation failures.
   * @return the actual value that has been checked.
   */
  public static <T, D extends ThankDataObject> T validate(ThankField<T, D, ?> field, D data, ThankValidationFailureReceiver receiver) {

    T value = field.get(data);
    if (isEmpty(value)) {
      receiver.add(new ThankValidationFailureRequired(field.getName(), data));
    }
    return value;
  }

  private static boolean isEmpty(Object value) {

    if (value == null) {
      return true;
    }
    if (value instanceof String) {
      return ((String) value).isEmpty();
    }
    if (value instanceof Collection) {
      return ((Collection<?>) value).isEmpty();
    }
    return false;
  }

}
