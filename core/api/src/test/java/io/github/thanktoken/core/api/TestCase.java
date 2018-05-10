package io.github.thanktoken.core.api;

import java.util.concurrent.Callable;

import org.assertj.core.api.Assertions;

/**
 * The abstract base class for test-cases.
 */
public abstract class TestCase extends Assertions implements TestData {

  /**
   * @param lambda the {@link Runnable} to execute that should throw a {@link Throwable}.
   * @param expectedError the {@link Class} reflecting the expected {@link Throwable}.
   * @param message the expected {@link Throwable#getMessage()}.
   */
  public static void shouldThrow(Runnable lambda, Class<? extends Throwable> expectedError, String message) {

    try {
      lambda.run();
      shouldHaveThrown(expectedError);
    } catch (Throwable e) {
      assertThat(e).isInstanceOf(expectedError).hasMessage(message);
    }
  }

  /**
   * @param lambda the {@link Callable} to execute that should throw a {@link Throwable}.
   * @param expectedError the {@link Class} reflecting the expected {@link Throwable}.
   * @param message the expected {@link Throwable#getMessage()}.
   */
  public static void shouldThrowException(Callable<?> lambda, Class<? extends Throwable> expectedError, String message) {

    try {
      lambda.call();
      shouldHaveThrown(expectedError);
    } catch (Throwable e) {
      if (e instanceof AssertionError) {
        String msg = expectedError.getSimpleName() + " should have been thrown";
        if (msg.equals(e.getMessage())) {
          throw (AssertionError) e;
        }
      }
      assertThat(e).isInstanceOf(expectedError).hasMessage(message);
    }
  }

}
