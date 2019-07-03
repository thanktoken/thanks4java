package io.github.thanktoken.core.api.reference;

import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.location.ThankLocation;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * Implementation of {@link ThankTokenReferenceMutable}.
 */
public class ThankTokenReferenceBean extends AbstractThankTokenReference implements ThankTokenReferenceMutable<ThankTokenReferenceBean> {

  private String type;

  private ThankTimestamp timestamp;

  private ThankLocation location;

  private ThankVersion version;

  private ThankAlgorithm algorithm;

  private ThankCurrency currency;

  /**
   * The constructor.
   */
  public ThankTokenReferenceBean() {

    super();
  }

  @Override
  public String getType() {

    return this.type;
  }

  @Override
  public ThankTokenReferenceBean setType(String type) {

    this.type = type;
    return this;
  }

  @Override
  public ThankTimestamp getTimestamp() {

    return this.timestamp;
  }

  @Override
  public ThankTokenReferenceBean setTimestamp(ThankTimestamp timestamp) {

    this.timestamp = timestamp;
    return this;
  }

  @Override
  public ThankLocation getLocation() {

    return this.location;
  }

  @Override
  public ThankTokenReferenceBean setLocation(ThankLocation location) {

    this.location = location;
    return this;
  }

  @Override
  public ThankVersion getVersion() {

    return this.version;
  }

  @Override
  public ThankTokenReferenceBean setVersion(ThankVersion version) {

    this.version = version;
    return this;
  }

  @Override
  public ThankAlgorithm getAlgorithm() {

    return this.algorithm;
  }

  @Override
  public ThankTokenReferenceBean setAlgorithm(ThankAlgorithm algorithm) {

    this.algorithm = algorithm;
    return this;
  }

  @Override
  public ThankCurrency getCurrency() {

    return this.currency;
  }

  @Override
  public ThankTokenReferenceBean setCurrency(ThankCurrency currency) {

    this.currency = currency;
    return this;
  }

}
