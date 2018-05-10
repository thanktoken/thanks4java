package io.github.thanktoken.core.api.header;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Test;

import io.github.thanktoken.core.api.TestCase;
import io.github.thanktoken.core.api.ThankConstants;
import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.currency.Thanks;

/**
 * Test of {@link ThankHeaderBean}.
 */
public class ThankHeaderBeanTest extends TestCase {

  private static final ThankHeaderBean EMPTY_TOKEN = createEmptyHeader();

  /** Test of {@link ThankHeaderBean#equals(Object)} for trivial cases. */
  @Test
  public void testEqualsTrivial() {

    assertThat(EMPTY_TOKEN).isEqualTo(EMPTY_TOKEN);
    assertThat(EMPTY_TOKEN).isNotEqualTo(null);
    assertThat(EMPTY_TOKEN).isNotEqualTo("");
  }

  /** Test of all {@link ThankHeaderField fields} of a {@link ThankToken} (getters, setters, etc.). */
  @Test
  public void testFields() {

    List<ThankHeaderField<?>> fields = new ArrayList<>();
    checkField(ThankHeaderField.VERSION, ThankVersion.of(999), ThankHeaderBean::getVersion, ThankHeaderBean::setVersion, fields);
    checkField(ThankHeaderField.AMOUNT, ThankConstants.AMOUNT_0001_00, ThankHeaderBean::getAmount, ThankHeaderBean::setAmount, fields);
    checkField(ThankHeaderField.CURRENCY, Thanks.INSTANCE, ThankHeaderBean::getCurrency, ThankHeaderBean::setCurrency, fields);
    checkField(ThankHeaderField.TIMESTAMP, Instant.now(), ThankHeaderBean::getTimestamp, ThankHeaderBean::setTimestamp, fields);
    checkField(ThankHeaderField.LOCATION, TEST_LOCATION, ThankHeaderBean::getLocation, ThankHeaderBean::setLocation, fields);
    checkField(ThankHeaderField.TARGET, ThankTarget.PERSON_INCOME, ThankHeaderBean::getTarget, ThankHeaderBean::setTarget, fields);
    checkField(ThankHeaderField.RECIPIENT, TEST_PUBLIC_KEY, ThankHeaderBean::getRecipient, ThankHeaderBean::setRecipient, fields);
    checkField(ThankHeaderField.SIGNATURE, TEST_SIGNATURE, ThankHeaderBean::getSignature, ThankHeaderBean::setSignature, fields);

    assertThat(fields).containsExactly(ThankHeaderField.getFields().getAll().toArray(new ThankHeaderField[0]));
  }

  private <T> void checkField(ThankHeaderField<T> field, T value, Function<ThankHeaderBean, T> getter, BiFunction<ThankHeaderBean, T, ThankHeaderBean> setter,
      List<ThankHeaderField<?>> fields) {

    ThankHeaderBean header = createEmptyHeader();

    field.set(header, null);
    assertThat(getter.apply(header)).isNull();
    assertThat(field.get(header)).isNull();
    assertThat(header).isEqualToComparingFieldByField(EMPTY_TOKEN);

    field.set(header, value);
    assertThat(getter.apply(header)).isSameAs(value);
    assertThat(field.get(header)).isSameAs(value);
    ThankHeader headerWithValue = header;
    header = new ThankHeaderBean(header);
    assertThat(header).isEqualToComparingFieldByField(headerWithValue);

    assertThat(setter.apply(header, null)).isSameAs(header);
    assertThat(getter.apply(header)).isNull();
    assertThat(field.get(header)).isNull();
    assertThat(header).isEqualToComparingFieldByField(EMPTY_TOKEN);

    assertThat(setter.apply(header, value)).isSameAs(header);
    assertThat(getter.apply(header)).isSameAs(value);
    assertThat(field.get(header)).isSameAs(value);
    assertThat(header).isEqualToComparingFieldByField(headerWithValue);

    fields.add(field);
  }

  private static ThankHeaderBean createEmptyHeader() {

    ThankHeaderBean token = new ThankHeaderBean();
    for (ThankHeaderField<?> f : ThankHeaderField.getFields().getAll()) {
      f.set(token, null);
    }
    return token;
  }

}
