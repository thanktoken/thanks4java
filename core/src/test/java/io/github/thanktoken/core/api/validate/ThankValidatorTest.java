package io.github.thanktoken.core.api.validate;

import org.junit.Test;

import io.github.thanktoken.core.api.TestCase;
import io.github.thanktoken.core.api.identity.TestIdentityProvider;
import io.github.thanktoken.core.api.identity.ThankIdentityProvider;
import io.github.thanktoken.core.api.repository.ThankTokenRepository;
import io.github.thanktoken.core.api.target.ThankTarget;
import io.github.thanktoken.core.api.token.ThankToken;

/**
 * Test of {@link ThankValidator} and {@link ThankValidatorImpl}.
 */
public class ThankValidatorTest extends TestCase {

  /**
   * @return the {@link ThankValidator} to test.
   */
  protected ThankValidator getValidator() {

    ThankValidatorImpl validator = new ThankValidatorImpl(getIdentityProvider(), getRepository());
    return validator;
  }

  /**
   * @return the {@link ThankIdentityProvider} to test.
   */
  private ThankIdentityProvider getIdentityProvider() {

    return new TestIdentityProvider();
  }

  /**
   * @return the {@link ThankTokenRepository} to test.
   */
  protected ThankTokenRepository getRepository() {

    return null;
  }

  /**
   * Test of {@link ThankValidator#validate(io.github.thanktoken.core.api.token.ThankToken) validation} with
   * {@link ThankTarget#PERSON_INCOME person income}.
   */
  @Test
  public void testValidateTokenP4() {

    // given
    ThankToken token = TEST_TOKEN_P4;
    ThankValidator validator = getValidator();
    // when
    ThankValidationMode mode = ThankValidationMode.FULL;
    ThankValidationResult validationResult = validator.validate(token, mode);
    // then
    assertThat(validationResult.getFailures()).isEmpty();
    assertThat(validationResult.isValid()).isTrue();
  }

}
