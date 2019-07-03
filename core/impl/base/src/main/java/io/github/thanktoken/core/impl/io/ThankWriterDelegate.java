package io.github.thanktoken.core.impl.io;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Objects;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.io.ThankWriter;
import io.github.thanktoken.core.api.strategy.ThankStrategyFactory;
import io.github.thanktoken.core.api.strategy.ThankVersionStrategy;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * This is an implementation of {@link ThankWriter} that uses {@link ThankStrategyFactory} in order to write
 * {@link ThankToken}s for arbitrary {@link ThankHeader#getVersion() versions}.
 */
public class ThankWriterDelegate implements ThankWriter {

  private static final ThankWriterDelegate INSTANCE = new ThankWriterDelegate();

  /**
   * The constructor.
   */
  public ThankWriterDelegate() {

    super();
  }

  /**
   * @return the singleton instance of this {@link ThankWriterDelegate}.
   */
  public static final ThankWriterDelegate get() {

    return INSTANCE;
  }

  private ThankWriter getWriter(ThankHeader header) {

    Objects.requireNonNull(header, "header");
    ThankVersion version = header.getVersion();
    ThankVersionStrategy strategy = getStrategyFactory().getStrategy(version);
    ThankWriter writer = strategy.getWriter();
    return writer;
  }

  /**
   * @see ThankStrategyFactory#get()
   * @return the {@link ThankStrategyFactory}.
   */
  protected ThankStrategyFactory getStrategyFactory() {

    return ThankStrategyFactory.get();
  }

  @Override
  public void writeHeader(ThankHeader header, Writer writer) {

    getWriter(header).writeHeader(header, writer);
  }

  @Override
  public void writeHeader(ThankHeader header, OutputStream out) {

    getWriter(header).writeHeader(header, out);
  }

  @Override
  public void writeTransaction(ThankToken token, ThankTransaction tx, Writer writer) {

    getWriter(token.getHeader()).writeTransaction(token, tx, writer);
  }

  @Override
  public void writeTransaction(ThankToken token, ThankTransaction tx, OutputStream out) {

    getWriter(token.getHeader()).writeTransaction(token, tx, out);
  }

}
