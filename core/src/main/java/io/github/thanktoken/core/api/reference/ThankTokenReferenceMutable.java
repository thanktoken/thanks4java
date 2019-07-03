package io.github.thanktoken.core.api.reference;

import io.github.thanktoken.core.api.attribute.AttributeWriteReferenceType;
import io.github.thanktoken.core.api.id.ThankTokenIdMutable;

/**
 * Mutable interface of {@link ThankTokenReference}.
 *
 * @param <SELF> this type itself.
 */
public interface ThankTokenReferenceMutable<SELF extends ThankTokenReferenceMutable<SELF>>
    extends ThankTokenReference, ThankTokenIdMutable<SELF>, AttributeWriteReferenceType<SELF> {

}
