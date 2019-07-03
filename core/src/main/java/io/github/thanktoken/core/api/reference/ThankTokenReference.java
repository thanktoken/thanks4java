package io.github.thanktoken.core.api.reference;

import io.github.thanktoken.core.api.attribute.AttributeReadReferenceType;
import io.github.thanktoken.core.api.id.ThankTokenId;
import io.github.thanktoken.core.api.id.ThankTokenIdType;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;

/**
 * A references to a {@link io.github.thanktoken.core.api.token.ThankToken}.
 */
public interface ThankTokenReference extends ThankTokenId, AttributeReadReferenceType {

  /**
   * @param header the {@link ThankTokenHeader} of the {@link io.github.thanktoken.core.api.token.ThankToken} owning the
   *        reference.
   * @return the resolved {@link ThankTokenReference} with optional {@code null} values replaced from the given
   *         {@code context}.
   */
  ThankTokenIdType resolve(ThankTokenHeader header);

  /**
   * @param header the {@link ThankTokenHeader} of the {@link io.github.thanktoken.core.api.token.ThankToken} that this
   *        reference is expected to point to.
   * @throws RuntimeException if this reference is not pointing to the given {@link ThankTokenHeader}.
   */
  void verify(ThankTokenHeader header);

}
