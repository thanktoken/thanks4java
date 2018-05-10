package io.github.thanktoken.core.api.transaction;

/**
 * This is the implementation of {@link ThankTransaction} as Java bean.
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
