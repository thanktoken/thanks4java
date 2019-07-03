package io.github.thanktoken.core.api.currency;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import io.github.thanktoken.core.api.address.ThankAddressType;
import io.github.thanktoken.core.api.target.ThankTarget;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.value.ThankValue;

/**
 * This is the default currency of a {@link ThankToken} which is called <em>Thanks</em>. The original unit for the the
 * {@link ThankTokenHeader#getAmount() amount} of a {@link ThankToken} in this {@link ThankTokenHeader#getCurrency()
 * currency} is called <em>{@link #THANKS Thanks}</em>. The unit of the actual {@link #getValue(ThankToken, Instant)
 * value} of a {@link ThankToken} is called <em>Thankpoints</em> (TP). This {@link #getValue(ThankToken, Instant) value}
 * decreases over time with a daily loss of {@link #RATE} (demurage).
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

  private static final Set<ThankTarget> VALID_TARGETS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(ThankTarget.PERSON_INCOME,
      ThankTarget.COMMUNITY_WORLD, ThankTarget.COMMUNITY_SECTOR, ThankTarget.COMMUNITY_AREA, ThankTarget.COMMUNITY_REGION)));

  /**
   * The constructor.
   */
  private Thanks() {

    super(THANKS);
  }

  @Override
  protected Set<ThankTarget> getValidTargets() {

    return VALID_TARGETS;
  }

  /**
   * @param target the {@link ThankTarget} the token was created for.
   * @return the proper {@link ThankTokenHeader#getAmount() amount} for creation according to the given
   *         {@link ThankTarget}.
   */
  @Override
  public ThankValue getAmount(ThankTarget target, ThankAddressType addressType) {

    if (addressType.isNaturalPerson()) {
      if (ThankAddressType.NATURAL_PERSON_CHILD.equals(addressType)) {
        if (target.isPersonIncome()) {
          return ThankValue.VALUE_16;
        } else if (target.isTypeCommunity()) {
          switch (target.getLevel()) {
            case 0:
              return ThankValue.VALUE_1;
            case 1:
              return ThankValue.VALUE_2;
            case 2:
              return ThankValue.VALUE_4;
            case 3:
              return ThankValue.VALUE_9;
          }
        }
      } else {
        if (target.isPersonIncome()) {
          return ThankValue.VALUE_32;
        } else if (target.isTypeCommunity()) {
          switch (target.getLevel()) {
            case 0:
              return ThankValue.VALUE_2;
            case 1:
              return ThankValue.VALUE_4;
            case 2:
              return ThankValue.VALUE_8;
            case 3:
              return ThankValue.VALUE_18;
          }
        }
      }
    }
    return null;
  }

  @Override
  public BigDecimal getDailyRetainingFactor() {

    return FACTOR;
  }

}
