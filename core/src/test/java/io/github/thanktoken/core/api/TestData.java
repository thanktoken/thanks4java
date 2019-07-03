/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api;

import java.time.Instant;

import net.sf.mmm.binary.api.BinaryType;
import net.sf.mmm.crypto.asymmetric.sign.SignatureBinary;
import net.sf.mmm.crypto.asymmetric.sign.rsa.SignatureRsa;
import net.sf.mmm.crypto.crypt.EncryptedData;

import io.github.thanktoken.core.api.address.ThankAddressType;
import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.algorithm.ThankAlgorithmSecp256k1;
import io.github.thanktoken.core.api.attribute.AttributeReadReferenceType;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.currency.Thanks;
import io.github.thanktoken.core.api.location.ThankLocation;
import io.github.thanktoken.core.api.location.ThankLocationOlc;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;
import io.github.thanktoken.core.api.target.ThankTarget;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.token.ThankTokenType;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.token.header.ThankTokenHeaderType;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.transaction.ThankTransactionType;
import io.github.thanktoken.core.api.value.ThankValue;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * Test-values (dummy data) for {@link ThankToken} and related objects.
 */
public interface TestData {

  /** {@link ThankVersion} for testing. */
  ThankVersion TEST_VERSION = ThankVersion.of(999);

  /** {@link ThankLocation} for testing (Frankfurt a.M., Germany). */
  ThankLocation TEST_LOCATION = ThankLocationOlc.of("9F2C4M");

  /** {@link ThankAlgorithm} for testing. */
  ThankAlgorithm TEST_ALGORITHM = ThankAlgorithmSecp256k1.get();

  /** {@link ThankCurrency} for testing. */
  ThankCurrency TEST_CURRENCY = Thanks.INSTANCE;

  /** {@link ThankValue} for testing. */
  ThankValue TEST_AMOUNT = ThankValue.VALUE_10;

  /** {@link TestKeyPair} of natural person for testing. */
  TestKeyPair TEST_KEY_PAIR_NP = new TestKeyPair("0ud24ahpdi7jTBqCm3VnpjPr4rMro2+OtH1Dxtw1SUo=", ThankAddressType.NATURAL_PERSON_ADULT,
      TEST_ALGORITHM);

  /** {@link TestKeyPair} of pseudonymous identity for testing. */
  TestKeyPair TEST_KEY_PAIR_PS = new TestKeyPair("XoIhkBnQdcZamrUUmS/Rfl6WH9hcC3aDq9OdcIC5m0s=", ThankAddressType.PSEUDONYMOUS, TEST_ALGORITHM);

  /** Base64 encoded string of {@link #TEST_SIGNATURE}. */
  String TEST_SIGNATURE_BASE64 = "Dw4NDAsKCQgHBgUEAwIBAA==";

  /** {@link SignatureBinary} for testing. */
  SignatureBinary TEST_SIGNATURE = new SignatureRsa(BinaryType.parseBase64(TEST_SIGNATURE_BASE64));

  /** Base64 encoded string of {@link #TEST_ENCRYPTED_DATA}. */
  String TEST_ENCRYPTED_DATA_BASE64 = "/+7dzLuqmYh3ZlVEMyIRAA==";

  /** {@link EncryptedData} for testing. */
  EncryptedData TEST_ENCRYPTED_DATA = EncryptedData.ofBase64(TEST_ENCRYPTED_DATA_BASE64);

  /** Compact string representation of {@link #TEST_TIMESTAMP}. */
  String TEST_TIMESTAMP_STRING = "19991231235959.000000001";

  /** {@link Instant} for testing. */
  ThankTimestamp TEST_TIMESTAMP = new ThankTimestamp(Instant.parse("1999-12-31T23:59:59.000000001Z"));

  /** {@link ThankTokenReferenceType} for testing. */
  ThankTokenReferenceType TEST_REFERENCE = new ThankTokenReferenceType(AttributeReadReferenceType.TYPE_MERGE_WITH, TEST_TIMESTAMP, TEST_LOCATION,
      null, null, null);

  /**
   * {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getPublicPurpose() public purpose} for testing.
   */
  String TEST_PUBLIC_PURPOSE = "Donation for charity";

  SignatureBinary TEST_TOKEN_P4_HEADER_SIGNATURE = TEST_ALGORITHM.getSignatureFactory()
      .createSignature(BinaryType.parseBase64("IOHvCChErKHm02o6mjxCNAoyFvJ+UgA/5Ir806wI/5d0G1RUXYonfioTRy0PDHZu8zwyb/2dk+vF5Xr2+N/ko+c="));

  ThankTokenHeader TEST_TOKEN_P4_HEADER = new ThankTokenHeaderType(TEST_VERSION, TEST_ALGORITHM, TEST_CURRENCY, ThankValue.VALUE_32, TEST_TIMESTAMP,
      TEST_LOCATION, ThankTarget.PERSON_INCOME, null, TEST_KEY_PAIR_NP.getAddress(), TEST_TOKEN_P4_HEADER_SIGNATURE);

  SignatureBinary TEST_TOKEN_P4_TX_0_SIGNATURE = TEST_ALGORITHM.getSignatureFactory()
      .createSignature(BinaryType.parseBase64("ICoImylwg6mw2fF/rQtpXZg3age6+4qYYsnUw0EgROppDwRVC6xcJqllpCmsNcJla89jEZZw8JaUci/96g92VmY="));

  ThankTransaction TEST_TOKEN_P4_TX_0 = new ThankTransactionType(ThankTimestamp.of("20000101000101.000000001"), TEST_KEY_PAIR_PS.getAddress(), null,
      TEST_ENCRYPTED_DATA, null, TEST_TOKEN_P4_TX_0_SIGNATURE);

  ThankToken TEST_TOKEN_P4 = new ThankTokenType(TEST_TOKEN_P4_HEADER).addTransaction(TEST_TOKEN_P4_TX_0);

}
