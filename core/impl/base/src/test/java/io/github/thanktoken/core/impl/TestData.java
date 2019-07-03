/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.impl;

import java.security.PublicKey;
import java.time.Instant;

import net.sf.mmm.crypto.asymmetric.sign.SignatureBinary;
import net.sf.mmm.crypto.crypt.EncryptedData;
import net.sf.mmm.cryptoooo.asymmetric.key.SecurityPublicKey;
import net.sf.mmm.cryptoooo.asymmetric.key.generic.SecurityPublicKeyGeneric;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.location.ThankLocation;
import io.github.thanktoken.core.api.location.ThankLocationOlc;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * Test-values (dummy data) for {@link ThankToken} and related objects.
 */
public interface TestData {

  /** {@link ThankLocation} for testing (Frankfurt a.M., Germany). */
  ThankLocation TEST_LOCATION = ThankLocationOlc.of("9F2C4M");

  /** Base64 encoded string of {@link #TEST_PUBLIC_KEY}. */
  String TEST_PUBLIC_KEY_BASE64 = "AwAAAAAAAAAAAAAAAAAAAAAPDg0MCwoJCAcGBQQDAgEA";

  /** {@link SecurityPublicKey} for testing. */
  SecurityPublicKey TEST_PUBLIC_KEY = new SecurityPublicKeyGeneric(TEST_PUBLIC_KEY_BASE64, (PublicKey) null);

  /** Base64 encoded string of {@link #TEST_SIGNATURE}. */
  String TEST_SIGNATURE_BASE64 = "Dw4NDAsKCQgHBgUEAwIBAA==";

  /** {@link SignatureBinary} for testing. */
  SignatureBinary TEST_SIGNATURE = SignatureBinary.ofBase64(TEST_SIGNATURE_BASE64);

  /** Base64 encoded string of {@link #TEST_ENCRYPTED_DATA}. */
  String TEST_ENCRYPTED_DATA_BASE64 = "/+7dzLuqmYh3ZlVEMyIRAA==";

  /** {@link EncryptedData} for testing. */
  EncryptedData TEST_ENCRYPTED_DATA = EncryptedData.ofBase64(TEST_ENCRYPTED_DATA_BASE64);

  /** Compact string representation of {@link #TEST_TIMESTAMP}. */
  String TEST_TIMESTAMP_STRING = "20001231235959.0001";

  /**
   * {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getPublicPurpose() public purpose} for testing.
   */
  String TEST_PUBLIC_PURPOSE = "Donation for charity";

  /** {@link Instant} for testing. */
  Instant TEST_TIMESTAMP = Instant.parse("2000-12-31T23:59:59.0001Z");

  /** {@link ThankTokenReferenceType} for testing. */
  ThankTokenReferenceType TEST_REFERENCE = new ThankTokenReferenceType(ThankVersion.of(2), TEST_LOCATION, TEST_TIMESTAMP, TEST_PUBLIC_KEY);

}
