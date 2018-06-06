/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import io.github.thanktoken.core.api.ThankDataObject;
import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.datatype.ThankValue;

/**
 * Interface to {@link #getAmount() read} the {@link ThankValue}.
 */
public interface AttributeReadAmount extends ThankDataObject {

  /**
   * @see ThankToken#getValue()
   *
   * @return the fixed amount of this object when created.
   * @see io.github.thanktoken.core.api.header.ThankHeader#getAmount()
   */
  ThankValue getAmount();
}
