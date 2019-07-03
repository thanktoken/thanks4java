package io.github.thanktoken.core.api.timestamp;

import java.time.Instant;

import io.github.thanktoken.core.api.address.ThankAddress;

/**
 * Implementation of {@link ThankTimestampFactory} based on {@link Instant} with nano-precision.
 *
 * @since 1.0.0
 */
public class ThankTimestampFactoryNanoTime implements ThankTimestampFactory {

  @Override
  public ThankTimestamp create(ThankAddress address) {

    return new ThankTimestamp(Instant.now(ThankNanoClock.INSTANCE));
  }

}
