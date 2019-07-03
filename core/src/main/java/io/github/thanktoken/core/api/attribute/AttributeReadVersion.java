/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.util.Objects;

import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * Interface to {@link #getVersion() read} the {@link ThankVersion}.
 */
public interface AttributeReadVersion extends ThankDataObject {

  /** Name of property {@link #getVersion()}. */
  String PROPERTY_VERSION = "version";

  /**
   * @return the {@link ThankVersion}. May be {@code null} for uninitialized bean.
   * @see io.github.thanktoken.core.api.token.header.ThankTokenHeader#getVersion()
   * @see io.github.thanktoken.core.api.reference.ThankTokenReference#getVersion()
   * @see io.github.thanktoken.core.api.message.ThankMessage#getVersion()
   */
  ThankVersion getVersion();

  /**
   * @return the required {@link #getVersion() version}.
   */
  default ThankVersion requireVersion() {

    ThankVersion version = getVersion();
    Objects.requireNonNull(version, PROPERTY_VERSION);
    return version;
  }

}
