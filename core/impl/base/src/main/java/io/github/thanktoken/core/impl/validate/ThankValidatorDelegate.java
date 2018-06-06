package io.github.thanktoken.core.impl.validate;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.message.ThankMessage;
import io.github.thanktoken.core.api.strategy.ThankStrategyFactory;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.validate.ThankValidationException;
import io.github.thanktoken.core.api.validate.ThankValidationMode;
import io.github.thanktoken.core.api.validate.ThankValidator;
import io.github.thanktoken.core.impl.ThankDelegate;

/**
 * This is an implementation of {@link ThankValidator} that uses {@link ThankStrategyFactory} in order to validate
 * {@link ThankToken}s for arbitrary {@link ThankHeader#getVersion() versions}.
 */
public class ThankValidatorDelegate extends ThankDelegate implements ThankValidator {

  private static final ThankValidatorDelegate INSTANCE = new ThankValidatorDelegate();

  /**
   * The constructor.
   */
  protected ThankValidatorDelegate() {

    super();
  }

  /**
   * @return the singleton instance of this {@link ThankValidatorDelegate}.
   */
  public static ThankValidatorDelegate get() {

    return INSTANCE;
  }

  @Override
  public void validate(ThankToken token, ThankValidationMode mode) {

    getStrategy(token).getValidator().validate(token, mode);
  }

  @Override
  public void validateHeader(ThankToken token, ThankValidationMode mode) {

    getStrategy(token).getValidator().validateHeader(token, mode);
  }

  @Override
  public void validateTransaction(ThankToken token, ThankTransaction tx, ThankValidationMode mode) {

    getStrategy(token).getValidator().validateTransaction(token, tx, mode);
  }

  @Override
  public void validateMessage(ThankMessage message, ThankValidationMode mode) throws ThankValidationException {

    getStrategyFactory().getStrategy(message.getVersion()).getValidator().validateMessage(message, mode);
  }

}
