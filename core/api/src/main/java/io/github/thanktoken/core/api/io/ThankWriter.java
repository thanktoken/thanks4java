package io.github.thanktoken.core.api.io;

import java.io.OutputStream;
import java.io.Writer;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * Interface for a writer that can {@link #writeHeader(ThankHeader, OutputStream) write} a {@link ThankToken} to an
 * {@link OutputStream}.
 */
public interface ThankWriter {

  /**
   * Writes the given {@link ThankToken} to the given {@link OutputStream}.
   *
   * @param header is the {@link ThankToken} to serialize.
   * @param out is where the given {@code token} is written to. Will NOT be closed.
   */
  void writeHeader(ThankHeader header, OutputStream out);

  /**
   * Writes the given {@link ThankToken} to the given {@link Writer}.
   *
   * @param header is the {@link ThankToken} to serialize.
   * @param writer is where the given {@code token} is written to. Will NOT be closed.
   */
  void writeHeader(ThankHeader header, Writer writer);

  /**
   * Writes the given {@link ThankTransaction} to the given {@link OutputStream}.
   *
   * @param token the {@link ThankToken} {@link ThankToken#getTransactions() owning} the given {@link ThankTransaction}.
   *        Required e.g. to know the {@link ThankHeader#getVersion() version}.
   * @param tx is the {@link ThankTransaction} to serialize.
   * @param out is where the given {@code token} is written to. Will NOT be closed.
   */
  void writeTransaction(ThankToken token, ThankTransaction tx, OutputStream out);

  /**
   * Writes the given {@link ThankTransaction} to the given {@link Writer}.
   *
   * @param token the {@link ThankToken} {@link ThankToken#getTransactions() owning} the given {@link ThankTransaction}.
   *        Required e.g. to know the {@link ThankHeader#getVersion() version}.
   * @param tx is the {@link ThankTransaction} to serialize.
   * @param writer is where the given {@code token} is written to. Will NOT be closed.
   */
  void writeTransaction(ThankToken token, ThankTransaction tx, Writer writer);

}
