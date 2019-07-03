/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.base.config;

import io.github.thanktoken.core.api.config.ThankConfiguration;
import io.github.thanktoken.core.api.io.ThankReader;
import io.github.thanktoken.core.api.io.ThankReaderImpl;
import io.github.thanktoken.core.api.io.ThankValueParser;
import io.github.thanktoken.core.api.io.ThankValueParserDefault;
import io.github.thanktoken.core.api.io.ThankWriter;
import io.github.thanktoken.core.api.io.ThankWriterImpl;
import io.github.thanktoken.core.api.sign.ThankSigner;
import io.github.thanktoken.core.api.validate.ThankValidator;

/**
 * Implementation of {@link ThankConfiguration}.
 */
public class ThankConfigurationImpl implements ThankConfiguration {

  /**
   * The constructor.
   */
  public ThankConfigurationImpl() {

    super();
  }

  @Override
  public ThankSigner getSigner() {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ThankValidator getValidator() {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ThankReader getReader() {

    return ThankReaderImpl.get();
  }

  @Override
  public ThankWriter getWriter() {

    return ThankWriterImpl.get();
  }

  @Override
  public ThankValueParser getValueParser() {

    return ThankValueParserDefault.get();
  }

}
