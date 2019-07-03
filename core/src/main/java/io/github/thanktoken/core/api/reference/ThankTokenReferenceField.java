package io.github.thanktoken.core.api.reference;

import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.attribute.ThankAttributeField;
import io.github.thanktoken.core.api.field.ThankField;
import io.github.thanktoken.core.api.field.ThankFieldMap;
import io.github.thanktoken.core.api.location.ThankLocation;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * {@link ThankField} for {@link ThankTokenReference}.
 *
 * @see #getFields()
 * @param <T> see {@link #getType()}.
 */
public final class ThankTokenReferenceField<T> extends ThankField<T, ThankTokenReference, ThankTokenReferenceBean> {

  private static final FieldMap FIELD_MAP = new FieldMap();

  /** Identifies the {@link ThankTokenReference#getVersion() version}. */
  public static final ThankTokenReferenceField<String> TYPE = new ThankTokenReferenceField<>(ThankAttributeField.REFERENCE_TYPE);

  /** Identifies the {@link ThankTokenReference#getTimestamp() timestamp}. */
  public static final ThankTokenReferenceField<ThankTimestamp> TIMESTAMP = new ThankTokenReferenceField<>(ThankAttributeField.TIMESTAMP);

  /** Identifies the {@link ThankTokenReference#getLocation() location}. */
  public static final ThankTokenReferenceField<ThankLocation> LOCATION = new ThankTokenReferenceField<>(ThankAttributeField.LOCATION);

  /** Identifies the {@link ThankTokenReference#getVersion() version}. */
  public static final ThankTokenReferenceField<ThankVersion> VERSION = new ThankTokenReferenceField<>(ThankAttributeField.VERSION, true);

  /** Identifies the {@link ThankTokenReference#getAlgorithm() algorithm}. */
  public static final ThankTokenReferenceField<ThankAlgorithm> ALGORITHM = new ThankTokenReferenceField<>(ThankAttributeField.ALGORITHM,
      true);

  /**
   * The constructor.
   *
   * @param parent - see {@link #getParent()}.
   */
  public ThankTokenReferenceField(ThankField<T, ? super ThankTokenReference, ? super ThankTokenReferenceMutable<?>> parent) {

    super(parent);
    FIELD_MAP.add(this);
  }

  /**
   * The constructor.
   *
   * @param parent - see {@link #getParent()}.
   * @param optional - see {@link #isOptional()}.
   */
  public ThankTokenReferenceField(ThankField<T, ? super ThankTokenReference, ? super ThankTokenReferenceMutable<?>> parent,
      boolean optional) {

    super(parent, optional);
    FIELD_MAP.add(this);
  }

  /**
   * @return the {@link FieldMap} with all available {@link ThankTokenReferenceField}s.
   */
  public static FieldMap getFields() {

    return FIELD_MAP;
  }

  /**
   * {@link ThankFieldMap} for {@link ThankTokenReferenceField}s.
   */
  public static class FieldMap extends ThankFieldMap<ThankTokenReference, ThankTokenReferenceBean, ThankTokenReferenceField<?>> {

    private FieldMap() {

      super();
    }

    @Override
    public ThankTokenReferenceBean newBean() {

      return new ThankTokenReferenceBean();
    }

    @Override
    public ThankTokenReference bean2data(ThankTokenReferenceBean bean) {

      return new ThankTokenReferenceType(bean);
    }

    @Override
    protected void add(ThankTokenReferenceField<?> field) {

      super.add(field);
    }

  }

}
