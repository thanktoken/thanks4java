/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.identity;

import io.github.thanktoken.core.api.header.ThankTarget;

/**
 * The type of a {@link ThankIdentity}.
 *
 * @see ThankIdentity#getType()
 */
public class ThankIdentityType {

  private final String type;

  /** {@link ThankIdentity#getType() Type} of a natural person. */
  public static final ThankIdentityType NATURAL_PERSON = new ThankIdentityType("P3");

  /** {@link ThankIdentity#getType() Type} of an organization. */
  public static final ThankIdentityType ORGANIZATION = new ThankIdentityType("ORG");

  /** {@link ThankIdentity#getType() Type} of a company supporting only sustainability goals. */
  public static final ThankIdentityType SUSTAINABILITY = new ThankIdentityType("SUS");

  /** {@link ThankIdentity#getType() Type} of {@link ThankTarget#getLevel() level} 0 representatives (country). */
  public static final ThankIdentityType REPRESENTATIVES_L0 = new ThankIdentityType("R0");

  /** {@link ThankIdentity#getType() Type} of {@link ThankTarget#getLevel() level} 1 representatives (state). */
  public static final ThankIdentityType REPRESENTATIVES_L1 = new ThankIdentityType("R1");

  /** {@link ThankIdentity#getType() Type} of {@link ThankTarget#getLevel() level} 2 representatives (community). */
  public static final ThankIdentityType REPRESENTATIVES_L2 = new ThankIdentityType("R2");

  /**
   * The constructor.
   *
   * @param type the {@link String} representation of this type.
   */
  protected ThankIdentityType(String type) {

    super();
    this.type = type;
  }

  @Override
  public String toString() {

    return this.type;
  }

}
