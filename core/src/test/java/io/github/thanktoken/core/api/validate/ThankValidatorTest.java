package io.github.thanktoken.core.api.validate;

import org.junit.jupiter.api.Test;

import io.github.thanktoken.core.api.TestCase;
import io.github.thanktoken.core.api.identity.TestIdentityProvider;
import io.github.thanktoken.core.api.identity.ThankIdentityProvider;
import io.github.thanktoken.core.api.repository.ThankTokenRepository;
import io.github.thanktoken.core.api.target.ThankTarget;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.token.ThankTokenType;
import io.github.thanktoken.core.api.transaction.ThankTransactionBean;
import io.github.thanktoken.core.api.validate.failure.ThankValidationFailure;
import io.github.thanktoken.core.api.validate.failure.ThankValidationFailureSignature;

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
    ThankValidationMode mode = ThankValidationMode.FULL
        .setFailureMode(ThankValidationFailureMode.THROW_ON_FIRST_FAILURE);
    // when
    ThankValidationResult validationResult = validator.validate(token, mode);
    // then
    assertThat(validationResult.getFailures()).isEmpty();
    assertThat(validationResult.isValid()).isTrue();
  }

  /**
   * Test of {@link ThankValidator#validate(io.github.thanktoken.core.api.token.ThankToken) validation} with an invalid
   * {@link ThankToken} for {@link ThankTarget#PERSON_INCOME person income}.
   */
  @Test
  public void testValidateTokenP4Invalid() {

    // given
    ThankTransactionBean tx = new ThankTransactionBean(TEST_TOKEN_P4_TX_0);
    tx.setSignature(toggleRandomBit(TEST_TOKEN_P4_TX_0_SIGNATURE));
    ThankToken token = new ThankTokenType(TEST_TOKEN_P4_HEADER).addTransaction(tx);
    ThankValidator validator = getValidator();
    ThankValidationMode mode = ThankValidationMode.FULL.setFailureMode(ThankValidationFailureMode.RETURN_RESULT);
    // when
    ThankValidationResult validationResult = validator.validate(token, mode);
    // then
    assertThat(validationResult.isValid()).isFalse();
    assertThat(validationResult.getFailures()).hasSize(1);
    ThankValidationFailure failure = validationResult.getFailures().get(0);
    assertThat(failure.getId()).as(failure.toString()).isEqualTo(ThankValidationFailureSignature.ID);
  }

}
