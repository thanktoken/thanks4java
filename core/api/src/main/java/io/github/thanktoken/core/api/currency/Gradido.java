package io.github.thanktoken.core.api.currency;

import java.math.BigDecimal;
import java.time.Instant;

import io.github.thanktoken.core.api.ThankConstants;
import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.header.ThankTarget;

/**
 * This {@link ThankCurrency currency} is called <em>Gradido</em> (g). The {@link #getValue(ThankToken, Instant) value}
 * of a {@link ThankToken} decreases over time with a daily loss of {@link #RATE} (demurage). In one year (365 days) the
 * loss is {@code 50%}.
 */
public class Gradido extends ThankCurrency {

  /** The rate of negative interest (demurage) per day on gradido (results in {@code 0,5%} ppa for 365 days/year). */
  public static final BigDecimal RATE = BigDecimal.valueOf(0.001897231348);

  /** The factor as <code>100% - {@link #RATE}</code>. */
  public static final BigDecimal FACTOR = BigDecimal.ONE.subtract(RATE);

  /** The {@link #getValue() name} of this currency. */
  public static final String GRADIDO = "Gradido";

  /** The singleton instance. */
  public static final Gradido INSTANCE = new Gradido();

  /**
   * The constructor.
   */
  private Gradido() {

    super(GRADIDO);
  }

  @Override
  public BigDecimal getValue(ThankToken token, Instant time) {

    return getValue(token, time, FACTOR);
  }

  @Override
  protected void validateAmount(ThankHeader header) {

    if (header.getReference() != null) {
      // token was created by split or merge - any positive value is possible
      return;
    }
    if (ThankTarget.PERSON_INCOME == header.getTarget()) {
      // token amount of 33/day are 12045/year (~1000/month)
      validate(header, header.getAmount(), ThankConstants.AMOUNT_0033_33);
    } else {
      // other income is spread equally across the 3 levels of the hierarchy (community, state, country)
      validate(header, header.getAmount(), ThankConstants.AMOUNT_0011_11);
    }
  }

}
