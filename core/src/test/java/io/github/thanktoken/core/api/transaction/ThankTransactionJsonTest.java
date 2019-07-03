package io.github.thanktoken.core.api.transaction;

import javax.json.stream.JsonParser;

import org.junit.Test;

import io.github.thanktoken.core.api.TestCase;
import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.field.ThankFieldMap;
import io.github.thanktoken.core.api.io.ThankValueParser;
import io.github.thanktoken.core.api.token.header.ThankTokenHeaderBean;
import io.github.thanktoken.core.api.version.ThankVersion;

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

    ThankTokenHeaderBean header = new ThankTokenHeaderBean();
    header.setAlgorithm(TEST_ALGORITHM);
    header.setVersion(ThankVersion.of(1));

    ThankTransactionBean tx = new ThankTransactionBean();
    tx.setTimestamp(TEST_TIMESTAMP);
    tx.setPublicPurpose(publicPurpose);
    tx.setEncryptedPurpose(TEST_ENCRYPTED_DATA);
    tx.setReference(TEST_REFERENCE);
    tx.setRecipient(TEST_KEY_PAIR_PS.getAddress());
    tx.setSignature(TEST_SIGNATURE);

    // when
    String json = ThankTransactionField.getFields().toJson(tx, header);

    // then
    assertThat(json).isEqualTo("{\"ts\":\"" + TEST_TIMESTAMP_STRING + "\",\"rcp\":\"" + TEST_KEY_PAIR_PS.getAddress().formatBase64()
        + "\",\"pp\":\"hello world\",\"ep\":\"" + TEST_ENCRYPTED_DATA_BASE64
        + "\",\"ref\":{\"t\":\"mergeWith\",\"ts\":\"19991231235959.000000001\",\"loc\":\"9F2C4M\"},\"sig\":\"" + TEST_SIGNATURE_BASE64 + "\"}");
  }

}
