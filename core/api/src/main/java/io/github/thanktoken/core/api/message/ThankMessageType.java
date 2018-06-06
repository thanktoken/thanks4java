/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.message;

/**
 * Type of a {@link ThankMessage}.
 *
 * @param <M> generic type of the {@link ThankMessage}.
 * @see ThankMessage#getType()
 */
public class ThankMessageType<M extends ThankMessage> {

  /** {@link ThankMessageType} for {@link ThankMessageTokenCreation}. */
  public static ThankMessageType<ThankMessageTokenCreation> CREATION = new ThankMessageType<>("C");

  /** {@link ThankMessageType} for {@link ThankMessageTokenFork}. */
  public static ThankMessageType<ThankMessageTokenFork> FORK = new ThankMessageType<>("F");

  /** {@link ThankMessageType} for {@link ThankMessageTokenMerge}. */
  public static ThankMessageType<ThankMessageTokenMerge> MERGE = new ThankMessageType<>("M");

  /** {@link ThankMessageType} for {@link ThankMessageTokenTransfer}. */
  public static ThankMessageType<ThankMessageTokenTransfer> TRANSFER = new ThankMessageType<>("T");

  private final String name;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   */
  public ThankMessageType(String name) {

    super();
    this.name = name;
  }

  /**
   * @return the name of this type.
   */
  public String getName() {

    return this.name;
  }

  @Override
  public String toString() {

    return this.name;
  }

}
