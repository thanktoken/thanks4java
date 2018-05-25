package io.github.thanktoken.core.api.strategy;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.config.ThankConfiguration;
import io.github.thanktoken.core.api.header.ThankVersion;
import io.github.thanktoken.core.api.io.ThankReader;
import io.github.thanktoken.core.api.io.ThankWriter;
import io.github.thanktoken.core.api.sign.ThankSigner;
import io.github.thanktoken.core.api.validate.ThankValidator;

/**
 * This is the interface for a strategy that bridges to the implementation of particular {@link ThankVersion}(s).
 */
public interface ThankStrategy {

  /**
   * @return the {@link ThankConfiguration} of this strategy.
   */
  ThankConfiguration getConfiguration();

  /**
   * @return the {@link ThankSigner} used to sign a {@link ThankToken}.
   */
  ThankSigner getSigner();

  /**
   * @return the {@link ThankSigner} used to validate a {@link ThankToken}.
   */
  ThankValidator getValidator();

  /**
   * @return the {@link ThankReader} used to read a {@link ThankToken}.
   */
  ThankReader getReader();

  /**
   * @return the {@link ThankWriter} used to write a {@link ThankToken}.
   */
  ThankWriter getWriter();

}
