/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.currency;

import java.math.BigDecimal;
import java.time.Instant;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.datatype.ThankValue;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.header.ThankTarget;

/**
 * A {@link ThankCurrency} that is sustainable because it can be created per person and its
 * {@link #getValue(ThankToken, Instant) value} continuously decreases.
 */
public abstract class ThankSustainableCurrency extends ThankCurrency {

  /**
   * The constructor.
   *
   * @param value the name of the currency.
   */
  public ThankSustainableCurrency(String value) {

    super(value);
  }

  /**
   * @param target the {@link ThankTarget} the token was created for.
   * @return the proper {@link ThankHeader#getAmount() amount} for creation according to the given {@link ThankTarget}.
   */
  public ThankValue getAmount(ThankTarget target) {

    if (ThankTarget.PERSON_INCOME.equals(target)) {
      // amount of 33.33333333/day are 12166.66666545/year (~1013.8/month)
      return ThankValue.VALUE_33_33333333;
    } else {
      // other income is spread equally across the 3 levels of the hierarchy (community, state, country)
      return ThankValue.VALUE_11_11111111;
    }
  }

  /**
   * @return the factor less than {@code 1} for the retaining of the {@link #getValue(ThankToken, Instant) value} per
   *         day.
   */
  public abstract BigDecimal getDailyRetainingFactor();

  @Override
  protected void validateAmount(ThankHeader header) {

    super.validateAmount(header);
    if (header.getReference() != null) {
      // token was created by split or merge - any positive value is possible
      return;
    }
    validate(header, header.getAmount(), getAmount(header.getTarget()));
  }

  @Override
  public ThankValue getValue(ThankToken token, Instant time) {

    ThankValue value = getValue(token, time, getDailyRetainingFactor());
    int txCount = token.getTransactions().size();
    return ThankValue.ofUnscaled(value.getUnscaledValue() - txCount);
  }

}
