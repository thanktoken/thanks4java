package io.github.thanktoken.core.api.transaction;

import java.time.Instant;
import java.util.List;

import org.junit.Test;

import io.github.thanktoken.core.api.TestCase;
import net.sf.mmm.security.api.crypt.SecurityEncryptedData;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;
import net.sf.mmm.security.api.sign.SecuritySignature;

/**
 * Test of {@link ThankTransactionField}.
 */
public class ThankTransactionFieldTest extends TestCase {

  /**
   * Test of {@link ThankTransactionField#TIMESTAMP}.
   */
  @Test
  public void testTimestamp() {

    checkField(ThankTransactionField.TIMESTAMP, "timestamp", Instant.class);
  }

  /**
   * Test of {@link ThankTransactionField#RECIPIENT}.
   */
  @Test
  public void testRecipient() {

    checkField(ThankTransactionField.RECIPIENT, "recipient", SecurityPublicKey.class);
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

    checkField(ThankTransactionField.ENCRYPTED_PURPOSE, "encryptedPurpose", SecurityEncryptedData.class);
  }

  /**
   * Test of {@link ThankTransactionField#SIGNATURE}.
   */
  @Test
  public void testSignature() {

    checkField(ThankTransactionField.SIGNATURE, "signature", SecuritySignature.class);
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
    assertThat(values).containsExactly(ThankTransactionField.TIMESTAMP, ThankTransactionField.RECIPIENT, ThankTransactionField.PUBLIC_PURPOSE,
        ThankTransactionField.ENCRYPTED_PURPOSE, ThankTransactionField.REFERENCE, ThankTransactionField.REFERENCES, ThankTransactionField.SIGNATURE);
  }

}
