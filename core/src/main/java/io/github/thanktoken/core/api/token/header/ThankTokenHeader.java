package io.github.thanktoken.core.api.token.header;

import io.github.thanktoken.core.api.attribute.AttributeReadAmount;
import io.github.thanktoken.core.api.attribute.AttributeReadHash;
import io.github.thanktoken.core.api.attribute.AttributeReadReference;
import io.github.thanktoken.core.api.attribute.AttributeReadTarget;
import io.github.thanktoken.core.api.data.ThankSignedObject;
import io.github.thanktoken.core.api.id.ThankTokenId;
import io.github.thanktoken.core.api.id.ThankTokenIdType;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;
import io.github.thanktoken.core.api.token.ThankToken;

/**
 * The fixed {@link ThankToken#getHeader() header} of a {@link ThankToken}.
 *
 * @see ThankToken
 */
public interface ThankTokenHeader
    extends ThankSignedObject, ThankTokenId, AttributeReadTarget, AttributeReadAmount, AttributeReadReference, AttributeReadHash {

  /**
   * @return the {@link ThankTokenReferenceType reference} on the {@link ThankToken} this {@link ThankToken} was created
   *         from via {@link io.github.thanktoken.core.api.message.ThankMessageTokenFork fork} or
   *         {@link io.github.thanktoken.core.api.message.ThankMessageTokenMerge merge} operation. Will be {@code null}
   *         otherwise (if {@link io.github.thanktoken.core.api.message.ThankMessageTokenCreation created regular for/by
   *         a natural person}).
   */
  @Override
  ThankTokenReferenceType getReference();

  /**
   * @return the {@link ThankTokenIdType} of this header and its owning {@link ThankToken}. Shall only be called if the
   *         header is completed (in case of a mutable header bean).
   */
  ThankTokenIdType getId();

}
