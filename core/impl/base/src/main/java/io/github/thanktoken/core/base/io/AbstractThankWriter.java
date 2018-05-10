/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.base.io;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

import io.github.thanktoken.core.api.ThankConstants;
import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.io.ThankWriter;
import io.github.thanktoken.core.api.strategy.ThankStrategy;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * Abstract base implementation of {@link ThankWriter}.
 */
public interface AbstractThankWriter extends ThankWriter, ThankConstants {

  /**
   * @return the {@link ThankStrategy} instance.
   */
  ThankStrategy getStrategy();

  @Override
  default void writeHeader(ThankHeader header, OutputStream out) {

    writeHeader(header, new OutputStreamWriter(out, UTF_8));
  }

  @Override
  default void writeTransaction(ThankToken token, ThankTransaction tx, OutputStream out) {

    writeTransaction(token, tx, new OutputStreamWriter(out, UTF_8));
  }

}
