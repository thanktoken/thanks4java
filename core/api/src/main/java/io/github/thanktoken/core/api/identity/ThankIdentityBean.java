/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.identity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;
import net.sf.mmm.security.api.sign.SecuritySignature;

/**
 * Implementation of {@link ThankIdentity} as simple Java bean.
 */
public class ThankIdentityBean implements ThankIdentity {

  private String id;

  private ThankIdentityType type;

  private Map<String, String> properties;

  private SecurityPublicKey latestPublicKey;

  private List<SecuritySignature> signatures;

  @Override
  public String getId() {

    return this.id;
  }

  /**
   * @param id the new value of {@link #getId()}.
   * @return this
   */
  public ThankIdentityBean setId(String id) {

    this.id = id;
    return this;
  }

  @Override
  public ThankIdentityType getType() {

    return this.type;
  }

  /**
   * @param type the new value of {@link #getType()}.
   * @return this
   */
  public ThankIdentityBean setType(ThankIdentityType type) {

    this.type = type;
    return this;
  }

  @Override
  public Collection<SecuritySignature> getSignatures() {

    if (this.signatures == null) {
      this.signatures = new ArrayList<>();
    }
    return this.signatures;
  }

  /**
   * @param signatures the new value of {@link #getSignatures()}.
   * @return this
   */
  public ThankIdentityBean setSignatures(List<SecuritySignature> signatures) {

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
  public SecurityPublicKey getLatestPublicKey() {

    return this.latestPublicKey;
  }

  /**
   * @param latestPublicKey the new value of {@link #getLatestPublicKey()}.
   * @return this
   */
  public ThankIdentityBean setLatestPublicKey(SecurityPublicKey latestPublicKey) {

    this.latestPublicKey = latestPublicKey;
    return this;
  }

}
