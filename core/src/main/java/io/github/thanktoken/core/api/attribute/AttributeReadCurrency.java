/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.util.Objects;

import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.data.ThankDataObject;

/**
 * Interface to {@link #getCurrency() read} the {@link ThankCurrency}.
 */
public interface AttributeReadCurrency extends ThankDataObject {

  /** Name of property {@link #getCurrency()}. */
  String PROPERTY_CURRENCY = "currency";

  /**
   * @return the {@link ThankCurrency} of this object.
   * @see io.github.thanktoken.core.api.token.header.ThankTokenHeader#getCurrency()
   */
  ThankCurrency getCurrency();

  /**
   * @return the required {@link #getCurrency() currency}.
   */
  default ThankCurrency requireCurrency() {

    ThankCurrency currency = getCurrency();
    Objects.requireNonNull(currency, PROPERTY_CURRENCY);
    return currency;
  }

}
