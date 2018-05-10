package io.github.thanktoken.core.impl.strategy;

import io.github.thanktoken.core.api.ThankConstants;
import io.github.thanktoken.core.api.config.ThankConfiguration;
import io.github.thanktoken.core.api.identity.ThankIdentityProvider;
import io.github.thanktoken.core.api.io.ThankReader;
import io.github.thanktoken.core.api.io.ThankWriter;
import io.github.thanktoken.core.api.sign.ThankTokenSigner;
import io.github.thanktoken.core.api.strategy.ThankVersionStrategy;
import io.github.thanktoken.core.api.validate.ThankTokenValidator;
import io.github.thanktoken.core.impl.io.ThankReaderImpl;
import io.github.thanktoken.core.impl.io.ThankWriterImpl;
import io.github.thanktoken.core.impl.sign.ThankTokenSignerImpl;
import io.github.thanktoken.core.impl.validate.ThankTokenValidatorImpl;

/**
 * The abstract base implementation of {@link ThankVersionStrategy}.
 */
public abstract class AbstractThankVersionStrategy implements ThankVersionStrategy, ThankConstants {

  private final ThankConfiguration configuration;

  private ThankIdentityProvider identityProvider;

  private ThankReader reader;

  private ThankWriter writer;

  private ThankTokenSigner signer;

  private ThankTokenValidator validator;

  /**
   * The constructor.
   *
   * @param configuration the {@link ThankConfiguration} for this strategy.
   * @param identityProvider the {@link ThankIdentityProvider} for this strategy.
   */
  public AbstractThankVersionStrategy(ThankConfiguration configuration, ThankIdentityProvider identityProvider) {

    super();
    this.configuration = configuration;
    this.identityProvider = identityProvider;
  }

  @Override
  public ThankConfiguration getConfiguration() {

    return this.configuration;
  }

  @Override
  public ThankReader getReader() {

    if (this.reader == null) {
      this.reader = createReader();
    }
    return this.reader;
  }

  /**
   * @return the new {@link #getReader() reader}.
   */
  protected ThankReader createReader() {

    return new ThankReaderImpl(this);
  }

  @Override
  public ThankWriter getWriter() {

    if (this.writer == null) {
      this.writer = createWriter();
    }
    return this.writer;
  }

  /**
   * @return the new {@link #getWriter() writer}.
   */
  protected ThankWriter createWriter() {

    return new ThankWriterImpl(this);
  }

  @Override
  public ThankTokenSigner getSigner() {

    if (this.signer == null) {
      this.signer = createSigner();
    }
    return this.signer;
  }

  /**
   * @return the new {@link #getSigner() signer}.
   */
  protected ThankTokenSigner createSigner() {

    return new ThankTokenSignerImpl(this);
  }

  @Override
  public ThankTokenValidator getValidator() {

    if (this.validator == null) {
      this.validator = createValidator();
    }
    return this.validator;
  }

  /**
   * @return the {@link ThankIdentityProvider}.
   */
  public ThankIdentityProvider getIdentityProvider() {

    return this.identityProvider;
  }

  /**
   * @return the new {@link #getValidator() validator}.
   */
  protected ThankTokenValidator createValidator() {

    return new ThankTokenValidatorImpl(this);
  }

}
