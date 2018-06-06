package io.github.thanktoken.core.api;

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

}
