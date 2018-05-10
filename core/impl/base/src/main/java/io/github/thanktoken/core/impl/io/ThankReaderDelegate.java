package io.github.thanktoken.core.impl.io;

import java.io.InputStream;
import java.io.Reader;

import javax.json.stream.JsonParser;

import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.header.ThankVersion;
import io.github.thanktoken.core.api.io.ThankReader;
import io.github.thanktoken.core.api.strategy.ThankStrategy;
import io.github.thanktoken.core.api.strategy.ThankStrategyFactory;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.base.io.AbstractThankReader;
import io.github.thanktoken.core.impl.ThankDelegate;

/**
 * This is an implementation of {@link ThankReader} that uses {@link ThankStrategyFactory} in order to read
 * {@link ThankHeader}s and {@link ThankTransaction}s for arbitrary {@link ThankHeader#getVersion() versions}.
 */
public class ThankReaderDelegate extends ThankDelegate implements AbstractThankReader {

  private static final ThankReaderDelegate INSTANCE = new ThankReaderDelegate();

  /**
   * The constructor.
   */
  public ThankReaderDelegate() {

    super();
  }

  /**
   * @return the singleton instance of this {@link ThankReaderDelegate}.
   */
  public static final ThankReaderDelegate get() {

    return INSTANCE;
  }

  @Override
  public ThankStrategy getStrategy() {

    return getStrategyFactory().getGlobalStrategy();
  }

  /**
   * @param version the {@link ThankVersion}.
   * @return the {@link AbstractThankReader} for the given {@link ThankVersion}.
   */
  protected AbstractThankReader getReader(ThankVersion version) {

    return (AbstractThankReader) getStrategyFactory().getStrategy(version).getReader();
  }

  @Override
  public ThankHeader readHeader(ThankVersion version, JsonParser jsonParser) {

    return getReader(version).readHeader(version, jsonParser);
  }

  @Override
  public ThankTransaction readTransaction(ThankVersion version, InputStream in) {

    return getReader(version).readTransaction(version, in);
  }

  @Override
  public ThankTransaction readTransaction(ThankVersion version, Reader reader) {

    return getReader(version).readTransaction(version, reader);
  }

  @Override
  public ThankTransaction readTransaction(ThankVersion version, JsonParser jsonParser) {

    return getReader(version).readTransaction(version, jsonParser);
  }

}
