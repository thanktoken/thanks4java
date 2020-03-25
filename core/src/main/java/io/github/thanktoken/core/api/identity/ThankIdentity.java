/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.identity;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;

import io.github.mmm.crypto.asymmetric.sign.SignatureBinary;
import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.attribute.AttributeReadLocation;
import io.github.thanktoken.core.api.token.ThankToken;

/**
 * Identity of a participant or in other words a user of {@link ThankToken}s.
 */
public interface ThankIdentity extends AttributeReadLocation {

  /**
   * @return the {@link ThankAddress} of the identity.
   */
  ThankAddress getAddress();

  /**
   * @return the {@link Collection} with the {@link SignatureBinary}s that verified this identity.
   */
  Collection<SignatureBinary> getSignatures();

  /**
   * @return the unmodifiable {@link Map} with the optional properties of the identity.
   */
  Map<String, String> getProperties();

  /**
   * @return {@code true} if (currently) valid, {@code false} otherwise.
   */
  default boolean isValid() {

    return isValid(Instant.now());
  }

  /**
   * @param timestamp the {@link Instant moment in time} when this identify was potentially valid.
   * @return {@code true} if valid for the given {@code timestamp}, {@code false} otherwise.
   */
  boolean isValid(Instant timestamp);

}
