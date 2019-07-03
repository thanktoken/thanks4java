/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import io.github.thanktoken.core.api.location.ThankLocation;

/**
 * Interface to {@link #getLocation() read} and {@link #setLocation(ThankLocation) write} the {@link ThankLocation}.
 *
 * @param <SELF> this type itself.
 */
public interface AttributeWriteLocation<SELF extends AttributeWriteLocation<SELF>> extends AttributeReadLocation {

  /**
   * @param location the new value of {@link #getLocation()}.
   * @return this
   */
  SELF setLocation(ThankLocation location);

}
