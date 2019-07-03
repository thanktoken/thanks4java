/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.util.Objects;

import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.target.ThankTarget;

/**
 * Interface to {@link #getTarget() read} the {@link ThankTarget}.
 */
public interface AttributeReadTarget extends ThankDataObject {

  /** Name of property {@link #getTarget()}. */
  String PROPERTY_TARGET = "target";

  /**
   * @return the {@link ThankTarget} of this object.
   * @see io.github.thanktoken.core.api.token.header.ThankTokenHeader#getTarget()
   */
  ThankTarget getTarget();

  /**
   * @return the required {@link #getTarget() target}.
   */
  default ThankTarget requireTarget() {

    ThankTarget target = getTarget();
    Objects.requireNonNull(target, PROPERTY_TARGET);
    return target;
  }
}
