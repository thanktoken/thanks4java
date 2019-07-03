/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.util.Objects;

import io.github.thanktoken.core.api.data.ThankDataObject;

/**
 * Interface to {@link #getPublicPurpose() read} the {@link #getPublicPurpose() public purpose}.
 */
public interface AttributeReadPublicPurpose extends ThankDataObject {

  /** Name of property {@link #getPublicPurpose()}. */
  String PROPERTY_PUBLIC_PURPOSE = "publicPurpose";

  /**
   * @return the public purpose of this object or {@code null} if not available.
   *
   * @see io.github.thanktoken.core.api.transaction.ThankTransaction#getPublicPurpose()
   */
  String getPublicPurpose();

  /**
   * @return the required {@link #getPublicPurpose() public purpose}.
   */
  default String requirePublicPurpose() {

    String publicPurpose = getPublicPurpose();
    if ((publicPurpose == null) || publicPurpose.isEmpty()) {
      Objects.requireNonNull(null, PROPERTY_PUBLIC_PURPOSE);
    }
    return publicPurpose;
  }
}
