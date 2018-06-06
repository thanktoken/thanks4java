/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import io.github.thanktoken.core.api.ThankDataObject;
import io.github.thanktoken.core.api.header.ThankLocation;

/**
 * Interface to {@link #getLocation() read} the {@link ThankLocation}.
 */
public interface AttributeReadLocation extends ThankDataObject {

  /**
   * @return the {@link ThankLocation} this object belongs to.
   * @see io.github.thanktoken.core.api.header.ThankHeader#getLocation()
   */
  ThankLocation getLocation();
}
