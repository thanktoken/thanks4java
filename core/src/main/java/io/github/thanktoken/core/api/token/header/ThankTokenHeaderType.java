package io.github.thanktoken.core.api.token.header;

import net.sf.mmm.crypto.asymmetric.sign.SignatureBinary;

import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.currency.ThankCurrency;
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
public class ThankTokenHeaderType extends AbstractThankTokenHeader {

  private final ThankVersion version;

  private final ThankAlgorithm algorithm;

  private final ThankCurrency currency;

  private final ThankValue amount;

  private final ThankTimestamp timestamp;

  private final ThankLocation location;

  private final ThankTarget target;

  private final ThankAddress recipient;

  private final SignatureBinary signature;

  private final ThankTokenReferenceType reference;

  /**
   * The constructor.
   *
   * @param version - see {@link #getVersion()}.
   * @param algorithm - see {@link #getAlgorithm()}.
   * @param currency - see {@link #getCurrency()}.
   * @param amount - see {@link #getAmount()}.
   * @param timestamp - see {@link #getTimestamp()}.
   * @param location - see {@link #getLocation()}.
   * @param target - see {@link #getTarget()}.
   * @param reference - see {@link #getReference()}.
   * @param recipient - see {@link #getRecipient()}.
   * @param signature - see {@link #getSignature()}.
   */
  public ThankTokenHeaderType(ThankVersion version, ThankAlgorithm algorithm, ThankCurrency currency, ThankValue amount,
      ThankTimestamp timestamp, ThankLocation location, ThankTarget target, ThankTokenReferenceType reference, ThankAddress recipient,
      SignatureBinary signature) {

    super();
    this.version = version;
    this.algorithm = algorithm;
    this.currency = currency;
    this.amount = amount;
    this.timestamp = timestamp;
    this.location = location;
    this.target = target;
    this.reference = reference;
    this.recipient = recipient;
    this.signature = signature;
  }

  /**
   * The constructor.
   *
   * @param header the {@link ThankTokenHeader} to copy.
   */
  public ThankTokenHeaderType(ThankTokenHeader header) {

    super();
    this.version = header.getVersion();
    this.algorithm = header.getAlgorithm();
    this.currency = header.getCurrency();
    this.amount = header.getAmount();
    this.timestamp = header.getTimestamp();
    this.location = header.getLocation();
    this.target = header.getTarget();
    this.reference = header.getReference();
    this.recipient = header.getRecipient();
    this.signature = header.getSignature();
  }

  @Override
  public ThankVersion getVersion() {

    return this.version;
  }

  @Override
  public ThankAlgorithm getAlgorithm() {

    return this.algorithm;
  }

  @Override
  public ThankCurrency getCurrency() {

    return this.currency;
  }

  @Override
  public ThankValue getAmount() {

    return this.amount;
  }

  @Override
  public ThankTimestamp getTimestamp() {

    return this.timestamp;
  }

  @Override
  public ThankLocation getLocation() {

    return this.location;
  }

  @Override
  public ThankAddress getRecipient() {

    return this.recipient;
  }

  @Override
  public ThankTarget getTarget() {

    return this.target;
  }

  @Override
  public SignatureBinary getSignature() {

    return this.signature;
  }

  @Override
  public ThankTokenReferenceType getReference() {

    return this.reference;
  }

}
