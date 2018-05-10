package io.github.thanktoken.core.impl.strategy;

import io.github.thanktoken.core.api.config.ThankConfiguration;
import io.github.thanktoken.core.api.io.ThankReader;
import io.github.thanktoken.core.api.io.ThankWriter;
import io.github.thanktoken.core.api.sign.ThankTokenSigner;
import io.github.thanktoken.core.api.strategy.ThankGlobalStrategy;
import io.github.thanktoken.core.api.validate.ThankTokenValidator;
import io.github.thanktoken.core.impl.config.ThankConfigurationImpl;
import io.github.thanktoken.core.impl.io.ThankReaderDelegate;
import io.github.thanktoken.core.impl.io.ThankWriterDelegate;
import io.github.thanktoken.core.impl.sign.ThankTokenSignerDelegate;
import io.github.thanktoken.core.impl.validate.ThankTokenValidatorDelegate;

/**
 * Implementation of {@link ThankGlobalStrategy}.
 */
public class ThankGlobalStrategyImpl implements ThankGlobalStrategy {

  private final ThankConfiguration configuration;

  /**
   * The constructor.
   */
  public ThankGlobalStrategyImpl() {

    super();
    this.configuration = new ThankConfigurationImpl(null, null, null, null);
  }

  @Override
  public ThankConfiguration getConfiguration() {

    return this.configuration;
  }

  @Override
  public ThankTokenSigner getSigner() {

    return ThankTokenSignerDelegate.get();
  }

  @Override
  public ThankTokenValidator getValidator() {

    return ThankTokenValidatorDelegate.get();
  }

  @Override
  public ThankReader getReader() {

    return ThankReaderDelegate.get();
  }

  @Override
  public ThankWriter getWriter() {

    return ThankWriterDelegate.get();
  }

}
