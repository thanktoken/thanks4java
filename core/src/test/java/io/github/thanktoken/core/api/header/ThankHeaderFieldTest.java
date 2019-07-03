package io.github.thanktoken.core.api.header;

import java.util.List;

import org.junit.Test;

import io.github.thanktoken.core.api.TestCase;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.location.ThankLocation;
import io.github.thanktoken.core.api.target.ThankTarget;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;
import io.github.thanktoken.core.api.token.header.ThankTokenHeaderField;
import io.github.thanktoken.core.api.value.ThankValue;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * Test of {@link ThankTokenHeaderField}.
 */
public class ThankHeaderFieldTest extends TestCase {

  /** Test of {@link ThankTokenHeaderField#VERSION}. */
  @Test
  public void testVersion() {

    checkField(ThankTokenHeaderField.VERSION, "version", ThankVersion.class);
  }

  /** Test of {@link ThankTokenHeaderField#CURRENCY}. */
  @Test
  public void testCurrency() {

    checkField(ThankTokenHeaderField.CURRENCY, "currency", ThankCurrency.class);
  }

  /** Test of {@link ThankTokenHeaderField#AMOUNT}. */
  @Test
  public void testAmount() {

    checkField(ThankTokenHeaderField.AMOUNT, "amount", ThankValue.class);
  }

  /** Test of {@link ThankTokenHeaderField#TIMESTAMP}. */
  @Test
  public void testTimestamp() {

    checkField(ThankTokenHeaderField.TIMESTAMP, "timestamp", ThankTimestamp.class);
  }

  /** Test of {@link ThankTokenHeaderField#LOCATION}. */
  @Test
  public void testLocation() {

    checkField(ThankTokenHeaderField.LOCATION, "location", ThankLocation.class);
  }

  /** Test of {@link ThankTokenHeaderField#TARGET}. */
  @Test
  public void testTarget() {

    checkField(ThankTokenHeaderField.TARGET, "target", ThankTarget.class);
  }

  private <T> void checkField(ThankTokenHeaderField<T> field, String name, Class<T> type) {

    assertThat(field.getName()).isEqualTo(name);
    assertThat(field.getType()).isSameAs(type);
    assertThat(field.toString()).isEqualTo(name);
  }

  /**
   * Test of {@link ThankTokenHeaderField#getFields()}.
   */
  @Test
  public void testValues() {

    List<ThankTokenHeaderField<?>> values = ThankTokenHeaderField.getFields().getAll();
    assertThat(values).containsExactly(ThankTokenHeaderField.VERSION, ThankTokenHeaderField.ALGORITHM, ThankTokenHeaderField.CURRENCY,
        ThankTokenHeaderField.AMOUNT, ThankTokenHeaderField.TIMESTAMP, ThankTokenHeaderField.LOCATION, ThankTokenHeaderField.TARGET,
        ThankTokenHeaderField.RECIPIENT, ThankTokenHeaderField.SIGNATURE);
  }

}
