/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import io.github.thanktoken.core.api.currency.ThankCurrency;

/**
 * Interface to {@link #getCurrency() read} and {@link #setCurrency(ThankCurrency) write} the {@link ThankCurrency}.
 *
 * @param <SELF> this type itself.
 */
public interface AttributeWriteCurrency<SELF extends AttributeWriteCurrency<SELF>> extends AttributeReadCurrency {

  /**
   * @param currency the new value of {@link #getCurrency()}.
   * @return this
   */
  SELF setCurrency(ThankCurrency currency);

}
