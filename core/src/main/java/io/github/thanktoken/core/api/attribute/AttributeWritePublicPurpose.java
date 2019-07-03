/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

/**
 * Interface to {@link #getPublicPurpose() read} and {@link #setPublicPurpose(String) write} the
 * {@link #getPublicPurpose() public purpose}.
 *
 * @param <SELF> this type itself.
 */
public interface AttributeWritePublicPurpose<SELF extends AttributeWritePublicPurpose<SELF>> extends AttributeReadPublicPurpose {

  /**
   * @param publicPurpose the new value of {@link #getPublicPurpose()}.
   * @return this
   */
  SELF setPublicPurpose(String publicPurpose);

}
