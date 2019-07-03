package io.github.thanktoken.core.api.context;

import java.util.Objects;

import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * Helper class for {@link ThankTokenContext}.
 *
 * @since 1.0.0
 */
public class ThankTokenContextHelper {

  /**
   * @param context the {@link ThankTokenContext}.
   * @return the {@link ThankTokenContext#getAlgorithm() algorithm}.
   */
  public static ThankAlgorithm requireAlgorithm(ThankTokenContext context) {

    Objects.requireNonNull(context, "context");
    ThankAlgorithm algorithm = context.getAlgorithm();
    Objects.requireNonNull(algorithm, "context.algorithm");
    return algorithm;
  }

  /**
   * @param context the {@link ThankTokenContext}.
   * @return the {@link ThankTokenContext#getCurrency() currency}.
   */
  public static ThankCurrency requireCurrency(ThankTokenContext context) {

    Objects.requireNonNull(context, "context");
    ThankCurrency currency = context.getCurrency();
    Objects.requireNonNull(currency, "context.currency");
    return currency;
  }

  /**
   * @param context the {@link ThankTokenContext}.
   * @return the {@link ThankTokenContext#getVersion() version}.
   */
  public static ThankVersion requireVersion(ThankTokenContext context) {

    Objects.requireNonNull(context, "context");
    ThankVersion version = context.getVersion();
    Objects.requireNonNull(version, "context.version");
    return version;
  }

}
