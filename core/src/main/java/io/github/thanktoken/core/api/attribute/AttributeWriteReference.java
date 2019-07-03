/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;

/**
 * Interface to {@link #getReference() read} and {@link #setReference(ThankTokenReferenceType) write} the
 * {@link ThankTokenReferenceType}.
 *
 * @param <SELF> this type itself.
 */
public interface AttributeWriteReference<SELF extends AttributeWriteReference<SELF>> extends AttributeReadReference {

  /**
   * @param reference the new value of {@link #getReference()}.
   * @return this
   */
  SELF setReference(ThankTokenReferenceType reference);

}
