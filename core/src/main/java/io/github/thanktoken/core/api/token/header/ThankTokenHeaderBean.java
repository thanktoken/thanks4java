package io.github.thanktoken.core.api.token.header;

import io.github.mmm.crypto.asymmetric.sign.SignatureBinary;
import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.currency.Thanks;
import io.github.thanktoken.core.api.location.ThankLocation;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;
import io.github.thanktoken.core.api.target.ThankTarget;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.value.ThankValue;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * This is the implementation of {@link ThankToken} as Java bean.
 */
public class ThankTokenHeaderBean extends AbstractThankTokenHeader
    implements ThankTokenHeaderMutable<ThankTokenHeaderBean> {

  private ThankVersion version;

  private ThankAlgorithm algorithm;

  private ThankCurrency currency;

  private ThankValue amount;

  private ThankTimestamp timestamp;

  private ThankLocation location;

  private ThankTarget target;

  private ThankAddress recipient;

  private SignatureBinary signature;

  private ThankTokenReferenceType reference;

  /**
   * The constructor.
   */
  public ThankTokenHeaderBean() {

    super();
    this.currency = Thanks.INSTANCE;
    this.amount = ThankValue.ZERO;
    this.version = ThankVersion.of(1);
  }

  /**
   * The constructor.
   *
   * @param header the {@link ThankTokenHeader} to copy.
   */
  public ThankTokenHeaderBean(ThankTokenHeader header) {

    this();
    this.version = header.getVersion();
    this.algorithm = header.getAlgorithm();
    this.currency = header.getCurrency();
    this.amount = header.getAmount();
    this.timestamp = header.getTimestamp();
    this.location = header.getLocation();
    this.recipient = header.getRecipient();
    this.target = header.getTarget();
    this.signature = header.getSignature();
  }

  @Override
  public ThankVersion getVersion() {

    return this.version;
  }

  @Override
  public ThankTokenHeaderBean setVersion(ThankVersion version) {

    this.version = version;
    return this;
  }

  @Override
  public ThankAlgorithm getAlgorithm() {

    return this.algorithm;
  }

  @Override
  public ThankTokenHeaderBean setAlgorithm(ThankAlgorithm algorithm) {

    this.algorithm = algorithm;
    return this;
  }

  @Override
  public ThankCurrency getCurrency() {

    return this.currency;
  }

  @Override
  public ThankTokenHeaderBean setCurrency(ThankCurrency currency) {

    this.currency = currency;
    return this;
  }

  @Override
  public ThankValue getAmount() {

    return this.amount;
  }

  @Override
  public ThankTokenHeaderBean setAmount(ThankValue amount) {

    this.amount = amount;
    return this;
  }

  @Override
  public ThankTimestamp getTimestamp() {

    return this.timestamp;
  }

  @Override
  public ThankTokenHeaderBean setTimestamp(ThankTimestamp timestamp) {

    this.timestamp = timestamp;
    return this;
  }

  @Override
  public ThankLocation getLocation() {

    return this.location;
  }

  @Override
  public ThankTokenHeaderBean setLocation(ThankLocation location) {

    this.location = location;
    return this;
  }

  @Override
  public ThankAddress getRecipient() {

    return this.recipient;
  }

  @Override
  public ThankTokenHeaderBean setRecipient(ThankAddress creator) {

    this.recipient = creator;
    return this;
  }

  @Override
  public ThankTarget getTarget() {

    return this.target;
  }

  @Override
  public ThankTokenHeaderBean setTarget(ThankTarget target) {

    this.target = target;
    return this;
  }

  @Override
  public SignatureBinary getSignature() {

    return this.signature;
  }

  /**
   * @param signature the new {@link #getSignature() signature}.
   * @return this.
   */
  @Override
  public ThankTokenHeaderBean setSignature(SignatureBinary signature) {

    this.signature = signature;
    return this;
  }

  /**
   * @param reference new value of {@link #getReference() reference}.
   * @return this.
   */
  public ThankTokenHeaderBean setReference(ThankTokenReferenceType reference) {

    this.reference = reference;
    return this;
  }

  @Override
  public ThankTokenReferenceType getReference() {

    return this.reference;
  }

}
