package io.github.thanktoken.core.api.field;

import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import io.github.mmm.base.exception.ObjectNotFoundException;
import io.github.mmm.crypto.CryptoBinary;
import io.github.mmm.crypto.hash.HashCreator;
import io.github.thanktoken.core.api.attribute.AttributeReadSignature;
import io.github.thanktoken.core.api.context.ThankTokenContext;
import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.io.ThankValueParser;

/**
 * Map for available {@link ThankField}s of a {@link ThankDataObject}.
 *
 * @see #get(String)
 *
 * @param <D> type of the owning {@link ThankDataObject}.
 * @param <B> type of the mutable {@link ThankDataObject}.
 * @param <F> type of the contained {@link ThankField}s.
 */
public abstract class ThankFieldMap<D extends ThankDataObject, B extends D, F extends ThankField<?, D, B>>
    implements Iterable<F> {

  private static final Map<String, Object> JSON_CONFIG = createConfig();

  private final List<F> fields;

  private final List<F> all;

  private final Map<String, F> fieldMap;

  /** Predicate that accepts all fields. */
  public static final Predicate<ThankField<?, ?, ?>> ALL_FIELDS = (x) -> true;

  private static final Predicate<ThankField<?, ?, ?>> ALL_FIELDS_BUT_SIGNATURE = (
      x) -> (x.getName() != AttributeReadSignature.PROPERTY_SIGNATURE);

  /**
   * The constructor.
   */
  public ThankFieldMap() {

    super();
    this.fields = new ArrayList<>();
    this.all = Collections.unmodifiableList(this.fields);
    this.fieldMap = new HashMap<>();
  }

  private static Map<String, Object> createConfig() {

    Map<String, Object> map = new HashMap<>();
    // map.put(JsonGenerator.PRETTY_PRINTING, Boolean.FALSE);
    return map;
  }

  /**
   * @param field the {@link ThankField} to add.
   */
  protected void add(F field) {

    this.fields.add(field);
    add(field.getId(), field);
    add(field.getName(), field);
  }

  private void add(String key, F field) {

    doAdd(key, field);
    doAdd('"' + key + '"', field);
  }

  private void doAdd(String key, F field) {

    F old = this.fieldMap.put(key, field);
    if (old != null) {
      throw new IllegalArgumentException(key);
    }
  }

  /**
   * @param nameOrId the {@link ThankField#getName() name} or {@link ThankField#getId() ID} of the field.
   * @return the requested {@link ThankField} or {@code null} if no such field exists.
   */
  public F get(String nameOrId) {

    F field = this.fieldMap.get(nameOrId);
    if (field == null) {
      field = this.fieldMap.get(nameOrId.trim());
    }
    return field;
  }

  /**
   * @return the {@link List} of {@link ThankField}s.
   */
  public List<F> getAll() {

    return this.all;
  }

  @Override
  public Iterator<F> iterator() {

    return this.all.iterator();
  }

  /**
   * @param bean the {@link ThankDataObject} bean.
   * @return the converted {@link ThankDataObject}.
   */
  public D bean2data(B bean) {

    return bean;
  }

  /**
   * @return a new instance of the {@link ThankDataObject} bean.
   */
  public abstract B newBean();

  /**
   * @param jsonParser the {@link JsonParser} to read the JSON from.
   * @param valueParser the {@link ThankValueParser}.
   * @return the parsed {@link ThankDataObject}.
   */
  public D fromJson(JsonParser jsonParser, ThankValueParser valueParser) {

    return fromJson(jsonParser, valueParser, null);
  }

  /**
   * @param jsonParser the {@link JsonParser} to read the JSON from.
   * @param valueParser the {@link ThankValueParser}.
   * @param context the {@link ThankTokenContext} required for parsing or formatting context-sensitive fields.
   * @return the parsed {@link ThankDataObject}.
   */
  public D fromJson(JsonParser jsonParser, ThankValueParser valueParser, ThankTokenContext context) {

    B bean = newBean();
    if ((context == null) && (bean instanceof ThankTokenContext)) {
      context = (ThankTokenContext) bean;
    }
    fromJson(jsonParser, valueParser, bean, context);
    return bean2data(bean);
  }

  /**
   * @param jsonParser the {@link JsonParser} to read the JSON from.
   * @param valueParser the {@link ThankValueParser}.
   * @param bean the mutable {@link ThankDataObject} where to apply the parsed data to.
   * @param context the {@link ThankTokenContext} required for parsing or formatting context-sensitive fields.
   */
  public void fromJson(JsonParser jsonParser, ThankValueParser valueParser, B bean, ThankTokenContext context) {

    boolean started = false;
    while (jsonParser.hasNext()) {
      Event e = jsonParser.next();
      if (e == Event.START_OBJECT) {
        if (started) {
          throw new IllegalStateException(e.toString());
        }
      } else if (e == Event.END_OBJECT) {
        return;
      } else if (e == Event.KEY_NAME) {
        String key = jsonParser.getString();
        F field = get(key);
        if (field == null) {
          throw new ObjectNotFoundException(bean.getClass(), key);
        }
        field.fromJson(jsonParser, bean, valueParser, context);
      } else {
        throw new IllegalStateException(e.toString());
      }
      started = true;
    }
  }

  /**
   * @param data the {@link ThankDataObject} to write as JSON.
   * @return the JSON as {@link String}.
   */
  public String toJson(D data) {

    assert (data instanceof ThankTokenContext);
    return toJson(data, null);
  }

  /**
   * @param data the {@link ThankDataObject} to write as JSON.
   * @param context the {@link ThankTokenContext} required for parsing or formatting context-sensitive fields.
   * @return the JSON as {@link String}.
   */
  public String toJson(D data, ThankTokenContext context) {

    Writer writer = new StringWriter(2048);
    JsonGenerator jsonGenerator = Json.createGeneratorFactory(JSON_CONFIG).createGenerator(writer);
    toJson(data, context, jsonGenerator);
    jsonGenerator.close();
    return writer.toString();
  }

  /**
   * @param data the {@link ThankDataObject} to write as JSON.
   * @param context the {@link ThankTokenContext} required for parsing or formatting context-sensitive fields.
   * @param jsonGenerator the {@link JsonGenerator} to write to.
   */
  public void toJson(D data, ThankTokenContext context, JsonGenerator jsonGenerator) {

    toJson(data, context, jsonGenerator, ThankFieldMap.ALL_FIELDS);
  }

  /**
   * @param data the {@link ThankDataObject} to write as JSON.
   * @param context the {@link ThankTokenContext} required for parsing or formatting context-sensitive fields.
   * @param jsonGenerator the {@link JsonGenerator} to write to.
   * @param fieldIncluder the {@link Predicate} that {@link Predicate#test(Object) decides} which fields to include. If
   *        it returns {@code false} for a field, that field will be excluded from the JSON.
   */
  public void toJson(D data, ThankTokenContext context, JsonGenerator jsonGenerator,
      Predicate<ThankField<?, ?, ?>> fieldIncluder) {

    if ((context == null) && (data instanceof ThankTokenContext)) {
      context = (ThankTokenContext) data;
    }
    jsonGenerator.writeStartObject();
    for (F field : this.fields) {
      if (fieldIncluder.test(field)) {
        field.toJson(data, context, jsonGenerator, fieldIncluder);
      }
    }
    jsonGenerator.writeEnd();
  }

  /**
   * Creates the hash that needs to be signed for the signature. It therefore hashes {@link #getAll() all fields in
   * defined order} excluding the signature.
   *
   * @param data the {@link ThankDataObject} to hash.
   * @param hashCreator the {@link HashCreator}.
   * @return the {@link HashCreator#hash() hash} of the given {@link ThankDataObject}.
   * @see io.github.thanktoken.core.api.attribute.AttributeReadHash#getHash2Sign()
   */
  public byte[] hash2Sign(D data, HashCreator hashCreator) {

    return hash(data, hashCreator, ALL_FIELDS_BUT_SIGNATURE);
  }

  /**
   * @param data the {@link ThankDataObject} to hash.
   * @param hashCreator the {@link HashCreator}.
   * @param fieldIncluder the {@link Predicate} that {@link Predicate#test(Object) decides} which fields to include. If
   *        it returns {@code false} for a field, that field will be excluded from the hash.
   * @return the {@link HashCreator#hash() hash} of the given {@link ThankDataObject}.
   */
  public byte[] hash(D data, HashCreator hashCreator, Predicate<ThankField<?, ?, ?>> fieldIncluder) {

    for (F field : this.fields) {
      if (fieldIncluder.test(field)) {
        Object value = field.get(data);
        boolean empty = false;
        if (value == null) {
          empty = true;
        } else if (value instanceof CryptoBinary) {
          CryptoBinary binary = (CryptoBinary) value;
          if (binary.getLength() == 0) {
            empty = true;
          } else {
            hashCreator.update(binary);
          }
        } else {
          String string = value.toString();
          if (string.isEmpty()) {
            empty = true;
          } else {
            byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
            hashCreator.update(bytes);
          }
        }
        if (empty && !field.isOptional()) {
          throw new IllegalArgumentException("Field " + field + " shall not be empty!");
        }
      }
    }
    byte[] hash = hashCreator.hash(true);
    return hash;
  }

}
