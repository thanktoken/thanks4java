package io.github.thanktoken.core.api.timestamp;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

/**
 * Implementation of {@link Clock} based on {@link System#nanoTime()} with microsecond-precision so the last three
 * digits of the {@link Instant#getNano() nano-seconds} are always zero. These digits are reserved as an internal
 * sequence counter when tokens are created. With microsecond-precision still one million concurrent people could
 * potentially create tokens per second within a {@link io.github.thanktoken.core.api.location.ThankLocation location}
 * what gives a rather low probability for collisions. To
 *
 * @since 1.0.0
 */
class ThankNanoClock extends Clock {

  static final ThankNanoClock INSTANCE = new ThankNanoClock();

  private final Clock clock;

  private final long initialNanos;

  private final Instant initialInstant;

  ThankNanoClock() {

    this(Clock.systemUTC());
  }

  ThankNanoClock(Clock clock) {

    this.clock = clock;
    this.initialInstant = clock.instant();
    this.initialNanos = System.nanoTime();
  }

  @Override
  public ZoneId getZone() {

    return this.clock.getZone();
  }

  @Override
  public Clock withZone(ZoneId zone) {

    return new ThankNanoClock(this.clock.withZone(zone));
  }

  @Override
  public Instant instant() {

    return this.initialInstant.plusNanos(System.nanoTime() - this.initialNanos);
  }

}
