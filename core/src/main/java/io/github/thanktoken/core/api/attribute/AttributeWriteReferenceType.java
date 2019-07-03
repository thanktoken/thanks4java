/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

/**
 * Interface to {@link #getType() read} and {@link #setType(String) write} the {@link #getType() type}.
 *
 * @param <SELF> this type itself.
 */
public interface AttributeWriteReferenceType<SELF extends AttributeWriteReferenceType<SELF>> extends AttributeReadReferenceType {

  /**
   * @param type the new value of {@link #getType()}.
   * @return this
   */
  SELF setType(String type);

}
