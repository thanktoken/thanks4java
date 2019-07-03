package io.github.thanktoken.core.api.validate.failure;

import java.time.Instant;

import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.field.ThankField;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;
import io.github.thanktoken.core.api.validate.ThankValidationFailureReceiver;

/**
 * {@link ThankValidationFailure} if a date property lies in the future.
 *
 * @since 1.0.0
 */
public class ThankValidationFailureInFuture extends ThankValidationFailure {

  /** {@link #getId() ID} of this failure. */
  public static final String ID = "MIS";

  /**
   * The constructor.
   *
   * @param property the {@link io.github.thanktoken.core.api.field.ThankField#getName() name} of the affected property.
   * @param value the actual value as {@link String}.
   *
   * @param data the optional {@link ThankDataObject}.
   */
  public ThankValidationFailureInFuture(String property, String value, ThankDataObject data) {

    super(ID, "Property " + property + " has value " + value + " that is in the future.", data, null);
  }

  /**
   * @param <D> type of the {@link ThankDataObject} to validate.
   * @param field the {@link ThankField} to check.
   * @param data the {@link ThankDataObject} to validate.
   * @param receiver the {@link ThankValidationFailureReceiver} that collects potential validation failures.
   * @return the actual value.
   */
  public static <D extends ThankDataObject> ThankTimestamp validateTimestamp(ThankField<ThankTimestamp, D, ?> field, D data,
      ThankValidationFailureReceiver receiver) {

    ThankTimestamp value = field.get(data);
    if (value == null) {
      receiver.add(new ThankValidationFailureRequired(field.getName(), data));
    } else {
      if (value.isInFuture()) {
        receiver.add(new ThankValidationFailureInFuture(field.getName(), "" + value, data));
      }
    }
    return value;
  }

  /**
   * @param <D> type of the {@link ThankDataObject} to validate.
   * @param field the {@link ThankField} to check.
   * @param data the {@link ThankDataObject} to validate.
   * @param receiver the {@link ThankValidationFailureReceiver} that collects potential validation failures.
   * @return the actual value.
   */
  public static <D extends ThankDataObject> Instant validateInstant(ThankField<Instant, D, ?> field, D data,
      ThankValidationFailureReceiver receiver) {

    Instant value = field.get(data);
    if (value == null) {
      receiver.add(new ThankValidationFailureRequired(field.getName(), data));
    } else {
      if (value.isAfter(Instant.now())) {
        receiver.add(new ThankValidationFailureInFuture(field.getName(), "" + value, data));
      }
    }
    return value;
  }

}
