package io.github.thanktoken.core.impl.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import io.github.thanktoken.core.api.ThankConstants;
import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.ThankTokenBean;
import io.github.thanktoken.core.api.config.ThankConfiguration;
import io.github.thanktoken.core.api.currency.Gradido;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.header.ThankHeaderBean;
import io.github.thanktoken.core.api.header.ThankTarget;
import io.github.thanktoken.core.api.header.ThankVersion;
import io.github.thanktoken.core.api.io.ThankReader;
import io.github.thanktoken.core.api.io.ThankWriter;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.transaction.ThankTransactionBean;
import io.github.thanktoken.core.impl.TestData;
import io.github.thanktoken.core.impl.config.ThankConfigurationImpl;
import io.github.thanktoken.core.impl.strategy.AbstractThankVersionStrategy;
import net.sf.mmm.security.api.crypt.asymmetric.Curve25519;
import net.sf.mmm.security.api.hash.SecurityHashConfigSha256;

/**
 * Test-case for {@link ThankReader} and {@link ThankWriter}.<br>
 * <b>ATTENTION:</b> The {@link ThankToken}s created by this test are totally invalid. This test is only about testing
 * {@link ThankWriter} and {@link ThankReader}.
 */
public class ThankTokenReaderWriterTest extends Assertions implements ThankConstants, TestData {

  protected static final ThankVersion TEST_VERSION = ThankVersion.of(999);

  private static final String EXPECTED_HEADER_STRING = "{\"v\":999,\"a\":100.00,\"c\":\"Gradido\",\"ts\":\"" + TEST_TIMESTAMP_STRING
      + "\",\"loc\":\"DE/HE/412000\",\"tgt\":\"P3\",\"rcp\":\"" + TEST_PUBLIC_KEY_BASE64 + "\"}";

  private static final String EXPECTED_TX_STRING = "{\"ts\":\"" + TEST_TIMESTAMP_STRING + "\",\"rcp\":\"" + TEST_PUBLIC_KEY_BASE64 + "\",\"pp\":\""
      + TEST_PUBLIC_PURPOSE + "\",\"ep\":\"" + TEST_ENCRYPTED_DATA_BASE64 + "\",\"sig\":\"" + TEST_SIGNATURE_BASE64 + "\"}";

  /**
   * @return the {@link ThankWriter} to test.
   */
  protected ThankWriter getWriter() {

    return new ThankWriterImpl(Strategy.INSTANCE);
  }

  /**
   * @return the {@link ThankReader} to test.
   */
  protected ThankReader getReader() {

    return new ThankReaderImpl(Strategy.INSTANCE);
  }

  /** Test of {@link ThankWriter#writeHeader(ThankHeader, java.io.OutputStream)}. */
  @Test
  public void testWriteHeader() {

    // given
    ThankHeader header = createTestHeader();
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    // when
    getWriter().writeHeader(header, out);
    byte[] byteArray = out.toByteArray();

    // then
    String actual = new String(byteArray, UTF_8);
    assertThat(actual).isEqualTo(EXPECTED_HEADER_STRING);

  }

  /** Test of {@link ThankReader#readHeader(InputStream)}. */
  @Test
  public void testReadHeader() {

    // given
    InputStream in = new ByteArrayInputStream(EXPECTED_HEADER_STRING.getBytes(UTF_8));

    // when
    ThankHeader header = getReader().readHeader(in);

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
    String actual = new String(byteArray, UTF_8);
    assertThat(actual).isEqualTo(EXPECTED_TX_STRING);
  }

  /** Test of {@link ThankWriter#writeTransaction(ThankToken, ThankTransaction, java.io.OutputStream)}. */
  @Test
  public void testReadTx() {

    // given
    InputStream in = new ByteArrayInputStream(EXPECTED_TX_STRING.getBytes(UTF_8));

    // when
    ThankTransaction tx = getReader().readTransaction(TEST_VERSION, in);

    // then
    assertThat(tx).isEqualToComparingFieldByField(createTestTransaction());
  }

  private ThankTransaction createTestTransaction() {

    ThankTransactionBean tx = new ThankTransactionBean();
    tx.setTimestamp(TEST_TIMESTAMP);
    tx.setPublicPurpose(TEST_PUBLIC_PURPOSE);
    tx.setEncryptedPurpose(TEST_ENCRYPTED_DATA);
    // tx.setReference(TEST_REFERENCE);
    tx.setRecipient(TEST_PUBLIC_KEY);
    tx.setSignature(TEST_SIGNATURE);
    return tx;
  }

  private ThankHeader createTestHeader() {

    ThankHeaderBean header = new ThankHeaderBean();
    header.setVersion(TEST_VERSION);
    header.setAmount(AMOUNT_0100_00);
    header.setCurrency(Gradido.INSTANCE);
    header.setTimestamp(TEST_TIMESTAMP);
    header.setTarget(ThankTarget.PERSON_INCOME);
    header.setLocation(TEST_LOCATION);
    header.setRecipient(TEST_PUBLIC_KEY);
    return header;
  }

  /**
   * A dummy implementation of {@link AbstractThankVersionStrategy} for testing.
   */
  protected static class Strategy extends AbstractThankVersionStrategy {

    /** The signleton instance. */
    protected static final Strategy INSTANCE = new Strategy(
        new ThankConfigurationImpl(Curve25519.create().withHash(SecurityHashConfigSha256.SHA_256).getFactories()));

    private Strategy(ThankConfiguration configuration) {

      super(configuration, null);
    }

    @Override
    public ThankVersion getVersion() {

      return TEST_VERSION;
    }

  }
}
