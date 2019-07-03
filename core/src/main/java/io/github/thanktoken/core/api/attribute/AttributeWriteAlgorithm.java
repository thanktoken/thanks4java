/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;

/**
 * Interface to {@link #getAlgorithm() read} and {@link #setAlgorithm(ThankAlgorithm) write} the {@link ThankAlgorithm}.
 *
 * @param <SELF> this type itself.
 */
public interface AttributeWriteAlgorithm<SELF extends AttributeWriteAlgorithm<SELF>> extends AttributeReadAlgorithm {

  /**
   * @param algorithm the new value of {@link #getAlgorithm()}.
   * @return this
   */
  SELF setAlgorithm(ThankAlgorithm algorithm);

}
