/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.time.Instant;

import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.value.ThankValue;

/**
 * Interface to {@link #getValue() read} the {@link ThankValue}.
 */
public interface AttributeReadValue extends ThankDataObject {

  /**
   * @return the current value of this object in the actual
   *         {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader#getCurrency() currency}. The value is determined
   *         from the original {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader#getAmount() amount} and changes
   *         over the time {@link ThankCurrency#getValue(ThankToken, Instant) according} to its
   *         {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader#getCurrency() currency}.
   * @see #getValue(Instant)
   * @see io.github.thanktoken.core.api.token.ThankToken#getValue()
   */
  default ThankValue getValue() {

    return getValue(Instant.now());
  }

  /**
   * @param timestamp is the point in time for which the value shall be calculated. Should not be before the
   *        {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader#getTimestamp() creation timestamp}.
   * @return the value of this object at the given {@link Instant}.
   * @see #getValue()
   */
  ThankValue getValue(Instant timestamp);
}
