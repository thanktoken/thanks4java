package io.github.thanktoken.core.api.io;

import java.io.Reader;
import java.io.StringReader;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import net.sf.mmm.util.exception.api.IllegalCaseException;

import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.token.ThankTokenBean;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.token.header.ThankTokenHeaderField;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.transaction.ThankTransactionField;

/**
 * Implementation of {@link ThankReader}.
 */
public class ThankReaderImpl implements ThankReader {

  private static final ThankReaderImpl INSTANCE = new ThankReaderImpl();

  @Override
  public ThankToken read(String header, String... transactions) {

    ThankTokenHeader tokenHeader = readHeader(new StringReader(header));
    ThankTokenBean token = new ThankTokenBean(tokenHeader);
    for (String transaction : transactions) {
      ThankTransaction tx = readTransaction(tokenHeader, new StringReader(transaction));
      token.addTransaction(tx);
    }
    return token;
  }

  /**
   * @return the {@link ThankValueParser}.
   */
  public ThankValueParser getValueParser() {

    return ThankValueParserDefault.get();
  }

  @Override
  public ThankTokenHeader readHeader(Reader reader) {

    JsonParser jsonParser = Json.createParser(reader);
    Event e = jsonParser.next();
    if (e != Event.START_OBJECT) {
      throw new IllegalCaseException(Event.class, e);
    }
    return ThankTokenHeaderField.getFields().fromJson(jsonParser, getValueParser());
  }

  @Override
  public ThankTransaction readTransaction(ThankTokenHeader header, Reader reader) {

    JsonParser jsonParser = Json.createParser(reader);
    Event e = jsonParser.next();
    if (e != Event.START_OBJECT) {
      throw new IllegalCaseException(Event.class, e);
    }
    return ThankTransactionField.getFields().fromJson(jsonParser, getValueParser(), header);
  }

  /**
   * @return the {@link ThankReader} implementation.
   */
  public static ThankReader get() {

    return INSTANCE;
  }

}
