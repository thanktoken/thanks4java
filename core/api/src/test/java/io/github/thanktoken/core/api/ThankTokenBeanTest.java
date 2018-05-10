/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;

import org.assertj.core.data.Offset;
import org.junit.Test;

import io.github.thanktoken.core.api.header.ThankHeaderBean;

/**
 * Test of {@link ThankTokenBean}.
 */
public class ThankTokenBeanTest extends TestCase {

  /** Required precision for calculations. */
  private static final Offset<BigDecimal> PRECISION = Offset.offset(new BigDecimal("0.0000000000000001"));

  /**
   * Test of {@link ThankTokenBean#getValue()} for a token created today.
   */
  @Test
  public void testGetValueToday() {

    // given
    BigDecimal amount = ThankConstants.AMOUNT_0000_01;
    ThankHeaderBean header = new ThankHeaderBean();
    header.setAmount(amount);
    header.setTimestamp(Instant.now());
    ThankTokenBean token = new ThankTokenBean(header);

    // when
    BigDecimal value = token.getValue();

    // then
    assertThat(value).isCloseTo(amount, PRECISION);
  }

  /**
   * Test of {@link ThankTokenBean#getValue(Instant)} for a token with the age of one year.
   */
  @Test
  public void testGetValueOneYear() {

    // given
    BigDecimal amount = ThankConstants.AMOUNT_0001_00;
    ThankHeaderBean header = new ThankHeaderBean();
    header.setAmount(amount);
    header.setTimestamp(OffsetDateTime.now().minusDays(365).toInstant());
    ThankTokenBean token = new ThankTokenBean(header);

    // when
    BigDecimal value = token.getValue();

    // then
    assertThat(value).isCloseTo(BigDecimal.valueOf(0.833146618070608531D), PRECISION);
  }

}
