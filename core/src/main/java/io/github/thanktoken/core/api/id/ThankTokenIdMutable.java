package io.github.thanktoken.core.api.id;

import io.github.thanktoken.core.api.attribute.AttributeWriteAlgorithm;
import io.github.thanktoken.core.api.attribute.AttributeWriteCurrency;
import io.github.thanktoken.core.api.attribute.AttributeWriteLocation;
import io.github.thanktoken.core.api.attribute.AttributeWriteTimestamp;
import io.github.thanktoken.core.api.attribute.AttributeWriteVersion;
import io.github.thanktoken.core.api.reference.ThankTokenReference;

/**
 * Mutable interface of {@link ThankTokenReference}.
 *
 * @param <SELF> this type itself.
 */
public interface ThankTokenIdMutable<SELF extends ThankTokenIdMutable<SELF>> extends ThankTokenId, AttributeWriteVersion<SELF>,
    AttributeWriteAlgorithm<SELF>, AttributeWriteCurrency<SELF>, AttributeWriteLocation<SELF>, AttributeWriteTimestamp<SELF> {

}
