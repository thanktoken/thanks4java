package io.github.thanktoken.core.api.context;

import io.github.thanktoken.core.api.attribute.AttributeReadAlgorithm;
import io.github.thanktoken.core.api.attribute.AttributeReadCurrency;
import io.github.thanktoken.core.api.attribute.AttributeReadLocation;
import io.github.thanktoken.core.api.attribute.AttributeReadVersion;
import io.github.thanktoken.core.api.token.ThankToken;

/**
 * Context that holds the key properties for a {@link ThankToken}.
 */
public interface ThankTokenContext extends AttributeReadVersion, AttributeReadAlgorithm, AttributeReadLocation, AttributeReadCurrency {

}
