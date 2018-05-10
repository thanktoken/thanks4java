package io.github.thanktoken.core.api.config;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.io.ThankValueParser;
import io.github.thanktoken.core.api.strategy.ThankStrategy;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import net.sf.mmm.security.api.crypt.asymmetric.SecurityAsymmetricCryptorFactoryPublicPrivate;
import net.sf.mmm.security.api.hash.SecurityHashCreator;
import net.sf.mmm.security.api.hash.SecurityHashFactory;
import net.sf.mmm.security.api.key.asymmetric.SecurityAsymmetricKeyCreator;
import net.sf.mmm.security.api.sign.SecuritySignatureCreator;
import net.sf.mmm.security.api.sign.SecuritySignatureFactory;

/**
 * The {@link ThankStrategy#getConfiguration() configuration} of a {@link ThankStrategy}.
 *
 * @see ThankStrategy#getConfiguration()
 */
public interface ThankConfiguration {

  /**
   * @return the {@link ThankValueParser}.
   */
  ThankValueParser getValueParser();

  /**
   * @return the {@link SecurityHashCreator} used to calculate hash values of the {@link ThankToken} data, esp. for
   *         {@link ThankTransaction#getSignature()}.
   */
  SecurityHashFactory getHashFactory();

  /**
   * @return the {@link SecurityAsymmetricCryptorFactoryPublicPrivate} used to encrypt and decrypt data.
   */
  SecurityAsymmetricCryptorFactoryPublicPrivate getCryptorFactory();

  /**
   * @return the {@link SecurityAsymmetricKeyCreator} used to deal with asymmetric keys.
   */
  SecurityAsymmetricKeyCreator getKeyCreator();

  /**
   * @return the {@link SecuritySignatureCreator} used for signing and verifying.
   */
  SecuritySignatureFactory getSignatureFactory();

}
