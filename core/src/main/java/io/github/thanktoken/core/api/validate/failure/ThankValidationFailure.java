package io.github.thanktoken.core.api.validate.failure;

import io.github.thanktoken.core.api.data.ThankDataObject;

/**
 * A single validation failure.
 *
 * @since 1.0.0
 */
public class ThankValidationFailure {

  private final String id;

  private final String message;

  private final ThankDataObject data;

  private final Throwable error;

  /**
   * The constructor.
   *
   * @param id the {@link #getId() ID}.
   * @param message the {@link #getMessage() message}.
   * @param data the {@link #getData() data}.
   * @param error the optional {@link Throwable}.
   */
  public ThankValidationFailure(String id, String message, ThankDataObject data, Throwable error) {

    super();
    this.id = id;
    this.message = message;
    this.data = data;
    this.error = error;
  }

  /**
   * @return id the technical ID of this failure.
   */
  public String getId() {

    return this.id;
  }

  /**
   * @return the description explaining the failure.
   */
  public String getMessage() {

    return this.message;
  }

  /**
   * @return the invalid {@link ThankDataObject}.
   */
  public ThankDataObject getData() {

    return this.data;
  }

  /**
   * @return the optional {@link Throwable} or {@code null} for no catched error.
   */
  public Throwable getError() {

    return this.error;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    toString(sb);
    return sb.toString();
  }

  /**
   * @param sb the {@link StringBuilder} where to append the string representation.
   */
  public void toString(StringBuilder sb) {

    sb.append('#');
    sb.append(this.id);
    sb.append(": ");
    sb.append(this.message);
    if (this.data != null) {
      sb.append('[');
      sb.append(this.data);
      sb.append(']');
    }
    if (this.error != null) {
      sb.append(" - Caused by: ");
      sb.append(this.error);
    }
  }

}
