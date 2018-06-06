/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import net.sf.mmm.security.api.sign.SecuritySignature;

/**
 * Interface to {@link #getSignature() read} and {@link #setSignature(SecuritySignature) write} the
 * {@link SecuritySignature}.
 *
 * @param <SELF> this type itself.
 */
public interface AttributeWriteSignature<SELF extends AttributeWriteSignature<SELF>> extends AttributeReadSignature {

  /**
   * @param signature the new value of {@link #getSignature()}.
   * @return this
   */
  SELF setSignature(SecuritySignature signature);

}
