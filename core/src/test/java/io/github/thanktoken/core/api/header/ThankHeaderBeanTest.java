package io.github.thanktoken.core.api.header;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Test;

import io.github.thanktoken.core.api.TestCase;
import io.github.thanktoken.core.api.currency.Thanks;
import io.github.thanktoken.core.api.target.ThankTarget;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.token.header.ThankTokenHeaderBean;
import io.github.thanktoken.core.api.token.header.ThankTokenHeaderField;
import io.github.thanktoken.core.api.value.ThankValue;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * Test of {@link ThankTokenHeaderBean}.
 */
public class ThankHeaderBeanTest extends TestCase {

  private static final ThankTokenHeaderBean EMPTY_TOKEN = createEmptyHeader();

  /** Test of {@link ThankTokenHeaderBean#equals(Object)} for trivial cases. */
  @Test
  public void testEqualsTrivial() {

    assertThat(EMPTY_TOKEN).isEqualTo(EMPTY_TOKEN);
    assertThat(EMPTY_TOKEN).isNotEqualTo(null);
    assertThat(EMPTY_TOKEN).isNotEqualTo("");
  }

  /** Test of all {@link ThankTokenHeaderField fields} of a {@link ThankToken} (getters, setters, etc.). */
  @Test
  public void testFields() {

    List<ThankTokenHeaderField<?>> fields = new ArrayList<>();
    checkField(ThankTokenHeaderField.VERSION, ThankVersion.of(999), ThankTokenHeaderBean::getVersion, ThankTokenHeaderBean::setVersion, fields);
    checkField(ThankTokenHeaderField.ALGORITHM, TEST_ALGORITHM, ThankTokenHeaderBean::getAlgorithm, ThankTokenHeaderBean::setAlgorithm, fields);
    checkField(ThankTokenHeaderField.CURRENCY, Thanks.INSTANCE, ThankTokenHeaderBean::getCurrency, ThankTokenHeaderBean::setCurrency, fields);
    checkField(ThankTokenHeaderField.AMOUNT, ThankValue.VALUE_1, ThankTokenHeaderBean::getAmount, ThankTokenHeaderBean::setAmount, fields);
    checkField(ThankTokenHeaderField.TIMESTAMP, ThankTimestamp.now(), ThankTokenHeaderBean::getTimestamp, ThankTokenHeaderBean::setTimestamp, fields);
    checkField(ThankTokenHeaderField.LOCATION, TEST_LOCATION, ThankTokenHeaderBean::getLocation, ThankTokenHeaderBean::setLocation, fields);
    checkField(ThankTokenHeaderField.TARGET, ThankTarget.PERSON_INCOME, ThankTokenHeaderBean::getTarget, ThankTokenHeaderBean::setTarget, fields);
    checkField(ThankTokenHeaderField.RECIPIENT, TEST_KEY_PAIR_NP.getAddress(), ThankTokenHeaderBean::getRecipient, ThankTokenHeaderBean::setRecipient,
        fields);
    checkField(ThankTokenHeaderField.SIGNATURE, TEST_SIGNATURE, ThankTokenHeaderBean::getSignature, ThankTokenHeaderBean::setSignature, fields);

    assertThat(fields).containsExactly(ThankTokenHeaderField.getFields().getAll().toArray(new ThankTokenHeaderField[0]));
  }

  private <T> void checkField(ThankTokenHeaderField<T> field, T value, Function<ThankTokenHeaderBean, T> getter,
      BiFunction<ThankTokenHeaderBean, T, ThankTokenHeaderBean> setter, List<ThankTokenHeaderField<?>> fields) {

    ThankTokenHeaderBean header = createEmptyHeader();

    field.set(header, null);
    assertThat(getter.apply(header)).isNull();
    assertThat(field.get(header)).isNull();
    assertThat(header).isEqualToComparingFieldByField(EMPTY_TOKEN);

    field.set(header, value);
    assertThat(getter.apply(header)).isSameAs(value);
    assertThat(field.get(header)).isSameAs(value);
    ThankTokenHeader headerWithValue = header;
    header = new ThankTokenHeaderBean(header);
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

  private static ThankTokenHeaderBean createEmptyHeader() {

    ThankTokenHeaderBean token = new ThankTokenHeaderBean();
    for (ThankTokenHeaderField<?> f : ThankTokenHeaderField.getFields().getAll()) {
      f.set(token, null);
    }
    return token;
  }

}
