package io.github.thanktoken.core.api.field;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import io.github.thanktoken.core.api.ThankConstants;
import io.github.thanktoken.core.api.ThankDataObject;
import io.github.thanktoken.core.api.io.ThankValueParser;
import net.sf.mmm.security.api.hash.SecurityHashCreator;
import net.sf.mmm.security.api.sign.SecuritySignature;
import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;

/**
 * Map for available {@link ThankField}s of a {@link ThankDataObject}.
 *
 * @see #get(String)
 *
 * @param <D> type of the owning {@link ThankDataObject}.
 * @param <B> type of the mutable {@link ThankDataObject}.
 * @param <F> type of the contained {@link ThankField}s.
 */
public abstract class ThankFieldMap<D extends ThankDataObject, B extends D, F extends ThankField<?, D, B>> {

  private static final Map<String, Object> JSON_CONFIG = createConfig();

  private final List<F> fields;

  private final List<F> all;

  private final Map<String, F> fieldMap;

  /** Predicate that accepts all fields. */
  public static final Predicate<ThankField<?, ?, ?>> ALL_FIELDS = (x) -> true;

  private static final Predicate<ThankField<?, ?, ?>> ALL_FIELDS_BUT_SIGNATURE = (x) -> (x.getType() != SecuritySignature.class);

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

    B bean = newBean();
    fromJson(jsonParser, valueParser, bean);
    return bean2data(bean);
  }

  /**
   * @param jsonParser the {@link JsonParser} to read the JSON from.
   * @param valueParser the {@link ThankValueParser}.
   * @param bean the mutable {@link ThankDataObject} where to apply the parsed data to.
   */
  public void fromJson(JsonParser jsonParser, ThankValueParser valueParser, B bean) {

    boolean started = false;
    while (jsonParser.hasNext()) {
      Event e = jsonParser.next();
      if (e == Event.START_OBJECT) {
        if (started) {
          throw new IllegalCaseException(Event.class, e);
        }
      } else if (e == Event.END_OBJECT) {
        return;
      } else if (e == Event.KEY_NAME) {
        String key = jsonParser.getString();
        F field = get(key);
        if (field == null) {
          throw new ObjectNotFoundException(bean.getClass(), key);
        }
        field.fromJson(jsonParser, bean, valueParser);
      } else {
        throw new IllegalCaseException(Event.class, e);
      }
      started = true;
    }
  }

  /**
   * @param data the {@link ThankDataObject} to write as JSON.
   * @return the JSON as {@link String}.
   */
  public String toJson(D data) {

    Writer writer = new StringWriter(2048);
    JsonGenerator jsonGenerator = Json.createGeneratorFactory(JSON_CONFIG).createGenerator(writer);
    toJson(data, jsonGenerator);
    jsonGenerator.close();
    return writer.toString();
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
  public void toJson(D data, JsonGenerator jsonGenerator, Predicate<ThankField<?, ?, ?>> fieldIncluder) {

    jsonGenerator.writeStartObject();
    for (F field : this.fields) {
      if (fieldIncluder.test(field)) {
        field.toJson(data, jsonGenerator, fieldIncluder);
      }
    }
    jsonGenerator.writeEnd();
  }

  /**
   * @param data the {@link ThankDataObject} to hash.
   * @param hashCreator the {@link SecurityHashCreator}.
   * @return the {@link SecurityHashCreator#hash() hash} of the given {@link ThankDataObject}.
   */
  public byte[] hash(D data, SecurityHashCreator hashCreator) {

    return hash(data, hashCreator, ALL_FIELDS_BUT_SIGNATURE);
  }

  /**
   * @param data the {@link ThankDataObject} to hash.
   * @param hashCreator the {@link SecurityHashCreator}.
   * @param fieldIncluder the {@link Predicate} that {@link Predicate#test(Object) decides} which fields to include. If
   *        it returns {@code false} for a field, that field will be excluded from the hash.
   * @return the {@link SecurityHashCreator#hash() hash} of the given {@link ThankDataObject}.
   */
  public byte[] hash(D data, SecurityHashCreator hashCreator, Predicate<ThankField<?, ?, ?>> fieldIncluder) {

    for (F field : this.fields) {
      if (fieldIncluder.test(field)) {
        String value = field.getString(data);
        if (!value.isEmpty()) {
          byte[] bytes = value.getBytes(ThankConstants.UTF_8);
          hashCreator.update(bytes);
        }
      }
    }
    byte[] hash = hashCreator.hash(true);
    return hash;
  }

}
