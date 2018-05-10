package io.github.thanktoken.core.api.currency;

import java.math.BigDecimal;
import java.time.Instant;

import io.github.thanktoken.core.api.ThankConstants;
import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.header.ThankTarget;

/**
 * This is the default currency of a {@link ThankToken} which is called <em>Thanks</em>. The original unit for the the
 * {@link ThankHeader#getAmount() amount} of a {@link ThankToken} in this {@link ThankHeader#getCurrency() currency} is
 * called <em>{@link #THANKS Thanks}</em>. The unit of the actual {@link #getValue(ThankToken, Instant) value} of a
 * {@link ThankToken} is called <em>Thankpoints</em> (TP). This {@link #getValue(ThankToken, Instant) value} decreases
 * over time with a daily loss of {@link #RATE} (demurage).
 */
public class Thanks extends ThankCurrency {

  /** The rate of negative interest (demurage) per day on thank-points (1/2000). */
  public static final BigDecimal RATE = BigDecimal.valueOf(0.0005);

  /** The factor as <code>100% - {@link #RATE}</code>. */
  public static final BigDecimal FACTOR = BigDecimal.ONE.subtract(RATE);

  /** The {@link #getValue() name} of this currency. */
  public static final String THANKS = "Thanks";

  /** The singleton instance. */
  public static final Thanks INSTANCE = new Thanks();

  /**
   * The constructor.
   */
  private Thanks() {

    super(THANKS);
  }

  @Override
  public BigDecimal getValue(ThankToken token, Instant time) {

    return getValue(token, time, FACTOR);
  }

  /**
   * @param header the {@link ThankHeader} of which the {@link ThankHeader#getAmount() amount} is to be validated.
   */
  @Override
  protected void validateAmount(ThankHeader header) {

    if (header.getReference() != null) {
      // token was created by split or merge - any positive value is possible
      return;
    }
    if (ThankTarget.PERSON_INCOME.equals(header.getTarget())) {
      // token amount of 33/day are 12045/year (~1000/month)
      validate(header, header.getAmount(), ThankConstants.AMOUNT_0033_33);
    } else {
      // other income is spread equally across the 3 levels of the hierarchy (community, state, country)
      validate(header, header.getAmount(), ThankConstants.AMOUNT_0011_11);
    }
  }

}
