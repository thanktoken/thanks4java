package io.github.thanktoken.core.api.io;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * Interface for a writer that can {@link #writeHeader(ThankTokenHeader, OutputStream) write} a {@link ThankToken} to an
 * {@link OutputStream}.
 */
public interface ThankWriter {

  /**
   * Writes the given {@link ThankToken} to the given {@link OutputStream}.
   *
   * @param header is the {@link ThankToken} to serialize.
   * @param out is where the given {@code token} is written to. Will NOT be closed.
   */
  default void writeHeader(ThankTokenHeader header, OutputStream out) {

    OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
    writeHeader(header, writer);
  }

  /**
   * Writes the given {@link ThankToken} to the given {@link Writer}.
   *
   * @param header is the {@link ThankToken} to serialize.
   * @param writer is where the given {@code token} is written to. Will NOT be closed.
   */
  void writeHeader(ThankTokenHeader header, Writer writer);

  /**
   * Writes the given {@link ThankTransaction} to the given {@link OutputStream}.
   *
   * @param token the {@link ThankToken} {@link ThankToken#getTransactions() owning} the given {@link ThankTransaction}.
   *        Required e.g. to know the {@link ThankTokenHeader#getVersion() version}.
   * @param tx is the {@link ThankTransaction} to serialize.
   * @param out is where the given {@code token} is written to. Will NOT be closed.
   */
  default void writeTransaction(ThankToken token, ThankTransaction tx, OutputStream out) {

    OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
    writeTransaction(token, tx, writer);
  }

  /**
   * Writes the given {@link ThankTransaction} to the given {@link Writer}.
   *
   * @param token the {@link ThankToken} {@link ThankToken#getTransactions() owning} the given {@link ThankTransaction}.
   *        Required e.g. to know the {@link ThankTokenHeader#getVersion() version}.
   * @param tx is the {@link ThankTransaction} to serialize.
   * @param writer is where the given {@code token} is written to. Will NOT be closed.
   */
  void writeTransaction(ThankToken token, ThankTransaction tx, Writer writer);

}
