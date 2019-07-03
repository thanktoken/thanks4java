package io.github.thanktoken.core.api.token.header;

import io.github.thanktoken.core.api.attribute.AttributeWriteAmount;
import io.github.thanktoken.core.api.attribute.AttributeWriteRecipient;
import io.github.thanktoken.core.api.attribute.AttributeWriteSignature;
import io.github.thanktoken.core.api.attribute.AttributeWriteTarget;
import io.github.thanktoken.core.api.id.ThankTokenIdMutable;

/**
 * Mutable interface for {@link ThankTokenHeader}.
 *
 * @param <SELF> this type itself.
 */
public interface ThankTokenHeaderMutable<SELF extends ThankTokenHeaderMutable<SELF>> extends ThankTokenHeader, ThankTokenIdMutable<SELF>,
    AttributeWriteAmount<SELF>, AttributeWriteTarget<SELF>, AttributeWriteRecipient<SELF>, AttributeWriteSignature<SELF> {

}
