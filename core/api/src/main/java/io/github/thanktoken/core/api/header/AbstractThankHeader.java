package io.github.thanktoken.core.api.header;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.field.ThankField;

/**
 * This is the implementation of {@link ThankToken} as Java bean.
 */
public abstract class AbstractThankHeader implements ThankHeader {

  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder(32);
    buffer.append(ThankField.formatBigDecimal(getAmount()));
    buffer.append(getCurrency());
    buffer.append('@');
    buffer.append(getTimestamp());
    return buffer.toString();
  }

}
