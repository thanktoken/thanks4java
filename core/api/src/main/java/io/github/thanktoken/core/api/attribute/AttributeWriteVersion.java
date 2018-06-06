/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import io.github.thanktoken.core.api.header.ThankVersion;

/**
 * Interface to {@link #getVersion() read} and {@link #setVersion(ThankVersion) write} the {@link ThankVersion}.
 *
 * @param <SELF> this type itself.
 */
public interface AttributeWriteVersion<SELF extends AttributeWriteVersion<SELF>> extends AttributeReadVersion {

  /**
   * @param version the new value of {@link #getVersion()}.
   * @return this
   */
  SELF setVersion(ThankVersion version);

}
