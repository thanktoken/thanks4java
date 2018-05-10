package io.github.thanktoken.core.api.currency;

import java.util.HashMap;
import java.util.Map;

/**
 * This class gives {@link #getCurrency(String) access} to the available instances of {@link ThankCurrency}. It can be
 * extended what allows to {@link #register(ThankCurrency) register} custom currencies. You need to instantiate your
 * sub-class on early setup to ensure that {@link #get()} will provide your custom implementation.
 *
 * @see ThankCurrency#of(String)
 */
public class ThankCurrencyProvider {

  private static ThankCurrencyProvider INSTANCE;

  private final Map<String, ThankCurrency> currencyMap;

  /**
   * The constructor.
   */
  public ThankCurrencyProvider() {

    super();
    INSTANCE = this;
    this.currencyMap = new HashMap<>();
    register(Thanks.INSTANCE);
    register(Gradido.INSTANCE);
  }

  /**
   * @param currency the {@link ThankCurrency} to register.
   */
  protected void register(ThankCurrency currency) {

    ThankCurrency duplicate = this.currencyMap.put(currency.getValue(), currency);
    if (duplicate != null) {
      throw new IllegalStateException("Currency name '" + currency.getValue() + "' conflict with " + duplicate + " and " + currency);
    }
  }

  /**
   * @return the singleton instance of this {@link ThankCurrencyProvider}.
   */
  public static final ThankCurrencyProvider get() {

    if (INSTANCE == null) {
      ThankCurrencyProvider provider = new ThankCurrencyProvider();
      assert (INSTANCE == provider);
    }
    return INSTANCE;
  }

  /**
   * @param name the name of the currency.
   * @return the {@link ThankCurrency} {@link ThankCurrency#getValue() with} the given {@code name}.
   */
  public ThankCurrency getCurrency(String name) {

    ThankCurrency currency = this.currencyMap.get(name.trim());
    if (currency == null) {
      throw new IllegalArgumentException("Unknown currency: " + name);
    }
    return currency;
  }

}
