package io.github.thanktoken.core.api.io;

/**
 * An {@link ThankNetworkException} is thrown if an operation (e.g. validation) failed due to (temporary) network error.
 */
public class ThankNetworkException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   *
   * @param cause is the exception that caused this error.
   */
  public ThankNetworkException(Throwable cause) {

    super("Operation failed due to a (temporary) network error. Check your connection and retry.", cause);
  }

}
