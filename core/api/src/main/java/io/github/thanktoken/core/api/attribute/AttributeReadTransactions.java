/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.util.List;

import io.github.thanktoken.core.api.ThankDataObject;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * Interface to {@link #getTransactions() read} the {@link ThankTransaction}s.
 */
public interface AttributeReadTransactions extends ThankDataObject {

  /**
   * @return the {@link List} of {@link ThankTransaction}s of this object.
   * @see io.github.thanktoken.core.api.ThankToken#getTransactions()
   * @see io.github.thanktoken.core.api.message.ThankMessage#getTransactions()
   */
  List<ThankTransaction> getTransactions();
}
