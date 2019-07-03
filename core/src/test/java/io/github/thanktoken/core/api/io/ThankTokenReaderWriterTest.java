package io.github.thanktoken.core.api.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import io.github.thanktoken.core.api.TestData;
import io.github.thanktoken.core.api.algorithm.ThankAlgorithmRsa4096;
import io.github.thanktoken.core.api.currency.Gradido;
import io.github.thanktoken.core.api.target.ThankTarget;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.token.ThankTokenBean;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.token.header.ThankTokenHeaderBean;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.transaction.ThankTransactionBean;
import io.github.thanktoken.core.api.value.ThankValue;

/**
 * Test-case for {@link ThankReader} and {@link ThankWriter}.<br>
 * <b>ATTENTION:</b> The {@link ThankToken}s created by this test are totally invalid. This test is only about testing
 * {@link ThankWriter} and {@link ThankReader}.
 */
public class ThankTokenReaderWriterTest extends Assertions implements TestData {

  private static final String EXPECTED_HEADER_STRING = "{\"ver\":999,\"alg\":\"rsa4k\",\"cur\":\"Gradido\",\"amt\":\"100\",\"ts\":\""
      + TEST_TIMESTAMP_STRING + "\",\"loc\":\"" + TEST_LOCATION + "\",\"tgt\":\"P4\",\"rcp\":\"" + TEST_KEY_PAIR_NP.getAddress().formatBase64()
      + "\"}";

  private static final String EXPECTED_TX_STRING = "{\"ts\":\"" + TEST_TIMESTAMP_STRING + "\",\"rcp\":\""
      + TEST_KEY_PAIR_PS.getAddress().formatBase64() + "\",\"pp\":\"" + TEST_PUBLIC_PURPOSE + "\",\"ep\":\"" + TEST_ENCRYPTED_DATA_BASE64
      + "\",\"sig\":\"" + TEST_SIGNATURE_BASE64 + "\"}";

  /**
   * @return the {@link ThankWriter} to test.
   */
  protected ThankWriter getWriter() {

    return new ThankWriterImpl();
  }

  /**
   * @return the {@link ThankReader} to test.
   */
  protected ThankReader getReader() {

    return new ThankReaderImpl();
  }

  /** Test of {@link ThankWriter#writeHeader(ThankTokenHeader, java.io.OutputStream)}. */
  @Test
  public void testWriteHeader() {

    // given
    ThankTokenHeader header = createTestHeader();
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    // when
    getWriter().writeHeader(header, out);
    byte[] byteArray = out.toByteArray();

    // then
    String actual = new String(byteArray, StandardCharsets.UTF_8);
    assertThat(actual).isEqualTo(EXPECTED_HEADER_STRING);

  }

  /** Test of {@link ThankReader#readHeader(InputStream)}. */
  @Test
  public void testReadHeader() {

    // given
    InputStream in = new ByteArrayInputStream(EXPECTED_HEADER_STRING.getBytes(StandardCharsets.UTF_8));

    // when
    ThankTokenHeader header = getReader().readHeader(in);

    // then
    assertThat(header).isEqualToComparingFieldByField(createTestHeader());
  }

  /** Test of {@link ThankWriter#writeTransaction(ThankToken, ThankTransaction, java.io.OutputStream)}. */
  @Test
  public void testWriteTx() {

    // given
    ThankTransaction tx = createTestTransaction();
    ThankTokenBean token = new ThankTokenBean(createTestHeader());
    token.addTransaction(tx);
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    // when
    getWriter().writeTransaction(token, tx, out);
    byte[] byteArray = out.toByteArray();

    // then
    String actual = new String(byteArray, StandardCharsets.UTF_8);
    assertThat(actual).isEqualTo(EXPECTED_TX_STRING);
  }

  /** Test of {@link ThankWriter#writeTransaction(ThankToken, ThankTransaction, java.io.OutputStream)}. */
  @Test
  public void testReadTx() {

    // given
    InputStream in = new ByteArrayInputStream(EXPECTED_TX_STRING.getBytes(StandardCharsets.UTF_8));
    ThankTokenHeader header = createTestHeader();

    // when
    ThankTransaction tx = getReader().readTransaction(header, in);

    // then
    assertThat(tx).isEqualToComparingFieldByField(createTestTransaction());
  }

  private ThankTransaction createTestTransaction() {

    ThankTransactionBean tx = new ThankTransactionBean();
    tx.setTimestamp(TEST_TIMESTAMP);
    tx.setPublicPurpose(TEST_PUBLIC_PURPOSE);
    tx.setEncryptedPurpose(TEST_ENCRYPTED_DATA);
    // tx.setReference(TEST_REFERENCE);
    tx.setRecipient(TEST_KEY_PAIR_PS.getAddress());
    tx.setSignature(TEST_SIGNATURE);
    return tx;
  }

  private ThankTokenHeader createTestHeader() {

    ThankTokenHeaderBean header = new ThankTokenHeaderBean();
    header.setVersion(TEST_VERSION);
    header.setAlgorithm(ThankAlgorithmRsa4096.get());
    header.setAmount(ThankValue.VALUE_100);
    header.setCurrency(Gradido.INSTANCE);
    header.setTimestamp(TEST_TIMESTAMP);
    header.setTarget(ThankTarget.PERSON_INCOME);
    header.setLocation(TEST_LOCATION);
    header.setRecipient(TEST_KEY_PAIR_NP.getAddress());
    return header;
  }
}
