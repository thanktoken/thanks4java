/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.time.Instant;

import io.github.thanktoken.core.api.ThankDataObject;

/**
 * Interface to {@link #getTimestamp() read the timestamp} as {@link Instant}.
 */
public interface AttributeReadTimestamp extends ThankDataObject {

  /**
   * @return the exact {@link Instant} when this object was created.
   */
  Instant getTimestamp();

}
