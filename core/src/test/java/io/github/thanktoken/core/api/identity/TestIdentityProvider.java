package io.github.thanktoken.core.api.identity;

import io.github.thanktoken.core.api.TestData;
import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.context.ThankTokenContext;
import io.github.thanktoken.core.api.io.ThankNetworkException;

/**
 * TODO hohwille This type ...
 *
 * @since 1.0.0
 */
public class TestIdentityProvider implements ThankIdentityProvider {

  @Override
  public ThankIdentity findIdentity(ThankAddress address, ThankTokenContext context) throws ThankNetworkException {

    if (address == TestData.TEST_KEY_PAIR_NP.getAddress()) {
      return TestData.TEST_KEY_PAIR_NP.getIdentity();
    } else if (address == TestData.TEST_KEY_PAIR_PS.getAddress()) {
      return TestData.TEST_KEY_PAIR_PS.getIdentity();
    }
    return null;
  }

}
