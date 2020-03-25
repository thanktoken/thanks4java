package io.github.thanktoken.core.api.field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import io.github.mmm.binary.BinaryType;
import io.github.mmm.binary.codec.BinaryCodec;
import io.github.thanktoken.core.api.context.ThankTokenContext;
import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.datatype.IntegerType;
import io.github.thanktoken.core.api.io.ThankValueParser;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * An instance of {@link ThankField} represent a field (property) of a {@link ThankDataObject}.
 *
 * @param <T> generic type of the value of this field.
 * @param <D> generic type of the {@link ThankDataObject} owning this {@link ThankField}.
 * @param <B> generic type of the mutable bean owning this {@link ThankField}.
 */
public abstract class ThankField<T, D extends ThankDataObject, B extends D> {

  private final String id;

  private final String name;

  private final Class<T> type;

  private final boolean optional;

  private final Function<? super D, T> getter;

  private final BiConsumer<? super B, T> setter;

  private final ThankField<T, ? super D, ?> parent;

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
  protected ThankField(String id, String name, Class<T> type, Function<? super D, T> getter,
      BiConsumer<? super B, T> setter, boolean optional) {

    super();
    this.id = id;
    this.name = name;
    this.type = type;
    this.optional = optional;
    this.getter = getter;
    this.setter = setter;
    this.parent = null;
  }

  /**
   * The constructor.
   *
   * @param parent - see {@link #getParent()}.
   */
  // https://github.com/m-m-m/util/issues/166
  protected ThankField(ThankField<T, ? super D, ?/* super B */> parent) {

    this(parent, parent.optional);
  }

  /**
   * The constructor.
   *
   * @param parent - see {@link #getParent()}.
   * @param optional - see {@link #isOptional()}.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  // https://github.com/m-m-m/util/issues/166
  protected ThankField(ThankField<T, ? super D, ?/* super B */> parent, boolean optional) {

    super();
    this.id = parent.id;
    this.name = parent.name;
    this.type = parent.type;
    this.optional = optional;
    this.getter = parent.getter;
    this.setter = (BiConsumer) parent.setter;
    this.parent = parent;
  }

  /**
   * @return the unique short name of this field.
   */
  public String getId() {

    return this.id;
  }

  /**
   * @return the unique long name of the field.
   */
  public String getName() {

    return this.name;
  }

  /**
   * @return {@link Class} reflecting the actual type of this field.
   */
  public Class<T> getType() {

    return this.type;
  }

  /**
   * @return {@link Class} reflecting the component type or {@code null} if the {@link #getType() type} is no
   *         {@link java.util.Collection} or array type.
   */
  public Class<?> getComponentType() {

    if (this.parent != null) {
      return this.parent.getComponentType();
    }
    return null;
  }

  /**
   * @return {@code true} if optional (may be {@code null} but still valid), {@code false} otherwise (required not to be
   *         {@code null}).
   */
  public boolean isOptional() {

    return this.optional;
  }

  /**
   * @return {@code true} if mandatory, {@code false} otherwise (if {@link #isOptional() optional}).
   */
  public boolean isMandatory() {

    return !this.optional;
  }

  /**
   * @return the {@link ThankFieldMap} if the {@link #getType() type} or {@link #getComponentType() component type} of
   *         this field is a {@link ThankDataObject}, {@code null} otherwise.
   */
  public ThankFieldMap<?, ?, ?> getFieldMap() {

    if (this.parent != null) {
      return this.parent.getFieldMap();
    }
    return null;
  }

  /**
   * @return the parent {@link ThankField} to inherit this field or {@code null} if not inherited.
   */
  public ThankField<T, ? super D, ?> getParent() {

    return this.parent;
  }

  /**
   * @param value the value to set for this {@link ThankField}.
   * @param bean the {@link ThankDataObject} where to set this {@link ThankField} to the given {@code value}.
   */
  public void set(B bean, T value) {

    this.setter.accept(bean, value);
  }

  /**
   * @param value the value to set for this {@link ThankField}.
   * @param bean the {@link ThankDataObject} where to set this {@link ThankField} to the given {@code value}.
   * @param context the {@link ThankTokenContext} required for parsing and formatting context-sensitive fields.
   * @param parser the {@link ThankValueParser}.
   */
  public void setString(B bean, String value, ThankValueParser parser, ThankTokenContext context) {

    set(bean, parser.parse(value, this.type, context));
  }

  /**
   * @param data the {@link ThankDataObject} where to get this {@link ThankField} from.
   * @return the value of this {@link ThankField} from the given {@link ThankDataObject}.
   */
  public T get(D data) {

    return this.getter.apply(data);
  }

  /**
   * @param data the {@link ThankDataObject} where to get this {@link ThankField} from.
   * @param context the {@link ThankTokenContext} required for parsing and formatting context-sensitive fields.
   * @return the value of this {@link ThankField} from the given {@link ThankDataObject}.
   */
  public String getString(D data, ThankTokenContext context) {

    T value = get(data);
    return format(value, context);
  }

  /**
   * @param value the {@link #get(ThankDataObject) value} for this field.
   * @param context the {@link ThankTokenContext} required for parsing and formatting context-sensitive fields.
   * @return the {@link String} representation of the given {@code value}.
   */
  public String format(T value, ThankTokenContext context) {

    return formatObject(value, context);
  }

  private static String formatObject(Object value, ThankTokenContext context) {

    if (value == null) {
      return "";
    }
    String valueAsString;
    if (value instanceof BinaryType) {
      BinaryCodec codec = getBinaryCodec(context);
      valueAsString = ((BinaryType) value).format(codec);
    } else {
      valueAsString = value.toString();
    }
    return valueAsString;
  }

  private static BinaryCodec getBinaryCodec(ThankTokenContext context) {

    return context.getVersion().getCodec();
  }

  /**
   * @param data the {@link ThankDataObject} to write as JSON.
   * @param context the {@link ThankTokenContext} required for parsing and formatting context-sensitive fields.
   * @param jsonGenerator the {@link JsonGenerator} to write to.
   */
  public void toJson(D data, ThankTokenContext context, JsonGenerator jsonGenerator) {

    toJson(data, context, jsonGenerator, ThankFieldMap.ALL_FIELDS);
  }

  /**
   * @param data the {@link ThankDataObject} to write as JSON.
   * @param context the {@link ThankTokenContext} required for parsing and formatting context-sensitive fields.
   * @param jsonGenerator the {@link JsonGenerator} to write to.
   * @param fieldIncluder the {@link Predicate} that {@link Predicate#test(Object) decides} which fields to include. If
   *        it returns {@code false} for a field, that field will be excluded from the JSON.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public void toJson(D data, ThankTokenContext context, JsonGenerator jsonGenerator,
      Predicate<ThankField<?, ?, ?>> fieldIncluder) {

    T value = get(data);
    if (isEmpty(value)) {
      return;
    }
    if (value instanceof ThankDataObject) {
      jsonGenerator.writeKey(this.id);
      ((ThankFieldMap) getFieldMap()).toJson((ThankDataObject) value, context, jsonGenerator);
    } else if (value instanceof IntegerType) {
      int i = ((IntegerType) value).getValue();
      jsonGenerator.write(this.id, i);
    } else if (value instanceof Collection) {
      jsonGenerator.writeStartArray(this.id);
      Collection<?> collection = (Collection<?>) value;
      for (Object item : collection) {
        if (item instanceof ThankDataObject) {
          ((ThankFieldMap) getFieldMap()).toJson((ThankDataObject) item, context, jsonGenerator);
        } else if (item instanceof IntegerType) {
          jsonGenerator.write(((IntegerType) item).getValue());
        } else {
          jsonGenerator.write(formatObject(item, context));
        }
      }
      jsonGenerator.writeEnd();
    } else {
      jsonGenerator.write(this.id, format(value, context));
    }
  }

  /**
   * @param jsonParser the {@link JsonParser} to read the JSON from.
   * @param e the current {@link Event} from {@link JsonParser#next()}.
   * @param parser the {@link ThankValueParser}.
   * @param context the {@link ThankTokenContext} required for parsing context-sensitive fields.
   * @return the parsed value.
   */
  @SuppressWarnings("unchecked")
  public T fromJson(JsonParser jsonParser, Event e, ThankValueParser parser, ThankTokenContext context) {

    return (T) fromJsonUntyped(jsonParser, e, parser, context);
  }

  private Object fromJsonUntyped(JsonParser jsonParser, Event e, ThankValueParser parser, ThankTokenContext context) {

    if (e == Event.VALUE_NULL) {
      return null;
    } else if (e == Event.VALUE_TRUE) {
      return Boolean.TRUE;
    } else if (e == Event.VALUE_FALSE) {
      return Boolean.FALSE;
    } else if (e == Event.VALUE_NUMBER) {
      if (this.type.equals(Integer.class)) {
        return Integer.valueOf(jsonParser.getInt());
      } else if (this.type.equals(Long.class)) {
        return Long.valueOf(jsonParser.getLong());
      } else if (this.type.equals(ThankVersion.class)) {
        return ThankVersion.of(jsonParser.getInt());
      } else {
        throw new IllegalArgumentException(this.type.getName() + "(as JSON number)");
      }
    } else if (e == Event.VALUE_STRING) {
      return parser.parse(jsonParser.getString(), this.type, context);
    }
    return null;
  }

  /**
   * @param jsonParser the {@link JsonParser} to read the JSON from.
   * @param bean the {@link ThankDataObject} as mutable bean where to apply the data parsed from the JSON.
   * @param parser the {@link ThankValueParser}.
   * @param context the {@link ThankTokenContext} required for parsing context-sensitive fields.
   * @return the {@link ThankValueParser}. Typically the given one but may be
   *         {@link #enhanceParser(ThankValueParser, Object) enhanced}.
   */
  @SuppressWarnings({ "unchecked" })
  public ThankValueParser fromJson(JsonParser jsonParser, B bean, ThankValueParser parser, ThankTokenContext context) {

    Event e = jsonParser.next();
    Object value = fromJsonUntyped(jsonParser, e, parser, context);
    if ((value == null) && (e != Event.VALUE_NULL)) {
      if (e == Event.START_ARRAY) {
        fromJsonArray(jsonParser, bean, parser, context);
        return parser;
      } else if (e == Event.START_OBJECT) {
        ThankFieldMap<?, ?, ?> fieldMap = getFieldMap();
        if (fieldMap == null) {
          throw new IllegalStateException("JSON object for field '" + this.name + "' without field map!");
        }
        value = fieldMap.fromJson(jsonParser, parser, context);
      } else {
        throw new IllegalStateException(e.toString());
      }
    }
    set(bean, (T) value);
    parser = enhanceParser(parser, value);
    return parser;
  }

  /**
   * @param parser the current {@link ThankValueParser}.
   * @param value the parsed field value.
   * @return The {@link ThankValueParser} used to proceed parsing the data. Typically the given {@code parser}. Allows a
   *         specific field (such as {@link ThankTokenHeader#getCurrency() currency}) to enhance the parser.
   */
  protected ThankValueParser enhanceParser(ThankValueParser parser, Object value) {

    return parser;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private void fromJsonArray(JsonParser jsonParser, B bean, ThankValueParser parser, ThankTokenContext context) {

    Class<?> componentType = getComponentType();
    if (componentType == null) {
      throw new IllegalStateException("JSON array for field '" + this.name + "' without component type!");
    }
    Collection collection = (Collection) this.getter.apply(bean);
    boolean setCollection = (collection == null);
    if (collection == null) {
      collection = createCollection();
    }
    Event e = jsonParser.next();
    while (e != Event.END_ARRAY) {
      Object item = fromJsonUntyped(jsonParser, e, parser, context);
      collection.add(item);
      e = jsonParser.next();
    }
    if (setCollection) {
      this.setter.accept(bean, (T) collection);
    }
  }

  private <V> Collection<V> createCollection() {

    if (this.type.equals(List.class)) {
      return new ArrayList<>();
    } else if (this.type.equals(Set.class)) {
      return new HashSet<>();
    } else {
      throw new IllegalStateException("Can not create collection of type '" + this.type.getName() + "'.");
    }
  }

  private static boolean isEmpty(Object value) {

    if (value == null) {
      return true;
    } else if (value instanceof String) {
      return ((String) value).isEmpty();
    } else if (value instanceof Collection) {
      return ((Collection<?>) value).isEmpty();
    }
    return false;
  }

  @Override
  public String toString() {

    return this.name;
  }

}
