package io.github.thanktoken.core.impl.io;

import javax.json.stream.JsonParser;

import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.header.ThankHeaderBean;
import io.github.thanktoken.core.api.header.ThankHeaderField;
import io.github.thanktoken.core.api.io.ThankReader;
import io.github.thanktoken.core.api.io.ThankReaderImpl;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.transaction.ThankTransactionField;
import io.github.thanktoken.core.api.version.ThankVersion;
import io.github.thanktoken.core.impl.strategy.AbstractThankVersionStrategy;
import io.github.thanktoken.core.impl.strategy.AbstractThankVersionStrategyContainer;

/**
 * Implementation of {@link ThankReader}.
 */
public class ThankReaderImpl extends AbstractThankVersionStrategyContainer implements ThankReaderImpl {

  /**
   * The constructor.
   *
   * @param strategy the {@link AbstractThankVersionStrategy}.
   */
  public ThankReaderImpl(AbstractThankVersionStrategy strategy) {

    super(strategy);
  }

  @Override
  public ThankHeader readHeader(ThankVersion version, JsonParser jsonParser) {

    verifyVersion(version);
    ThankHeaderBean token = new ThankHeaderBean();
    token.setVersion(version);
    ThankHeaderField.getFields().fromJson(jsonParser, getValueParser(), token);
    return token;
  }

  @Override
  public ThankTransaction readTransaction(ThankVersion version, JsonParser jsonParser) {

    verifyVersion(version);
    return ThankTransactionField.getFields().fromJson(jsonParser, getValueParser());
  }

}
