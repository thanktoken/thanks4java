/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.util.Objects;

import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.value.ThankValue;

/**
 * Interface to {@link #getAmount() read} the {@link ThankValue}.
 */
public interface AttributeReadAmount extends ThankDataObject {

  /** Name of property {@link #getAmount()}. */
  String PROPERT_AMOUNT = "amount";

  /**
   * @see ThankToken#getValue()
   *
   * @return the fixed amount of this object when created.
   * @see io.github.thanktoken.core.api.token.header.ThankTokenHeader#getAmount()
   */
  ThankValue getAmount();

  /**
   * @return the required {@link #getAmount() amount}.
   */
  default ThankValue requireAmount() {

    ThankValue amount = getAmount();
    Objects.requireNonNull(amount, PROPERT_AMOUNT);
    return amount;
  }
}
