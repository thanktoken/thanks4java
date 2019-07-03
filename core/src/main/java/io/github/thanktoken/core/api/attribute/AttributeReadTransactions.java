/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.util.List;

import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * Interface to {@link #getTransactions() read} the {@link ThankTransaction}s.
 */
public interface AttributeReadTransactions extends ThankDataObject {

  /** Name of property {@link #getTransactions()}. */
  String PROPERTY_TRANSACTIONS = "transactions";

  /**
   * A {@link ThankToken} is a digital coin that has one owner at a time. Therefore, it contains a {@link List} of
   * {@link ThankTransaction}s that represents the entire transaction history of the {@link ThankToken}. Each
   * {@link ThankTransaction} represents a transaction transferring the {@link ThankToken} from its previous owner to
   * the next owner.
   *
   * @return the {@link List} of {@link ThankTransaction}s of this object.
   * @see io.github.thanktoken.core.api.token.ThankToken#getTransactions()
   * @see io.github.thanktoken.core.api.message.ThankMessage#getTransactions()
   */
  List<? extends ThankTransaction> getTransactions();

  /**
   * @return the last {@link ThankTransaction} from the {@link #getTransactions() transactions} or {@code null} if no
   *         transactions available.
   */
  default ThankTransaction getLastTransaction() {

    List<? extends ThankTransaction> transactions = getTransactions();
    if (transactions.isEmpty()) {
      return null;
    }
    return transactions.get(transactions.size() - 1);
  }

}
