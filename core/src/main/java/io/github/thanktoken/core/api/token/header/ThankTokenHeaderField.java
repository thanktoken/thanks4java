package io.github.thanktoken.core.api.token.header;

import net.sf.mmm.crypto.asymmetric.sign.SignatureBinary;

import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.attribute.ThankAttributeField;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.field.ThankField;
import io.github.thanktoken.core.api.field.ThankFieldMap;
import io.github.thanktoken.core.api.location.ThankLocation;
import io.github.thanktoken.core.api.target.ThankTarget;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.transaction.ThankTransactionField;
import io.github.thanktoken.core.api.value.ThankValue;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * {@link ThankField} for {@link ThankToken}.
 *
 * @see #getFields()
 * @param <T> see {@link #getType()}.
 */
public class ThankTokenHeaderField<T> extends ThankField<T, ThankTokenHeader, ThankTokenHeaderBean> {

  private static final FieldMap FIELD_MAP = new FieldMap();

  /** Identifies the {@link ThankTokenHeader#getVersion() version}. */
  public static final ThankTokenHeaderField<ThankVersion> VERSION = new ThankTokenHeaderField<>(ThankAttributeField.VERSION);

  /** Identifies the {@link ThankTokenHeader#getAlgorithm() algorithm}. */
  public static final ThankTokenHeaderField<ThankAlgorithm> ALGORITHM = new ThankTokenHeaderField<>(ThankAttributeField.ALGORITHM);

  /** Identifies the {@link ThankTokenHeader#getCurrency() currency}. */
  public static final ThankTokenHeaderField<ThankCurrency> CURRENCY = new ThankTokenHeaderField<>(ThankAttributeField.CURRENCY);

  /** Identifies the {@link ThankTokenHeader#getAmount() amount}. */
  public static final ThankTokenHeaderField<ThankValue> AMOUNT = new ThankTokenHeaderField<>(ThankAttributeField.AMOUNT);

  /** Identifies the {@link ThankTokenHeader#getTimestamp() timestamp}. */
  public static final ThankTokenHeaderField<ThankTimestamp> TIMESTAMP = new ThankTokenHeaderField<>(ThankAttributeField.TIMESTAMP);

  /** Identifies the {@link ThankTokenHeader#getLocation() location}. */
  public static final ThankTokenHeaderField<ThankLocation> LOCATION = new ThankTokenHeaderField<>(ThankAttributeField.LOCATION);

  /** Identifies the {@link ThankTokenHeader#getTarget() target}. */
  public static final ThankTokenHeaderField<ThankTarget> TARGET = new ThankTokenHeaderField<>(ThankAttributeField.TARGET);

  /** Identifies the {@link ThankTokenHeader#getRecipient() recipient}. */
  public static final ThankTokenHeaderField<ThankAddress> RECIPIENT = new ThankTokenHeaderField<>(ThankAttributeField.RECIPIENT);

  /** Identifies the {@link ThankTokenHeader#getSignature() signature}. */
  public static final ThankTokenHeaderField<SignatureBinary> SIGNATURE = new ThankTokenHeaderField<>(ThankAttributeField.SIGNATURE);

  @Override
  public Class<?> getComponentType() {

    return ThankTransaction.class;
  }

  @Override
  public ThankFieldMap<?, ?, ?> getFieldMap() {

    return ThankTransactionField.getFields();
  }

  /**
   * The constructor.
   *
   * @param parent - see {@link #getParent()}.
   */
  protected ThankTokenHeaderField(ThankField<T, ? super ThankTokenHeader, ? super ThankTokenHeaderMutable<?>> parent) {

    super(parent);
    FIELD_MAP.add(this);
  }

  /**
   * @return the {@link FieldMap} with all available {@link ThankTokenHeaderField}s.
   */
  public static FieldMap getFields() {

    return FIELD_MAP;
  }

  /**
   * {@link ThankFieldMap} for {@link ThankTokenHeaderField}s.
   */
  public static class FieldMap extends ThankFieldMap<ThankTokenHeader, ThankTokenHeaderBean, ThankTokenHeaderField<?>> {

    private FieldMap() {

      super();
    }

    @Override
    public ThankTokenHeaderBean newBean() {

      return new ThankTokenHeaderBean();
    }

    @Override
    protected void add(ThankTokenHeaderField<?> field) {

      super.add(field);
    }

  }

}
