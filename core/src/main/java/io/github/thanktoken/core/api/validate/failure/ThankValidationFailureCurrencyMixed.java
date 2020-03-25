package io.github.thanktoken.core.api.validate.failure;

import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.validate.ThankValidationFailureReceiver;

/**
 * {@link ThankValidationFailure} if two different currencies are mixed.
 *
 * @since 1.0.0
 */
public class ThankValidationFailureCurrencyMixed extends ThankValidationFailure {

  /** {@link #getId() ID} of this failure. */
  public static final String ID = "CURMX";

  /**
   * The constructor.
   *
   * @param currency the initial {@link ThankCurrency}.
   * @param extraCurrency the unexpected additional {@link ThankCurrency}.
   * @param data the optional {@link ThankDataObject}.
   */
  public ThankValidationFailureCurrencyMixed(ThankCurrency currency, ThankCurrency extraCurrency,
      ThankDataObject data) {

    this(currency, extraCurrency, data, null);
  }

  /**
   * The constructor.
   *
   * @param currency the initial {@link ThankCurrency}.
   * @param extraCurrency the unexpected additional {@link ThankCurrency}.
   * @param data the optional {@link ThankDataObject}.
   * @param error the optional {@link Throwable}.
   */
  public ThankValidationFailureCurrencyMixed(ThankCurrency currency, ThankCurrency extraCurrency, ThankDataObject data,
      Throwable error) {

    super(ID, "Tokens with currency " + currency + " may not be mixed with currency " + extraCurrency + ".", data,
        error);
  }

  /**
   * @param currency the initial {@link ThankCurrency}.
   * @param extraCurrency the additional {@link ThankCurrency}.
   * @param data the {@link ThankDataObject} to validate.
   * @param receiver the {@link ThankValidationFailureReceiver} that collects potential validation failures.
   */
  public static void validate(ThankCurrency currency, ThankCurrency extraCurrency, ThankDataObject data,
      ThankValidationFailureReceiver receiver) {

    if ((extraCurrency != null) && !extraCurrency.equals(currency)) {
      receiver.add(new ThankValidationFailureCurrencyMixed(currency, extraCurrency, data));
    }
  }

}
