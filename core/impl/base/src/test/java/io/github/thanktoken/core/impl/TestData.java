/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.impl;

import java.security.PublicKey;
import java.time.Instant;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.header.ThankLocation;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;
import net.sf.mmm.security.api.crypt.SecurityEncryptedData;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKeyGeneric;
import net.sf.mmm.security.api.sign.SecuritySignature;

/**
 * Test-values (dummy data) for {@link ThankToken} and related objects.
 */
public interface TestData {

  /** {@link ThankLocation} for testing. */
  ThankLocation TEST_LOCATION = ThankLocation.of("DE/HE/412000");

  /** Base64 encoded string of {@link #TEST_PUBLIC_KEY}. */
  String TEST_PUBLIC_KEY_BASE64 = "AwAAAAAAAAAAAAAAAAAAAAAPDg0MCwoJCAcGBQQDAgEA";

  /** {@link SecurityPublicKey} for testing. */
  SecurityPublicKey TEST_PUBLIC_KEY = new SecurityPublicKeyGeneric(TEST_PUBLIC_KEY_BASE64, (PublicKey) null);

  /** Base64 encoded string of {@link #TEST_SIGNATURE}. */
  String TEST_SIGNATURE_BASE64 = "Dw4NDAsKCQgHBgUEAwIBAA==";

  /** {@link SecuritySignature} for testing. */
  SecuritySignature TEST_SIGNATURE = SecuritySignature.ofBase64(TEST_SIGNATURE_BASE64);

  /** Base64 encoded string of {@link #TEST_ENCRYPTED_DATA}. */
  String TEST_ENCRYPTED_DATA_BASE64 = "/+7dzLuqmYh3ZlVEMyIRAA==";

  /** {@link SecurityEncryptedData} for testing. */
  SecurityEncryptedData TEST_ENCRYPTED_DATA = SecurityEncryptedData.ofBase64(TEST_ENCRYPTED_DATA_BASE64);

  /** Compact string representation of {@link #TEST_TIMESTAMP}. */
  String TEST_TIMESTAMP_STRING = "20001231235959.0001";

  /**
   * {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getPublicPurpose() public purpose} for testing.
   */
  String TEST_PUBLIC_PURPOSE = "Donation for charity";

  /** {@link Instant} for testing. */
  Instant TEST_TIMESTAMP = Instant.parse("2000-12-31T23:59:59.0001Z");

  /** {@link ThankTokenReferenceType} for testing. */
  ThankTokenReferenceType TEST_REFERENCE = new ThankTokenReferenceType(TEST_LOCATION, TEST_TIMESTAMP, TEST_PUBLIC_KEY);

}
