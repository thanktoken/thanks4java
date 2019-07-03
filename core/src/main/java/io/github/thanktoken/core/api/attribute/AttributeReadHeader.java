/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.util.Objects;

import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;

/**
 * Interface to {@link #getHeader() read} the {@link ThankTokenHeader}.
 */
public interface AttributeReadHeader extends ThankDataObject {

  /** Name of property {@link #getHeader()}. */
  String PROPERTY_HEADER = "header";

  /**
   * @return the {@link ThankTokenHeader}. May be {@code null} for uninitialized bean.
   * @see io.github.thanktoken.core.api.token.ThankToken#getHeader()
   */
  ThankTokenHeader getHeader();

  /**
   * @return the required {@link #getHeader() header}.
   */
  default ThankTokenHeader requireHeader() {

    ThankTokenHeader header = getHeader();
    Objects.requireNonNull(header, PROPERTY_HEADER);
    return header;
  }

}
