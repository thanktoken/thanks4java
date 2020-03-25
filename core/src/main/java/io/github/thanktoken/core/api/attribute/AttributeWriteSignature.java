/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import io.github.mmm.crypto.asymmetric.sign.SignatureBinary;

/**
 * Interface to {@link #getSignature() read} and {@link #setSignature(SignatureBinary) write} the
 * {@link SignatureBinary}.
 *
 * @param <SELF> this type itself.
 */
public interface AttributeWriteSignature<SELF extends AttributeWriteSignature<SELF>> extends AttributeReadSignature {

  /**
   * @param signature the new value of {@link #getSignature()}.
   * @return this
   */
  SELF setSignature(SignatureBinary signature);

}
