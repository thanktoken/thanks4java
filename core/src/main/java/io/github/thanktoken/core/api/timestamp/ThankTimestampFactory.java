package io.github.thanktoken.core.api.timestamp;

import io.github.thanktoken.core.api.address.ThankAddress;

/**
 * Factory to {@link #create(ThankAddress) create} a new initial {@link ThankTimestamp} in order to create new
 * {@link io.github.thanktoken.core.api.token.ThankToken}s.
 *
 * @since 1.0.0
 */
public interface ThankTimestampFactory {

  /**
   * @param address the {@link ThankAddress} for which to create tokens.
   * @return a new initial {@link ThankTimestamp} for the current moment in time targeted for the given
   *         {@link ThankAddress}.
   */
  ThankTimestamp create(ThankAddress address);

}
