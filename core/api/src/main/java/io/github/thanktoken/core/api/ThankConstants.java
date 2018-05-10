package io.github.thanktoken.core.api;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.io.ThankReader;
import io.github.thanktoken.core.api.io.ThankWriter;

/**
 * Constants for {@link ThankToken}, {@link ThankHeader} and other data of this project.
 *
 * @see ThankReader
 * @see ThankWriter
 */
public interface ThankConstants {

  /** The unicode (UTF-8) {@link Charset}. */
  Charset UTF_8 = Charset.forName("UTF-8");

  /** The US-ASCII {@link Charset}. */
  Charset ASCII = Charset.forName("US-ASCII");

  /** Constant for universal time zone. */
  ZoneId UTC = ZoneId.of("UTC");

  /** The {@link DateTimeFormatter} used for format and parse date/time data such as {@link Instant}. */
  DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss.SSSS").withZone(UTC);

  /** {@link BigDecimal} of 0.01. */
  BigDecimal AMOUNT_0000_01 = new BigDecimal("0.01");

  /** {@link BigDecimal} of 0.10. */
  BigDecimal AMOUNT_0000_10 = new BigDecimal("0.10");

  /** {@link BigDecimal} of 1.00. */
  BigDecimal AMOUNT_0001_00 = new BigDecimal("1.00");

  /** {@link BigDecimal} of 10.00. */
  BigDecimal AMOUNT_0010_00 = new BigDecimal("10.00");

  /** {@link BigDecimal} of 100.00. */
  BigDecimal AMOUNT_0100_00 = new BigDecimal("100.00");

  /** {@link BigDecimal} of 1000.00. */
  BigDecimal AMOUNT_1000_00 = new BigDecimal("1000.00");

  /** {@link BigDecimal} of 33.33. */
  BigDecimal AMOUNT_0033_33 = new BigDecimal("33.33");

  /** {@link BigDecimal} of 11.11. */
  BigDecimal AMOUNT_0011_11 = new BigDecimal("11.11");

}
