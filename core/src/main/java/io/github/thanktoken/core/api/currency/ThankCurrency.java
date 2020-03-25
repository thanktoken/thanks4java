package io.github.thanktoken.core.api.currency;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

import io.github.thanktoken.core.api.context.ThankTokenContext;
import io.github.thanktoken.core.api.datatype.StringType;
import io.github.thanktoken.core.api.io.ThankValueParser;
import io.github.thanktoken.core.api.location.ThankLocation;
import io.github.thanktoken.core.api.target.ThankTarget;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.token.header.ThankTokenHeaderField;
import io.github.thanktoken.core.api.validate.ThankValidationFailureReceiver;
import io.github.thanktoken.core.api.validate.failure.ThankValidationFailureMismatch;
import io.github.thanktoken.core.api.validate.failure.ThankValidationFailureNoOption;
import io.github.thanktoken.core.api.validate.failure.ThankValidationFailureOutOfRange;
import io.github.thanktoken.core.api.value.ThankValue;

/**
 * A {@link ThankCurrency} represents the {@link ThankTokenHeader#getCurrency() currency} of a {@link ThankToken}.
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
   *        {@link ThankTokenHeader#getCurrency() currency}.
   * @param time the {@link Instant} for the point in time for which the value shall be calculated.
   * @return the {@link ThankValue} representing the value of the {@link ThankToken} for the given {@link Instant}.
   */
  public abstract ThankValue getValue(ThankToken token, Instant time);

  /**
   * @param header the {@link ThankTokenHeader} to validate. Only validates currency specific aspects such as
   *        {@link ThankTokenHeader#getAmount() amount} or {@link ThankTokenHeader#getTarget() target}.
   * @param receiver the {@link ThankValidationFailureReceiver} that collects potential validation failures.
   * @see io.github.thanktoken.core.api.validate.ThankValidator
   */
  public void validate(ThankTokenHeader header, ThankValidationFailureReceiver receiver) {

    // override to add custom validation
    validateField(header, ThankTokenHeaderField.CURRENCY, this, receiver);
    validateTarget(header, receiver);
    validateAmount(header, receiver);
  }

  /**
   * @param <T> generic type of the value.
   * @param header the {@link ThankTokenHeader} to check.
   * @param field the {@link ThankTokenHeaderField} to check.
   * @param expectedValue the expected value.
   * @param failureReceiver the {@link ThankValidationFailureReceiver} that collects potential validation failures.
   */
  protected <T> void validateField(ThankTokenHeader header, ThankTokenHeaderField<T> field, T expectedValue,
      ThankValidationFailureReceiver failureReceiver) {

    T actualValue = field.get(header);
    if (!Objects.equals(actualValue, expectedValue)) {
      failureReceiver
          .add(new ThankValidationFailureMismatch(field.getName(), "" + actualValue, "" + expectedValue, header));
    }
  }

  /**
   * @param <V> generic type of the value to validate.
   * @param header the {@link ThankTokenHeader} to {@link #validate(ThankTokenHeader, ThankValidationFailureReceiver)
   *        validate}.
   * @param actual the actual value to validate.
   * @param expected the {@link Set} containing the allowed values.
   */
  protected <V> void validate(ThankTokenHeader header, V actual, Set<V> expected) {

    if (!expected.contains(actual)) {
      StringBuilder sb = new StringBuilder();
      for (V e : expected) {
        if (sb.length() > 0) {
          sb.append('|');
        }
        sb.append(e);
      }
      throw new IllegalStateException(
          "Missing value " + actual + " in " + sb.toString() + " for header: " + header.toString());
    }
  }

  /**
   * @param header the {@link ThankTokenHeader} of which the {@link ThankTokenHeader#getTarget() target} is to be
   *        validated.
   * @param receiver the {@link ThankValidationFailureReceiver} that collects potential validation failures.
   */
  protected void validateTarget(ThankTokenHeader header, ThankValidationFailureReceiver receiver) {

    ThankValidationFailureNoOption.validate(ThankTokenHeaderField.TARGET, header, getValidTargets(), receiver);
  }

  /**
   * @return the {@link Set} with the {@link ThankTarget}s that are valid for this currency.
   */
  protected Set<ThankTarget> getValidTargets() {

    return ThankTarget.OFFICIAL_TARGETS;
  }

  /**
   * @param header the {@link ThankTokenHeader} of which the {@link ThankTokenHeader#getAmount() amount} is to be
   *        validated.
   * @param receiver the {@link ThankValidationFailureReceiver} that collects potential validation failures.
   */
  protected void validateAmount(ThankTokenHeader header, ThankValidationFailureReceiver receiver) {

    ThankValidationFailureOutOfRange.validate(ThankTokenHeaderField.AMOUNT, header, getMinAmount(), getMaxAmount(),
        receiver);
  }

  /**
   * This method allows a {@link ThankCurrency} to customize the {@link ThankValueParser} used for {@link ThankToken}s
   * of this currency. A currency might want to use a different implementation and format of a particular datatype as
   * e.g. {@link ThankLocation} and can "override" the {@link ThankValueParser} with this method.
   *
   * @param parser the {@link ThankValueParser} for
   *        {@link ThankValueParser#parseDefault(String, Class, ThankTokenContext) delegation} (default).
   * @param <T> generic type of the value.
   * @param stringValue the value as {@link String}.
   * @param type the {@link Class} reflecting the requested value type.
   * @param context the {@link ThankTokenContext} required for parsing context-sensitive fields.
   * @return the given {@code value} converted to the requested {@code type}.
   */
  public <T> T parse(ThankValueParser parser, String stringValue, Class<T> type, ThankTokenContext context) {

    return parser.parseDefault(stringValue, type, context);
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
   *        {@link ThankTokenHeader#getCurrency() currency}.
   * @param time the {@link Instant} for the point in time for which the value shall be calculated.
   * @param dailyFactor the percentage factor as interest rate per day (e.g. {@code 0.9995} for a daily demurage of
   *        {@code 0.05%}).
   * @return the {@link ThankValue} representing the value of the {@link ThankToken} for the given {@link Instant}.
   */
  protected ThankValue getValue(ThankToken token, Instant time, BigDecimal dailyFactor) {

    Objects.requireNonNull(token, "token");
    Objects.requireNonNull(time, "time");
    ThankTokenHeader header = token.requireHeader();
    assert (equals(header.getCurrency()));
    int ageInDays = header.requireTimestamp().getDaysTo(time);
    BigDecimal factor = dailyFactor.pow(ageInDays);
    BigDecimal amountValue = header.getAmount().bigDecimalValue().multiply(factor);
    return ThankValue.of(amountValue);
  }

  @Override
  public int getMaxLength() {

    return MAX_LENGTH;
  }

  /**
   * @return the absolute minimum {@link ThankTokenHeader#getAmount() amount} for {@link ThankToken}s accepted by this
   *         currency. In case you merge small and aged {@link ThankToken}s with low {@link ThankToken#getValue() value}
   *         you need to collect at least this amount.
   */
  public ThankValue getMinAmount() {

    return ThankValue.VALUE_0_01;
  }

  /**
   * @return the absolute maximum {@link ThankTokenHeader#getAmount() amount} for {@link ThankToken}s accepted by this
   *         currency. You may not merge {@link ThankToken}s up to an amount larger than this maximum.
   */
  public ThankValue getMaxAmount() {

    return ThankValue.VALUE_1000;
  }
}
