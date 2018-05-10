package io.github.thanktoken.core.api.strategy;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.config.ThankConfiguration;
import io.github.thanktoken.core.api.header.ThankVersion;
import io.github.thanktoken.core.api.io.ThankReader;
import io.github.thanktoken.core.api.io.ThankWriter;
import io.github.thanktoken.core.api.sign.ThankTokenSigner;
import io.github.thanktoken.core.api.validate.ThankTokenValidator;

/**
 * This is the interface for a strategy that bridges to the implementation of particular {@link ThankVersion}(s).
 */
public interface ThankStrategy {

  /**
   * @return the {@link ThankConfiguration} of this strategy.
   */
  ThankConfiguration getConfiguration();

  /**
   * @return the {@link ThankTokenSigner} used to sign a {@link ThankToken}.
   */
  ThankTokenSigner getSigner();

  /**
   * @return the {@link ThankTokenSigner} used to validate a {@link ThankToken}.
   */
  ThankTokenValidator getValidator();

  /**
   * @return the {@link ThankReader} used to read a {@link ThankToken}.
   */
  ThankReader getReader();

  /**
   * @return the {@link ThankWriter} used to write a {@link ThankToken}.
   */
  ThankWriter getWriter();

}
