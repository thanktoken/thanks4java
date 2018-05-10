package io.github.thanktoken.core.api.header;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.junit.Test;

import io.github.thanktoken.core.api.TestCase;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.header.ThankHeaderField;
import io.github.thanktoken.core.api.header.ThankLocation;
import io.github.thanktoken.core.api.header.ThankTarget;
import io.github.thanktoken.core.api.header.ThankVersion;

/**
 * Test of {@link ThankHeaderField}.
 */
public class ThankHeaderFieldTest extends TestCase {

  /** Test of {@link ThankHeaderField#VERSION}. */
  @Test
  public void testVersion() {

    checkField(ThankHeaderField.VERSION, "version", ThankVersion.class);
  }

  /** Test of {@link ThankHeaderField#CURRENCY}. */
  @Test
  public void testCurrency() {

    checkField(ThankHeaderField.CURRENCY, "currency", ThankCurrency.class);
  }

  /** Test of {@link ThankHeaderField#AMOUNT}. */
  @Test
  public void testAmount() {

    checkField(ThankHeaderField.AMOUNT, "amount", BigDecimal.class);
  }

  /** Test of {@link ThankHeaderField#TIMESTAMP}. */
  @Test
  public void testTimestamp() {

    checkField(ThankHeaderField.TIMESTAMP, "timestamp", Instant.class);
  }

  /** Test of {@link ThankHeaderField#LOCATION}. */
  @Test
  public void testLocation() {

    checkField(ThankHeaderField.LOCATION, "location", ThankLocation.class);
  }

  /** Test of {@link ThankHeaderField#TARGET}. */
  @Test
  public void testTarget() {

    checkField(ThankHeaderField.TARGET, "target", ThankTarget.class);
  }

  private void checkField(ThankHeaderField<?> field, String name, Class<?> type) {

    assertThat(field.getName()).isEqualTo(name);
    assertThat(field.getType()).isSameAs(type);
    assertThat(field.toString()).isEqualTo(name);
  }

  /**
   * Test of {@link ThankHeaderField#getFields()}.
   */
  @Test
  public void testValues() {

    List<ThankHeaderField<?>> values = ThankHeaderField.getFields().getAll();
    assertThat(values).containsExactly(ThankHeaderField.VERSION, ThankHeaderField.AMOUNT, ThankHeaderField.CURRENCY, ThankHeaderField.TIMESTAMP,
        ThankHeaderField.LOCATION, ThankHeaderField.TARGET, ThankHeaderField.RECIPIENT, ThankHeaderField.SIGNATURE);
  }

}
