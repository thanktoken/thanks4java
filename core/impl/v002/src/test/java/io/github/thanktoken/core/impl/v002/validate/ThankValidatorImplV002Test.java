package io.github.thanktoken.core.impl.v002.validate;

import net.sf.mmm.crypto.asymmetric.key.SecurityPublicKey;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.ThankTokenBean;
import io.github.thanktoken.core.api.identity.ThankIdentity;
import io.github.thanktoken.core.api.identity.ThankIdentityBean;
import io.github.thanktoken.core.api.identity.ThankIdentityProvider;
import io.github.thanktoken.core.api.identity.ThankIdentityType;
import io.github.thanktoken.core.api.validate.ThankValidationException;
import io.github.thanktoken.core.api.validate.ThankValidationMode;
import io.github.thanktoken.core.api.validate.ThankValidator;
import io.github.thanktoken.core.api.validate.ThankValidatorImpl;
import io.github.thanktoken.core.impl.v002.TestKeyPairsV002;
import io.github.thanktoken.core.impl.v002.strategy.ThankVersionStrategyV002;

/**
 * Test of {@link ThankValidatorImpl}.
 */
public class ThankValidatorImplV002Test extends Assertions implements TestKeyPairsV002 {

  private static final ThankVersionStrategyV002 STRATEGY = new ThankVersionStrategyV002(new TestIdentityProvider());

  private static final ThankToken TEST_TOKEN_P3_WITH_TX = STRATEGY.getReader().read(
      "{\"v\":2,\"a\":\"33.33333333\",\"c\":\"Thanks\",\"ts\":\"19991231235959.9999\",\"loc\":\"DE/HE/412000\",\"tgt\":\"P3\",\"rcp\":\"AjFP+4dtZBXTwGTQd8SSSWq2VRSmsRiPZdlwFx2tzu/v\",\"sig\":\"MEQCIAswGb7NF+Z58XIFY4k/JdgxvAP+AzOf7yEWAzYBgwRYAiAMnQIbYR6XeV9y6Hur9HNTB1tYZbsCA04NwVMhXW0Yqg==\"}",
      "{\"ts\":\"20000101000159.9999\",\"rcp\":\"A0wfOCzxVYtELPk27ELyFuZoIceUXVhCKjQN7JAkfJdm\",\"sig\":\"MEQCIAMNVS0zF7wzlR5+d3dil77B3HbUndBwunIPBDFHxqYyAiAOFJZ+XqOAZN4D/HPeWXhaoN9CdeKSU5U410a2dpgh/g==\"}",
      "{\"ts\":\"20000101000359.9999\",\"rcp\":\"Ajj2WOGKCftSsjSqWH8lJBqpK3kcEYFM9JWLJDZRvMG6\",\"sig\":\"MEQCIAdRRUm/1vy87BNG1+FHncJpngg6HdEDzaEyKRzFnbX9AiANZRiFOYqA8UGXK8+sD2JtOjaOd4Rp+4v/Sji/HwgbIQ==\"}");

  private static final ThankToken TEST_TOKEN_P3_WITHOUT_TX = new ThankTokenBean(TEST_TOKEN_P3_WITH_TX.getHeader());

  /**
   * Tests {@link ThankValidator#validate(io.github.thanktoken.core.api.ThankToken, ThankValidationMode)} with
   * {@code null} as token.
   */
  @Test(expected = ThankValidationException.class)
  public void testInvalidTokenNull() {

    STRATEGY.getValidator().validate(null, ThankValidationMode.MINIMAL);
  }

  /**
   * Tests {@link ThankValidator#validate(io.github.thanktoken.core.api.ThankToken, ThankValidationMode)} with
   * {@code null} as {@link ThankValidationMode}.
   */
  @Test(expected = ThankValidationException.class)
  public void testInvalidModeNull() {

    STRATEGY.getValidator().validate(TEST_TOKEN_P3_WITHOUT_TX, null);
  }

  /**
   * Tests {@link ThankValidator#validate(ThankToken, ThankValidationMode)} with a valid {@link ThankToken}s and
   * {@link ThankValidationMode#MINIMAL}.
   */
  @Test
  public void testValidMinimal() {

    ThankValidator validator = STRATEGY.getValidator();
    validator.validate(TEST_TOKEN_P3_WITHOUT_TX, ThankValidationMode.MINIMAL);
    validator.validate(TEST_TOKEN_P3_WITH_TX, ThankValidationMode.MINIMAL);
  }

  /**
   * Tests {@link ThankValidator#validate(io.github.thanktoken.core.api.ThankToken, ThankValidationMode)} with with
   * valid {@link ThankToken}s and {@link ThankValidationMode#MINIMAL}.
   */
  @Test
  public void testValidFull() {

    ThankValidator validator = STRATEGY.getValidator();
    validator.validate(TEST_TOKEN_P3_WITHOUT_TX, ThankValidationMode.FULL);
    validator.validate(TEST_TOKEN_P3_WITH_TX, ThankValidationMode.FULL);
  }

  /**
   * Tests {@link ThankValidator#validate(io.github.thanktoken.core.api.ThankToken, ThankValidationMode)} with valid
   * {@link ThankToken}s and {@link ThankValidationMode#OFFLINE}.
   */
  @Test
  public void testValidOffline() {

    ThankValidator validator = STRATEGY.getValidator();
    validator.validate(TEST_TOKEN_P3_WITHOUT_TX, ThankValidationMode.OFFLINE);
    validator.validate(TEST_TOKEN_P3_WITH_TX, ThankValidationMode.OFFLINE);
  }

  private static class TestIdentityProvider implements ThankIdentityProvider {

    @Override
    public ThankIdentity findIdentity(SecurityPublicKey publicKey) {

      if (KEY_PERSON_1.getPublicKey().equals(publicKey)) {
        return id(publicKey, ThankIdentityType.NATURAL_PERSON);
      } else if (KEY_PERSON_2.getPublicKey().equals(publicKey)) {
        return id(publicKey, ThankIdentityType.NATURAL_PERSON);
      } else if (KEY_COUNTRY_DE.getPublicKey().equals(publicKey)) {
        return id(publicKey, ThankIdentityType.REPRESENTATIVE_L1);
      } else if (KEY_STATE.getPublicKey().equals(publicKey)) {
        return id(publicKey, ThankIdentityType.REPRESENTATIVE_L2);
      } else if (KEY_COMMUNITY.getPublicKey().equals(publicKey)) {
        return id(publicKey, ThankIdentityType.REPRESENTATIVE_L3);
      } else if (KEY_ORGANIZATION_1.getPublicKey().equals(publicKey)) {
        return id(publicKey, ThankIdentityType.ORGANIZATION);
      } else if (KEY_SUSTAINANILITY_1.getPublicKey().equals(publicKey)) {
        return id(publicKey, ThankIdentityType.SUSTAINABILITY);
      } else {
        return null;
      }
    }

    private ThankIdentityBean id(SecurityPublicKey publicKey, ThankIdentityType type) {

      return new ThankIdentityBean().setId(publicKey.getBase64()).setLatestAddress(publicKey).setType(type);
    }
  }

}
