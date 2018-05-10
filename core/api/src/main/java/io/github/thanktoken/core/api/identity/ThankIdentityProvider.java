/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.identity;

import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;

/**
 * Interface for the provider of {@link ThankIdentity identities}.
 *
 * @see #findIdentity(SecurityPublicKey)
 */
public interface ThankIdentityProvider {

  /**
   * Lookup {@link ThankIdentity} for {@link SecurityPublicKey}. Will be requested online.
   *
   * @param publicKey the {@link SecurityPublicKey}.
   * @return the {@link ThankIdentity} corresponding to the given {@link SecurityPublicKey} or {@code null} if no
   *         identity is associated with the given {@link SecurityPublicKey} (pseudonymous user).
   */
  ThankIdentity findIdentity(SecurityPublicKey publicKey);

}
