/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.impl.strategy;

import java.util.List;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.header.ThankHeaderField;
import io.github.thanktoken.core.api.header.ThankVersion;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.transaction.ThankTransactionField;
import net.sf.mmm.security.api.hash.SecurityHashCreator;
import net.sf.mmm.security.api.sign.SecuritySignature;
import net.sf.mmm.util.exception.api.ObjectMismatchException;

/**
 * Abstract base class for a sub-component of a {@link io.github.thanktoken.core.api.strategy.ThankStrategy}.
 */
public class AbstractThankVersionStrategyContainer {

  private final AbstractThankVersionStrategy strategy;

  /**
   * The constructor.
   *
   * @param strategy the {@link AbstractThankVersionStrategy}.
   */
  public AbstractThankVersionStrategyContainer(AbstractThankVersionStrategy strategy) {

    super();
    this.strategy = strategy;
  }

  /**
   * @return the {@link io.github.thanktoken.core.api.strategy.ThankStrategy} instance.
   */
  public AbstractThankVersionStrategy getStrategy() {

    return this.strategy;
  }

  /**
   * @param version the {@link ThankVersion} that is expected to match the
   *        {@link AbstractThankVersionStrategy#getVersion() version} of the {@link #getStrategy() strategy}.
   */
  protected void verifyVersion(ThankVersion version) {

    if (!this.strategy.getVersion().equals(version)) {
      throw new ObjectMismatchException(version, this.strategy.getVersion(), ThankHeaderField.VERSION.getName());
    }
  }

  /**
   * @param header the {@link ThankHeader}.
   * @param hashCreator the {@link SecurityHashCreator} to use.
   * @return the hash of the given {@link ThankHeader}.
   */
  protected byte[] hash(ThankHeader header, SecurityHashCreator hashCreator) {

    return ThankHeaderField.getFields().hash(header, hashCreator);
  }

  /**
   * @param tx the {@link ThankTransaction}.
   * @param hashCreator the {@link SecurityHashCreator} to use.
   * @return the hash of the given {@link ThankTransaction}.
   */
  protected byte[] hash(ThankTransaction tx, SecurityHashCreator hashCreator) {

    return ThankTransactionField.getFields().hash(tx, hashCreator);
  }

  /**
   * @param token the {@link ThankToken}.
   * @param txIndex the {@link java.util.List#get(int) index} of the {@link ThankTransaction} from the
   *        {@link ThankToken#getTransactions() transactions} of the {@link ThankToken} to get a hash from or {@code -1}
   *        to get the hash from the {@link ThankToken#getHeader() header}.
   * @param hashCreator the {@link SecurityHashCreator} to use.
   * @return the hash of the given {@link ThankTransaction}.
   */
  protected byte[] hash(ThankToken token, int txIndex, SecurityHashCreator hashCreator) {

    List<? extends ThankTransaction> transactions = token.getTransactions();
    if ((txIndex < -1) || (txIndex >= transactions.size())) {
      throw new IndexOutOfBoundsException(Integer.toString(txIndex));
    }
    SecuritySignature signature;
    if (txIndex == -1) {
      signature = token.getHeader().getSignature();
    } else {
      ThankTransaction tx = transactions.get(txIndex);
      signature = tx.getSignature();
    }
    return hashCreator.process(signature, true);
  }

}
