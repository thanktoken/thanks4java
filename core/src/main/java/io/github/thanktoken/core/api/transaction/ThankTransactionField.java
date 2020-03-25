package io.github.thanktoken.core.api.transaction;

import java.util.function.BiConsumer;
import java.util.function.Function;

import io.github.mmm.crypto.asymmetric.sign.SignatureBinary;
import io.github.mmm.crypto.crypt.EncryptedData;
import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.attribute.ThankAttributeField;
import io.github.thanktoken.core.api.field.ThankField;
import io.github.thanktoken.core.api.field.ThankFieldMap;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceField;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;

/**
 * {@link ThankField} for {@link ThankTransaction}.
 *
 * @see #getFields()
 * @param <T> see {@link #getType()}.
 */
public class ThankTransactionField<T> extends ThankField<T, ThankTransaction, ThankTransactionBean> {

  private static final FieldMap FIELD_MAP = new FieldMap();

  /** Identifies the {@link ThankTransaction#getTimestamp() timestamp}. */
  public static final ThankTransactionField<ThankTimestamp> TIMESTAMP = new ThankTransactionField<>(
      ThankAttributeField.TIMESTAMP);

  /** Identifies the {@link ThankTransaction#getRecipient() recipient}. */
  public static final ThankTransactionField<ThankAddress> RECIPIENT = new ThankTransactionField<>(
      ThankAttributeField.RECIPIENT, true);

  /** Identifies the {@link ThankTransaction#getPublicPurpose() public purpose}. */
  public static final ThankTransactionField<String> PUBLIC_PURPOSE = new ThankTransactionField<>(
      ThankAttributeField.PUBLIC_PURPOSE);

  /** Identifies the {@link ThankTransaction#getEncryptedPurpose() encrypted purpose}. */
  public static final ThankTransactionField<EncryptedData> ENCRYPTED_PURPOSE = new ThankTransactionField<>(
      ThankAttributeField.ENCRYPTED_PURPOSE);

  /** Identifies the {@link ThankTransaction#getReference() reference}. */
  public static final ThankTransactionField<ThankTokenReferenceType> REFERENCE = new ThankTransactionField<>(
      ThankAttributeField.REFERENCE) {
    @Override
    public ThankFieldMap<?, ?, ?> getFieldMap() {

      return ThankTokenReferenceField.getFields();
    }
  };

  /** Identifies the {@link ThankTransaction#getSignature() signature}. */
  public static final ThankTransactionField<SignatureBinary> SIGNATURE = new ThankTransactionField<>("sig", "signature",
      SignatureBinary.class, ThankTransaction::getSignature, ThankTransactionBean::setSignature);

  /**
   * The constructor.
   */
  private ThankTransactionField(String id, String name, Class<T> type, Function<ThankTransaction, T> getter,
      BiConsumer<ThankTransactionBean, T> setter) {

    this(id, name, type, getter, setter, false);
  }

  /**
   * The constructor.
   */
  private ThankTransactionField(String id, String name, Class<T> type, Function<ThankTransaction, T> getter,
      BiConsumer<ThankTransactionBean, T> setter, boolean optional) {

    super(id, name, type, getter, setter, optional);
    FIELD_MAP.add(this);
  }

  private ThankTransactionField(ThankField<T, ? super ThankTransaction, ? super ThankTransactionBean> parent) {

    super(parent);
    FIELD_MAP.add(this);
  }

  private ThankTransactionField(ThankField<T, ? super ThankTransaction, ? super ThankTransactionBean> parent,
      boolean optional) {

    super(parent, optional);
    FIELD_MAP.add(this);
  }

  /**
   * @return the {@link FieldMap} with all available {@link ThankTransactionField}s.
   */
  public static FieldMap getFields() {

    return FIELD_MAP;
  }

  /**
   * {@link ThankFieldMap} for {@link ThankTransactionField}s.
   */
  public static class FieldMap extends ThankFieldMap<ThankTransaction, ThankTransactionBean, ThankTransactionField<?>> {

    private FieldMap() {

      super();
    }

    @Override
    public ThankTransactionBean newBean() {

      return new ThankTransactionBean();
    }

    @Override
    protected void add(ThankTransactionField<?> field) {

      super.add(field);
    }

  }

}
