package io.github.thanktoken.core.api.repository;

import java.util.Objects;

import io.github.thanktoken.core.api.token.ThankToken;

/**
 * Container for a {@link ThankToken} with its {@link #isValid() validity}.
 *
 * @since 1.0.0
 */
public class ThankTokenContainer {

  private final ThankToken token;

  private Boolean valid;

  /**
   * The constructor.
   *
   * @param token the {@link #getToken() token}.
   */
  public ThankTokenContainer(ThankToken token) {

    super();
    Objects.requireNonNull(token, "token");
    this.token = token;
  }

  /**
   * @return the wrapped {@link ThankToken}.
   */
  public ThankToken getToken() {

    return this.token;
  }

  /**
   * @return {@code true} if valid.
   */
  public boolean isValid() {

    return (this.valid == Boolean.TRUE);
  }

  /**
   * @return {@code true} if invalid.
   */
  public boolean isInvalid() {

    return (this.valid == Boolean.FALSE);
  }

  Boolean getValid() {

    return this.valid;
  }

  /**
   * @param valid2
   */
  void setValid(boolean validity) {

    if (this.valid == null) {
      this.valid = Boolean.valueOf(validity);
    } else if (this.valid.booleanValue() != validity) {
      throw new IllegalStateException(); // can not change your mind!!!
    }

  }

}
