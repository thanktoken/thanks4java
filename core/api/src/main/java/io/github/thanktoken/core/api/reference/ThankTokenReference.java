package io.github.thanktoken.core.api.reference;

import java.time.Instant;

import io.github.thanktoken.core.api.ThankDataObject;
import io.github.thanktoken.core.api.header.ThankLocation;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;

/**
 * Identifier that references a {@link io.github.thanktoken.core.api.ThankToken}.
 */
public interface ThankTokenReference extends ThankDataObject {

  /**
   * @return the {@link Instant} representing the {@link io.github.thanktoken.core.api.header.ThankHeader#getTimestamp()
   *         timestamp} of the referenced {@link io.github.thanktoken.core.api.ThankToken}.
   */
  Instant getTimestamp();

  /**
   * @return the {@link ThankLocation} representing the
   *         {@link io.github.thanktoken.core.api.header.ThankHeader#getLocation() location} of the referenced
   *         {@link io.github.thanktoken.core.api.ThankToken}.
   */
  ThankLocation getLocation();

  /**
   * @return the {@link SecurityPublicKey} representing the
   *         {@link io.github.thanktoken.core.api.header.ThankHeader#getRecipient() recipient} of the referenced
   *         {@link io.github.thanktoken.core.api.ThankToken}.
   */
  SecurityPublicKey getRecipient();

}
