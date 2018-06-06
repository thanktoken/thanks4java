package io.github.thanktoken.core.api.reference;

import java.time.Instant;
import java.util.Objects;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.attribute.AttributeWriteRecipient;
import io.github.thanktoken.core.api.datatype.TimestampHelper;
import io.github.thanktoken.core.api.header.ThankLocation;
import io.github.thanktoken.core.api.header.ThankVersion;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;

/**
 * Identifier that references a {@link ThankToken}.
 */
public class ThankTokenReferenceBean implements ThankTokenReference, AttributeWriteRecipient<ThankTokenReferenceBean> {

  private ThankVersion version;

  private Instant timestamp;

  private ThankLocation location;

  private SecurityPublicKey creator;

  /**
   * The constructor.
   */
  public ThankTokenReferenceBean() {

    super();
  }

  @Override
  public ThankVersion getVersion() {

    return this.version;
  }

  /**
   * @param version the new value of {@link #getVersion()}.
   * @return this
   */
  public ThankTokenReferenceBean setVersion(ThankVersion version) {

    this.version = version;
    return this;
  }

  @Override
  public Instant getTimestamp() {

    return this.timestamp;
  }

  /**
   * @param timestamp the new value of {@link #getTimestamp()}.
   * @return this
   */
  public ThankTokenReferenceBean setTimestamp(Instant timestamp) {

    this.timestamp = timestamp;
    return this;
  }

  @Override
  public ThankLocation getLocation() {

    return this.location;
  }

  /**
   * @param location the new value of {@link #getLocation()}.
   * @return this
   */
  public ThankTokenReferenceBean setLocation(ThankLocation location) {

    this.location = location;
    return this;
  }

  @Override
  public SecurityPublicKey getRecipient() {

    return this.creator;
  }

  /**
   * @param creator the new value of {@link #getRecipient()}.
   * @return this
   */
  @Override
  public ThankTokenReferenceBean setRecipient(SecurityPublicKey creator) {

    this.creator = creator;
    return this;
  }

  @Override
  public int hashCode() {

    return Objects.hash(this.version, this.timestamp, this.location, this.creator);
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
    ThankTokenReferenceBean other = (ThankTokenReferenceBean) obj;
    if (!Objects.equals(this.version, other.version)) {
      return false;
    }
    if (!Objects.equals(this.timestamp, other.timestamp)) {
      return false;
    }
    if (!Objects.equals(this.location, other.location)) {
      return false;
    }
    if (!Objects.equals(this.creator, other.creator)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {

    return this.location + "@" + TimestampHelper.format(this.timestamp) + '@' + this.creator.getHex();
  }

}
