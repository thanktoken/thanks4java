/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import io.github.thanktoken.core.api.ThankDataObject;
import io.github.thanktoken.core.api.header.ThankVersion;

/**
 * Interface to {@link #getVersion() read} the {@link ThankVersion}.
 */
public interface AttributeReadVersion extends ThankDataObject {

  /**
   * @return the {@link ThankVersion}.
   * @see io.github.thanktoken.core.api.header.ThankHeader#getVersion()
   * @see io.github.thanktoken.core.api.reference.ThankTokenReference#getVersion()
   * @see io.github.thanktoken.core.api.message.ThankMessage#getVersion()
   */
  ThankVersion getVersion();
}
