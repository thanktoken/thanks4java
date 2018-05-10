package io.github.thanktoken.core.api.strategy;

import io.github.thanktoken.core.api.ThankToken;

/**
 * This is the {@link ThankStrategy} that delegates to the according {@link ThankVersionStrategy} for the proper
 * {@link ThankToken#getVersion() version} of a given {@link ThankToken}.
 */
public interface ThankGlobalStrategy extends ThankStrategy {

}
