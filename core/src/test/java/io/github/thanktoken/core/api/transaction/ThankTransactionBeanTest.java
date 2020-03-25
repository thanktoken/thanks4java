package io.github.thanktoken.core.api.transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import io.github.thanktoken.core.api.TestCase;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;

/**
 * Test of {@link ThankTransactionBean}.
 */
public class ThankTransactionBeanTest extends TestCase {

  private static final ThankTransactionBean EMPTY_TX = new ThankTransactionBean();

  /** Test of {@link ThankTransactionBean#equals(Object)} for trivial cases. */
  @Test
  public void testEqualsTrivial() {

    assertThat(EMPTY_TX).isEqualTo(EMPTY_TX);
    assertThat(EMPTY_TX).isNotEqualTo(null);
    assertThat(EMPTY_TX).isNotEqualTo("");
  }

  /** Test of all {@link ThankTransactionField fields} of a {@link ThankTransactionBean} (getters, setters, etc.). */
  @Test
  public void testFields() {

    List<ThankTransactionField<?>> fields = new ArrayList<>();
    checkField(ThankTransactionField.TIMESTAMP, ThankTimestamp.now(), ThankTransaction::getTimestamp,
        ThankTransactionBean::setTimestamp, fields);
    checkField(ThankTransactionField.RECIPIENT, TEST_KEY_PAIR_PS.getAddress(), ThankTransaction::getRecipient,
        ThankTransactionBean::setRecipient, fields);
    checkField(ThankTransactionField.PUBLIC_PURPOSE, "Hello World", ThankTransaction::getPublicPurpose,
        ThankTransactionBean::setPublicPurpose, fields);
    checkField(ThankTransactionField.ENCRYPTED_PURPOSE, TEST_ENCRYPTED_DATA, ThankTransaction::getEncryptedPurpose,
        ThankTransactionBean::setEncryptedPurpose, fields);
    checkField(ThankTransactionField.REFERENCE, TEST_REFERENCE, ThankTransaction::getReference,
        ThankTransactionBean::setReference, fields);
    checkField(ThankTransactionField.SIGNATURE, TEST_SIGNATURE, ThankTransaction::getSignature,
        ThankTransactionBean::setSignature, fields);

    assertThat(fields)
        .containsExactly(ThankTransactionField.getFields().getAll().toArray(new ThankTransactionField[0]));
  }

  private <T> void checkField(ThankTransactionField<T> field, T value, Function<ThankTransactionBean, T> getter,
      BiFunction<ThankTransactionBean, T, ThankTransactionBean> setter, Collection<ThankTransactionField<?>> fields) {

    ThankTransactionBean line = new ThankTransactionBean();

    field.set(line, null);
    assertThat(getter.apply(line)).isNull();
    assertThat(field.get(line)).isNull();
    assertThat(line).isEqualToComparingFieldByField(EMPTY_TX);

    field.set(line, value);
    assertThat(getter.apply(line)).isSameAs(value);
    assertThat(field.get(line)).isSameAs(value);
    assertThat(line).isNotEqualTo(EMPTY_TX);
    ThankTransactionBean lineWithValue = line;
    line = new ThankTransactionBean(line);
    assertThat(line).isEqualToComparingFieldByField(lineWithValue);

    assertThat(setter.apply(line, null)).isSameAs(line);
    assertThat(getter.apply(line)).isNull();
    assertThat(field.get(line)).isNull();
    assertThat(line).isEqualToComparingFieldByField(EMPTY_TX);

    assertThat(setter.apply(line, value)).isSameAs(line);
    assertThat(getter.apply(line)).isSameAs(value);
    assertThat(field.get(line)).isSameAs(value);
    assertThat(line).isEqualToComparingFieldByField(lineWithValue);

    fields.add(field);
  }

}
