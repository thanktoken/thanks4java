package io.github.thanktoken.core.api.header;

import java.math.BigDecimal;
import java.time.Instant;

import io.github.thanktoken.core.api.ThankSignedObject;
import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.reference.ThankTokenReference;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;
import net.sf.mmm.security.api.sign.SecuritySignature;

/**
 * The fixed {@link ThankToken#getHeader() header} of a {@link ThankToken}.
 *
 * @see ThankToken
 */
public interface ThankHeader extends ThankSignedObject, ThankTokenReference {

  /**
   * @return the {@link ThankVersion version} of this token.
   * @see io.github.thanktoken.core.api.strategy.ThankVersionStrategy
   */
  ThankVersion getVersion();

  /**
   * @see ThankToken#getValue()
   *
   * @return the fixed amount of thanks of which this {@link ThankToken} was created.
   */
  BigDecimal getAmount();

  /**
   * @return the {@link ThankCurrency} of this {@link ThankToken}.
   */
  ThankCurrency getCurrency();

  /**
   * @return the exact {@link Instant} when this {@link ThankToken} was created with nano-second precision.
   */
  @Override
  Instant getTimestamp();

  /**
   * @return the {@link ThankLocation} of the person that created this {@link ThankToken}.
   */
  @Override
  ThankLocation getLocation();

  /**
   * @return the {@link ThankTarget target} (initial purpose) this token was created for.
   */
  ThankTarget getTarget();

  /**
   * @return the {@link SecurityPublicKey} of the creator of this token who is also the initial recipient (owner) of the
   *         token.
   */
  @Override
  SecurityPublicKey getRecipient();

  /**
   * @return the {@link SecuritySignature} as its hash (excluding this {@link SecuritySignature}) signed using the
   *         private key of the (previous) {@link #getRecipient() recipient} (owner).
   */
  @Override
  SecuritySignature getSignature();

  /**
   * @return the {@link ThankTokenReferenceType reference} on the {@link ThankToken} this {@link ThankToken} was created
   *         from via fork or merge operation. Will be {@code null} otherwise (if initially created for a given
   *         {@link ThankTarget}).
   */
  ThankTokenReferenceType getReference();
}
