package io.github.thanktoken.core.impl.v002.strategy;

import io.github.thanktoken.core.api.header.ThankVersion;
import io.github.thanktoken.core.api.identity.ThankIdentityProvider;
import io.github.thanktoken.core.api.strategy.ThankVersionStrategy;
import io.github.thanktoken.core.impl.config.ThankConfigurationImpl;
import io.github.thanktoken.core.impl.strategy.AbstractThankVersionStrategy;
import net.sf.mmm.security.api.crypt.asymmetric.Curve25519;
import net.sf.mmm.security.api.hash.SecurityHashConfigSha256;

/**
 * The implementation of {@link ThankVersionStrategy} for
 * {@link io.github.thanktoken.core.api.header.ThankHeader#getVersion() version} {@code 2}.
 */
public class ThankVersionStrategyV002 extends AbstractThankVersionStrategy {

  static final ThankVersion VERSION = ThankVersion.of(2);

  /**
   * The constructor.
   */
  public ThankVersionStrategyV002() {

    this(null); // TODO
  }

  /**
   * The constructor.
   *
   * @param identityProvider the {@link ThankIdentityProvider} for this strategy.
   */
  public ThankVersionStrategyV002(ThankIdentityProvider identityProvider) {

    super(new ThankConfigurationImpl(Curve25519.create().withHash(SecurityHashConfigSha256.SHA_256).getFactories()), identityProvider);
  }

  @Override
  public ThankVersion getVersion() {

    return VERSION;
  }

}
