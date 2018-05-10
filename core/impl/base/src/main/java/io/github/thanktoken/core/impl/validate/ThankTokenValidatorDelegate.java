package io.github.thanktoken.core.impl.validate;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.strategy.ThankStrategyFactory;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.validate.ThankTokenValidationException;
import io.github.thanktoken.core.api.validate.ThankTokenValidator;
import io.github.thanktoken.core.api.validate.ThankValidationMode;
import io.github.thanktoken.core.impl.ThankDelegate;

/**
 * This is an implementation of {@link ThankTokenValidator} that uses {@link ThankStrategyFactory} in order to validate
 * {@link ThankToken}s for arbitrary {@link ThankHeader#getVersion() versions}.
 */
public class ThankTokenValidatorDelegate extends ThankDelegate implements ThankTokenValidator {

  private static final ThankTokenValidatorDelegate INSTANCE = new ThankTokenValidatorDelegate();

  /**
   * The constructor.
   */
  protected ThankTokenValidatorDelegate() {

    super();
  }

  /**
   * @return the singleton instance of this {@link ThankTokenValidatorDelegate}.
   */
  public static ThankTokenValidatorDelegate get() {

    return INSTANCE;
  }

  @Override
  public void validate(ThankToken token, ThankValidationMode mode) throws ThankTokenValidationException {

    getStrategy(token).getValidator().validate(token, mode);
  }

  @Override
  public void validateHeader(ThankToken token, ThankValidationMode mode) throws ThankTokenValidationException {

    getStrategy(token).getValidator().validateHeader(token, mode);
  }

  @Override
  public void validateTransaction(ThankToken token, ThankTransaction tx, ThankValidationMode mode) throws ThankTokenValidationException {

    getStrategy(token).getValidator().validateTransaction(token, tx, mode);
  }

}
