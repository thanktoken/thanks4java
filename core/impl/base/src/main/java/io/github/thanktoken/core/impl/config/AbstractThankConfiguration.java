/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.impl.config;

import io.github.thanktoken.core.api.config.ThankConfiguration;
import net.sf.mmm.security.api.AbstractSecurityFactories;
import net.sf.mmm.security.api.crypt.asymmetric.SecurityAsymmetricCryptorFactoryPublicPrivate;
import net.sf.mmm.security.api.hash.SecurityHashFactory;
import net.sf.mmm.security.api.key.asymmetric.SecurityAsymmetricKeyCreator;
import net.sf.mmm.security.api.sign.SecuritySignatureFactory;

/**
 * Abstract base implementation of {@link ThankConfiguration}.
 */
public abstract class AbstractThankConfiguration implements ThankConfiguration {

  private final SecurityHashFactory hashFactory;

  private final SecurityAsymmetricCryptorFactoryPublicPrivate cryptorFactory;

  private final SecurityAsymmetricKeyCreator keyCreator;

  private final SecuritySignatureFactory signatureFactory;

  /**
   * The constructor.
   *
   * @param hashFactory - see {@link #getHashFactory()}.
   * @param cryptorFactory - see {@link #getCryptorFactory()}.
   * @param keyCreator - see {@link #getKeyCreator()}.
   * @param signatureFactory - see {@link #getSignatureFactory()}.
   */
  public AbstractThankConfiguration(SecurityHashFactory hashFactory, SecurityAsymmetricCryptorFactoryPublicPrivate cryptorFactory,
      SecurityAsymmetricKeyCreator keyCreator, SecuritySignatureFactory signatureFactory) {

    super();
    this.hashFactory = hashFactory;
    this.cryptorFactory = cryptorFactory;
    this.keyCreator = keyCreator;
    this.signatureFactory = signatureFactory;
  }

  /**
   * The constructor.
   *
   * @param factories the {@link AbstractSecurityFactories} to retrieve all factories from.
   */
  public AbstractThankConfiguration(AbstractSecurityFactories factories) {

    this(factories.getHashFactoryRequired(), (SecurityAsymmetricCryptorFactoryPublicPrivate) factories.getCryptorFactory(),
        factories.getAsymmetricKeyFactoryRequired().newKeyCreator(), factories.getSignatureFactoryRequired());
  }

  @Override
  public SecurityHashFactory getHashFactory() {

    return this.hashFactory;
  }

  @Override
  public SecurityAsymmetricCryptorFactoryPublicPrivate getCryptorFactory() {

    return this.cryptorFactory;
  }

  @Override
  public SecurityAsymmetricKeyCreator getKeyCreator() {

    return this.keyCreator;
  }

  @Override
  public SecuritySignatureFactory getSignatureFactory() {

    return this.signatureFactory;
  }

}
