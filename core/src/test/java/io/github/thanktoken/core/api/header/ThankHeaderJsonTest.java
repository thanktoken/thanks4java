/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.header;

import java.io.StringReader;

import javax.json.Json;
import javax.json.stream.JsonParser;

import org.junit.Test;

import io.github.thanktoken.core.api.TestCase;
import io.github.thanktoken.core.api.currency.Thanks;
import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.field.ThankFieldMap;
import io.github.thanktoken.core.api.io.ThankValueParser;
import io.github.thanktoken.core.api.io.ThankValueParserDefault;
import io.github.thanktoken.core.api.target.ThankTarget;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.token.header.ThankTokenHeaderBean;
import io.github.thanktoken.core.api.token.header.ThankTokenHeaderField;
import io.github.thanktoken.core.api.value.ThankValue;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * Test of {@link ThankFieldMap#toJson(ThankDataObject)} and
 * {@link ThankFieldMap#fromJson(JsonParser, ThankValueParser)} for {@link ThankTokenHeaderBean}.
 */
public class ThankHeaderJsonTest extends TestCase {

  /**
   * Test of {@link ThankFieldMap#toJson(ThankDataObject)}
   */
  @Test
  public void testToJson() {

    // given
    ThankTokenHeaderBean header = new ThankTokenHeaderBean();
    header.setVersion(ThankVersion.of(1));
    header.setAlgorithm(TEST_ALGORITHM);
    header.setAmount(ThankValue.VALUE_0_1);
    header.setCurrency(Thanks.INSTANCE);
    header.setTimestamp(TEST_TIMESTAMP);
    header.setLocation(TEST_LOCATION);
    header.setTarget(ThankTarget.PERSON_INCOME);
    header.setRecipient(TEST_KEY_PAIR_NP.getAddress());

    // when
    String json = ThankTokenHeaderField.getFields().toJson(header);

    // then
    assertThat(json).isEqualTo(
        "{\"ver\":1,\"alg\":\"secp256k1\",\"cur\":\"Thanks\",\"amt\":\"0.1\",\"ts\":\"19991231235959.000000001\",\"loc\":\"9F2C4M\",\"tgt\":\"P4\",\"rcp\":\"AVTVgCGfN3kcAwg/Gf0/mwAMf8jHw8CaFUifAITI56nZAA==\"}");
  }

  /** Test of {@link ThankFieldMap#fromJson(JsonParser, ThankValueParser)} */
  @Test
  public void testFromJson() {

    // given
    String json = "{\"ver\":1,\"alg\":\"secp256k1\",\"amt\":\"0.1\",\"cur\":\"Thanks\",\"ts\":\"19991231235959.000000001\",\"loc\":\"9F2C4M\",\"tgt\":\"P4\",\"rcp\":\"AVTVgCGfN3kcAwg/Gf0/mwAMf8jHw8CaFUifAITI56nZAA==\"}";
    JsonParser jsonParser = Json.createParser(new StringReader(json));

    // when
    ThankTokenHeader header = ThankTokenHeaderField.getFields().fromJson(jsonParser, ThankValueParserDefault.get());

    // then
    assertThat(header).isNotNull();
    assertThat(header.getVersion()).isEqualTo(ThankVersion.of(1));
    assertThat(header.getAlgorithm()).isSameAs(TEST_ALGORITHM);
    assertThat(header.getAmount()).isSameAs(ThankValue.VALUE_0_1);
    assertThat(header.getCurrency()).isEqualTo(Thanks.INSTANCE);
    assertThat(header.getTimestamp()).isEqualTo(TEST_TIMESTAMP);
    assertThat(header.getLocation()).isEqualTo(TEST_LOCATION);
    assertThat(header.getTarget()).isEqualTo(ThankTarget.PERSON_INCOME);
    assertThat(header.getRecipient()).isEqualTo(TEST_KEY_PAIR_NP.getAddress());
  }

}
