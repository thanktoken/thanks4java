/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.identity;

import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.context.ThankTokenContext;
import io.github.thanktoken.core.api.io.ThankNetworkException;

/**
 * Interface for the provider of {@link ThankIdentity identities}.
 *
 * @see #findIdentity(ThankAddress, ThankTokenContext)
 */
public interface ThankIdentityProvider {

  /**
   * Lookup {@link ThankIdentity} for {@link ThankAddress}. Will be requested online.
   *
   * @param address the {@link ThankAddress}.
   * @param context TODO
   * @return the {@link ThankIdentity} corresponding to the given {@link ThankAddress} or {@code null} if no identity is
   *         associated with the given {@link ThankAddress} (pseudonymous or non-existing user).
   * @throws ThankNetworkException in case of a (temporary) network error.
   */
  ThankIdentity findIdentity(ThankAddress address, ThankTokenContext context) throws ThankNetworkException;

}
