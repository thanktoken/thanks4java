package io.github.thanktoken.core.impl;

import java.util.Objects;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.header.ThankVersion;
import io.github.thanktoken.core.api.io.ThankWriter;
import io.github.thanktoken.core.api.strategy.ThankStrategyFactory;
import io.github.thanktoken.core.api.strategy.ThankVersionStrategy;

/**
 * This is an implementation of {@link ThankWriter} that uses {@link ThankStrategyFactory} in order to write
 * {@link ThankToken}s for arbitrary {@link ThankHeader#getVersion() versions}.
 */
public abstract class ThankDelegate {

  /**
   * The constructor.
   */
  public ThankDelegate() {

    super();
  }

  /**
   * @see ThankStrategyFactory#getStrategy(ThankVersion)
   * @param token the {@link ThankToken}.
   * @return the {@link ThankVersionStrategy} corresponding to the {@link ThankHeader#getVersion() version} of the given
   *         {@link ThankToken}.
   */
  protected final ThankVersionStrategy getStrategy(ThankToken token) {

    Objects.requireNonNull(token, "token");
    return getStrategy(token.getHeader());
  }

  /**
   * @see ThankStrategyFactory#getStrategy(ThankVersion)
   * @param header the {@link ThankHeader}.
   * @return the {@link ThankVersionStrategy} corresponding to the {@link ThankHeader#getVersion() version} of the given
   *         {@link ThankHeader}.
   */
  protected final ThankVersionStrategy getStrategy(ThankHeader header) {

    Objects.requireNonNull(header, "header");
    ThankVersion version = header.getVersion();
    return getStrategyFactory().getStrategy(version);
  }

  /**
   * @see ThankStrategyFactory#get()
   * @return the {@link ThankStrategyFactory}.
   */
  protected ThankStrategyFactory getStrategyFactory() {

    return ThankStrategyFactory.get();
  }

}
