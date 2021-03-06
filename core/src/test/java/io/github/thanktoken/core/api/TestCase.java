package io.github.thanktoken.core.api;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.Callable;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.bouncycastle.util.Arrays;

import io.github.mmm.crypto.asymmetric.sign.SignatureBinary;
import io.github.mmm.crypto.asymmetric.sign.SignatureFactory;

/**
 * The abstract base class for test-cases.
 */
public abstract class TestCase extends Assertions implements TestData {

  private static final SecureRandom RANDOM;

  /** Required precision for calculations. */
  protected static final Offset<BigDecimal> PRECISION = Offset.offset(new BigDecimal("0.0000000000000001"));

  /** Required precision for calculations. */
  protected static final Offset<Double> PRECISION_DOUBLE = Offset.offset(Double.valueOf("0.000000000001"));

  static {
    SecureRandom rnd;
    try {
      rnd = SecureRandom.getInstanceStrong();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      rnd = new SecureRandom();
    }
    RANDOM = rnd;
  }

  /**
   * @param lambda the {@link Runnable} to execute that should throw a {@link Throwable}.
   * @param expectedError the {@link Class} reflecting the expected {@link Throwable}.
   * @param message the expected {@link Throwable#getMessage()}.
   */
  public static void shouldThrow(Runnable lambda, Class<? extends Throwable> expectedError, String message) {

    try {
      lambda.run();
      shouldHaveThrown(expectedError);
    } catch (AssertionError e) {
      throw e;
    } catch (Throwable e) {
      assertThat(e).isInstanceOf(expectedError).hasMessage(message);
    }
  }

  /**
   * @param lambda the {@link Callable} to execute that should throw a {@link Throwable}.
   * @param expectedError the {@link Class} reflecting the expected {@link Throwable}.
   * @param message the expected {@link Throwable#getMessage()}.
   */
  public static void shouldThrowException(Callable<?> lambda, Class<? extends Throwable> expectedError,
      String message) {

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

  /**
   * Generic checks of {@link #equals(Object)} and {@link #hashCode()}.
   *
   * @param <T> the generic type of the object to test.
   * @param x the first instance of the object to test.
   * @param y the second instance of the object to test.
   * @param equal - {@code true} if {@code x.equals(y)} is expected, {@code false} otherwise.
   */
  public static <T> void checkEqualsAndHashCode(T x, T y, boolean equal) {

    checkEquals(x, y, equal);
    checkHashCode(x, y);
  }

  /**
   * Generic checks of {@link #equals(Object)}.
   *
   * @param <T> the generic type of the object to test.
   * @param x the first instance of the object to test.
   * @param y the second instance of the object to test.
   * @param equal - {@code true} if {@code x.equals(y)} is expected, {@code false} otherwise.
   */
  public static <T> void checkEquals(T x, T y, boolean equal) {

    // equals has to be reflexive...
    assertThat(x).isNotNull().isNotEqualTo(null).isEqualTo(x);
    assertThat(y).isNotNull().isNotEqualTo(null).isEqualTo(y);
    // equals has to be symmetric...
    assertThat(x).isNotSameAs(y);
    assertThat(x.equals(y)).isEqualTo(y.equals(x)).isEqualTo(equal);
    // equals has to consider type
    assertThat(x).isNotEqualTo(new Object());
  }

  /**
   * Generic checks of {@link #hashCode()}.
   *
   * @param <T> the generic type of the object to test.
   * @param x the first instance of the object to test.
   * @param y the second instance of the object to test.
   */
  public static <T> void checkHashCode(T x, T y) {

    assertThat(x).isNotNull();
    assertThat(y).isNotNull();
    // hashCode() has to be self consistent
    assertThat(x.hashCode()).isEqualTo(x.hashCode());
    assertThat(y.hashCode()).isEqualTo(y.hashCode());
    // hashCode() has to be consistent with equals
    assertThat(x).isNotSameAs(y);
    if (x.equals(y)) {
      assertThat(x.hashCode()).isEqualTo(y.hashCode());
    } else {
      // this one is maybe a little dangerous as there can be a hash collision
      // However, users are requested to provide reasonable arguments to avoid this...
      assertThat(x.hashCode()).isNotEqualTo(y.hashCode());
    }
  }

  public static SignatureBinary toggleRandomBit(SignatureBinary binary) {

    return toggleRandomBit(binary, TEST_ALGORITHM.getSignatureFactory());
  }

  public static SignatureBinary toggleRandomBit(SignatureBinary binary, SignatureFactory<?> signatureFactory) {

    byte[] data = binary.getData();
    data = toggleRandomBit(data);
    SignatureBinary copy = signatureFactory.createSignature(data);
    assertThat(copy.getClass()).isSameAs(binary.getClass());
    return copy;
  }

  public static byte[] toggleRandomBit(byte[] data) {

    assertThat(data).isNotNull().isNotEmpty();
    int length = data.length;
    byte[] copy = Arrays.copyOf(data, length);
    int toggleByteIndex = RANDOM.nextInt(length);
    copy[toggleByteIndex] = toggleRandomBit(data[toggleByteIndex]);
    return copy;
  }

  public static byte toggleRandomBit(byte b) {

    return toggleBit(b, RANDOM.nextInt(8));
  }

  public static byte toggleBit(byte b, int bit) {

    assertThat(bit).isGreaterThanOrEqualTo(0).isLessThan(8);
    int mask = 1 << bit;
    int value = b & 0x0FF;
    int selectedBit = value & mask;
    if (selectedBit == 0) {
      value = value | mask;
    } else {
      value = value & (~mask);
    }
    return (byte) value;
  }

}
