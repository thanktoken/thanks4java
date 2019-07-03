/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.util.Objects;

import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.data.ThankDataObject;

/**
 * Interface to {@link #getRecipient() read} the {@link #getRecipient() recipient} as {@link ThankAddress}.
 */
public interface AttributeReadRecipient extends ThankDataObject {

  /** Name of property {@link #getRecipient()}. */
  String PROPERTY_RECIPIENT = "recipient";

  /**
   * @return the {@link ThankAddress} representing the
   *         {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader#getRecipient() recipient} of this object. The
   *         recipient is the current owner at the time when this object (or in case of a
   *         {@link io.github.thanktoken.core.api.reference.ThankTokenReferenceType reference} the creator of the
   *         referenced {@link io.github.thanktoken.core.api.token.ThankToken}) was created and verified.
   * @see io.github.thanktoken.core.api.token.header.ThankTokenHeader#getRecipient()
   * @see io.github.thanktoken.core.api.transaction.ThankTransaction#getRecipient()
   * @see io.github.thanktoken.core.api.message.ThankMessage#getRecipient()
   */
  ThankAddress getRecipient();

  /**
   * @return the required {@link #getRecipient() recipient}.
   */
  default ThankAddress requireRecipient() {

    ThankAddress recipient = getRecipient();
    Objects.requireNonNull(recipient, PROPERTY_RECIPIENT);
    return recipient;
  }
}
