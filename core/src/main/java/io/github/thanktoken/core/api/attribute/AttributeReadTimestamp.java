/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.util.Objects;

import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;

/**
 * Interface to {@link #getTimestamp() read the timestamp} as {@link ThankTimestamp}.
 */
public interface AttributeReadTimestamp extends ThankDataObject {

  /** Name of property {@link #getTimestamp()}. */
  String PROPERTY_TIMESTAMP = "timestamp";

  /**
   * @return the exact {@link ThankTimestamp timestamp} when this object was created.
   */
  ThankTimestamp getTimestamp();

  /**
   * @return the required {@link #getTimestamp() timestamp}.
   */
  default ThankTimestamp requireTimestamp() {

    ThankTimestamp timestamp = getTimestamp();
    Objects.requireNonNull(timestamp, PROPERTY_TIMESTAMP);
    return timestamp;
  }
}
