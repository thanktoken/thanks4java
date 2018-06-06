/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.attribute;

import java.time.Instant;
import java.util.function.BiConsumer;
import java.util.function.Function;

import io.github.thanktoken.core.api.ThankDataObject;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.datatype.ThankValue;
import io.github.thanktoken.core.api.field.ThankField;
import io.github.thanktoken.core.api.field.ThankFieldMap;
import io.github.thanktoken.core.api.header.ThankLocation;
import io.github.thanktoken.core.api.header.ThankTarget;
import io.github.thanktoken.core.api.header.ThankVersion;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceField;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;
import net.sf.mmm.security.api.crypt.SecurityEncryptedData;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;
import net.sf.mmm.security.api.sign.SecuritySignature;

/**
 * {@link ThankField}s to use as {@link #getParent() parent} for extension.
 *
 * @param <T> generic type of the value of this field.
 * @param <D> generic type of the {@link ThankDataObject} owning this {@link ThankField}.
 * @param <B> generic type of the mutable bean owning this {@link ThankField}.
 */
public class ThankAttributeField<T, D extends ThankDataObject, B extends D> extends ThankField<T, D, B> {

  /** {@link ThankAttributeField} for {@link AttributeReadVersion#getVersion()}. */
  public static final ThankAttributeField<ThankVersion, AttributeReadVersion, AttributeWriteVersion<?>> VERSION = new ThankAttributeField<>("v", "version",
      ThankVersion.class, AttributeReadVersion::getVersion, AttributeWriteVersion::setVersion);

  /** {@link ThankAttributeField} for {@link AttributeReadAmount#getAmount()}. */
  public static final ThankAttributeField<ThankValue, AttributeReadAmount, AttributeWriteAmount<?>> AMOUNT = new ThankAttributeField<>("a", "amount",
      ThankValue.class, AttributeReadAmount::getAmount, AttributeWriteAmount::setAmount);

  /** {@link ThankAttributeField} for {@link AttributeReadAmount#getAmount()}. */
  public static final ThankAttributeField<ThankCurrency, AttributeReadCurrency, AttributeWriteCurrency<?>> CURRENCY = new ThankAttributeField<>("c", "currency",
      ThankCurrency.class, AttributeReadCurrency::getCurrency, AttributeWriteCurrency::setCurrency);

  /** {@link ThankAttributeField} for {@link AttributeReadTimestamp#getTimestamp()}. */
  public static final ThankAttributeField<Instant, AttributeReadTimestamp, AttributeWriteTimestamp<?>> TIMESTAMP = new ThankAttributeField<>("ts", "timestamp",
      Instant.class, AttributeReadTimestamp::getTimestamp, AttributeWriteTimestamp::setTimestamp);

  /** {@link ThankAttributeField} for {@link AttributeReadLocation#getLocation()}. */
  public static final ThankAttributeField<ThankLocation, AttributeReadLocation, AttributeWriteLocation<?>> LOCATION = new ThankAttributeField<>("loc",
      "location", ThankLocation.class, AttributeReadLocation::getLocation, AttributeWriteLocation::setLocation);

  /** {@link ThankAttributeField} for {@link AttributeReadTarget#getTarget()}. */
  public static final ThankAttributeField<ThankTarget, AttributeReadTarget, AttributeWriteTarget<?>> TARGET = new ThankAttributeField<>("tgt", "target",
      ThankTarget.class, AttributeReadTarget::getTarget, AttributeWriteTarget::setTarget);

  /** {@link ThankAttributeField} for {@link AttributeReadRecipient#getRecipient()}. */
  public static final ThankAttributeField<SecurityPublicKey, AttributeReadRecipient, AttributeWriteRecipient<?>> RECIPIENT = new ThankAttributeField<>("rcp",
      "recipient", SecurityPublicKey.class, AttributeReadRecipient::getRecipient, AttributeWriteRecipient::setRecipient);

  /** {@link ThankAttributeField} for {@link AttributeReadReference#getReference()}. */
  public static final ThankAttributeField<ThankTokenReferenceType, AttributeReadReference, AttributeWriteReference<?>> REFERENCE = new ThankAttributeField<ThankTokenReferenceType, AttributeReadReference, AttributeWriteReference<?>>(
      "ref", "reference", ThankTokenReferenceType.class, AttributeReadReference::getReference, AttributeWriteReference::setReference) {

    @Override
    public ThankFieldMap<?, ?, ?> getFieldMap() {

      return ThankTokenReferenceField.getFields();
    }

  };

  /** {@link ThankAttributeField} for {@link AttributeReadPublicPurpose#getPublicPurpose()}. */
  public static final ThankAttributeField<String, AttributeReadPublicPurpose, AttributeWritePublicPurpose<?>> PUBLIC_PURPOSE = new ThankAttributeField<>("pp",
      "publicPurpose", String.class, AttributeReadPublicPurpose::getPublicPurpose, AttributeWritePublicPurpose::setPublicPurpose);

  /** {@link ThankAttributeField} for {@link AttributeReadEncryptedPurpose#getEncryptedPurpose()}. */
  public static final ThankAttributeField<SecurityEncryptedData, AttributeReadEncryptedPurpose, AttributeWriteEncryptedPurpose<?>> ENCRYPTED_PURPOSE = new ThankAttributeField<>(
      "ep", "encryptedPurpose", SecurityEncryptedData.class, AttributeReadEncryptedPurpose::getEncryptedPurpose,
      AttributeWriteEncryptedPurpose::setEncryptedPurpose);

  /** {@link ThankAttributeField} for {@link AttributeReadSignature#getSignature()}. */
  public static final ThankAttributeField<SecuritySignature, AttributeReadSignature, AttributeWriteSignature<?>> SIGNATURE = new ThankAttributeField<>("sig",
      "signature", SecuritySignature.class, AttributeReadSignature::getSignature, AttributeWriteSignature::setSignature);

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

    super(id, name, type, getter, setter);
  }

}
