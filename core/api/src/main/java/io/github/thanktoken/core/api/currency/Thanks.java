package io.github.thanktoken.core.api.currency;

import java.math.BigDecimal;
import java.time.Instant;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.header.ThankHeader;

/**
 * This is the default currency of a {@link ThankToken} which is called <em>Thanks</em>. The original unit for the the
 * {@link ThankHeader#getAmount() amount} of a {@link ThankToken} in this {@link ThankHeader#getCurrency() currency} is
 * called <em>{@link #THANKS Thanks}</em>. The unit of the actual {@link #getValue(ThankToken, Instant) value} of a
 * {@link ThankToken} is called <em>Thankpoints</em> (TP). This {@link #getValue(ThankToken, Instant) value} decreases
 * over time with a daily loss of {@link #RATE} (demurage).
 */
public class Thanks extends ThankSustainableCurrency {

  /**
   * The rate of negative interest (demurage) per day on thank-points (1/2000). That results in 16.685338% ppa.
   */
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
  public BigDecimal getDailyRetainingFactor() {

    return FACTOR;
  }

}
