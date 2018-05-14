package io.github.thanktoken.core.impl.v001.validate;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.ThankTokenBean;
import io.github.thanktoken.core.api.identity.ThankIdentity;
import io.github.thanktoken.core.api.identity.ThankIdentityBean;
import io.github.thanktoken.core.api.identity.ThankIdentityProvider;
import io.github.thanktoken.core.api.identity.ThankIdentityType;
import io.github.thanktoken.core.api.validate.ThankTokenValidationException;
import io.github.thanktoken.core.api.validate.ThankTokenValidator;
import io.github.thanktoken.core.api.validate.ThankValidationMode;
import io.github.thanktoken.core.impl.v001.TestKeyPairs;
import io.github.thanktoken.core.impl.v001.strategy.ThankVersionStrategyV001;
import io.github.thanktoken.core.impl.validate.ThankTokenValidatorImpl;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;

/**
 * Test of {@link ThankTokenValidatorImpl}.
 */
public class ThankTokenValidatorImplV001Test extends Assertions {

  private static final ThankVersionStrategyV001 STRATEGY = new ThankVersionStrategyV001(new TestIdentityProvider());

  private static final ThankToken TEST_TOKEN_P3_WITH_TX = STRATEGY.getReader().read(
      "{\"v\":1,\"a\":\"33.33333333\",\"c\":\"Thanks\",\"ts\":\"19991231235959.9999\",\"loc\":\"DE/HE/412000\",\"tgt\":\"P3\",\"rcp\":\"MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAgqpNsDETYrzJXhrg94nXqTTxP6C3wf7oPONH6FdH+DP7E8gq/f4IG0QHaEvx6Y6iu9iiPMCDPFGRqplemGJTUSGWbWUHbWEIzYQwUmdqDWPIb4Lpdouw9K71iTmkP0+kDZiW+H04R7nA/DS2tmKXnADENhGzPHoeflLlR42950fHP/tfVYJ2PNiuY1k9QOVs64jFay9QzJ5nKxokz6M1gZqqSrBOUjK00L7v4AFpIiqKRoXmEuFl1NCuL13llHIKuGH5aoq0cb4DV5Y/GyJtuNYxAnU9IcQI9m9VlR1cB5tsdjT8Uox9zp656zjjIDeKqQnuSOSbN3DyG23PlLyERRGjH4osZJ1BAGMU8mHqRP3nQYuQdN2LTo20oufokKl0DVeePiT7OyyITmlFpJyuC8z5zMX8y6I+s98iPgX+QPccHf0cB9vuYzYv5Jrzey4snZKsUhSOzM3jjC9f9qKhAswwN/h0HDyn96H/GR7VtSrNt+n3z8tLuS0oCdF3eFvZxeHELf9obK/NHpvVCNttgomPm5Avf3ehT5Z5U0qwKs0a+/H9iehWMeBkGyjCaZWKJNqOAlneUxhgl744XU8lMcFj5Z2TQrpHADQ1LZcWR95btGm8zsPQW30a3Wv8FKUTmN2yryGALJWg1cM458sulGEPPuNgxha1a5Pff8dRJR0CAwEAAQ==\",\"sig\":\"Ezd3ECFfIKAi8hcpA1BWxevXN/piZaMLzUWnLxAygvL+/0ZZLUKFkpc4HVrLwORineniaQY/Xul8zTzfExJiIrLe1MTegR6AarItG3zbkQ+UvciUn/xvjN6qufyb3E4bUArlIb1ax5ptU9ebgQgYQHfTVGT6rIlpeJKu5V3T8Jsni5FcPUXFQy/UhaDYOdejoCJ0kfkHThvrALMQJBrZ52pZ06LIc77m5QZXQmOku6sqjRVIcW1i3W4CHRUmyo4vRGyaCdEn75UZ4w4XCcQtIxWzhQOBrubs+L9KqTpyG93EOeLT/honrKU1rHf/t0pJkYEmv+tdkxw625LgAKEDpSUOV4t46Rir/fghAWUq6t5JN1KuBujEigMQXhclYxpGF9gHXgjpsA8wB1AnOYV3bAk1/c6E/qWudYUiHqpNuLeD8Gj/EU643WtSmdQUuGQJZ5V0FAy6W0gZ9Dg/8jW+u4MVknEyaLrqouWhlT8ebsZann1isoIy2ikUf6Q1ba3d3tfN/UP3DbfvBFdjnVPFwW0hxzcgnJ9n2altRPo3xt5KzodW3cMR1sDgndKmuAxArdVaiYlN0um2o5kGLF9FJq+jiei4VkgTXuirea4v6n/qXexdU+EgGSwAV0DqGN8pIsuHcFVU0bwxdYoCRL3SGcdrpmqrjzpaWvf4ww3z5Lg=\"}",
      "{\"ts\":\"20000101000159.9999\",\"rcp\":\"MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAszxgTffw0h/c/kD9+xB963P7J8gMTCXG19+UO4oQzdowAccwnG5SEsSNuTQ9a+p0rSxJJbZ+Knni8/bwIVPrGOnfn47a6riE2HD+xpk+yb/yjw2jDcw/gS3MhY1NcDUeiIZ+wloLWpb/FNUHd0KyEPsQprT6Ek8NzUdRu+BeVmkHhCWDp6TfIMI1jgWHI1VvtfawBtjfD3hw9+cjDV2szzhE60Okfeh+1ESPByhi0CJhJ/HnvQIHQSgw7JxorDU091YOLl92VaCvX6kED06LqOCBdjTt4SYhWRb4uXX/3ysAuPBcWs/eaxbsipxcC0LKwC8de8ATKoAJ27yiX8lJDN+TC1bFuHAoShy4zgPphyrnha+DIpQWG7KzaVb3dEmPozcBVdg5KjW/CBY369B3BqJAQxd1HzhiH2qwzwy7JDTU41du/j+L+H7MR2uXvJh2/rVyXa0rH11HGE5dGTxvysuADxRcqXKU8ohni1wntS5BKzZEjr4ggQiTaDYXDE9P1czOa6+5nYA7vJEXqWKJi831ulssAjEuQMoUo/9SCJChfEnCyjMhvrYcHqhoPsRmcCtw0fkraxY0ngytf2NrYiOzhsV2W5GoqpTT2tXtdAAWYY8RYTDldo3FBcdOQYmOQ6g4rLhtoxeBADB9uySjhSTpJcOB+awkmvEnRlQqV2sCAwEAAQ==\",\"sig\":\"SMDsyxT4nUMETWtTr5WF8hwpLahjXeYdODafrIDWQkbvSQWP6prJDO4+IxWbE83tF4u0YiiX8fF02/LF27jH1bQSuMWuj5h8XDPtD1rrtPR5fa2t1jTPz6lthR0gwPchIN6WTs7MKq4KZnIWLCb4DMDy+NbLF68OtRGVz/tZtuNuFMgPGOzndJ63Rn0aMud64lb7i9rRzGCh8xbxo6GEpHVodNeWXnQqfNXkMEbh+Ik5r8l2Q/MF8kgNmCTyVghgqEAexlKedC5qW1lYhi2aV260FRkbzKhqBAQmRXEpvxVTPuT82GQR1cGTkSzWuCteR45056VWxFW7IgnAtqp7zUdRCFP70IFfrB07bo3Q9lCIcJeBaGif73EHZwXX7AYiC6teD392SfoJIQ3EJf5KwTNAZosVnU8FVuIixTMIRiwWNf5uiwzobr0hyVTAtzj7IUIJCsh7KmykXT3sKaNSPfYh2uLaWIrlnEMj4bkD2O9ZY8HINGWKGGgF8FAYzGHWb/kVgG0ZwdqV4CGR1E7Il/cPz2BV5FDEoKS6Z8cnx358rykf4yxT5vt5fAIkJlXbqLQzWuNcnDbCD8nIqZOdmvHcJ3QG8sbFgsFhIUKTPCH9+iugzrZgek+i7yTPtyAGTeEYPuoKjqwyoQnZHctmORSRv8wUsHUKcM0TO6lhfBc=\"}",
      "{\"ts\":\"20000101000359.9999\",\"rcp\":\"MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAnZgf9xF6P1HhlSZWtaaMU4k6xqpCzkWLS9A7ituUvIJemAdnNyLps7/6ICwF2amq5T9LVHnXaMokzAa6hApQnEc03trqxCvkD//NuNWRR7oXOloi3Nm4JolcfZdN7GdVtJoq2fZSsSnO4Nd0JwgqjZC9HlPJWXssdy1lFMX76IcJSEWeCuB9bJFKiGG9xuUsdNxjMcdPT04HKByU1Ik00WOdbxHK5KTgnBFGdXjGZSrx2HSF2wLeA44+ojqGlkXkmpO/xeVdZgM0x7QIqnOMgSByBpSzX1fJ1RnSMtyN55KK0qxrtp+KIxd55ChmnGB/vYcugVCHX8IzUyYUXVMUkYLnJB3nQiaTCjOias3wlJ24b1k7q6aIpDUiNvN8JJvfNjip/2fFmdBbtOWO6GrUAcd4JSfCoN+Wxts5UPcQ92P2y1/XCDNL1pobtAelr84VeDZ4BHjjJsj3FGLyMkR0Fqu+4UvCRb8PR/r84NxiPQy3mzfgQRFSIoydw3xwGWwECxu2b/uzqeASKY+wp3QfOu3oY8bu6MN7+Is0BcHpvqQJK3/n9yAyYNiZYw3AnNakMFwmLVLgk5nJVZnBRWBrxvS0AsZCIYbNSRPRROZkTc1zgfFCOwb+9nrJj+QPRfAfsXe8iA4F6MGuqfRKrn4QmCypWBDbLntrK7DKveENgtECAwEAAQ==\",\"sig\":\"a2VbtQdfWTnM9q8dLPF2u05TaWLLn1uRoXxIc8rbij9eMg4yMTLScg+aEepM0FoV58SjYcsMzK+dbd1VBJ/gHxDivK3ksW1qa2mT+2Q/w/3bZ3vraJfhhnK9gNzJMNLl0gR0oDAXkkl75dA6p16/7rP4QB+hldYNFMDy3z6LQ2UnnXfnDrmbB8No+YS9+xTB3VXatwrTqgql6mtM3g44DkpQk4Msw8IG8b2uPcDu6SWXIHGHQMWrvPd/M5yp71plO9YqMry8S06uGttGOJLnd0jye4nwPFqqr42Tnh5ijqTsfKdV6ZO68JFUxAb+oddfHQL360jFdY5UgXyJk8bW3GScAqOYoHGTkrnlJE+bJw1lpddKsIfdeoXLfMhlm/MUtChdXf2A7wWSsAidRRSF+tFc1Yqp0Ayym3aTrige0cm3ZtlkKvB4mPcVRLvy2kREkIAT4dkzc2r+BNqRQipXtFt0HFFd2VBdohu9fH39gDkz9ILcQZmyK4d5AL3jdVB3oz4VQm2JCQqVNzWis5Fvp4L64FBjDMcnf7mObgQ49q1Q1F+uUnTN495fZT/WBmXCDRTKyLNBYjqr8lKA/4zzRKnfJWPPE+XWemGv22TFChu+uReQOpt2tjVQJbx3srNjBJTOdZBquO2IBnDBilb9twCAeeBBm9/wUCRUy7LtC1M=\"}");

  private static final ThankToken TEST_TOKEN_P3_WITHOUT_TX = new ThankTokenBean(TEST_TOKEN_P3_WITH_TX.getHeader());

  /**
   * Tests {@link ThankTokenValidator#validate(io.github.thanktoken.core.api.ThankToken, ThankValidationMode)} with
   * {@code null} as token.
   */
  @Test(expected = ThankTokenValidationException.class)
  public void testInvalidTokenNull() {

    STRATEGY.getValidator().validate(null, ThankValidationMode.MINIMAL);
  }

  /**
   * Tests {@link ThankTokenValidator#validate(io.github.thanktoken.core.api.ThankToken, ThankValidationMode)} with
   * {@code null} as {@link ThankValidationMode}.
   */
  @Test(expected = ThankTokenValidationException.class)
  public void testInvalidModeNull() {

    STRATEGY.getValidator().validate(TEST_TOKEN_P3_WITHOUT_TX, null);
  }

  /**
   * Tests {@link ThankTokenValidator#validate(ThankToken, ThankValidationMode)} with a valid {@link ThankToken}s and
   * {@link ThankValidationMode#MINIMAL}.
   */
  @Test
  public void testValidMinimal() {

    ThankTokenValidator validator = STRATEGY.getValidator();
    validator.validate(TEST_TOKEN_P3_WITHOUT_TX, ThankValidationMode.MINIMAL);
    validator.validate(TEST_TOKEN_P3_WITH_TX, ThankValidationMode.MINIMAL);
  }

  /**
   * Tests {@link ThankTokenValidator#validate(io.github.thanktoken.core.api.ThankToken, ThankValidationMode)} with with
   * valid {@link ThankToken}s and {@link ThankValidationMode#MINIMAL}.
   */
  @Test
  public void testValidFull() {

    ThankTokenValidator validator = STRATEGY.getValidator();
    validator.validate(TEST_TOKEN_P3_WITHOUT_TX, ThankValidationMode.FULL);
    validator.validate(TEST_TOKEN_P3_WITH_TX, ThankValidationMode.FULL);
  }

  /**
   * Tests {@link ThankTokenValidator#validate(io.github.thanktoken.core.api.ThankToken, ThankValidationMode)} with
   * valid {@link ThankToken}s and {@link ThankValidationMode#OFFLINE}.
   */
  @Test
  public void testValidOffline() {

    ThankTokenValidator validator = STRATEGY.getValidator();
    validator.validate(TEST_TOKEN_P3_WITHOUT_TX, ThankValidationMode.OFFLINE);
    validator.validate(TEST_TOKEN_P3_WITH_TX, ThankValidationMode.OFFLINE);
  }

  private static class TestIdentityProvider implements ThankIdentityProvider {

    @Override
    public ThankIdentity findIdentity(SecurityPublicKey publicKey) {

      if (TestKeyPairs.KEY_PERSON_1.getPublicKey().equals(publicKey)) {
        return id(publicKey, ThankIdentityType.NATURAL_PERSON);
      } else if (TestKeyPairs.KEY_PERSON_2.getPublicKey().equals(publicKey)) {
        return id(publicKey, ThankIdentityType.NATURAL_PERSON);
      } else if (TestKeyPairs.KEY_COUNTRY_DE.getPublicKey().equals(publicKey)) {
        return id(publicKey, ThankIdentityType.REPRESENTATIVES_L0);
      } else if (TestKeyPairs.KEY_STATE.getPublicKey().equals(publicKey)) {
        return id(publicKey, ThankIdentityType.REPRESENTATIVES_L1);
      } else if (TestKeyPairs.KEY_COMMUNITY.getPublicKey().equals(publicKey)) {
        return id(publicKey, ThankIdentityType.REPRESENTATIVES_L2);
      } else if (TestKeyPairs.KEY_ORGANIZATION_1.getPublicKey().equals(publicKey)) {
        return id(publicKey, ThankIdentityType.ORGANIZATION);
      } else if (TestKeyPairs.KEY_SUSTAINANILITY_1.getPublicKey().equals(publicKey)) {
        return id(publicKey, ThankIdentityType.SUSTAINABILITY);
      } else {
        return null;
      }
    }

    private ThankIdentityBean id(SecurityPublicKey publicKey, ThankIdentityType type) {

      return new ThankIdentityBean().setId(publicKey.getBase64()).setLatestPublicKey(publicKey).setType(type);
    }
  }

}
