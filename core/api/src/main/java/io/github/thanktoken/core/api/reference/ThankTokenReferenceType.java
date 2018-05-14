package io.github.thanktoken.core.api.reference;

import java.time.Instant;
import java.util.Objects;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.datatype.TimestampHelper;
import io.github.thanktoken.core.api.header.ThankLocation;
import io.github.thanktoken.core.api.io.ThankValueParser;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;

/**
 * Identifier that references a {@link ThankToken}.
 */
public class ThankTokenReferenceType implements ThankTokenReference {

  private final ThankLocation location;

  private final Instant timestamp;

  private final SecurityPublicKey recipient;

  /**
   * The constructor.
   *
   * @param location the {@link #getLocation() location}.
   * @param timestamp the {@link #getTimestamp() timestamp}.
   * @param recipient the {@link #getRecipient() creator}.
   */
  public ThankTokenReferenceType(ThankLocation location, Instant timestamp, SecurityPublicKey recipient) {

    super();
    Objects.requireNonNull(timestamp, "timestamp");
    Objects.requireNonNull(location, "location");
    Objects.requireNonNull(recipient, "recipient");
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

    this(ref.getLocation(), ref.getTimestamp(), ref.getRecipient());
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

    return this.location + "@" + TimestampHelper.format(this.timestamp) + '@' + this.recipient.getBase64();
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
    String[] segments = string.split("@");
    if (segments.length != 3) {
      throw new IllegalArgumentException("Invalid reference: '" + string + "'.");
    }
    ThankLocation location = parser.parse(segments[0], ThankLocation.class);
    Instant timestamp = parser.parse(segments[1], Instant.class);
    SecurityPublicKey recipient = parser.parse(segments[2], SecurityPublicKey.class);
    return new ThankTokenReferenceType(location, timestamp, recipient);
  }

}
