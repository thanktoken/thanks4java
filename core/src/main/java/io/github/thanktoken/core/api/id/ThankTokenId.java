package io.github.thanktoken.core.api.id;

import io.github.thanktoken.core.api.attribute.AttributeReadTimestamp;
import io.github.thanktoken.core.api.context.ThankTokenContext;

/**
 * Identifier of a {@link io.github.thanktoken.core.api.token.ThankToken}.
 */
public interface ThankTokenId extends ThankTokenContext, AttributeReadTimestamp {

}
