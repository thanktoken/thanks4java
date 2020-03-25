package io.github.thanktoken.core.api.transaction;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.mmm.crypto.asymmetric.sign.SignatureBinary;
import io.github.mmm.crypto.crypt.EncryptedData;
import io.github.thanktoken.core.api.TestCase;
import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;

/**
 * Test of {@link ThankTransactionField}.
 */
public class ThankTransactionFieldTest extends TestCase {

  /**
   * Test of {@link ThankTransactionField#TIMESTAMP}.
   */
  @Test
  public void testTimestamp() {

    checkField(ThankTransactionField.TIMESTAMP, "timestamp", ThankTimestamp.class);
  }

  /**
   * Test of {@link ThankTransactionField#RECIPIENT}.
   */
  @Test
  public void testRecipient() {

    checkField(ThankTransactionField.RECIPIENT, "recipient", ThankAddress.class);
  }

  /**
   * Test of {@link ThankTransactionField#PUBLIC_PURPOSE}.
   */
  @Test
  public void testPublicPurpose() {

    checkField(ThankTransactionField.PUBLIC_PURPOSE, "publicPurpose", String.class);
  }

  /**
   * Test of {@link ThankTransactionField#ENCRYPTED_PURPOSE}.
   */
  @Test
  public void testEncryptedPurpose() {

    checkField(ThankTransactionField.ENCRYPTED_PURPOSE, "encryptedPurpose", EncryptedData.class);
  }

  /**
   * Test of {@link ThankTransactionField#SIGNATURE}.
   */
  @Test
  public void testSignature() {

    checkField(ThankTransactionField.SIGNATURE, "signature", SignatureBinary.class);
  }

  private <T> void checkField(ThankTransactionField<T> field, String name, Class<T> type) {

    assertThat(field.getName()).isEqualTo(name);
    assertThat(field.getType()).isSameAs(type);
    assertThat(field.toString()).isEqualTo(name);
  }

  /**
   * Test of {@link ThankTransactionField#getFields()}.
   */
  @Test
  public void testValues() {

    List<ThankTransactionField<?>> values = ThankTransactionField.getFields().getAll();
    assertThat(values).containsExactly(ThankTransactionField.TIMESTAMP, ThankTransactionField.RECIPIENT,
        ThankTransactionField.PUBLIC_PURPOSE, ThankTransactionField.ENCRYPTED_PURPOSE, ThankTransactionField.REFERENCE,
        ThankTransactionField.SIGNATURE);
  }

}
