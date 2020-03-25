package io.github.thanktoken.core.api;

import org.junit.jupiter.api.Test;

/**
 * Test of {@link TestData}.
 */
public class TestDataTest extends TestCase {

  /**
   * Test of {@link TestData#TEST_KEY_PAIR_NP}.
   */
  @Test
  public void testKeyPairNp() {

    TestKeyPair keyPair = TEST_KEY_PAIR_NP;
    assertThat(keyPair.getPrivateKeyBinary().formatBase64()).isEqualTo("0ud24ahpdi7jTBqCm3VnpjPr4rMro2+OtH1Dxtw1SUo=");
    assertThat(keyPair.getPublicKeyBinary().formatBase64()).isEqualTo("AxadjvlJKhgifak8cEaiq7FFZLomtWOp2mZhYiLvby+B");
    assertThat(keyPair.getAddress().formatBase64()).isEqualTo("AWRU1YAhnzd5HAMIPxn9P5sADH/Ix8PAmhVInwCEyOep2Q==");
  }

  /**
   * Test of {@link TestData#TEST_KEY_PAIR_PS}.
   */
  @Test
  public void testKeyPairPs() {

    TestKeyPair keyPair = TEST_KEY_PAIR_PS;
    assertThat(keyPair.getPrivateKeyBinary().formatBase64()).isEqualTo("XoIhkBnQdcZamrUUmS/Rfl6WH9hcC3aDq9OdcIC5m0s=");
    assertThat(keyPair.getPublicKeyBinary().formatBase64()).isEqualTo("A4/F8qt0E1E66t8tszATzSXWt2Z9Xde2+oDua9P2xYrG");
    assertThat(keyPair.getAddress().formatBase64()).isEqualTo("AAAKdosVGxJ0BQCao8ofIlfUcXH1lM3HSSw957vaF3lkBw==");
  }

}
