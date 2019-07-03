package io.github.thanktoken.core.impl.io;

import net.sf.mmm.crypto.asymmetric.key.SecurityPublicKey;

import io.github.thanktoken.core.api.io.ThankValueParser;

/**
 * Implementation of {@link ThankValueParser} that delegates to another instance.
 */
public class ThankValueParserDelegate implements ThankValueParser {

  private final ThankValueParser delegate;

  /**
   * The constructor.
   *
   * @param delegate
   */
  public ThankValueParserDelegate(ThankValueParser delegate) {

    super();
    this.delegate = delegate;
  }

  @Override
  public <T> T parse(String value, Class<T> type) {

    return this.delegate.parse(value, type);
  }

  @Override
  public SecurityPublicKey parsePublicKey(String hex) {

    return this.delegate.parsePublicKey(hex);
  }

}
