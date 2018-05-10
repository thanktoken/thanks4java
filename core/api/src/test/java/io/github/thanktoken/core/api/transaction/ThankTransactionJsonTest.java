package io.github.thanktoken.core.api.transaction;

import javax.json.stream.JsonParser;

import org.junit.Test;

import io.github.thanktoken.core.api.TestCase;
import io.github.thanktoken.core.api.ThankDataObject;
import io.github.thanktoken.core.api.field.ThankFieldMap;
import io.github.thanktoken.core.api.io.ThankValueParser;

/**
 * Test of {@link ThankFieldMap#toJson(ThankDataObject)} and
 * {@link ThankFieldMap#fromJson(JsonParser, ThankValueParser)} for {@link ThankTransaction}.
 */
public class ThankTransactionJsonTest extends TestCase {

  /** Test of {@link ThankFieldMap#toJson(ThankDataObject)} */
  @Test
  public void testToJson() {

    // given
    String publicPurpose = "hello world";

    ThankTransactionBean tx = new ThankTransactionBean();
    tx.setTimestamp(TEST_TIMESTAMP);
    tx.setPublicPurpose(publicPurpose);
    tx.setEncryptedPurpose(TEST_ENCRYPTED_DATA);
    // tx.setReference(TEST_REFERENCE);
    tx.setRecipient(TEST_PUBLIC_KEY);
    tx.setSignature(TEST_SIGNATURE);

    // when
    String json = ThankTransactionField.getFields().toJson(tx);

    // then
    assertThat(json).isEqualTo("{\"ts\":\"" + TEST_TIMESTAMP_STRING + "\",\"rcp\":\"" + TEST_PUBLIC_KEY_BASE64 + "\",\"pp\":\"hello world\",\"ep\":\""
        + TEST_ENCRYPTED_DATA_BASE64 + "\",\"sig\":\"" + TEST_SIGNATURE_BASE64 + "\"}");
  }

}
