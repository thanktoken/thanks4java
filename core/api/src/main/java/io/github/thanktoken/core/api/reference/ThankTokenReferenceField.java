package io.github.thanktoken.core.api.reference;

import java.time.Instant;
import java.util.function.BiConsumer;
import java.util.function.Function;

import io.github.thanktoken.core.api.field.ThankField;
import io.github.thanktoken.core.api.field.ThankFieldMap;
import io.github.thanktoken.core.api.header.ThankLocation;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;

/**
 * {@link ThankField} for {@link ThankTokenReference}.
 *
 * @see #getFields()
 * @param <T> see {@link #getType()}.
 */
public final class ThankTokenReferenceField<T> extends ThankField<T, ThankTokenReference, ThankTokenReferenceBean> {

  private static final FieldMap FIELD_MAP = new FieldMap();

  /** Identifies the {@link ThankTokenReference#getTimestamp() timestamp}. */
  public static final ThankTokenReferenceField<Instant> TIMESTAMP = new ThankTokenReferenceField<>("ts", "timestamp", Instant.class,
      ThankTokenReference::getTimestamp, ThankTokenReferenceBean::setTimestamp);

  /** Identifies the {@link ThankTokenReference#getLocation() location}. */
  public static final ThankTokenReferenceField<ThankLocation> LOCATION = new ThankTokenReferenceField<>("loc", "location", ThankLocation.class,
      ThankTokenReference::getLocation, ThankTokenReferenceBean::setLocation);

  /** Identifies the {@link ThankTokenReference#getRecipient() recipient}. */
  public static final ThankTokenReferenceField<SecurityPublicKey> RECIPIENT = new ThankTokenReferenceField<>("seq", "creator", SecurityPublicKey.class,
      ThankTokenReference::getRecipient, ThankTokenReferenceBean::setRecipient);

  private ThankTokenReferenceField(String id, String name, Class<T> type, Function<ThankTokenReference, T> getter,
      BiConsumer<ThankTokenReferenceBean, T> setter) {

    super(id, name, type, getter, setter);
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
