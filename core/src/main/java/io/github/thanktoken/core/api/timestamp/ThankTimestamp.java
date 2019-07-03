package io.github.thanktoken.core.api.timestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Technically a {@link ThankTimestamp} is just a {@link #getInstant() wrapper} of an {@link Instant}. However this is
 * used as the main identifier of a {@link io.github.thanktoken.core.api.token.ThankToken}. Within a
 * {@link io.github.thanktoken.core.api.location.ThankLocation location} the {@link ThankTimestamp} of each
 * {@link io.github.thanktoken.core.api.token.ThankToken} has to be unique. Therefore specific rules apply to create and
 * use instances of {@link ThankTimestamp}. Therefore, this wrapper class documents and supports these details.<br>
 * When multiple {@link io.github.thanktoken.core.api.token.ThankToken}s are created, an initial {@link ThankTimestamp}
 * is used for the first token and each sub-sequent token uses the {@link #next() next timestamp}.<br>
 * To avoid collisions
 *
 * @see io.github.thanktoken.core.api.reference.ThankTokenReference#getTimestamp()
 *
 * @since 1.0.0
 */
public class ThankTimestamp {

  /** Constant for universal time zone. */
  public static final ZoneId UTC = ZoneId.of("UTC");

  /** The {@link DateTimeFormatter} used for format and parse date/time data such as {@link Instant}. */
  public static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss.SSSSSSSSS").withZone(UTC);

  private final Instant instant;

  /**
   * The constructor.
   *
   * @param instant the {@link #getInstant() wrapped instant}.
   */
  public ThankTimestamp(Instant instant) {

    super();
    Objects.requireNonNull(instant, "instant");
    this.instant = instant;
  }

  /**
   * @return the wrapped {@link Instant}.
   */
  public Instant getInstant() {

    return this.instant;
  }

  /**
   * @param timestamp till which the number of days are requested.
   * @return the days from this {@link #getInstant() instant} to the given {@link Instant}. Integer precision is
   *         sufficient for ~5 million (5.883.516) years. As {@link io.github.thanktoken.core.api.token.ThankToken}
   *         typically only exist for few years before being renewed or faded this is more than enough.
   */
  public int getDaysTo(Instant timestamp) {

    LocalDate creationDay = asLocalDate();
    LocalDate targetDay = LocalDate.from(timestamp.atZone(UTC));
    return (int) ChronoUnit.DAYS.between(creationDay, targetDay);
  }

  /**
   * @return the {@link #getInstant() instant} as {@link LocalDate} in {@link #UTC}.
   */
  public LocalDate asLocalDate() {

    return LocalDate.from(asZonedDateTime());
  }

  /**
   * @return the {@link #getInstant() instant} as {@link ZonedDateTime} in {@link #UTC}.
   */
  public ZonedDateTime asZonedDateTime() {

    return this.instant.atZone(UTC);
  }

  /**
   * @return the next {@link ThankTimestamp} following directly after this one (+1 nanos).
   */
  public ThankTimestamp next() {

    return new ThankTimestamp(this.instant.plusNanos(1));
  }

  /**
   * @param next the {@link ThankTimestamp} to test.
   * @return {@code true} if the given {@link ThankTimestamp} is the {@link #next() next} timestamp of this one.
   */
  public boolean isNext(ThankTimestamp next) {

    if (next == null) {
      return false;
    }
    if (this.instant.getEpochSecond() != next.instant.getEpochSecond()) {
      return false;
    }
    if (this.instant.getNano() + 1 != next.instant.getNano()) {
      return false;
    }
    return true;
  }

  /**
   * @param previous the {@link ThankTimestamp} to test.
   * @return {@code true} if the given {@link ThankTimestamp} is the {@link #next() next} timestamp of this one.
   */
  public boolean isPrevious(ThankTimestamp previous) {

    if (previous == null) {
      return false;
    }
    if (this.instant.getEpochSecond() != previous.instant.getEpochSecond()) {
      return false;
    }
    if (this.instant.getNano() - 1 != previous.instant.getNano()) {
      return false;
    }
    return true;
  }

  /**
   * @return {@code true} if this {@link #getInstant() instant} is in the future, {@code false} otherwise.
   */
  public boolean isInFuture() {

    return this.instant.isAfter(Instant.now());
  }

  /**
   * @return {@code true} if this {@link #getInstant() instant} is in the past, {@code false} otherwise.
   */
  public boolean isInPast() {

    return this.instant.isBefore(Instant.now());
  }

  /**
   * @param timestamp the {@link ThankTimestamp} to compare.
   * @return {@code true} if {@link Instant#isAfter(Instant) after} the given {@code timestamp}.
   */
  public boolean isAfter(ThankTimestamp timestamp) {

    return this.instant.isAfter(timestamp.instant);
  }

  /**
   * @param timestamp the {@link ThankTimestamp} to compare.
   * @return {@code true} if {@link Instant#isBefore(Instant) before} the given {@code timestamp}.
   */
  public boolean isBefore(ThankTimestamp timestamp) {

    return this.instant.isBefore(timestamp.instant);
  }

  @Override
  public int hashCode() {

    return this.instant.hashCode();
  }

  @Override
  public boolean equals(Object obj) {

    if (obj == this) {
      return true;
    } else if ((obj == null) || (obj.getClass() != getClass())) {
      return false;
    }
    ThankTimestamp other = (ThankTimestamp) obj;
    return Objects.equals(this.instant, other.instant);
  }

  @Override
  public String toString() {

    return TIMESTAMP_FORMATTER.format(this.instant);
  }

  /**
   * <b>ATTENTION:</b><br>
   * For creation of initial timestamp for token creation (including merge and fork) please use
   * {@link ThankTimestampFactory}.
   *
   * @return the {@link ThankTimestamp} for the current moment in time.
   */
  public static ThankTimestamp now() {

    return new ThankTimestamp(Instant.now(ThankNanoClock.INSTANCE));
  }

  /**
   * @param ts the {@link #toString() string representation}.
   * @return the parsed {@link ThankTimestamp}.
   */
  public static ThankTimestamp of(String ts) {

    if (ts == null) {
      return null;
    }
    Instant instant = Instant.from(TIMESTAMP_FORMATTER.parse(ts));
    return new ThankTimestamp(instant);
  }

}
