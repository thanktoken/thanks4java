/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.util.Objects;

import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.reference.ThankTokenReference;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;

/**
 * Interface to {@link #getReference() read} the {@link ThankTokenReference}.
 */
public interface AttributeReadReference extends ThankDataObject {

  /** Name of property {@link #getReference()}. */
  String PROPERTY_REFERENCE = "reference";

  /**
   * @return the optional {@link ThankTokenReferenceType} of this object or {@code null} for no reference.
   * @see io.github.thanktoken.core.api.token.header.ThankTokenHeader#getReference()
   * @see io.github.thanktoken.core.api.transaction.ThankTransaction#getReference()
   */
  ThankTokenReferenceType getReference();

  /**
   * @return the required {@link #getReference() reference}.
   */
  default ThankTokenReferenceType requireReference() {

    ThankTokenReferenceType reference = getReference();
    Objects.requireNonNull(reference, PROPERTY_REFERENCE);
    return reference;
  }
}
