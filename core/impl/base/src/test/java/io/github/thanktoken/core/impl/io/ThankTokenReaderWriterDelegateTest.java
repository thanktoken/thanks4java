package io.github.thanktoken.core.impl.io;

import java.util.Collection;
import java.util.Collections;

import io.github.thanktoken.core.api.header.ThankVersion;
import io.github.thanktoken.core.api.io.ThankReader;
import io.github.thanktoken.core.api.io.ThankWriter;
import io.github.thanktoken.core.api.strategy.ThankGlobalStrategy;
import io.github.thanktoken.core.api.strategy.ThankStrategyFactory;
import io.github.thanktoken.core.api.strategy.ThankVersionStrategy;
import io.github.thanktoken.core.impl.strategy.ThankGlobalStrategyImpl;

/**
 * Extends {@link ThankTokenReaderWriterTest} to test {@link ThankReaderDelegate} and {@link ThankWriterDelegate}.
 */
public class ThankTokenReaderWriterDelegateTest extends ThankTokenReaderWriterTest {

  @Override
  protected ThankReader getReader() {

    return new ReaderDelegate();
  }

  @Override
  protected ThankWriter getWriter() {

    return new WriterDelegate();
  }

  private static class GlobalStrategy extends ThankGlobalStrategyImpl {

    private static final GlobalStrategy INSTANCE = new GlobalStrategy();
  }

  private static class Factory extends ThankStrategyFactory {

    private static final Factory INSTANCE = new Factory();

    @Override
    public ThankGlobalStrategy getGlobalStrategy() {

      return GlobalStrategy.INSTANCE;
    }

    @Override
    public Collection<ThankVersion> getVersions() {

      return Collections.singleton(TEST_VERSION);
    }

    @Override
    public ThankVersionStrategy getStrategy(ThankVersion version) {

      assertThat(version).isEqualTo(TEST_VERSION);
      return Strategy.INSTANCE;
    }

  }

  private static class ReaderDelegate extends ThankReaderDelegate {

    @Override
    protected ThankStrategyFactory getStrategyFactory() {

      return Factory.INSTANCE;
    }

  }

  private static class WriterDelegate extends ThankWriterDelegate {

    @Override
    protected ThankStrategyFactory getStrategyFactory() {

      return Factory.INSTANCE;
    }
  }

}
