/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import io.github.thanktoken.core.api.ThankDataObject;

/**
 * Interface to {@link #getPublicPurpose() read} the {@link #getPublicPurpose() public purpose}.
 */
public interface AttributeReadPublicPurpose extends ThankDataObject {

  /**
   * @return the public purpose of this object or {@code null} if not available.
   *
   * @see io.github.thanktoken.core.api.header.ThankHeader#getPublicPurpose()
   * @see io.github.thanktoken.core.api.transaction.ThankTransaction#getPublicPurpose()
   */
  String getPublicPurpose();
}
