package io.github.thanktoken.core.impl.v001.strategy;

import net.sf.mmm.crypto.asymmetric.access.rsa.Rsa;
import net.sf.mmm.crypto.hash.SecurityHashConfigSha256;

import io.github.thanktoken.core.api.identity.ThankIdentityProvider;
import io.github.thanktoken.core.api.strategy.ThankVersionStrategy;
import io.github.thanktoken.core.api.version.ThankVersion;
import io.github.thanktoken.core.impl.config.ThankConfigurationImpl;
import io.github.thanktoken.core.impl.strategy.AbstractThankVersionStrategy;

/**
 * The implementation of {@link ThankVersionStrategy} for
 * {@link io.github.thanktoken.core.api.header.ThankHeader#getVersion() version} {@code 2}.
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
