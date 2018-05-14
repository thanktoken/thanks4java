package io.github.thanktoken.core.api.header;

import java.time.Instant;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.currency.Thanks;
import io.github.thanktoken.core.api.datatype.ThankValue;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;
import net.sf.mmm.security.api.sign.SecuritySignature;

/**
 * This is the implementation of {@link ThankToken} as Java bean.
 */
public class ThankHeaderBean extends AbstractThankHeader {

  private ThankVersion version;

  private ThankCurrency currency;

  private ThankValue amount;

  private Instant timestamp;

  private ThankLocation location;

  private ThankTarget target;

  private SecurityPublicKey recipient;

  private SecuritySignature signature;

  private ThankTokenReferenceType reference;

  /**
   * The constructor.
   */
  public ThankHeaderBean() {

    super();
    this.currency = Thanks.INSTANCE;
    this.amount = ThankValue.ZERO;
    this.version = ThankVersion.of(1);
  }

  /**
   * The constructor.
   *
   * @param header the {@link ThankHeader} to copy.
   */
  public ThankHeaderBean(ThankHeader header) {

    this();
    this.version = header.getVersion();
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

  /**
   * @param version new value of {@link #getVersion()}.
   * @return this.
   */
  public ThankHeaderBean setVersion(ThankVersion version) {

    this.version = version;
    return this;
  }

  @Override
  public ThankCurrency getCurrency() {

    return this.currency;
  }

  /**
   * @param currency new {@link #getCurrency() currency}.
   * @return this.
   */
  public ThankHeaderBean setCurrency(ThankCurrency currency) {

    this.currency = currency;
    return this;
  }

  @Override
  public ThankValue getAmount() {

    return this.amount;
  }

  /**
   * @param amount the new {@link #getAmount() amount}. Where suitable you may use of the predefined {@code VALUE_*}
   *        constants such as {@link ThankValue#VALUE_0_01}.
   * @return this.
   */
  public ThankHeaderBean setAmount(ThankValue amount) {

    this.amount = amount;
    return this;
  }

  @Override
  public Instant getTimestamp() {

    return this.timestamp;
  }

  /**
   * @param timestamp the new {@link #getTimestamp() timestamp}.
   * @return this.
   */
  public ThankHeaderBean setTimestamp(Instant timestamp) {

    this.timestamp = timestamp;
    return this;
  }

  @Override
  public ThankLocation getLocation() {

    return this.location;
  }

  /**
   * @param location the new {@link #getLocation() location}.
   * @return this.
   */
  public ThankHeaderBean setLocation(ThankLocation location) {

    this.location = location;
    return this;
  }

  @Override
  public SecurityPublicKey getRecipient() {

    return this.recipient;
  }

  /**
   * @param creator new value of {@link #getRecipient()}.
   * @return this.
   */
  public ThankHeaderBean setRecipient(SecurityPublicKey creator) {

    this.recipient = creator;
    return this;
  }

  @Override
  public ThankTarget getTarget() {

    return this.target;
  }

  /**
   * @param target the new {@link #getTarget() target}.
   * @return this.
   */
  public ThankHeaderBean setTarget(ThankTarget target) {

    this.target = target;
    return this;
  }

  @Override
  public SecuritySignature getSignature() {

    return this.signature;
  }

  /**
   * @param signature the new {@link #getSignature() signature}.
   * @return this.
   */
  public ThankHeaderBean setSignature(SecuritySignature signature) {

    this.signature = signature;
    return this;
  }

  /**
   * @param reference new value of {@link #getReference() reference}.
   * @return this.
   */
  public ThankHeaderBean setReference(ThankTokenReferenceType reference) {

    this.reference = reference;
    return this;
  }

  @Override
  public ThankTokenReferenceType getReference() {

    return this.reference;
  }

}
