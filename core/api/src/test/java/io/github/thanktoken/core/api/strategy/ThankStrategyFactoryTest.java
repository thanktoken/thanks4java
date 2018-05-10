package io.github.thanktoken.core.api.strategy;

import org.junit.Test;

import io.github.thanktoken.core.api.TestCase;
import io.github.thanktoken.core.api.strategy.ThankStrategyFactory;

/**
 * The test-case for {@link ThankStrategyFactory}. As this module only contains the API there are no implementations to
 * test. Hence, this test is kind of boring. The implementations contain additional tests covering more interesting
 * cases of {@link ThankStrategyFactory}.
 */
public class ThankStrategyFactoryTest extends TestCase {

  /** Test of {@link ThankStrategyFactory}. */
  @Test
  public void testFactory() {

    ThankStrategyFactory factory = ThankStrategyFactory.get();
    assertThat(factory).isNotNull();
    assertThat(factory.getVersions()).isEmpty();
    assertThat(factory.getGlobalStrategy()).isNull();
    assertThat(factory.getLatestVersion()).isNull();
    assertThat(factory.getLatestStrategy()).isNull();
    try {
      assertThat(factory.getStrategy(null));
      failBecauseExceptionWasNotThrown(NullPointerException.class);
    } catch (NullPointerException e) {
      assertThat(e).hasMessage("version");
    }
  }

}
