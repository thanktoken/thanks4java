/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.util.function.BiConsumer;
import java.util.function.Function;

import net.sf.mmm.crypto.asymmetric.sign.SignatureBinary;
import net.sf.mmm.crypto.crypt.EncryptedData;

import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.field.ThankField;
import io.github.thanktoken.core.api.field.ThankFieldMap;
import io.github.thanktoken.core.api.location.ThankLocation;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceField;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;
import io.github.thanktoken.core.api.target.ThankTarget;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;
import io.github.thanktoken.core.api.value.ThankValue;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * {@link ThankField}s to use as {@link #getParent() parent} for extension.
 *
 * @param <T> generic type of the value of this field.
 * @param <D> generic type of the {@link ThankDataObject} owning this {@link ThankField}.
 * @param <B> generic type of the mutable bean owning this {@link ThankField}.
 */
public class ThankAttributeField<T, D extends ThankDataObject, B extends D> extends ThankField<T, D, B> {

  /** {@link ThankAttributeField} for {@link AttributeReadVersion#getVersion()}. */
  public static final ThankAttributeField<ThankVersion, AttributeReadVersion, AttributeWriteVersion<?>> VERSION = new ThankAttributeField<>(
      "ver", AttributeReadVersion.PROPERTY_VERSION, ThankVersion.class, AttributeReadVersion::getVersion,
      AttributeWriteVersion::setVersion);

  /** {@link ThankAttributeField} for {@link AttributeReadAlgorithm#getAlgorithm()}. */
  public static final ThankAttributeField<ThankAlgorithm, AttributeReadAlgorithm, AttributeWriteAlgorithm<?>> ALGORITHM = new ThankAttributeField<>(
      "alg", AttributeReadAlgorithm.PROPERTY_ALGORITHM, ThankAlgorithm.class, AttributeReadAlgorithm::getAlgorithm,
      AttributeWriteAlgorithm::setAlgorithm);

  /** {@link ThankAttributeField} for {@link AttributeReadAmount#getAmount()}. */
  public static final ThankAttributeField<ThankValue, AttributeReadAmount, AttributeWriteAmount<?>> AMOUNT = new ThankAttributeField<>(
      "amt", AttributeReadAmount.PROPERT_AMOUNT, ThankValue.class, AttributeReadAmount::getAmount, AttributeWriteAmount::setAmount);

  /** {@link ThankAttributeField} for {@link AttributeReadAmount#getAmount()}. */
  public static final ThankAttributeField<ThankCurrency, AttributeReadCurrency, AttributeWriteCurrency<?>> CURRENCY = new ThankAttributeField<>(
      "cur", AttributeReadCurrency.PROPERTY_CURRENCY, ThankCurrency.class, AttributeReadCurrency::getCurrency,
      AttributeWriteCurrency::setCurrency);

  /** {@link ThankAttributeField} for {@link AttributeReadTimestamp#getTimestamp()}. */
  public static final ThankAttributeField<ThankTimestamp, AttributeReadTimestamp, AttributeWriteTimestamp<?>> TIMESTAMP = new ThankAttributeField<>(
      "ts", AttributeReadTimestamp.PROPERTY_TIMESTAMP, ThankTimestamp.class, AttributeReadTimestamp::getTimestamp,
      AttributeWriteTimestamp::setTimestamp);

  /** {@link ThankAttributeField} for {@link AttributeReadLocation#getLocation()}. */
  public static final ThankAttributeField<ThankLocation, AttributeReadLocation, AttributeWriteLocation<?>> LOCATION = new ThankAttributeField<>(
      "loc", AttributeReadLocation.PROPERTY_LOCATION, ThankLocation.class, AttributeReadLocation::getLocation,
      AttributeWriteLocation::setLocation);

  /** {@link ThankAttributeField} for {@link AttributeReadTarget#getTarget()}. */
  public static final ThankAttributeField<ThankTarget, AttributeReadTarget, AttributeWriteTarget<?>> TARGET = new ThankAttributeField<>(
      "tgt", AttributeReadTarget.PROPERTY_TARGET, ThankTarget.class, AttributeReadTarget::getTarget, AttributeWriteTarget::setTarget);

  /** {@link ThankAttributeField} for {@link AttributeReadRecipient#getRecipient()}. */
  public static final ThankAttributeField<ThankAddress, AttributeReadRecipient, AttributeWriteRecipient<?>> RECIPIENT = new ThankAttributeField<>(
      "rcp", AttributeReadRecipient.PROPERTY_RECIPIENT, ThankAddress.class, AttributeReadRecipient::getRecipient,
      AttributeWriteRecipient::setRecipient);

  /** {@link ThankAttributeField} for {@link AttributeReadReference#getReference()}. */
  public static final ThankAttributeField<ThankTokenReferenceType, AttributeReadReference, AttributeWriteReference<?>> REFERENCE = new ThankAttributeField<ThankTokenReferenceType, AttributeReadReference, AttributeWriteReference<?>>(
      "ref", AttributeReadReference.PROPERTY_REFERENCE, ThankTokenReferenceType.class, AttributeReadReference::getReference,
      AttributeWriteReference::setReference, true) {

    @Override
    public ThankFieldMap<?, ?, ?> getFieldMap() {

      return ThankTokenReferenceField.getFields();
    }

  };

  /** {@link ThankAttributeField} for {@link AttributeReadReferenceType#getType()}. */
  public static final ThankAttributeField<String, AttributeReadReferenceType, AttributeWriteReferenceType<?>> REFERENCE_TYPE = new ThankAttributeField<>(
      "t", AttributeReadReferenceType.PROPERTY_TYPE, String.class, AttributeReadReferenceType::getType,
      AttributeWriteReferenceType::setType);

  /** {@link ThankAttributeField} for {@link AttributeReadPublicPurpose#getPublicPurpose()}. */
  public static final ThankAttributeField<String, AttributeReadPublicPurpose, AttributeWritePublicPurpose<?>> PUBLIC_PURPOSE = new ThankAttributeField<>(
      "pp", AttributeReadPublicPurpose.PROPERTY_PUBLIC_PURPOSE, String.class, AttributeReadPublicPurpose::getPublicPurpose,
      AttributeWritePublicPurpose::setPublicPurpose, true);

  /** {@link ThankAttributeField} for {@link AttributeReadEncryptedPurpose#getEncryptedPurpose()}. */
  public static final ThankAttributeField<EncryptedData, AttributeReadEncryptedPurpose, AttributeWriteEncryptedPurpose<?>> ENCRYPTED_PURPOSE = new ThankAttributeField<>(
      "ep", AttributeReadEncryptedPurpose.PROPERTY_ENCRYPTED_PURPOSE, EncryptedData.class,
      AttributeReadEncryptedPurpose::getEncryptedPurpose, AttributeWriteEncryptedPurpose::setEncryptedPurpose, true);

  /** {@link ThankAttributeField} for {@link AttributeReadSignature#getSignature()}. */
  public static final ThankAttributeField<SignatureBinary, AttributeReadSignature, AttributeWriteSignature<?>> SIGNATURE = new ThankAttributeField<>(
      "sig", AttributeReadSignature.PROPERTY_SIGNATURE, SignatureBinary.class, AttributeReadSignature::getSignature,
      AttributeWriteSignature::setSignature);

  /**
   * The constructor.
   *
   * @param id - see {@link #getId()}.
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param getter - see {@link #get(ThankDataObject)}.
   * @param setter - see {@link #set(ThankDataObject, Object)}.
   */
  public ThankAttributeField(String id, String name, Class<T> type, Function<? super D, T> getter, BiConsumer<? super B, T> setter) {

    this(id, name, type, getter, setter, false);
  }

  /**
   * The constructor.
   *
   * @param id - see {@link #getId()}.
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param getter - see {@link #get(ThankDataObject)}.
   * @param setter - see {@link #set(ThankDataObject, Object)}.
   * @param optional - see {@link #isOptional()}.
   */
  public ThankAttributeField(String id, String name, Class<T> type, Function<? super D, T> getter, BiConsumer<? super B, T> setter,
      boolean optional) {

    super(id, name, type, getter, setter, optional);
  }

}
