package io.github.thanktoken.core.api.currency;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import io.github.thanktoken.core.api.address.ThankAddressHeader;
import io.github.thanktoken.core.api.target.ThankTarget;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.value.ThankValue;

/**
 * This {@link ThankCurrency currency} is called <em>Gradido</em> (g). The {@link #getValue(ThankToken, Instant) value}
 * of a {@link ThankToken} decreases over time with a daily loss of {@link #RATE} (demurage). In one year (365 days) the
 * loss is {@code 50%}.
 */
public class Gradido extends ThankSustainableCurrency {

  /**
   * The rate of negative interest (demurage) per day on gradido (results in {@code 0,5} (50%) ppa for 365 days/year).
   */
  public static final BigDecimal RATE = BigDecimal.valueOf(0.001897231348);

  /** The factor as <code>100% - {@link #RATE}</code>. */
  public static final BigDecimal FACTOR = BigDecimal.ONE.subtract(RATE);

  /** The {@link #getValue() name} of this currency. */
  public static final String GRADIDO = "Gradido";

  /** The singleton instance. */
  public static final Gradido INSTANCE = new Gradido();

  private static final Set<ThankTarget> VALID_TARGETS = Collections
      .unmodifiableSet(new HashSet<>(Arrays.asList(ThankTarget.PERSON_INCOME, ThankTarget.COMMUNITY_SECTOR,
          ThankTarget.COMMUNITY_AREA, ThankTarget.COMMUNITY_REGION, ThankTarget.SUSTAINABILITY_SECTOR,
          ThankTarget.SUSTAINABILITY_AREA, ThankTarget.SUSTAINABILITY_REGION)));

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

  @Override
  protected Set<ThankTarget> getValidTargets() {

    return VALID_TARGETS;
  }

  @Override
  public ThankValue getAmount(ThankTarget target, ThankAddressHeader header) {

    if (target.isPersonIncome()) {
      // amount of 33.33333333/day are 12166.66666545/year (~1013.8/month)
      return ThankValue.VALUE_33_33333333;
    } else {
      // other income is spread equally across the 3 levels of the hierarchy (sector, area, region)
      return ThankValue.VALUE_11_11111111;
    }
  }

}
