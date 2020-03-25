/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.identity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.github.mmm.crypto.asymmetric.sign.SignatureBinary;
import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.location.ThankLocation;

/**
 * Implementation of {@link ThankIdentity} as simple Java bean.
 */
public class ThankIdentityBean implements ThankIdentity {

  private Map<String, String> properties;

  private ThankAddress address;

  private List<SignatureBinary> signatures;

  private ThankLocation location;

  @Override
  public ThankLocation getLocation() {

    return this.location;
  }

  /**
   * @param location the new value of {@link #getLocation()}.
   * @return this
   */
  public ThankIdentityBean setLocation(ThankLocation location) {

    this.location = location;
    return this;
  }

  @Override
  public Collection<SignatureBinary> getSignatures() {

    if (this.signatures == null) {
      this.signatures = new ArrayList<>();
    }
    return this.signatures;
  }

  /**
   * @param signatures the new value of {@link #getSignatures()}.
   * @return this
   */
  public ThankIdentityBean setSignatures(List<SignatureBinary> signatures) {

    this.signatures = signatures;
    return this;
  }

  @Override
  public Map<String, String> getProperties() {

    if (this.properties == null) {
      return this.properties;
    }
    return this.properties;
  }

  /**
   * @param properties the new value of {@link #getProperties()}.
   * @return this
   */
  public ThankIdentityBean setProperties(Map<String, String> properties) {

    this.properties = properties;
    return this;
  }

  @Override
  public ThankAddress getAddress() {

    return this.address;
  }

  /**
   * @param address the new value of {@link #getAddress()}.
   * @return this
   */
  public ThankIdentityBean setLatestAddress(ThankAddress address) {

    this.address = address;
    return this;
  }

  @Override
  public boolean isValid(Instant timestamp) {

    // TODO Auto-generated method stub
    return true;
  }

}
