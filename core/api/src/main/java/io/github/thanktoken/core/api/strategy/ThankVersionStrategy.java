package io.github.thanktoken.core.api.strategy;

import io.github.thanktoken.core.api.header.ThankVersion;

/**
 * This is the interface for a strategy that bridges to the implementation of a particular {@link ThankVersion}.
 */
public interface ThankVersionStrategy extends ThankStrategy {

  /**
   * @return the {@link ThankVersion} handled by this strategy.
   */
  ThankVersion getVersion();

}
