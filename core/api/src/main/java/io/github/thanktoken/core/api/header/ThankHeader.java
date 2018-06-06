package io.github.thanktoken.core.api.header;

import io.github.thanktoken.core.api.ThankSignedObject;
import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.attribute.AttributeReadAmount;
import io.github.thanktoken.core.api.attribute.AttributeReadCurrency;
import io.github.thanktoken.core.api.attribute.AttributeReadReference;
import io.github.thanktoken.core.api.attribute.AttributeReadTarget;
import io.github.thanktoken.core.api.reference.ThankTokenReference;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;

/**
 * The fixed {@link ThankToken#getHeader() header} of a {@link ThankToken}.
 *
 * @see ThankToken
 */
public interface ThankHeader
    extends ThankSignedObject, ThankTokenReference, AttributeReadTarget, AttributeReadCurrency, AttributeReadAmount, AttributeReadReference {

  /**
   * @return the {@link ThankTokenReferenceType reference} on the {@link ThankToken} this {@link ThankToken} was created
   *         from via {@link io.github.thanktoken.core.api.message.ThankMessageTokenFork fork} or
   *         {@link io.github.thanktoken.core.api.message.ThankMessageTokenMerge merge} operation. Will be {@code null}
   *         otherwise (if {@link io.github.thanktoken.core.api.message.ThankMessageTokenCreation created regular for/by
   *         a natural person}).
   */
  @Override
  ThankTokenReferenceType getReference();
}
