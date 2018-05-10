package io.github.thanktoken.core.impl.strategy;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import io.github.thanktoken.core.api.strategy.ThankGlobalStrategy;
import io.github.thanktoken.core.api.strategy.ThankStrategyFactory;
import io.github.thanktoken.core.impl.io.ThankReaderDelegate;
import io.github.thanktoken.core.impl.io.ThankWriterDelegate;
import io.github.thanktoken.core.impl.sign.ThankTokenSignerDelegate;
import io.github.thanktoken.core.impl.validate.ThankTokenValidatorDelegate;

/**
 * Test of {@link ThankGlobalStrategy} and {@link ThankStrategyFactory#getGlobalStrategy()}.
 */
public class ThankGlobalStrategyTest extends Assertions {

  /**
   * Test that {@link ThankStrategyFactory#getGlobalStrategy()} finds the corret implementation via service loader.
   */
  @Test
  public void testGlobalStrategy() {

    ThankGlobalStrategy globalStrategy = ThankStrategyFactory.get().getGlobalStrategy();
    assertThat(globalStrategy).isNotNull().isInstanceOf(ThankGlobalStrategyImpl.class);
    assertThat(globalStrategy.getReader()).isNotNull().isSameAs(ThankReaderDelegate.get());
    assertThat(globalStrategy.getWriter()).isNotNull().isSameAs(ThankWriterDelegate.get());
    assertThat(globalStrategy.getSigner()).isNotNull().isSameAs(ThankTokenSignerDelegate.get());
    assertThat(globalStrategy.getValidator()).isNotNull().isSameAs(ThankTokenValidatorDelegate.get());
  }

}
