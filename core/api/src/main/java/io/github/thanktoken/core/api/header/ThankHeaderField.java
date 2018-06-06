package io.github.thanktoken.core.api.header;

import java.time.Instant;
import java.util.function.BiConsumer;
import java.util.function.Function;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.attribute.ThankAttributeField;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.datatype.ThankValue;
import io.github.thanktoken.core.api.field.ThankField;
import io.github.thanktoken.core.api.field.ThankFieldMap;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.transaction.ThankTransactionField;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;
import net.sf.mmm.security.api.sign.SecuritySignature;

/**
 * {@link ThankField} for {@link ThankToken}.
 *
 * @see #getFields()
 * @param <T> see {@link #getType()}.
 */
public class ThankHeaderField<T> extends ThankField<T, ThankHeader, ThankHeaderBean> {

  private static final FieldMap FIELD_MAP = new FieldMap();

  /** Identifies the {@link ThankHeader#getVersion() version}. */
  public static final ThankHeaderField<ThankVersion> VERSION = new ThankHeaderField<>(ThankAttributeField.VERSION);

  /** Identifies the {@link ThankHeader#getAmount() amount}. */
  public static final ThankHeaderField<ThankValue> AMOUNT = new ThankHeaderField<>(ThankAttributeField.AMOUNT);

  /** Identifies the {@link ThankHeader#getCurrency() currency}. */
  public static final ThankHeaderField<ThankCurrency> CURRENCY = new ThankHeaderField<>(ThankAttributeField.CURRENCY);

  /** Identifies the {@link ThankHeader#getTimestamp() timestamp}. */
  public static final ThankHeaderField<Instant> TIMESTAMP = new ThankHeaderField<>(ThankAttributeField.TIMESTAMP);

  /** Identifies the {@link ThankHeader#getLocation() location}. */
  public static final ThankHeaderField<ThankLocation> LOCATION = new ThankHeaderField<>(ThankAttributeField.LOCATION);

  /** Identifies the {@link ThankHeader#getTarget() target}. */
  public static final ThankHeaderField<ThankTarget> TARGET = new ThankHeaderField<>(ThankAttributeField.TARGET);

  /** Identifies the {@link ThankHeader#getRecipient() recipient}. */
  public static final ThankHeaderField<SecurityPublicKey> RECIPIENT = new ThankHeaderField<>(ThankAttributeField.RECIPIENT);

  /** Identifies the {@link ThankHeader#getSignature() signature}. */
  public static final ThankHeaderField<SecuritySignature> SIGNATURE = new ThankHeaderField<>(ThankAttributeField.SIGNATURE);

  @Override
  public Class<?> getComponentType() {

    return ThankTransaction.class;
  }

  @Override
  public ThankFieldMap<?, ?, ?> getFieldMap() {

    return ThankTransactionField.getFields();
  }

  private ThankHeaderField(String id, String name, Class<T> type, Function<ThankHeader, T> getter, BiConsumer<ThankHeaderBean, T> setter) {

    super(id, name, type, getter, setter);
    FIELD_MAP.add(this);
  }

  private ThankHeaderField(ThankField<T, ? super ThankHeader, ? super ThankHeaderBean> parent) {

    super(parent);
    FIELD_MAP.add(this);
  }

  /**
   * @return the {@link FieldMap} with all available {@link ThankHeaderField}s.
   */
  public static FieldMap getFields() {

    return FIELD_MAP;
  }

  /**
   * {@link ThankFieldMap} for {@link ThankHeaderField}s.
   */
  public static class FieldMap extends ThankFieldMap<ThankHeader, ThankHeaderBean, ThankHeaderField<?>> {

    private FieldMap() {

      super();
    }

    @Override
    public ThankHeaderBean newBean() {

      return new ThankHeaderBean();
    }

    @Override
    protected void add(ThankHeaderField<?> field) {

      super.add(field);
    }

  }

}
