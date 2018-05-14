package io.github.thanktoken.core.api.currency;

import java.math.BigDecimal;
import java.time.Instant;

import io.github.thanktoken.core.api.ThankToken;

/**
 * This {@link ThankCurrency currency} is called <em>Gradido</em> (g). The {@link #getValue(ThankToken, Instant) value}
 * of a {@link ThankToken} decreases over time with a daily loss of {@link #RATE} (demurage). In one year (365 days) the
 * loss is {@code 50%}.
 */
public class Gradido extends ThankSustainableCurrency {

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
  public BigDecimal getDailyRetainingFactor() {

    return FACTOR;
  }

}
