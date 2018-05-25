package io.github.thanktoken.core.impl.strategy;

import io.github.thanktoken.core.api.config.ThankConfiguration;
import io.github.thanktoken.core.api.io.ThankReader;
import io.github.thanktoken.core.api.io.ThankWriter;
import io.github.thanktoken.core.api.sign.ThankSigner;
import io.github.thanktoken.core.api.strategy.ThankGlobalStrategy;
import io.github.thanktoken.core.api.validate.ThankValidator;
import io.github.thanktoken.core.impl.config.ThankConfigurationImpl;
import io.github.thanktoken.core.impl.io.ThankReaderDelegate;
import io.github.thanktoken.core.impl.io.ThankWriterDelegate;
import io.github.thanktoken.core.impl.sign.ThankSignerDelegate;
import io.github.thanktoken.core.impl.validate.ThankValidatorDelegate;

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
  public ThankSigner getSigner() {

    return ThankSignerDelegate.get();
  }

  @Override
  public ThankValidator getValidator() {

    return ThankValidatorDelegate.get();
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
