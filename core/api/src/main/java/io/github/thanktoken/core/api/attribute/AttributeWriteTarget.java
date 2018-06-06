/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import io.github.thanktoken.core.api.header.ThankTarget;

/**
 * Interface to {@link #getTarget() read} and {@link #setTarget(ThankTarget) write} the {@link ThankTarget}.
 *
 * @param <SELF> this type itself.
 */
public interface AttributeWriteTarget<SELF extends AttributeWriteTarget<SELF>> extends AttributeReadTarget {

  /**
   * @param target the new value of {@link #getTarget()}.
   * @return this
   */
  SELF setTarget(ThankTarget target);

}
