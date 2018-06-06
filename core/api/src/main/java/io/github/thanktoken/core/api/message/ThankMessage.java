/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.message;

import java.util.List;

import io.github.thanktoken.core.api.ThankSignedObject;
import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.attribute.AttributeReadTransactions;
import io.github.thanktoken.core.api.header.ThankVersion;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * A {@link ThankMessage} represents a message that is broadcasted within the peer-to-peer network of the ledger nodes
 * to publish new {@link ThankToken}s or {@link ThankTransaction}s.
 */
public interface ThankMessage extends ThankSignedObject, AttributeReadTransactions {

  /**
   * @return the {@link ThankMessageType} of this message.
   */
  ThankMessageType<?> getType();

  /**
   * @return the {@link List} of {@link ThankToken}s.
   */
  List<ThankToken> getTokens();

  /**
   * @return the {@link List} of {@link ThankTransaction}s.
   */
  @Override
  List<ThankTransaction> getTransactions();

  /**
   * @return the {@link ThankVersion} of this message.
   *
   * @see io.github.thanktoken.core.api.header.ThankHeader#getVersion()
   */
  default ThankVersion getVersion() {

    List<ThankToken> tokens = getTokens();
    if (tokens.isEmpty()) {
      return null;
    }
    return tokens.get(0).getHeader().getVersion();
  }

}
