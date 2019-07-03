package io.github.thanktoken.core.api.token;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import io.github.thanktoken.core.api.field.ThankField;
import io.github.thanktoken.core.api.field.ThankFieldMap;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.token.header.ThankTokenHeaderField;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.transaction.ThankTransactionField;

/**
 * {@link ThankField} for {@link ThankToken}.
 *
 * @see #getFields()
 * @param <T> see {@link #getType()}.
 */
public class ThankTokenField<T> extends ThankField<T, ThankToken, ThankTokenBean> {

  private static final FieldMap FIELD_MAP = new FieldMap();

  /** Identifies the {@link ThankTransaction#getTimestamp() timestamp}. */
  public static final ThankTokenField<ThankTokenHeader> HEADER = new ThankTokenField<ThankTokenHeader>("h", "header", ThankTokenHeader.class,
      ThankToken::getHeader, ThankTokenBean::setHeader) {

    @Override
    public ThankFieldMap<?, ?, ?> getFieldMap() {

      return ThankTokenHeaderField.getFields();
    }
  };

  /** Identifies the {@link ThankTransaction#getRecipient() recipient}. */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static final ThankTokenField<List<ThankTransaction>> TRANSACTIONS = (ThankTokenField) new ThankTokenField<List>("tx",
      "transactions", List.class, ThankToken::getTransactions, ThankTokenBean::setTransactions) {

    @Override
    public Class<?> getComponentType() {

      return ThankTransaction.class;
    }

    @Override
    public ThankFieldMap<?, ?, ?> getFieldMap() {

      return ThankTransactionField.getFields();
    }

  };

  /**
   * The constructor.
   */
  private ThankTokenField(String id, String name, Class<T> type, Function<ThankToken, T> getter, BiConsumer<ThankTokenBean, T> setter) {

    this(id, name, type, getter, setter, false);
  }

  /**
   * The constructor.
   */
  private ThankTokenField(String id, String name, Class<T> type, Function<ThankToken, T> getter, BiConsumer<ThankTokenBean, T> setter,
      boolean optional) {

    super(id, name, type, getter, setter, optional);
    FIELD_MAP.add(this);
  }

  /**
   * @return the {@link FieldMap} with all available {@link ThankTokenField}s.
   */
  public static FieldMap getFields() {

    return FIELD_MAP;
  }

  /**
   * {@link ThankFieldMap} for {@link ThankTokenField}s.
   */
  public static class FieldMap extends ThankFieldMap<ThankToken, ThankTokenBean, ThankTokenField<?>> {

    private FieldMap() {

      super();
    }

    @Override
    public ThankTokenBean newBean() {

      return new ThankTokenBean();
    }

    @Override
    protected void add(ThankTokenField<?> field) {

      super.add(field);
    }

    @Override
    public ThankToken bean2data(ThankTokenBean bean) {

      return new ThankTokenType(bean);
    }

  }

}
