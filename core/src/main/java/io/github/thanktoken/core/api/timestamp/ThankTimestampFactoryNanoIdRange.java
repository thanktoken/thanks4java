package io.github.thanktoken.core.api.timestamp;

import java.time.Instant;

import io.github.thanktoken.core.api.address.ThankAddress;

/**
 * Implementation of {@link ThankTimestampFactory} using the {@link Instant#getNano() nano-seconds} not as
 * time-information but instead as ID blocks based on the {@link ThankAddress}.
 *
 * @since 1.0.0
 */
public class ThankTimestampFactoryNanoIdRange implements ThankTimestampFactory {

  private static final long MASK_NANOS = 0x1FFFFC00L;

  @Override
  public ThankTimestamp create(ThankAddress address) {

    Instant instant = Instant.now();
    long nanos = (address.getTimestampId() << 10) & MASK_NANOS;
    return new ThankTimestamp(Instant.ofEpochSecond(instant.getEpochSecond(), nanos));
  }

}
