/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import io.github.thanktoken.core.api.ThankDataObject;
import io.github.thanktoken.core.api.header.ThankTarget;

/**
 * Interface to {@link #getTarget() read} the {@link ThankTarget}.
 */
public interface AttributeReadTarget extends ThankDataObject {

  /**
   * @return the {@link ThankTarget} of this object.
   * @see io.github.thanktoken.core.api.header.ThankHeader#getTarget()
   */
  ThankTarget getTarget();
}
