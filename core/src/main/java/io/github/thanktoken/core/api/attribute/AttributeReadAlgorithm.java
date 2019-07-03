/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.util.Objects;

import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.data.ThankDataObject;

/**
 * Interface to {@link #getAlgorithm() read} the {@link ThankAlgorithm}.
 */
public interface AttributeReadAlgorithm extends ThankDataObject {

  /** Name of property {@link #getAlgorithm()}. */
  String PROPERTY_ALGORITHM = "algorithm";

  /**
   * @return the {@link ThankAlgorithm}. May be {@code null} for uninitialized bean.
   * @see io.github.thanktoken.core.api.token.header.ThankTokenHeader#getAlgorithm()
   * @see io.github.thanktoken.core.api.reference.ThankTokenReference#getAlgorithm()
   * @see io.github.thanktoken.core.api.message.ThankMessage#getAlgorithm()
   */
  ThankAlgorithm getAlgorithm();

  /**
   * @return the required {@link #getAlgorithm() algorithm}.
   */
  default ThankAlgorithm requireAlgorithm() {

    ThankAlgorithm algorithm = getAlgorithm();
    Objects.requireNonNull(algorithm, PROPERTY_ALGORITHM);
    return algorithm;
  }

}
