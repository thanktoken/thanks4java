/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.time.Instant;

/**
 * Interface to {@link #getTimestamp() read} and {@link #setTimestamp(Instant) write} the {@link #getTimestamp()
 * timestamp}.
 *
 * @param <SELF> this type itself.
 */
public interface AttributeWriteTimestamp<SELF extends AttributeWriteTimestamp<SELF>> extends AttributeReadTimestamp {

  /**
   * @param timestamp the new value of {@link #getTimestamp()}.
   * @return this
   */
  SELF setTimestamp(Instant timestamp);

}
