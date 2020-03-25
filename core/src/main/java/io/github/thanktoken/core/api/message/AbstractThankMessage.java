/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.message;

import java.time.Instant;

import io.github.mmm.base.range.Range;
import io.github.mmm.base.range.RangeType;
import io.github.mmm.crypto.asymmetric.sign.SignatureBinary;
import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * This is the abstract base implementation of {@link ThankMessage}.
 */
public abstract class AbstractThankMessage implements ThankMessage {

  private SignatureBinary signature;

  /**
   * The constructor.
   */
  public AbstractThankMessage() {

    super();
  }

  @Override
  public ThankAlgorithm getAlgorithm() {

    return getTokens().get(0).getHeader().getAlgorithm();
  }

  @Override
  public SignatureBinary getSignature() {

    return this.signature;
  }

  @Override
  public String toString() {

    return "TODO";
  }

  /**
   * @param message the {@link ThankMessage} to get the timestamp {@link Range} from.
   * @return the timestamp {@link Range} of the new {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader}s
   *         or {@link io.github.thanktoken.core.api.transaction.ThankTransaction}s from the given {@link ThankMessage}.
   * @see io.github.thanktoken.core.api.token.header.ThankTokenHeader#getTimestamp()
   * @see io.github.thanktoken.core.api.transaction.ThankTransaction#getTimestamp()
   */
  public static Range<Instant> getTimestampRange(ThankMessage message) {

    Instant min = null;
    Instant max = null;
    for (ThankTransaction tx : message.getTransactions()) {
      Instant ts = tx.requireTimestamp().getInstant();
      if ((min == null) || min.isAfter(ts)) {
        min = ts;
      }
      if ((max == null) || max.isBefore(ts)) {
        max = ts;
      }
    }
    for (ThankToken token : message.getTokens()) {
      Instant ts = token.requireHeader().requireTimestamp().getInstant();
      if ((min == null) || min.isAfter(ts)) {
        min = ts;
      }
      if ((max == null) || max.isBefore(ts)) {
        max = ts;
      }
    }
    return new RangeType<>(min, max);
  }

}
