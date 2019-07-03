/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.util.Objects;

import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.reference.ThankTokenReference;

/**
 * Interface to {@link #getType() read} the {@link #getType() type} of a {@link ThankTokenReference}.
 */
public interface AttributeReadReferenceType extends ThankDataObject {

  /** Name of property {@link #getType()}. */
  String PROPERTY_TYPE = "type";

  /**
   * The {@link #getType() type} of a {@link io.github.thanktoken.core.api.reference.ThankTokenReference} for a forked
   * {@link io.github.thanktoken.core.api.token.ThankToken}. May only be used in
   * {@link io.github.thanktoken.core.api.transaction.ThankTransaction} with no
   * {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getRecipient() recipient}.
   */
  String TYPE_FORK_TO = "forkTo";

  /**
   * The {@link #getType() type} of a {@link io.github.thanktoken.core.api.reference.ThankTokenReference} used to link
   * the {@link io.github.thanktoken.core.api.token.ThankToken}s created from a {@link #TYPE_FORK_TO forked} token. May only
   * be used in {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader}.
   */
  String TYPE_FORK_WITH = "forkWith";

  /**
   * The {@link #getType() type} of a {@link io.github.thanktoken.core.api.reference.ThankTokenReference} used to link
   * the last {@link io.github.thanktoken.core.api.token.ThankToken} created from a {@link #TYPE_FORK_TO fork} with the
   * initially forked token. May only be used in {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader}.
   */
  String TYPE_FORK_FROM = "forkFrom";

  /**
   * The {@link #getType() type} of the {@link io.github.thanktoken.core.api.reference.ThankTokenReference} of a merged
   * {@link io.github.thanktoken.core.api.token.ThankToken}s (except for the {@link #TYPE_MERGE_TO last} one). May only be
   * used in {@link io.github.thanktoken.core.api.transaction.ThankTransaction} with no
   * {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getRecipient() recipient}.
   */
  String TYPE_MERGE_WITH = "mergeWith";

  /**
   * The {@link #getType() type} of the {@link io.github.thanktoken.core.api.reference.ThankTokenReference} used to link
   * the "last" {@link #TYPE_MERGE_WITH merged} {@link io.github.thanktoken.core.api.token.ThankToken} with the newly created
   * merged token. May only be used in {@link io.github.thanktoken.core.api.transaction.ThankTransaction} with no
   * {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getRecipient() recipient}.
   */
  String TYPE_MERGE_TO = "mergeTo";

  /**
   * The {@link #getType() type} of the {@link io.github.thanktoken.core.api.reference.ThankTokenReference} used to link
   * the newly created {@link #TYPE_MERGE_TO merged} {@link io.github.thanktoken.core.api.token.ThankToken} with the "first"
   * merged token. May only be used in {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader}.
   */
  String TYPE_MERGE_FROM = "mergeFrom";

  /**
   * @return the type of a {@link io.github.thanktoken.core.api.reference.ThankTokenReference}. See the {@code TYPE_*}
   *         constants defined in {@link AttributeReadReferenceType} for the available types and their meanings.
   *
   * @see io.github.thanktoken.core.api.reference.ThankTokenReference#getType()
   */
  String getType();

  /**
   * @return the required {@link #getType() type}.
   */
  default String requireType() {

    String type = getType();
    if ((type == null) || type.isEmpty()) {
      Objects.requireNonNull(null, PROPERTY_TYPE);
    }
    return type;
  }

}
