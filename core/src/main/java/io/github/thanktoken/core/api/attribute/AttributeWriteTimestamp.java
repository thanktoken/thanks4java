/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import io.github.thanktoken.core.api.timestamp.ThankTimestamp;

/**
 * Interface to {@link #getTimestamp() read} and {@link #setTimestamp(ThankTimestamp) write} the {@link #getTimestamp()
 * ThankTimestamp}.
 *
 * @param <SELF> this type itself.
 */
public interface AttributeWriteTimestamp<SELF extends AttributeWriteTimestamp<SELF>> extends AttributeReadTimestamp {

  /**
   * @param timestamp the new value of {@link #getTimestamp()}.
   * @return this
   */
  SELF setTimestamp(ThankTimestamp timestamp);

}
