package io.github.thanktoken.core.api.transaction;

import java.time.Instant;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import io.github.thanktoken.core.api.field.ThankField;
import io.github.thanktoken.core.api.field.ThankFieldMap;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceField;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;
import net.sf.mmm.security.api.crypt.SecurityEncryptedData;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;
import net.sf.mmm.security.api.sign.SecuritySignature;

/**
 * {@link ThankField} for {@link ThankTransaction}.
 *
 * @see #getFields()
 * @param <T> see {@link #getType()}.
 */
public class ThankTransactionField<T> extends ThankField<T, ThankTransaction, ThankTransactionBean> {

  private static final FieldMap FIELD_MAP = new FieldMap();

  /** Identifies the {@link ThankTransaction#getTimestamp() timestamp}. */
  public static final ThankTransactionField<Instant> TIMESTAMP = new ThankTransactionField<>("ts", "timestamp", Instant.class, ThankTransaction::getTimestamp,
      ThankTransactionBean::setTimestamp);

  /** Identifies the {@link ThankTransaction#getRecipient() recipient}. */
  public static final ThankTransactionField<SecurityPublicKey> RECIPIENT = new ThankTransactionField<>("rcp", "recipient", SecurityPublicKey.class,
      ThankTransaction::getRecipient, ThankTransactionBean::setRecipient);

  /** Identifies the {@link ThankTransaction#getPublicPurpose() public purpose}. */
  public static final ThankTransactionField<String> PUBLIC_PURPOSE = new ThankTransactionField<>("pp", "publicPurpose", String.class,
      ThankTransaction::getPublicPurpose, ThankTransactionBean::setPublicPurpose);

  /** Identifies the {@link ThankTransaction#getEncryptedPurpose() encrypted purpose}. */
  public static final ThankTransactionField<SecurityEncryptedData> ENCRYPTED_PURPOSE = new ThankTransactionField<>("ep", "encryptedPurpose",
      SecurityEncryptedData.class, ThankTransaction::getEncryptedPurpose, ThankTransactionBean::setEncryptedPurpose);

  /** Identifies the {@link ThankTransaction#getReference() reference}. */
  public static final ThankTransactionField<ThankTokenReferenceType> REFERENCE = new ThankTransactionField<ThankTokenReferenceType>("ref", "reference",
      ThankTokenReferenceType.class, ThankTransaction::getReference, ThankTransactionBean::setReference) {

    @Override
    public ThankFieldMap<?, ?, ?> getFieldMap() {

      return ThankTokenReferenceField.getFields();
    }

  };

  /** Identifies the {@link ThankTransaction#getReferences() references}. */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static final ThankTransactionField<List<ThankTokenReferenceType>> REFERENCES = (ThankTransactionField) new ThankTransactionField<List>("refs",
      "references", List.class, ThankTransaction::getReferences, ThankTransactionBean::setReferenceList) {

    @Override
    public Class<?> getComponentType() {

      return ThankTokenReferenceType.class;
    }

    @Override
    public ThankFieldMap<?, ?, ?> getFieldMap() {

      return ThankTokenReferenceField.getFields();
    }

  };

  /** Identifies the {@link ThankTransaction#getSignature() signature}. */
  public static final ThankTransactionField<SecuritySignature> SIGNATURE = new ThankTransactionField<>("sig", "signature", SecuritySignature.class,
      ThankTransaction::getSignature, ThankTransactionBean::setSignature);

  /**
   * The constructor.
   */
  private ThankTransactionField(String id, String name, Class<T> type, Function<ThankTransaction, T> getter, BiConsumer<ThankTransactionBean, T> setter) {

    super(id, name, type, getter, setter);
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
