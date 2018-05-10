package io.github.thanktoken.core.impl.v1.strategy;

import io.github.thanktoken.core.api.header.ThankVersion;
import io.github.thanktoken.core.api.identity.ThankIdentityProvider;
import io.github.thanktoken.core.api.strategy.ThankVersionStrategy;
import io.github.thanktoken.core.impl.config.ThankConfigurationImpl;
import io.github.thanktoken.core.impl.strategy.AbstractThankVersionStrategy;
import net.sf.mmm.security.api.crypt.asymmetric.Rsa;
import net.sf.mmm.security.api.hash.SecurityHashConfigSha256;

/**
 * The implementation of {@link ThankVersionStrategy} for {@code V001}.
 */
public class ThankVersionStrategyV001 extends AbstractThankVersionStrategy {

  static final ThankVersion VERSION = ThankVersion.of(1);

  /**
   * The constructor.
   */
  public ThankVersionStrategyV001() {

    this(null); // TODO
  }

  /**
   * The constructor.
   * 
   * @param identityProvider the {@link ThankIdentityProvider} for this strategy.
   */
  public ThankVersionStrategyV001(ThankIdentityProvider identityProvider) {

    super(new ThankConfigurationImpl(Rsa.keyLength4096().withHash(SecurityHashConfigSha256.SHA_256).getFactories()), identityProvider);
  }

  @Override
  public ThankVersion getVersion() {

    return VERSION;
  }

}
