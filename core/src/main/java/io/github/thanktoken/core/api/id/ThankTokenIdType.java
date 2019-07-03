package io.github.thanktoken.core.api.id;

import java.util.Objects;

import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.location.ThankLocation;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * Implementation of {@link ThankTokenId} as immutable type.
 *
 * @since 1.0.0
 */
public final class ThankTokenIdType implements ThankTokenId {

  private final ThankTimestamp timestamp;

  private final ThankLocation location;

  private final ThankVersion version;

  private final ThankAlgorithm algorithm;

  private final ThankCurrency currency;

  /**
   * The constructor.
   *
   * @param timestamp - see {@link #getTimestamp()}.
   * @param location - see {@link #getLocation()}.
   * @param version - see {@link #getVersion()}.
   * @param algorithm - see {@link #getAlgorithm()}.
   * @param currency - see {@link #getCurrency()}.
   */
  public ThankTokenIdType(ThankTimestamp timestamp, ThankLocation location, ThankVersion version, ThankAlgorithm algorithm, ThankCurrency currency) {

    super();
    this.timestamp = timestamp;
    this.location = location;
    this.version = version;
    this.algorithm = algorithm;
    this.currency = currency;
  }

  /**
   * The constructor.
   *
   * @param id the {@link ThankTokenId} to copy.
   */
  public ThankTokenIdType(ThankTokenId id) {

    super();
    this.timestamp = id.getTimestamp();
    this.location = id.getLocation();
    this.version = id.getVersion();
    this.algorithm = id.getAlgorithm();
    this.currency = id.getCurrency();
  }

  @Override
  public ThankTimestamp getTimestamp() {

    return this.timestamp;
  }

  @Override
  public ThankLocation getLocation() {

    return this.location;
  }

  @Override
  public ThankVersion getVersion() {

    return this.version;
  }

  @Override
  public ThankAlgorithm getAlgorithm() {

    return this.algorithm;
  }

  @Override
  public ThankCurrency getCurrency() {

    return this.currency;
  }

  /**
   * @param id the {@link ThankTokenId} to match (e.g. {@link ThankTokenHeader}).
   * @return {@code true} if all properties of this ID and the given {@code id} are equal, {@code false} otherwise.
   */
  public boolean matches(ThankTokenId id) {

    if (id == null) {
      return false;
    } else if (!Objects.equals(this.timestamp, id.getTimestamp())) {
      return false;
    } else if (!Objects.equals(this.location, id.getLocation())) {
      return false;
    } else if (!Objects.equals(this.version, id.getVersion())) {
      return false;
    } else if (!Objects.equals(this.algorithm, id.getAlgorithm())) {
      return false;
    } else if (!Objects.equals(this.currency, id.getCurrency())) {
      return false;
    }
    return true;

  }

  @Override
  public int hashCode() {

    return Objects.hash(this.timestamp, this.location, this.version, this.algorithm, this.currency);
  }

  @Override
  public boolean equals(Object obj) {

    if (obj == this) {
      return true;
    } else if ((obj == null) || (getClass() != obj.getClass())) {
      return false;
    }
    ThankTokenIdType other = (ThankTokenIdType) obj;
    if (!Objects.equals(this.timestamp, other.timestamp)) {
      return false;
    } else if (!Objects.equals(this.location, other.location)) {
      return false;
    } else if (!Objects.equals(this.version, other.version)) {
      return false;
    } else if (!Objects.equals(this.algorithm, other.algorithm)) {
      return false;
    } else if (!Objects.equals(this.currency, other.currency)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {

    return this.timestamp + "@" + this.location;
  }

  /**
   * @param id the {@link ThankTokenId} to reference.
   * @return the {@link ThankTokenIdType} referencing the given {@link ThankTokenId} or {@code null} if the given ID is
   *         not a valid {@link ThankTokenId} ({@code null} or missing required identifying properties).
   */
  public static ThankTokenIdType of(ThankTokenId id) {

    if (id == null) {
      return null;
    } else if (id instanceof ThankTokenIdType) {
      return (ThankTokenIdType) id;
    }
    ThankTimestamp timestamp = id.getTimestamp();
    if (timestamp == null) {
      return null;
    }
    ThankLocation location = id.getLocation();
    if (location == null) {
      return null;
    }
    ThankVersion version = id.getVersion();
    if (version == null) {
      return null;
    }
    ThankAlgorithm algorithm = id.getAlgorithm();
    if (algorithm == null) {
      return null;
    }
    ThankCurrency currency = id.getCurrency();
    if (currency == null) {
      return null;
    }
    return new ThankTokenIdType(timestamp, location, version, algorithm, currency);
  }

}
