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
      "{\"v\":1,\"a\":33.33,\"c\":\"Thanks\",\"ts\":\"19991231235959.9999\",\"loc\":\"DE/HE/412000\",\"tgt\":\"P3\",\"rcp\":\"MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAgqpNsDETYrzJXhrg94nXqTTxP6C3wf7oPONH6FdH+DP7E8gq/f4IG0QHaEvx6Y6iu9iiPMCDPFGRqplemGJTUSGWbWUHbWEIzYQwUmdqDWPIb4Lpdouw9K71iTmkP0+kDZiW+H04R7nA/DS2tmKXnADENhGzPHoeflLlR42950fHP/tfVYJ2PNiuY1k9QOVs64jFay9QzJ5nKxokz6M1gZqqSrBOUjK00L7v4AFpIiqKRoXmEuFl1NCuL13llHIKuGH5aoq0cb4DV5Y/GyJtuNYxAnU9IcQI9m9VlR1cB5tsdjT8Uox9zp656zjjIDeKqQnuSOSbN3DyG23PlLyERRGjH4osZJ1BAGMU8mHqRP3nQYuQdN2LTo20oufokKl0DVeePiT7OyyITmlFpJyuC8z5zMX8y6I+s98iPgX+QPccHf0cB9vuYzYv5Jrzey4snZKsUhSOzM3jjC9f9qKhAswwN/h0HDyn96H/GR7VtSrNt+n3z8tLuS0oCdF3eFvZxeHELf9obK/NHpvVCNttgomPm5Avf3ehT5Z5U0qwKs0a+/H9iehWMeBkGyjCaZWKJNqOAlneUxhgl744XU8lMcFj5Z2TQrpHADQ1LZcWR95btGm8zsPQW30a3Wv8FKUTmN2yryGALJWg1cM458sulGEPPuNgxha1a5Pff8dRJR0CAwEAAQ==\",\"sig\":\"bSMVtOS9HNvu+fsV/Ug1ZCYNhVJMOXxvZa1qjAJYQt4tSuxhKZtI4ngdXyr21QjQDRFzm2BKUye3OXcGcWGFpfL1rglplA2iWuYWyp+fiDC1U1iYxUZ+dM1GF1ZEfBXCAJhCi/C44ntv6/bGrPO7leRIWycLfLFMVyP/SG8MM1/7w7xRwopjRP2JWJj4yX6Ng868JNbqIsuO66A5XVPOalUraX+xHdw7/vUiMxdK+FtX5iQ23fGNaFVdjq1GlN0lIfZIz/Q3O+VlaZIvwj89G/1hIl6QAzq0K92VI9+r5XEXhlyz4Pro6RAFNnnH1S5YfXl7Unyu+Ii7a6cd2X0LgWTbsumOjZGNDa3hmDZ4aRsOhXop5C5d/qi33p4CbFNkVyLpt+fKX3znV+G4FcnGqsCmFdGszMvceCCYfT49MbMEZ9Q/LVjJH3+8qO65rIt7rbGUURow/LLP2Zv7wjMjsVirl9yu0J35jjitYqr7esW2b4vjd1Nk1DsLt5e0DQ8BK9MlpfGgz97oIJumyEbXAVfhixPVGqmo/KVVe3RMaNpIGmmDO0c+obWYshtZKbb1Lo9YI0nLyU5hCsGlV/S+dpi6nYD9acpCBZ/bFa+K7BUn6l7MFKwvhsouIoUDRmYhZk8JO9Sv/c4fXpKYAWT6M/louERlGH6EyVzmBL9NybI=\"}",
      "{\"ts\":\"20000101000159.9999\",\"rcp\":\"MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAszxgTffw0h/c/kD9+xB963P7J8gMTCXG19+UO4oQzdowAccwnG5SEsSNuTQ9a+p0rSxJJbZ+Knni8/bwIVPrGOnfn47a6riE2HD+xpk+yb/yjw2jDcw/gS3MhY1NcDUeiIZ+wloLWpb/FNUHd0KyEPsQprT6Ek8NzUdRu+BeVmkHhCWDp6TfIMI1jgWHI1VvtfawBtjfD3hw9+cjDV2szzhE60Okfeh+1ESPByhi0CJhJ/HnvQIHQSgw7JxorDU091YOLl92VaCvX6kED06LqOCBdjTt4SYhWRb4uXX/3ysAuPBcWs/eaxbsipxcC0LKwC8de8ATKoAJ27yiX8lJDN+TC1bFuHAoShy4zgPphyrnha+DIpQWG7KzaVb3dEmPozcBVdg5KjW/CBY369B3BqJAQxd1HzhiH2qwzwy7JDTU41du/j+L+H7MR2uXvJh2/rVyXa0rH11HGE5dGTxvysuADxRcqXKU8ohni1wntS5BKzZEjr4ggQiTaDYXDE9P1czOa6+5nYA7vJEXqWKJi831ulssAjEuQMoUo/9SCJChfEnCyjMhvrYcHqhoPsRmcCtw0fkraxY0ngytf2NrYiOzhsV2W5GoqpTT2tXtdAAWYY8RYTDldo3FBcdOQYmOQ6g4rLhtoxeBADB9uySjhSTpJcOB+awkmvEnRlQqV2sCAwEAAQ==\",\"sig\":\"UtKP59rBfsa/odsgo/FO/cmJokHONH5pkGDclgJWQ/8gIp9kHbvDJpSHUaWz1FkXN9bmXq1ly7oYfkWpTNzgDN106hCa2Iz1mlnGByJy9V3dDfVmFvy2iHjsly1bV9tjzvq+03eyyrMq5p2qDBvSkjHoKexNWLmlbXpPmQbDqaeLbHX9/ED+A393LcK+HQrzDZYy4lEDyMz604/V7NKFXM3DDnJ2SoMCcFPI4yH5M4+nzJTwWPkUKZ8CypGOWN6DFVKDCLzzpEO+XgpC7dYCaqhCuay8Y6SDrbaXWyTDweJ3z36j5s630RYc3Er4wV3IY6CvahOs4tB2ppTNzaQ5B0sd9oUiEBXQWufJ+doki3NfWHR8WfyuckrCgMHdQub308mfPNyc+wH70OUh/2bmTudEzrpBZzSg2iBWXgAN6QA87ZTJYL/QCklluMw6lTRrJ+onMMLgpsihTKF6QQHP6lc8B0gsw3idSJP8UIa1k3bDW+YVzP3bUoGIPy/3NS9MuRwY5t59IL4cLirtghBNIUkyRT2AQor8Mefk7VomGuPr0siQnShQ/k8yDp6uJu7I46rO6zhY9nY35otuoSwzYloKBk/dlhRDOilJ4f/pvX5LI/Budi1/5q/9uiOPqzEJ6zpYSzjC3Ygxe0hTXrL4jWgY32SP0EWn5gLESiY03kc=\"}",
      "{\"ts\":\"20000101000359.9999\",\"rcp\":\"MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAnZgf9xF6P1HhlSZWtaaMU4k6xqpCzkWLS9A7ituUvIJemAdnNyLps7/6ICwF2amq5T9LVHnXaMokzAa6hApQnEc03trqxCvkD//NuNWRR7oXOloi3Nm4JolcfZdN7GdVtJoq2fZSsSnO4Nd0JwgqjZC9HlPJWXssdy1lFMX76IcJSEWeCuB9bJFKiGG9xuUsdNxjMcdPT04HKByU1Ik00WOdbxHK5KTgnBFGdXjGZSrx2HSF2wLeA44+ojqGlkXkmpO/xeVdZgM0x7QIqnOMgSByBpSzX1fJ1RnSMtyN55KK0qxrtp+KIxd55ChmnGB/vYcugVCHX8IzUyYUXVMUkYLnJB3nQiaTCjOias3wlJ24b1k7q6aIpDUiNvN8JJvfNjip/2fFmdBbtOWO6GrUAcd4JSfCoN+Wxts5UPcQ92P2y1/XCDNL1pobtAelr84VeDZ4BHjjJsj3FGLyMkR0Fqu+4UvCRb8PR/r84NxiPQy3mzfgQRFSIoydw3xwGWwECxu2b/uzqeASKY+wp3QfOu3oY8bu6MN7+Is0BcHpvqQJK3/n9yAyYNiZYw3AnNakMFwmLVLgk5nJVZnBRWBrxvS0AsZCIYbNSRPRROZkTc1zgfFCOwb+9nrJj+QPRfAfsXe8iA4F6MGuqfRKrn4QmCypWBDbLntrK7DKveENgtECAwEAAQ==\",\"sig\":\"E0gyvfWEpCSGun6cpkFeoennbkAwhPy956KAcWH+xDcedc2ah91OkBSaSE93kzTPKHK3YAaYVtsM9lDW4pm2cyp1cEWqW1WLEv2lcRzmiug8KZLT+oF4jWb4Sl0yzg7Sr8Ax0X2jpjYw8qVsDE0A8ZOrVUftQHgBEze2ko2D8+p6VkWYq3i3LT2Jl6zxzSt0Rw5SuDT/qXkoYXN5S2Nmz1aIfRKFRpjDnB3VkIYGgaEUQ5/hYoSwx2hZk3RchCdCvVDFwFtVDeurJ6ejjUuZKIOuzgNgBnk0fLhnoGruTFQL3dQsan9MF0XHesz3pLHGC7Kvji+ZKwHIw5MJblPayKCb7G+FG2qJagzHkT2mIEemNKIMvQx6YV+ZuWMYzgMUYUtL3SNUeZMt3PPAjiKRjY3Fl0QI4h+8w/TRNQHp4am1g6+nLfxRovknfT053JjHpx95yZlb9OsLwnCFdb4C0/nVVfoOrytReujuHBWqxMhkArbYLXLLYi/KMtE1PLWcxY1vwXX8emMejMXIK1YbhreETAqxSFWvBThOnnfScT8YpNuNJM7C9IB/LIIMWVEPmJjvMUHTRnLQaDh2Guq79l8X7REwcUi4Je3rjdQIsLRfQsj03LiNnPdzQKLmsPVNZzRanlD9dL/g4fDd/r/zBD8hxHRzlme373Cx13C8tKU=\"}");

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
