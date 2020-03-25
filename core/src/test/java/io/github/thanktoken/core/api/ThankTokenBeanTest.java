/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api;

import java.time.Instant;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;

import io.github.thanktoken.core.api.timestamp.ThankTimestamp;
import io.github.thanktoken.core.api.token.ThankTokenBean;
import io.github.thanktoken.core.api.token.header.ThankTokenHeaderBean;
import io.github.thanktoken.core.api.value.ThankValue;

/**
 * Test of {@link ThankTokenBean}.
 */
public class ThankTokenBeanTest extends TestCase {

  /**
   * Test of {@link ThankTokenBean#getValue()} for a token created today.
   */
  @Test
  public void testGetValueToday() {

    // given
    ThankValue amount = ThankValue.VALUE_0_01;
    ThankTokenHeaderBean header = new ThankTokenHeaderBean();
    header.setAmount(amount);
    header.setTimestamp(ThankTimestamp.now());
    ThankTokenBean token = new ThankTokenBean(header);

    // when
    ThankValue value = token.getValue();

    // then
    assertThat(value).isEqualTo(amount);
  }

  /**
   * Test of {@link ThankTokenBean#getValue(Instant)} for a token with the age of one year.
   */
  @Test
  public void testGetValueOneYear() {

    // given
    ThankValue amount = ThankValue.VALUE_1;
    ThankTokenHeaderBean header = new ThankTokenHeaderBean();
    header.setAmount(amount);
    header.setTimestamp(new ThankTimestamp(OffsetDateTime.now().minusDays(365).toInstant()));
    ThankTokenBean token = new ThankTokenBean(header);

    // when
    ThankValue value = token.getValue();

    // then
    assertThat(value).isEqualTo(ThankValue.of("0.83314661"));
  }

  /**
   * Test of {@link ThankTokenBean#getValue(Instant)} for a token with the age of ten years.
   */
  @Test
  public void testGetValueTenYears() {

    // given
    ThankValue amount = ThankValue.VALUE_1;
    ThankTokenHeaderBean header = new ThankTokenHeaderBean();
    header.setAmount(amount);
    header.setTimestamp(new ThankTimestamp(OffsetDateTime.now().minusDays(3652).toInstant()));
    ThankTokenBean token = new ThankTokenBean(header);

    // when
    ThankValue value = token.getValue();

    // then
    assertThat(value).isEqualTo(ThankValue.of("0.16098297"));
  }

  /**
   * Test of {@link ThankTokenBean#getValue(Instant)} for a token with the age of 100 years.
   */
  @Test
  public void testGetValueHundredYears() {

    // given
    ThankValue amount = ThankValue.VALUE_1;
    ThankTokenHeaderBean header = new ThankTokenHeaderBean();
    header.setAmount(amount);
    header.setTimestamp(new ThankTimestamp(OffsetDateTime.now().minusDays(36524).toInstant()));
    ThankTokenBean token = new ThankTokenBean(header);

    // when
    ThankValue value = token.getValue();

    // then
    assertThat(value).isEqualTo(ThankValue.of("0.00000001"));
  }

  /**
   * Test of {@link ThankTokenBean#getValue(Instant)} for a token with the age of 100 years.
   */
  @Test
  public void testGetValueThousandYears() {

    // given
    ThankValue amount = ThankValue.VALUE_1;
    ThankTokenHeaderBean header = new ThankTokenHeaderBean();
    header.setAmount(amount);
    header.setTimestamp(new ThankTimestamp(OffsetDateTime.now().minusDays(365242).toInstant()));
    ThankTokenBean token = new ThankTokenBean(header);

    // when
    ThankValue value = token.getValue();

    // then
    assertThat(value).isSameAs(ThankValue.ZERO);
  }

}
