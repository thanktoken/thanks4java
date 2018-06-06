/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.message;

import java.time.Instant;
import java.util.List;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import net.sf.mmm.security.api.sign.SecuritySignature;
import net.sf.mmm.util.value.api.Range;

/**
 * This is the abstract base implementation of {@link ThankMessage}.
 */
public abstract class AbstractThankMessage implements ThankMessage {

  private SecuritySignature signature;

  /**
   * The constructor.
   */
  public AbstractThankMessage() {

    super();
  }

  /**
   * The constructor.
   *
   * @param tokens the {@link #getTokens() tokens}.
   * @param transactions the {@link #getTransactions() transactions}.
   */
  public AbstractThankMessage(List<ThankToken> tokens, List<ThankTransaction> transactions) {

    super();
  }

  @Override
  public SecuritySignature getSignature() {

    return this.signature;
  }

  @Override
  public String toString() {

    return "TODO";
  }

  /**
   * @param message the {@link ThankMessage} to get the timestamp {@link Range} from.
   * @return the timestamp {@link Range} of the new {@link io.github.thanktoken.core.api.header.ThankHeader}s or
   *         {@link io.github.thanktoken.core.api.transaction.ThankTransaction}s from the given {@link ThankMessage}.
   * @see io.github.thanktoken.core.api.header.ThankHeader#getTimestamp()
   * @see io.github.thanktoken.core.api.transaction.ThankTransaction#getTimestamp()
   */
  public static Range<Instant> getTimestampRange(ThankMessage message) {

    Instant min = null;
    Instant max = null;
    for (ThankTransaction tx : message.getTransactions()) {
      Instant ts = tx.getTimestamp();
      if ((min == null) || min.isAfter(ts)) {
        min = ts;
      }
      if ((max == null) || max.isBefore(ts)) {
        max = ts;
      }
    }
    for (ThankToken token : message.getTokens()) {
      Instant ts = token.getHeader().getTimestamp();
      if ((min == null) || min.isAfter(ts)) {
        min = ts;
      }
      if ((max == null) || max.isBefore(ts)) {
        max = ts;
      }
    }
    return new Range<>(min, max);
  }

}
