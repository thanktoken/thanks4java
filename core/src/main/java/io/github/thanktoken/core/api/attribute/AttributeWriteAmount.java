/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import io.github.thanktoken.core.api.value.ThankValue;

/**
 * Interface to {@link #getAmount() read} and {@link #setAmount(ThankValue) write} the {@link #getAmount() amount} as
 * {@link ThankValue}.
 *
 * @param <SELF> this type itself.
 */
public interface AttributeWriteAmount<SELF extends AttributeWriteAmount<SELF>> extends AttributeReadAmount {

  /**
   * @param amount the new value of {@link #getAmount()}.
   * @return this
   */
  SELF setAmount(ThankValue amount);

}
