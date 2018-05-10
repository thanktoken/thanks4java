/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.identity;

import java.util.Collection;
import java.util.Map;

import io.github.thanktoken.core.api.ThankToken;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;
import net.sf.mmm.security.api.sign.SecuritySignature;

/**
 * Identity of a participant or in other words a user of {@link ThankToken}s.
 */
public interface ThankIdentity {

  /**
   * @return the unique ID of this identity.
   */
  String getId();

  /**
   * @return the {@link ThankIdentityType} of this identity.
   */
  ThankIdentityType getType();

  /**
   * @return the {@link Collection} with the {@link SecuritySignature}s that verified this identity.
   */
  Collection<SecuritySignature> getSignatures();

  /**
   * @return the unmodifiable {@link Map} with the optional properties of the identity.
   */
  Map<String, String> getProperties();

  /**
   * @return the latest {@link SecurityPublicKey} of the identity.
   */
  SecurityPublicKey getLatestPublicKey();

}
