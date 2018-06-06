package io.github.thanktoken.core.api.reference;

import java.time.Instant;
import java.util.Objects;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.header.ThankLocation;
import io.github.thanktoken.core.api.header.ThankVersion;
import io.github.thanktoken.core.api.io.ThankValueParser;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;

/**
 * Identifier that references a {@link ThankToken}.
 */
public class ThankTokenReferenceType implements ThankTokenReference {

  private static final char VERSION_SUFFIX = '$';

  private static final char LOCATION_SUFFIX = '@';

  private static final char TIMESTAMP_SUFFIX = '~';

  private final ThankVersion version;

  private final ThankLocation location;

  private final Instant timestamp;

  private final SecurityPublicKey recipient;

  /**
   * The constructor.
   *
   * @param version the {@link #getVersion() version}.
   * @param location the {@link #getLocation() location}.
   * @param timestamp the {@link #getTimestamp() timestamp}.
   * @param recipient the {@link #getRecipient() creator}.
   */
  public ThankTokenReferenceType(ThankVersion version, ThankLocation location, Instant timestamp, SecurityPublicKey recipient) {

    super();
    Objects.requireNonNull(timestamp, "timestamp");
    Objects.requireNonNull(location, "location");
    Objects.requireNonNull(recipient, "recipient");
    this.version = version;
    this.timestamp = timestamp;
    this.location = location;
    this.recipient = recipient;
  }

  /**
   * The constructor.
   *
   * @param ref the {@link ThankTokenReference}.
   */
  public ThankTokenReferenceType(ThankTokenReference ref) {

    this(ref.getVersion(), ref.getLocation(), ref.getTimestamp(), ref.getRecipient());
  }

  /**
   * @return the {@link ThankVersion}.
   * @see io.github.thanktoken.core.api.header.ThankHeader#getVersion()
   */
  @Override
  public ThankVersion getVersion() {

    return this.version;
  }

  /**
   * @return the {@link ThankLocation} representing the
   *         {@link io.github.thanktoken.core.api.header.ThankHeader#getLocation() location} of the referenced
   *         {@link io.github.thanktoken.core.api.ThankToken}.
   */
  @Override
  public ThankLocation getLocation() {

    return this.location;
  }

  /**
   * @return the {@link Instant} representing the {@link io.github.thanktoken.core.api.header.ThankHeader#getTimestamp()
   *         timestamp} of the referenced {@link io.github.thanktoken.core.api.ThankToken}.
   */
  @Override
  public Instant getTimestamp() {

    return this.timestamp;
  }

  /**
   * @return the {@link SecurityPublicKey} representing the
   *         {@link io.github.thanktoken.core.api.header.ThankHeader#getRecipient() creator} of the referenced
   *         {@link io.github.thanktoken.core.api.ThankToken}.
   */
  @Override
  public SecurityPublicKey getRecipient() {

    return this.recipient;
  }

  @Override
  public int hashCode() {

    return Objects.hash(this.timestamp, this.location, this.recipient);
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ThankTokenReferenceType other = (ThankTokenReferenceType) obj;
    if (!Objects.equals(this.timestamp, other.timestamp)) {
      return false;
    }
    if (!Objects.equals(this.location, other.location)) {
      return false;
    }
    if (!Objects.equals(this.recipient, other.recipient)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    if (this.version != null) {
      sb.append(this.version);
      sb.append(VERSION_SUFFIX);
    }
    if (this.location != null) {
      sb.append(this.location);
      sb.append(LOCATION_SUFFIX);
    }
    sb.append(this.timestamp);
    if (this.recipient != null) {
      sb.append(TIMESTAMP_SUFFIX);
      sb.append(this.recipient.getBase64());
    }
    return sb.toString();
  }

  /**
   * @param string the {@link #toString() string representation} of a {@link ThankTokenReferenceType}.
   * @param parser the {@link ThankValueParser}.
   * @return the parsed {@link ThankTokenReferenceType} or {@code null} if given {@link String} was {@code null}.
   */
  public static ThankTokenReferenceType of(String string, ThankValueParser parser) {

    if (string == null) {
      return null;
    }
    // version
    ThankVersion version = null;
    int start = 0;
    int versionSuffixIndex = string.indexOf(VERSION_SUFFIX);
    if (versionSuffixIndex > 0) {
      version = ThankVersion.of(string.substring(0, versionSuffixIndex));
      start = versionSuffixIndex + 1;
    }
    // location
    ThankLocation location = null;
    int locationSuffixIndex = string.indexOf(LOCATION_SUFFIX, start);
    if (locationSuffixIndex > 0) {
      location = ThankLocation.of(string.substring(start, locationSuffixIndex));
      start = locationSuffixIndex + 1;
    }
    // recipient
    SecurityPublicKey recipient = null;
    int timestampSuffixIndex = string.indexOf(TIMESTAMP_SUFFIX, start);
    int end = string.length();
    if (timestampSuffixIndex > 0) {
      recipient = parser.parse(string.substring(timestampSuffixIndex), SecurityPublicKey.class);
      end = timestampSuffixIndex + 1;
    }
    // timestamp
    Instant timestamp = parser.parse(string.substring(start, end), Instant.class);
    return new ThankTokenReferenceType(version, location, timestamp, recipient);
  }

}
