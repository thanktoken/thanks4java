/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api.datatype;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import io.github.thanktoken.core.api.TestCase;

/**
 * Test of {@link ThankValue}.
 */
public class ThankValueTest extends TestCase {

  /**
   * Tests the constants of {@link ThankValue} such as {@link ThankValue#VALUE_0}.
   */
  @Test
  public void testConstants() {

    checkConstant(ThankValue.VALUE_0, "0", ThankValue.ZERO);
    checkConstant(ThankValue.VALUE_0_00000001, "0.00000001");
    checkConstant(ThankValue.VALUE_0_01, "0.01");
    checkConstant(ThankValue.VALUE_0_02, "0.02");
    checkConstant(ThankValue.VALUE_0_05, "0.05");
    checkConstant(ThankValue.VALUE_0_1, "0.1");
    checkConstant(ThankValue.VALUE_0_2, "0.2");
    checkConstant(ThankValue.VALUE_0_25, "0.25");
    checkConstant(ThankValue.VALUE_0_5, "0.5");
    checkConstant(ThankValue.VALUE_1, "1", ThankValue.ONE);
    checkConstant(ThankValue.VALUE_2, "2");
    checkConstant(ThankValue.VALUE_5, "5");
    checkConstant(ThankValue.VALUE_10, "10");
    checkConstant(ThankValue.VALUE_11_11111111, "11.11111111");
    checkConstant(ThankValue.VALUE_20, "20");
    checkConstant(ThankValue.VALUE_25, "25");
    checkConstant(ThankValue.VALUE_33_33333333, "33.33333333");
    checkConstant(ThankValue.VALUE_50, "50");
    checkConstant(ThankValue.VALUE_100, "100");
    checkConstant(ThankValue.VALUE_500, "500");
    checkConstant(ThankValue.VALUE_1000, "1000");
  }

  private static void checkConstant(ThankValue value, String string) {

    checkConstant(value, string, value);
  }

  private static void checkConstant(ThankValue value, String string, ThankValue sameValue) {

    assertThat(value).isSameAs(sameValue).hasToString(string).isSameAs(ThankValue.of(string)).isSameAs(ThankValue.of(value.bigDecimalValue()))
        .isSameAs(ThankValue.ofUnscaled(value.getUnscaledValue()));
  }

  /**
   * Tests {@link ThankValue#of(String)}.
   */
  @Test
  public void testOfString() {

    checkOfString("0", 0);
    checkOfString("0.00000001", 1);
    checkOfString("0.00000009", 9);
    checkOfString("9.87654321", 987654321);
    checkOfString("92233720368.54775807", 9223372036854775807L);
    checkOfString("0.0000001", 10);
    checkOfString("98.7654321", 9876543210L);
    checkOfString("0.000001", 100);
    checkOfString("987.654321", 98765432100L);
    checkOfString("0.00001", 1000);
    checkOfString("9876.54321", 987654321000L);
    checkOfString("0.0001", 10000);
    checkOfString("98765.4321", 9876543210000L);
    checkOfString("0.001", 100000);
    checkOfString("987654.321", 98765432100000L);
    checkOfString("0.01", 1000000);
    checkOfString("9876543.21", 987654321000000L);
    checkOfString("0.1", 10000000);
    checkOfString("98765432.1", 9876543210000000L);
    checkOfString("1", 100000000);
    checkOfString("987654321", 98765432100000000L);
    checkOfString("10", 1000000000);
    checkOfString("9876543210", 987654321000000000L);
    checkOfString("100", 10000000000L);
  }

  /**
   * Tests {@link ThankValue#of(String)} and methods with {@code null} as argument.
   */
  @Test
  public void testNull() {

    assertThat(ThankValue.of((BigDecimal) null)).isNull();
    assertThat(ThankValue.of((String) null)).isNull();
    assertThat(ThankValue.ZERO.compareTo(null)).isEqualTo(-1);
  }

  /**
   * Tests {@link ThankValue#of(String)} and other fabrications with illegal values.
   */
  @Test
  public void testOfInvalid() {

    // syntax
    checkOfStringSyntax("+");
    checkOfStringSyntax("0x0");
    checkOfStringSyntax(".");
    checkOfStringSyntax(".0");
    checkOfStringSyntax("0.");
    checkOfStringSyntax("0.0.0");
    checkOfStringSyntax("1F");
    checkOfStringSyntax("-1");
    checkOfStringSyntax("0.000000001");

    // overflow
    checkOfStringOverflow("98765432100");
    checkOfStringOverflow("92233720369");
    checkOfStringOverflow("92233720368.54775808");
    checkOfStringOverflow("123456789012");
    checkOfStringOverflow("1234567890123");
    checkOfStringOverflow("12345678901234");
    checkOfStringOverflow("123456789012345");
    checkOfStringOverflow("1234567890123456");
    checkOfStringOverflow("12345678901234567");
    checkOfStringOverflow("123456789012345678");
    checkOfStringOverflow("1234567890123456789");
    checkOfStringOverflow("12345678901234567890");
  }

  private void checkOfStringSyntax(String string) {

    shouldThrow(() -> ThankValue.of(string), IllegalArgumentException.class, string);
  }

  private void checkOfStringOverflow(String string) {

    shouldThrow(() -> ThankValue.of(string), IllegalArgumentException.class, string);
    shouldThrow(() -> ThankValue.of(new BigDecimal(string)), IllegalArgumentException.class,
        string /* + " is greater than maximum value 92233720368.54775807" */);
  }

  private void checkOfString(String string, long unscaled) {

    checkOfString(string, unscaled, new BigDecimal(string));
  }

  private void checkOfString(String string, long unscaled, BigDecimal expected) {

    ThankValue value = ThankValue.of(string);
    assertThat(value.getUnscaledValue()).isEqualTo(unscaled);
    assertThat(value.toString()).isEqualTo(string);
    assertThat(value.bigDecimalValue()).isEqualTo(expected);
  }

  /**
   * Test of {@link ThankValue#equals(Object)} and {@link ThankValue#hashCode()}.
   */
  @Test
  public void testEqualsAndHashCode() {

    // positives
    checkEqualsAndHashCode(ThankValue.ofUnscaled(42), ThankValue.ofUnscaled(42), true);
    checkEqualsAndHashCode(ThankValue.of("0.4"), ThankValue.of(new BigDecimal("0.40")), true);
    checkEqualsAndHashCode(ThankValue.of("3"), ThankValue.of(new BigDecimal("3.0")), true);

    // negatives
    checkEqualsAndHashCode(ThankValue.of("3"), ThankValue.of("3.00000001"), false);
  }

  /**
   * Test of {@link ThankValue#compareTo(ThankValue)}.
   */
  @Test
  public void testCompareTo() {

    List<ThankValue> values = new ArrayList<>();
    values.add(ThankValue.VALUE_1);
    values.add(ThankValue.VALUE_1000);
    values.add(ThankValue.VALUE_25);
    values.add(ThankValue.VALUE_33_33333333);
    values.add(ThankValue.VALUE_5);
    values.add(ThankValue.VALUE_50);
    values.add(ThankValue.VALUE_0_00000001);
    values.add(ThankValue.VALUE_0_1);
    values.add(ThankValue.VALUE_0_01);
    values.add(ThankValue.VALUE_20);
    values.add(ThankValue.VALUE_0_05);
    values.add(ThankValue.VALUE_0_2);
    values.add(ThankValue.VALUE_100);
    values.add(ThankValue.VALUE_11_11111111);
    values.add(ThankValue.VALUE_0_5);
    values.add(ThankValue.VALUE_0_02);
    values.add(ThankValue.VALUE_2);
    values.add(ThankValue.VALUE_10);
    values.add(ThankValue.VALUE_0_25);
    values.add(ThankValue.VALUE_1000);
    values.add(ThankValue.VALUE_0);
    values.add(ThankValue.of("42.42"));
    Collections.sort(values);
    assertThat(values).containsExactly(ThankValue.VALUE_0, ThankValue.VALUE_0_00000001, ThankValue.VALUE_0_01, ThankValue.VALUE_0_02, ThankValue.VALUE_0_05,
        ThankValue.VALUE_0_1, ThankValue.VALUE_0_2, ThankValue.VALUE_0_25, ThankValue.VALUE_0_5, ThankValue.VALUE_1, ThankValue.VALUE_2, ThankValue.VALUE_5,
        ThankValue.VALUE_10, ThankValue.VALUE_11_11111111, ThankValue.VALUE_20, ThankValue.VALUE_25, ThankValue.VALUE_33_33333333, ThankValue.of("42.42"),
        ThankValue.VALUE_50, ThankValue.VALUE_100, ThankValue.VALUE_1000, ThankValue.VALUE_1000);
  }

  /**
   * Test of {@link ThankValue#doubleValue()}.
   */
  @Test
  public void testDoubleValue() {

    assertThat(ThankValue.VALUE_0_25.doubleValue()).isEqualTo(0.25);
  }

  /**
   * Test of {@link ThankValue#doubleValue()}.
   */
  @Test
  public void testFloatValue() {

    assertThat(ThankValue.VALUE_0_25.floatValue()).isEqualTo(0.25F);
  }

  /**
   * Test of {@link ThankValue#add(ThankValue)}.
   */
  @Test
  public void testAdd() {

    // positives
    assertThat(ThankValue.VALUE_0_00000001.add(ThankValue.VALUE_1)).isEqualTo(ThankValue.of("1.00000001"));
    assertThat(ThankValue.VALUE_0_00000001.add(ThankValue.VALUE_1000)).isEqualTo(ThankValue.of("1000.00000001"));

    // negatives
    checkAddOverflow(ThankValue.VALUE_0_00000001, ThankValue.MAX_VALUE);
    checkAddOverflow(ThankValue.MAX_VALUE, ThankValue.MAX_VALUE);
    checkAddOverflow(ThankValue.ofUnscaled(4611686018427387903L), ThankValue.MAX_VALUE);
  }

  private void checkAddOverflow(ThankValue value1, ThankValue value2) {

    shouldThrow(() -> value1.add(value2), IllegalArgumentException.class, "Overflow whilst adding " + value1 + " and " + value2);
  }

}
