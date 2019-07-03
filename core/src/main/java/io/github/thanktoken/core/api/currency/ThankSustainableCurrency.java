/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.currency;

import java.math.BigDecimal;
import java.time.Instant;

import io.github.thanktoken.core.api.address.ThankAddressType;
import io.github.thanktoken.core.api.target.ThankTarget;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.token.header.ThankTokenHeaderField;
import io.github.thanktoken.core.api.validate.ThankValidationFailureReceiver;
import io.github.thanktoken.core.api.validate.failure.ThankValidationFailureMismatch;
import io.github.thanktoken.core.api.value.ThankValue;

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
   * @return the proper {@link ThankTokenHeader#getAmount() amount} for creation according to the given
   *         {@link ThankTarget}.
   */
  public ThankValue getAmount(ThankTarget target, ThankAddressType addressType) {

    if (addressType.isNaturalPerson()) {
      if (target.isPersonIncome()) {
        return ThankValue.VALUE_32;
      }
      if (ThankAddressType.NATURAL_PERSON_CHILD.equals(addressType)) {

      } else {

      }
    }
    return null;
  }

  /**
   * @return the factor less than {@code 1} for the retaining of the {@link #getValue(ThankToken, Instant) value} per
   *         day.
   */
  public abstract BigDecimal getDailyRetainingFactor();

  @Override
  protected void validateAmount(ThankTokenHeader header, ThankValidationFailureReceiver receiver) {

    super.validateAmount(header, receiver);
    if (header.getReference() != null) {
      // token was created by split or merge - any positive value is possible
      return;
    }
    ThankValue expectedAmount = getAmount(header.getTarget(), header.getRecipient().getType());
    ThankValidationFailureMismatch.validate(ThankTokenHeaderField.AMOUNT, header, expectedAmount, receiver);
  }

  @Override
  public ThankValue getValue(ThankToken token, Instant time) {

    ThankValue value = getValue(token, time, getDailyRetainingFactor());
    int txCount = token.getTransactions().size();
    return ThankValue.ofUnscaled(value.getUnscaledValue() - txCount);
  }

}
