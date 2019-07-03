/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.util.Objects;

import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.location.ThankLocation;

/**
 * Interface to {@link #getLocation() read} the {@link ThankLocation}.
 */
public interface AttributeReadLocation extends ThankDataObject {

  /** Name of property {@link #getLocation()}. */
  String PROPERTY_LOCATION = "location";

  /**
   * @return the {@link ThankLocation} this object belongs to.
   * @see io.github.thanktoken.core.api.token.header.ThankTokenHeader#getLocation()
   */
  ThankLocation getLocation();

  /**
   * @return the required {@link #getLocation() location}.
   */
  default ThankLocation requireLocation() {

    ThankLocation location = getLocation();
    Objects.requireNonNull(location, PROPERTY_LOCATION);
    return location;
  }
}
