package io.github.thanktoken.core.api.field;

import java.time.Instant;
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

import net.sf.mmm.util.datatype.api.BinaryType;
import net.sf.mmm.util.exception.api.IllegalCaseException;

import io.github.thanktoken.core.api.ThankDataObject;
import io.github.thanktoken.core.api.datatype.IntegerType;
import io.github.thanktoken.core.api.datatype.TimestampHelper;
import io.github.thanktoken.core.api.header.ThankVersion;
import io.github.thanktoken.core.api.io.ThankValueParser;

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
   */
  protected ThankField(String id, String name, Class<T> type, Function<? super D, T> getter,
      BiConsumer<? super B, T> setter) {

    super();
    this.id = id;
    this.name = name;
    this.type = type;
    this.getter = getter;
    this.setter = setter;
    this.parent = null;
  }

  /**
   * The constructor.
   *
   * @param parent - see #getParent.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  // https://github.com/m-m-m/util/issues/166
  protected ThankField(ThankField<T, ? super D, ?/* super B */> parent) {

    this.id = parent.id;
    this.name = parent.name;
    this.type = parent.type;
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
   * @param parser the {@link ThankValueParser}.
   */
  public void setString(B bean, String value, ThankValueParser parser) {

    set(bean, parser.parse(value, this.type));
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
   * @return the value of this {@link ThankField} from the given {@link ThankDataObject}.
   */
  public String getString(D data) {

    T value = get(data);
    return format(value);
  }

  /**
   * @param value the {@link #get(ThankDataObject) value} for this field.
   * @return the {@link String} representation of the given {@code value}.
   */
  public String format(T value) {

    return formatObject(value);
  }

  private static String formatObject(Object value) {

    if (value == null) {
      return "";
    }
    String valueAsString;
    if (value instanceof BinaryType) {
      valueAsString = ((BinaryType) value).getBase64();
    } else if (value instanceof Instant) {
      valueAsString = TimestampHelper.format((Instant) value);
    } else {
      valueAsString = value.toString();
    }
    return valueAsString;
  }

  /**
   * @param data the {@link ThankDataObject} to write as JSON.
   * @param jsonGenerator the {@link JsonGenerator} to write to.
   */
  public void toJson(D data, JsonGenerator jsonGenerator) {

    toJson(data, jsonGenerator, ThankFieldMap.ALL_FIELDS);
  }

  /**
   * @param data the {@link ThankDataObject} to write as JSON.
   * @param jsonGenerator the {@link JsonGenerator} to write to.
   * @param fieldIncluder the {@link Predicate} that {@link Predicate#test(Object) decides} which fields to include. If
   *        it returns {@code false} for a field, that field will be excluded from the JSON.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public void toJson(D data, JsonGenerator jsonGenerator, Predicate<ThankField<?, ?, ?>> fieldIncluder) {

    T value = get(data);
    if (isEmpty(value)) {
      return;
    }
    if (value instanceof ThankDataObject) {
      jsonGenerator.writeKey(this.id);
      ((ThankFieldMap) getFieldMap()).toJson((ThankDataObject) value, jsonGenerator);
    } else if (value instanceof IntegerType) {
      int i = ((IntegerType) value).getValue();
      jsonGenerator.write(this.id, i);
    } else if (value instanceof Collection) {
      jsonGenerator.writeStartArray(this.id);
      Collection<?> collection = (Collection<?>) value;
      for (Object item : collection) {
        if (item instanceof ThankDataObject) {
          ((ThankFieldMap) getFieldMap()).toJson((ThankDataObject) item, jsonGenerator);
        } else if (item instanceof IntegerType) {
          jsonGenerator.write(((IntegerType) item).getValue());
        } else {
          jsonGenerator.write(formatObject(item));
        }
      }
      jsonGenerator.writeEnd();
    } else {
      jsonGenerator.write(this.id, format(value));
    }
  }

  /**
   * @param jsonParser the {@link JsonParser} to read the JSON from.
   * @param e the current {@link Event} from {@link JsonParser#next()}.
   * @param parser the {@link ThankValueParser}.
   * @return the parsed value.
   */
  @SuppressWarnings("unchecked")
  public T fromJson(JsonParser jsonParser, Event e, ThankValueParser parser) {

    return (T) fromJsonUntyped(jsonParser, e, parser);
  }

  private Object fromJsonUntyped(JsonParser jsonParser, Event e, ThankValueParser parser) {

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
        throw new IllegalCaseException(this.type.getName() + "(as JSON number)");
      }
    } else if (e == Event.VALUE_STRING) {
      return parser.parse(jsonParser.getString(), this.type);
    }
    return null;
  }

  /**
   * @param jsonParser the {@link JsonParser} to read the JSON from.
   * @param bean the {@link ThankDataObject} as mutable bean where to apply the data parsed from the JSON.
   * @param parser the {@link ThankValueParser}.
   */
  @SuppressWarnings({ "unchecked" })
  public void fromJson(JsonParser jsonParser, B bean, ThankValueParser parser) {

    Event e = jsonParser.next();
    Object value = fromJsonUntyped(jsonParser, e, parser);
    if ((value == null) && (e != Event.VALUE_NULL)) {
      if (e == Event.START_ARRAY) {
        fromJsonArray(jsonParser, bean, parser);
        return;
      } else if (e == Event.START_OBJECT) {
        ThankFieldMap<?, ?, ?> fieldMap = getFieldMap();
        if (fieldMap == null) {
          throw new IllegalStateException("JSON object for field '" + this.name + "' without field map!");
        }
        value = fieldMap.fromJson(jsonParser, parser);
      } else {
        throw new IllegalCaseException(Event.class, e);
      }
    }
    set(bean, (T) value);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private void fromJsonArray(JsonParser jsonParser, B bean, ThankValueParser parser) {

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
      Object item = fromJsonUntyped(jsonParser, e, parser);
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
