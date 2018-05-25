package io.github.thanktoken.core.impl.v001.strategy;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import io.github.thanktoken.core.api.strategy.ThankGlobalStrategy;
import io.github.thanktoken.core.api.strategy.ThankStrategyFactory;
import io.github.thanktoken.core.api.strategy.ThankVersionStrategy;
import io.github.thanktoken.core.impl.io.ThankReaderDelegate;
import io.github.thanktoken.core.impl.io.ThankReaderImpl;
import io.github.thanktoken.core.impl.io.ThankWriterDelegate;
import io.github.thanktoken.core.impl.io.ThankWriterImpl;
import io.github.thanktoken.core.impl.sign.ThankSignerDelegate;
import io.github.thanktoken.core.impl.sign.ThankSignerImpl;
import io.github.thanktoken.core.impl.strategy.ThankGlobalStrategyImpl;
import io.github.thanktoken.core.impl.validate.ThankValidatorDelegate;
import io.github.thanktoken.core.impl.validate.ThankValidatorImpl;

/**
 * Test of {@link ThankGlobalStrategy} and {@link ThankStrategyFactory#getGlobalStrategy()}.
 */
public class ThankVersionStrategyV001Test extends Assertions {

  /**
   * Test that {@link ThankStrategyFactory#getGlobalStrategy()} finds the corret implementation via service loader.
   */
  @Test
  public void testGlobalStrategy() {

    ThankGlobalStrategy globalStrategy = ThankStrategyFactory.get().getGlobalStrategy();
    assertThat(globalStrategy).isNotNull().isInstanceOf(ThankGlobalStrategyImpl.class);
    assertThat(globalStrategy.getReader()).isNotNull().isSameAs(ThankReaderDelegate.get());
    assertThat(globalStrategy.getWriter()).isNotNull().isSameAs(ThankWriterDelegate.get());
    assertThat(globalStrategy.getSigner()).isNotNull().isSameAs(ThankSignerDelegate.get());
    assertThat(globalStrategy.getValidator()).isNotNull().isSameAs(ThankValidatorDelegate.get());
  }

  /**
   * Test that {@link ThankStrategyFactory#getGlobalStrategy()} finds the corret implementation via service loader.
   */
  @Test
  public void testVersionStrategy() {

    ThankStrategyFactory factory = ThankStrategyFactory.get();
    assertThat(factory.getVersions()).containsExactly(ThankVersionStrategyV001.VERSION);
    assertThat(factory.getLatestVersion()).isEqualTo(ThankVersionStrategyV001.VERSION);
    ThankVersionStrategy strategy = factory.getLatestStrategy();
    assertThat(strategy).isNotNull().isInstanceOf(ThankVersionStrategyV001.class);
    assertThat(strategy.getVersion()).isEqualTo(ThankVersionStrategyV001.VERSION);
    assertThat(strategy.getReader()).isInstanceOf(ThankReaderImpl.class);
    assertThat(strategy.getWriter()).isInstanceOf(ThankWriterImpl.class);
    assertThat(strategy.getSigner()).isInstanceOf(ThankSignerImpl.class);
    assertThat(strategy.getValidator()).isInstanceOf(ThankValidatorImpl.class);
  }

}
