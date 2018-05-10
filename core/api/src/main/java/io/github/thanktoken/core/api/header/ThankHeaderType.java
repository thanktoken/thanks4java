package io.github.thanktoken.core.api.header;

import java.math.BigDecimal;
import java.time.Instant;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;
import net.sf.mmm.security.api.sign.SecuritySignature;

/**
 * This is the implementation of {@link ThankToken} as Java bean.
 */
public class ThankHeaderType extends AbstractThankHeader {

  private final ThankVersion version;

  private final ThankCurrency currency;

  private final BigDecimal amount;

  private final Instant timestamp;

  private final ThankLocation location;

  private final ThankTarget target;

  private final SecurityPublicKey recipient;

  private final SecuritySignature signature;

  private final ThankTokenReferenceType reference;

  /**
   * The constructor.
   *
   * @param version - see {@link #getVersion()}.
   * @param currency - see {@link #getCurrency()}.
   * @param amount - see {@link #getAmount()}.
   * @param timestamp - see {@link #getTimestamp()}.
   * @param location - see {@link #getLocation()}.
   * @param target - see {@link #getTarget()}.
   * @param reference - see {@link #getReference()}.
   * @param recipient - see {@link #getRecipient()}.
   * @param signature - see {@link #getSignature()}.
   */
  public ThankHeaderType(ThankVersion version, ThankCurrency currency, BigDecimal amount, Instant timestamp, ThankLocation location, ThankTarget target,
      ThankTokenReferenceType reference, SecurityPublicKey recipient, SecuritySignature signature) {

    super();
    this.version = version;
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
   * @param header the {@link ThankHeader} to copy.
   */
  public ThankHeaderType(ThankHeader header) {

    super();
    this.version = header.getVersion();
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
  public ThankCurrency getCurrency() {

    return this.currency;
  }

  @Override
  public BigDecimal getAmount() {

    return this.amount;
  }

  @Override
  public Instant getTimestamp() {

    return this.timestamp;
  }

  @Override
  public ThankLocation getLocation() {

    return this.location;
  }

  @Override
  public SecurityPublicKey getRecipient() {

    return this.recipient;
  }

  @Override
  public ThankTarget getTarget() {

    return this.target;
  }

  @Override
  public SecuritySignature getSignature() {

    return this.signature;
  }

  @Override
  public ThankTokenReferenceType getReference() {

    return this.reference;
  }

}
