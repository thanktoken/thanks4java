package io.github.thanktoken.core.api.identity.confirm;

import java.time.LocalDate;

import io.github.thanktoken.core.api.attribute.AttributeReadHash;
import io.github.thanktoken.core.api.attribute.AttributeReadSignature;
import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.identity.ThankIdentity;

/**
 * {@link ThankDataObject} for a confirmation of a {@link ThankIdentity}.
 *
 * @since 1.0.0
 */
public interface ThankIdentityConfirmation extends AttributeReadSignature, AttributeReadHash {

  /**
   * @return the {@link LocalDate} when the validity of the object starts in UTC. Has to be before the
   *         {@link #getValidTo() valid to date}.
   */
  LocalDate getValidFrom();

  /**
   * @return the {@link LocalDate} when the validity of the object ends in UTC. Has to be after the
   *         {@link #getValidFrom() valid from date}.
   */
  LocalDate getValidTo();

}
