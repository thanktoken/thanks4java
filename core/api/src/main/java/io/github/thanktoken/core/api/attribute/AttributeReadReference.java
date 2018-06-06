/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import io.github.thanktoken.core.api.ThankDataObject;
import io.github.thanktoken.core.api.reference.ThankTokenReference;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;

/**
 * Interface to {@link #getReference() read} the {@link ThankTokenReference}.
 */
public interface AttributeReadReference extends ThankDataObject {

  /**
   * @return the optional {@link ThankTokenReferenceType} of this object or {@code null} for no reference.
   * @see io.github.thanktoken.core.api.header.ThankHeader#getReference()
   * @see io.github.thanktoken.core.api.transaction.ThankTransaction#getReference()
   */
  ThankTokenReferenceType getReference();
}
