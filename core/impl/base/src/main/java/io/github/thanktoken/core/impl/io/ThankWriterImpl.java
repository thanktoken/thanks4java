package io.github.thanktoken.core.impl.io;

import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.header.ThankHeaderField;
import io.github.thanktoken.core.api.io.ThankWriter;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.transaction.ThankTransactionField;
import io.github.thanktoken.core.base.io.AbstractThankWriter;
import io.github.thanktoken.core.impl.strategy.AbstractThankVersionStrategy;
import io.github.thanktoken.core.impl.strategy.AbstractThankVersionStrategyContainer;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * Implementation of {@link ThankWriter}.
 */
public class ThankWriterImpl extends AbstractThankVersionStrategyContainer implements AbstractThankWriter {

  /**
   * The constructor.
   *
   * @param strategy the {@link AbstractThankVersionStrategy}.
   */
  public ThankWriterImpl(AbstractThankVersionStrategy strategy) {

    super(strategy);
  }

  @Override
  public void writeHeader(ThankHeader header, Writer writer) {

    Objects.requireNonNull(header, "header");
    Objects.requireNonNull(writer, "writer");
    verifyVersion(header.getVersion());
    JsonGenerator jsonGenerator = jsonGenerator(writer);
    ThankHeaderField.getFields().toJson(header, jsonGenerator);
    jsonGenerator.close();
    try {
      writer.close();
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.CLOSE);
    }
  }

  private JsonGenerator jsonGenerator(Writer writer) {

    JsonGenerator jsonGenerator = Json.createGenerator(writer);
    return jsonGenerator;
  }

  @Override
  public void writeTransaction(ThankToken token, ThankTransaction tx, Writer writer) {

    Objects.requireNonNull(token, "token");
    Objects.requireNonNull(writer, "writer");
    ThankHeader header = token.getHeader();
    Objects.requireNonNull(header, "header");
    verifyVersion(header.getVersion());
    Objects.requireNonNull(tx, "tx");
    assert (token.getTransactions().contains(tx));
    JsonGenerator jsonGenerator = jsonGenerator(writer);
    ThankTransactionField.getFields().toJson(tx, jsonGenerator);
    jsonGenerator.close();
    try {
      writer.close();
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.CLOSE);
    }
  }

}
