package io.github.thanktoken.core.base.io;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import io.github.thanktoken.core.api.ThankConstants;
import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.ThankTokenBean;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.header.ThankHeaderField;
import io.github.thanktoken.core.api.header.ThankVersion;
import io.github.thanktoken.core.api.io.ThankReader;
import io.github.thanktoken.core.api.io.ThankValueParser;
import io.github.thanktoken.core.api.strategy.ThankStrategy;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.transaction.ThankTransactionField;
import net.sf.mmm.util.exception.api.IllegalCaseException;

/**
 * Abstract base implementation of {@link ThankReader}.
 */
public interface AbstractThankReader extends ThankReader, ThankConstants {

  /**
   * @return the {@link ThankStrategy} instance.
   */
  ThankStrategy getStrategy();

  @Override
  default ThankToken read(String header, String... transactions) {

    ThankHeader tokenHeader = readHeader(new StringReader(header));
    // Writer sw = new StringWriter();
    // getStrategy().getWriter().writeHeader(tokenHeader, sw);
    // assert (sw.toString().equals(header));
    ThankTokenBean token = new ThankTokenBean(tokenHeader);
    for (String transaction : transactions) {
      ThankTransaction tx = readTransaction(tokenHeader.getVersion(), new StringReader(transaction));
      token.addTransaction(tx);
      // sw = new StringWriter();
      // getStrategy().getWriter().writeTransaction(token, tx, sw);
      // assert (sw.toString().equals(transaction));
    }
    return token;
  }

  @Override
  default ThankHeader readHeader(InputStream in) {

    InputStreamReader reader = new InputStreamReader(in, UTF_8);
    return readHeader(reader);
  }

  /**
   * @return the {@link ThankValueParser}.
   */
  default ThankValueParser getValueParser() {

    return getStrategy().getConfiguration().getValueParser();
  }

  @Override
  default ThankHeader readHeader(Reader reader) {

    JsonParser jsonParser = Json.createParser(reader);
    Event e = jsonParser.next();
    if (e != Event.START_OBJECT) {
      throw new IllegalCaseException(Event.class, e);
    }
    e = jsonParser.next();
    if (e != Event.KEY_NAME) {
      throw new IllegalCaseException(Event.class, e);
    }
    String key = jsonParser.getString();
    ThankHeaderField<ThankVersion> field = ThankHeaderField.VERSION;
    if (ThankHeaderField.getFields().get(key) != field) {
      throw new IllegalCaseException(key);
    }
    e = jsonParser.next();
    ThankValueParser valueParser = getValueParser();
    ThankVersion version = field.fromJson(jsonParser, e, valueParser);
    return readHeader(version, jsonParser);
  }

  /**
   * Reads a {@link ThankHeader} from the given {@link InputStream} assuming that the {@link ThankVersion} has already
   * been read and consumed from the {@link InputStream}.
   *
   * @param version the {@link ThankVersion} that has already been read.
   * @param jsonParser is the {@link JsonParser} where to read the {@link ThankHeader} from.
   * @return the parsed {@link ThankHeader}.
   */
  ThankHeader readHeader(ThankVersion version, JsonParser jsonParser);

  @Override
  default ThankTransaction readTransaction(ThankVersion version, InputStream in) {

    InputStreamReader reader = new InputStreamReader(in, UTF_8);
    return readTransaction(version, reader);
  }

  @Override
  default ThankTransaction readTransaction(ThankVersion version, Reader reader) {

    JsonParser jsonParser = Json.createParser(reader);
    Event e = jsonParser.next();
    if (e != Event.START_OBJECT) {
      throw new IllegalCaseException(Event.class, e);
    }
    return ThankTransactionField.getFields().fromJson(jsonParser, getValueParser());
  }

  /**
   * @param version the {@link ThankVersion} of the owning {@link ThankToken}.
   * @param jsonParser is the {@link JsonParser} where to read the {@link ThankToken} from.
   * @return the parsed {@link ThankTransaction}.
   */
  ThankTransaction readTransaction(ThankVersion version, JsonParser jsonParser);

}
