package io.github.thanktoken.core.api.transaction;

/**
 * This is the abstract base implementation of {@link ThankTransaction}.
 */
public abstract class AbstractThankTransaction implements ThankTransaction {

  /**
   * The constructor.
   */
  public AbstractThankTransaction() {

    super();
  }

  @Override
  public String toString() {

    return "TX@" + getTimestamp();
  }

}
