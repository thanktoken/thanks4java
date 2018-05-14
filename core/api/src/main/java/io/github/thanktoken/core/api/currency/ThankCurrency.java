package io.github.thanktoken.core.api.currency;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Set;

import io.github.thanktoken.core.api.ThankConstants;
import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.datatype.StringType;
import io.github.thanktoken.core.api.datatype.ThankValue;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.header.ThankHeaderField;
import io.github.thanktoken.core.api.header.ThankTarget;
import net.sf.mmm.util.exception.api.ObjectMismatchException;
import net.sf.mmm.util.exception.api.ValueOutOfRangeException;

/**
 * A {@link ThankCurrency} represents the {@link ThankHeader#getCurrency() currency} of a {@link ThankToken}.
 */
public abstract class ThankCurrency extends StringType {

  private static final int DAYS_PER_YEAR = 365;

  /**
   * The length of a {@link ThankCurrency} in bytes.
   *
   * @see #getMaxLength()
   */
  public static final int MAX_LENGTH = 8;

  /**
   * The constructor.
   *
   * @param value the name of the currency.
   */
  public ThankCurrency(String value) {

    super(value);
  }

  /**
   * @see ThankToken#getValue(Instant)
   *
   * @param token the {@link ThankToken} to calculate the value of. Shall have this {@link ThankCurrency} as
   *        {@link ThankHeader#getCurrency() currency}.
   * @param time the {@link Instant} for the point in time for which the value shall be calculated.
   * @return the {@link ThankValue} representing the value of the {@link ThankToken} for the given {@link Instant}.
   */
  public abstract ThankValue getValue(ThankToken token, Instant time);

  /**
   * @param header the {@link ThankHeader} to validate. Only validates currency specific aspects such as
   *        {@link ThankHeader#getAmount() amount} or {@link ThankHeader#getTarget() target}.
   * @see io.github.thanktoken.core.api.validate.ThankTokenValidator
   */
  public void validate(ThankHeader header) {

    // override to add custom validation
    if (header.getCurrency() != this) {
      throw new ObjectMismatchException(header.getCurrency(), this, header.toString());
    }
    validateTarget(header);
    validateAmount(header);
  }

  /**
   * @param <V> generic type of the value to validate.
   * @param header the {@link ThankHeader} to {@link #validate(ThankHeader) validate}.
   * @param value the actual value to validate.
   * @param expected the expected value.
   */
  protected <V> void validate(ThankHeader header, V value, V expected) {

    if (!Objects.equals(value, expected)) {
      throw new ObjectMismatchException(value, expected, header.toString());
    }
  }

  /**
   * @param <V> generic type of the value to validate.
   * @param header the {@link ThankHeader} to {@link #validate(ThankHeader) validate}.
   * @param value the actual value to validate.
   * @param expected the {@link Set} containing the allowed values.
   */
  protected <V> void validate(ThankHeader header, V value, Set<V> expected) {

    if (!expected.contains(value)) {
      StringBuilder sb = new StringBuilder();
      for (V e : expected) {
        if (sb.length() > 0) {
          sb.append('|');
        }
        sb.append(e);
      }
      throw new ObjectMismatchException(value, sb.toString(), header.toString());
    }
  }

  /**
   * @param header the {@link ThankHeader} of which the {@link ThankHeader#getTarget() target} is to be validated.
   */
  protected void validateTarget(ThankHeader header) {

    validate(header, header.getTarget(), ThankTarget.OFFICIAL_TARGETS);
  }

  /**
   * @param header the {@link ThankHeader} of which the {@link ThankHeader#getAmount() amount} is to be validated.
   */
  protected void validateAmount(ThankHeader header) {

    ValueOutOfRangeException.checkRange(header.getAmount(), getMinAmount(), getMaxAmount(), ThankHeaderField.AMOUNT.getName());
  }

  /**
   * @param name the {@link #getValue() name} of the requested {@link ThankCurrency}.
   * @return the requested {@link ThankCurrency}.
   * @see ThankCurrencyProvider#getCurrency(String)
   */
  public static ThankCurrency of(String name) {

    return ThankCurrencyProvider.get().getCurrency(name);
  }

  /**
   * @param factorPerDay the factor per day (e.g. {@code 0.0005} for {@code 0.05 %}).
   * @return the factor per year.
   */
  protected static BigDecimal getFactorPerYear(BigDecimal factorPerDay) {

    BigDecimal factorPerYear = factorPerDay.pow(DAYS_PER_YEAR);
    return factorPerYear;
  }

  /**
   * @see #getValue(ThankToken, Instant)
   *
   * @param token the {@link ThankToken} to calculate the value of. Shall have this {@link ThankCurrency} as
   *        {@link ThankHeader#getCurrency() currency}.
   * @param time the {@link Instant} for the point in time for which the value shall be calculated.
   * @param dailyFactor the percentage factor as interest rate per day (e.g. {@code 0.9995} for a daily demurage of
   *        {@code 0.05%}).
   * @return the {@link ThankValue} representing the value of the {@link ThankToken} for the given {@link Instant}.
   */
  protected ThankValue getValue(ThankToken token, Instant time, BigDecimal dailyFactor) {

    Objects.requireNonNull(token, "token");
    Objects.requireNonNull(time, "time");
    ThankHeader header = token.getHeader();
    Objects.requireNonNull(header, "header");
    assert (header.getCurrency().equals(this));
    LocalDate creationDay = LocalDate.from(header.getTimestamp().atZone(ThankConstants.UTC));
    LocalDate targetDay = LocalDate.from(time.atZone(ThankConstants.UTC));
    // int precision is sufficient for ~5 million (5.883.516) years
    int ageInDays = (int) ChronoUnit.DAYS.between(creationDay, targetDay);
    BigDecimal factor = dailyFactor.pow(ageInDays);
    BigDecimal value = header.getAmount().bigDecimalValue().multiply(factor);
    return ThankValue.of(value);
  }

  @Override
  public int getMaxLength() {

    return MAX_LENGTH;
  }

  /**
   * @return the absolute minimum {@link ThankHeader#getAmount() amount} for {@link ThankToken}s accepted by this
   *         currency. In case you merge small and aged {@link ThankToken}s with low {@link ThankToken#getValue() value}
   *         you need to collect at least this amount.
   */
  public ThankValue getMinAmount() {

    return ThankValue.VALUE_0_01;
  }

  /**
   * @return the absolute maximum {@link ThankHeader#getAmount() amount} for {@link ThankToken}s accepted by this
   *         currency. You may not merge {@link ThankToken}s up to an amount larger than this maximum.
   */
  public ThankValue getMaxAmount() {

    return ThankValue.VALUE_1000;
  }
}
