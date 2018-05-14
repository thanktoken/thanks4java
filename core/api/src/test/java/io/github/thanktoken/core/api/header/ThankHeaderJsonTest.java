/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.header;

import java.io.StringReader;
import java.security.PublicKey;

import javax.json.Json;
import javax.json.stream.JsonParser;

import org.junit.Test;

import io.github.thanktoken.core.api.TestCase;
import io.github.thanktoken.core.api.ThankDataObject;
import io.github.thanktoken.core.api.currency.Thanks;
import io.github.thanktoken.core.api.datatype.ThankValue;
import io.github.thanktoken.core.api.field.ThankFieldMap;
import io.github.thanktoken.core.api.io.ThankValueParser;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKeyGeneric;
import net.sf.mmm.util.lang.api.BinaryType;

/**
 * Test of {@link ThankFieldMap#toJson(ThankDataObject)} and
 * {@link ThankFieldMap#fromJson(JsonParser, ThankValueParser)} for {@link ThankHeaderBean}.
 */
public class ThankHeaderJsonTest extends TestCase {

  /** Test of {@link ThankFieldMap#toJson(ThankDataObject)} */
  @Test
  public void testToJson() {

    // given
    ThankHeaderBean token = new ThankHeaderBean();
    token.setVersion(ThankVersion.of(1));
    token.setAmount(ThankValue.VALUE_0_1);
    token.setCurrency(Thanks.INSTANCE);
    token.setTimestamp(TEST_TIMESTAMP);
    token.setLocation(TEST_LOCATION);
    token.setTarget(ThankTarget.PERSON_INCOME);
    token.setRecipient(TEST_PUBLIC_KEY);

    // when
    String json = ThankHeaderField.getFields().toJson(token);

    // then
    assertThat(json).isEqualTo(
        "{\"v\":1,\"a\":\"0.1\",\"c\":\"Thanks\",\"ts\":\"20001231235959.0001\",\"loc\":\"DE/HE/412000\",\"tgt\":\"P3\",\"rcp\":\"AAECAwQFBgcICQoLDA0ODw==\"}");
  }

  /** Test of {@link ThankFieldMap#fromJson(JsonParser, ThankValueParser)} */
  @Test
  public void testFromJson() {

    // given
    String json = "{\"v\":1,\"a\":\"0.1\",\"c\":\"Thanks\",\"ts\":\"20001231235959.0001\",\"loc\":\"DE/HE/412000\",\"tgt\":\"P3\",\"rcp\":\"AAECAwQFBgcICQoLDA0ODw==\"}";
    JsonParser jsonParser = Json.createParser(new StringReader(json));
    ThankValueParser parser = new ThankValueParser() {

      @SuppressWarnings("unchecked")
      @Override
      public <T> T parse(String value, Class<T> type) {

        if (type.equals(SecurityPublicKey.class)) {
          byte[] data = BinaryType.parseBase64(value);
          return (T) new SecurityPublicKeyGeneric(data, (PublicKey) null);
        } else {
          return ThankValueParser.super.parse(value, type);
        }
      }
    };

    // when
    ThankHeader header = ThankHeaderField.getFields().fromJson(jsonParser, parser);

    // then
    assertThat(header).isNotNull();
    assertThat(header.getVersion()).isEqualTo(ThankVersion.of(1));
    assertThat(header.getAmount()).isSameAs(ThankValue.VALUE_0_1);
    assertThat(header.getCurrency()).isEqualTo(Thanks.INSTANCE);
    assertThat(header.getTimestamp()).isEqualTo(TEST_TIMESTAMP);
    assertThat(header.getLocation()).isEqualTo(TEST_LOCATION);
    assertThat(header.getTarget()).isEqualTo(ThankTarget.PERSON_INCOME);
    assertThat(header.getRecipient()).isEqualTo(TEST_PUBLIC_KEY);
  }

}
