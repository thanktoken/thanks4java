/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.io;

import java.io.Writer;
import java.util.Objects;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.token.header.ThankTokenHeaderField;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.transaction.ThankTransactionField;

/**
 * Abstract base implementation of {@link ThankWriter}.
 */
public class ThankWriterImpl implements ThankWriter {

  private static final ThankWriterImpl INSTANCE = new ThankWriterImpl();

  private JsonGenerator jsonGenerator(Writer writer) {

    JsonGenerator jsonGenerator = Json.createGenerator(writer);
    return jsonGenerator;
  }

  @Override
  public void writeHeader(ThankTokenHeader header, Writer writer) {

    Objects.requireNonNull(header, "header");
    Objects.requireNonNull(writer, "writer");
    try {
      JsonGenerator jsonGenerator = jsonGenerator(writer);
      ThankTokenHeaderField.getFields().toJson(header, header, jsonGenerator);
      jsonGenerator.close();
      writer.close();
    } catch (Exception e) {
      throw new IllegalStateException("Failed to write token header.", e);
    }
  }

  @Override
  public void writeTransaction(ThankToken token, ThankTransaction tx, Writer writer) {

    Objects.requireNonNull(token, "token");
    Objects.requireNonNull(writer, "writer");
    ThankTokenHeader header = token.getHeader();
    Objects.requireNonNull(header, "header");
    Objects.requireNonNull(tx, "tx");
    assert (token.getTransactions().contains(tx));
    try {
      JsonGenerator jsonGenerator = jsonGenerator(writer);
      ThankTransactionField.getFields().toJson(tx, header, jsonGenerator);
      jsonGenerator.close();
      writer.close();
    } catch (Exception e) {
      throw new IllegalStateException("Failed to write transaction.", e);
    }
  }

  /**
   * @return the {@link ThankWriter} implementation.
   */
  public static ThankWriter get() {

    return INSTANCE;
  }

}
