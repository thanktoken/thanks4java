/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.message;

import java.util.List;

import io.github.thanktoken.core.api.attribute.AttributeReadAlgorithm;
import io.github.thanktoken.core.api.attribute.AttributeReadTransactions;
import io.github.thanktoken.core.api.attribute.AttributeReadVersion;
import io.github.thanktoken.core.api.data.ThankSignedObject;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * A {@link ThankMessage} represents a message that is broadcasted within the peer-to-peer network of the ledger nodes
 * to publish new {@link ThankToken}s or {@link ThankTransaction}s.
 */
public interface ThankMessage extends ThankSignedObject, AttributeReadTransactions, AttributeReadAlgorithm, AttributeReadVersion {

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
   * @see io.github.thanktoken.core.api.token.header.ThankTokenHeader#getVersion()
   */
  @Override
  default ThankVersion getVersion() {

    List<ThankToken> tokens = getTokens();
    if (tokens.isEmpty()) {
      return null;
    }
    return tokens.get(0).getHeader().getVersion();
  }

}
