/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.impl.config;

import net.sf.mmm.security.api.AbstractSecurityFactories;
import net.sf.mmm.security.api.crypt.asymmetric.SecurityAsymmetricCryptorFactoryPublicPrivate;
import net.sf.mmm.security.api.hash.SecurityHashFactory;
import net.sf.mmm.security.api.key.asymmetric.SecurityAsymmetricKeyCreator;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;
import net.sf.mmm.security.api.sign.SecuritySignatureFactory;
import net.sf.mmm.util.datatype.api.BinaryType;

import io.github.thanktoken.core.api.io.ThankValueParser;

/**
 * Implementation of {@link io.github.thanktoken.core.api.config.ThankConfiguration}.
 */
public class ThankConfigurationImpl extends AbstractThankConfiguration implements ThankValueParser {

  /**
   * The constructor.
   *
   * @param hashFactory - see {@link #getHashFactory()}.
   * @param cryptorFactory - see {@link #getCryptorFactory()}.
   * @param keyCreator - see {@link #getKeyCreator()}.
   * @param signatureFactory - see {@link #getSignatureFactory()}.
   */
  public ThankConfigurationImpl(SecurityHashFactory hashFactory,
      SecurityAsymmetricCryptorFactoryPublicPrivate cryptorFactory, SecurityAsymmetricKeyCreator keyCreator,
      SecuritySignatureFactory signatureFactory) {

    super(hashFactory, cryptorFactory, keyCreator, signatureFactory);
  }

  /**
   * The constructor.
   *
   * @param factories the {@link AbstractSecurityFactories} to retrieve all factories from.
   */
  public ThankConfigurationImpl(AbstractSecurityFactories factories) {

    super(factories);
  }

  @Override
  public ThankValueParser getValueParser() {

    return this;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T parse(String value, Class<T> type) {

    if (type.equals(SecurityPublicKey.class)) {
      return (T) parsePublicKey(value);
    }
    return ThankValueParser.super.parse(value, type);
  }

  /**
   * @param hex the {@link BinaryType#getHex() hexadecimal representation} of the {@link SecurityPublicKey}.
   * @return the parsed {@link SecurityPublicKey}.
   */
  public SecurityPublicKey parsePublicKey(String hex) {

    return getKeyCreator().deserializePublicKey(hex);
  }

}
