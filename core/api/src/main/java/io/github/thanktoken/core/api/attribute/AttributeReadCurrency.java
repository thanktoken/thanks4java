/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import io.github.thanktoken.core.api.ThankDataObject;
import io.github.thanktoken.core.api.currency.ThankCurrency;

/**
 * Interface to {@link #getCurrency() read} the {@link ThankCurrency}.
 */
public interface AttributeReadCurrency extends ThankDataObject {

  /**
   * @return the {@link ThankCurrency} of this object.
   * @see io.github.thanktoken.core.api.header.ThankHeader#getCurrency()
   */
  ThankCurrency getCurrency();
}
