package io.github.thanktoken.core.api;

import org.junit.Test;

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
    assertThat(keyPair.getAddress().formatBase64()).isEqualTo("AlTVgCGfN3kcAwg/Gf0/mwAMf8jHw8CaFUifAITI56nZ");
  }

  /**
   * Test of {@link TestData#TEST_KEY_PAIR_PS}.
   */
  @Test
  public void testKeyPairPs() {

    TestKeyPair keyPair = TEST_KEY_PAIR_PS;
    assertThat(keyPair.getPrivateKeyBinary().formatBase64()).isEqualTo("XoIhkBnQdcZamrUUmS/Rfl6WH9hcC3aDq9OdcIC5m0s=");
    assertThat(keyPair.getPublicKeyBinary().formatBase64()).isEqualTo("A4/F8qt0E1E66t8tszATzSXWt2Z9Xde2+oDua9P2xYrG");
    assertThat(keyPair.getAddress().formatBase64()).isEqualTo("AAp2ixUbEnQFAJqjyh8iV9RxcfWUzcdJLD3nu9oXeWQH");
  }

}
