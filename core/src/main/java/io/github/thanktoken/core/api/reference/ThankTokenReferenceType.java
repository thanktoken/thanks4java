package io.github.thanktoken.core.api.reference;

import java.util.Objects;

import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.location.ThankLocation;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * Implementation of {@link ThankTokenReference} as immutable type.
 */
public class ThankTokenReferenceType extends AbstractThankTokenReference {

  private final String type;

  private final ThankTimestamp timestamp;

  private final ThankLocation location;

  private final ThankVersion version;

  private final ThankAlgorithm algorithm;

  private final ThankCurrency currency;

  /**
   * The constructor.
   *
   * @param type the {@link #getType() type}.
   * @param timestamp the {@link #getTimestamp() timestamp}.
   * @param location the {@link #getLocation() location}.
   * @param version the {@link #getVersion() version}.
   * @param algorithm the {@link #getAlgorithm() algorithm}.
   * @param currency the {@link #getCurrency() currency}.
   */
  public ThankTokenReferenceType(String type, ThankTimestamp timestamp, ThankLocation location, ThankVersion version,
      ThankAlgorithm algorithm, ThankCurrency currency) {

    super();
    Objects.requireNonNull(type, "type");
    this.type = type;
    this.version = version;
    this.algorithm = algorithm;
    this.currency = currency;
    this.timestamp = timestamp;
    this.location = location;
  }

  /**
   * The constructor.
   *
   * @param ref the {@link ThankTokenReference}.
   */
  public ThankTokenReferenceType(ThankTokenReference ref) {

    this(ref.getType(), ref.getTimestamp(), ref.getLocation(), ref.getVersion(), ref.getAlgorithm(), ref.getCurrency());
  }

  @Override
  public String getType() {

    return this.type;
  }

  /**
   * @return the {@link ThankTimestamp} representing the
   *         {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader#getTimestamp() timestamp} of the referenced
   *         {@link io.github.thanktoken.core.api.token.ThankToken}.
   */
  @Override
  public ThankTimestamp getTimestamp() {

    return this.timestamp;
  }

  /**
   * @return the {@link ThankLocation} representing the
   *         {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader#getLocation() location} of the referenced
   *         {@link io.github.thanktoken.core.api.token.ThankToken}.
   */
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

}
