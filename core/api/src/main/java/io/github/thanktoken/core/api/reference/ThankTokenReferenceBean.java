package io.github.thanktoken.core.api.reference;

import java.time.Instant;
import java.util.Objects;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.field.ThankField;
import io.github.thanktoken.core.api.header.ThankLocation;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;

/**
 * Identifier that references a {@link ThankToken}.
 */
public class ThankTokenReferenceBean implements ThankTokenReference {

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
  public Instant getTimestamp() {

    return this.timestamp;
  }

  /**
   * @param timestamp the new value of {@link #getTimestamp()}.
   */
  public void setTimestamp(Instant timestamp) {

    this.timestamp = timestamp;
  }

  @Override
  public ThankLocation getLocation() {

    return this.location;
  }

  /**
   * @param location the new value of {@link #getLocation()}.
   */
  public void setLocation(ThankLocation location) {

    this.location = location;
  }

  @Override
  public SecurityPublicKey getRecipient() {

    return this.creator;
  }

  /**
   * @param creator the new value of {@link #getRecipient()}.
   */
  public void setRecipient(SecurityPublicKey creator) {

    this.creator = creator;
  }

  @Override
  public int hashCode() {

    return Objects.hash(this.timestamp, this.location, this.creator);
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

    return this.location + "@" + ThankField.formatInstant(this.timestamp) + '@' + this.creator.getHex();
  }

}
