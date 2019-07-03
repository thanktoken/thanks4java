package io.github.thanktoken.core.api.io;

/**
 * Implementation of {@link ThankValueParser}.
 */
public class ThankValueParserDefault implements ThankValueParser {

  private static final ThankValueParser INSTANCE = new ThankValueParserDefault();

  /**
   * @return the singleton instance of {@link ThankValueParser}.
   */
  public static ThankValueParser get() {

    return INSTANCE;
  }

}