package io.github.thanktoken.core.api.transaction;

import io.github.mmm.crypto.asymmetric.sign.SignatureBinary;
import io.github.mmm.crypto.crypt.EncryptedData;
import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.attribute.AttributeWriteEncryptedPurpose;
import io.github.thanktoken.core.api.attribute.AttributeWritePublicPurpose;
import io.github.thanktoken.core.api.attribute.AttributeWriteRecipient;
import io.github.thanktoken.core.api.attribute.AttributeWriteReference;
import io.github.thanktoken.core.api.attribute.AttributeWriteSignature;
import io.github.thanktoken.core.api.attribute.AttributeWriteTimestamp;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;

/**
 * This is the implementation of {@link ThankTransaction} as Java bean.
 */
public class ThankTransactionBean extends AbstractThankTransaction
    implements AttributeWriteTimestamp<ThankTransactionBean>, AttributeWriteSignature<ThankTransactionBean>,
    AttributeWriteReference<ThankTransactionBean>, AttributeWriteRecipient<ThankTransactionBean>,
    AttributeWritePublicPurpose<ThankTransactionBean>, AttributeWriteEncryptedPurpose<ThankTransactionBean> {

  private ThankTimestamp timestamp;

  private ThankAddress recipient;

  private String publicPurpose;

  private EncryptedData encryptedPurpose;

  private ThankTokenReferenceType reference;

  private SignatureBinary signature;

  /**
   * The constructor.
   */
  public ThankTransactionBean() {

    super();
  }

  /**
   * The constructor.
   *
   * @param tx the {@link ThankTransaction} to copy.
   */
  public ThankTransactionBean(ThankTransaction tx) {

    super();
    this.timestamp = tx.getTimestamp();
    this.recipient = tx.getRecipient();
    this.signature = tx.getSignature();
    this.publicPurpose = tx.getPublicPurpose();
    this.reference = tx.getReference();
    this.encryptedPurpose = tx.getEncryptedPurpose();
  }

  @Override
  public ThankTimestamp getTimestamp() {

    return this.timestamp;
  }

  @Override
  public ThankTransactionBean setTimestamp(ThankTimestamp timestamp) {

    this.timestamp = timestamp;
    return this;
  }

  @Override
  public ThankAddress getRecipient() {

    return this.recipient;
  }

  @Override
  public ThankTransactionBean setRecipient(ThankAddress recipient) {

    this.recipient = recipient;
    return this;
  }

  @Override
  public String getPublicPurpose() {

    return this.publicPurpose;
  }

  @Override
  public ThankTransactionBean setPublicPurpose(String publicPurpose) {

    this.publicPurpose = publicPurpose;
    return this;
  }

  @Override
  public EncryptedData getEncryptedPurpose() {

    return this.encryptedPurpose;
  }

  @Override
  public ThankTransactionBean setEncryptedPurpose(EncryptedData privatePurpose) {

    this.encryptedPurpose = privatePurpose;
    return this;
  }

  @Override
  public ThankTokenReferenceType getReference() {

    return this.reference;
  }

  @Override
  public ThankTransactionBean setReference(ThankTokenReferenceType reference) {

    this.reference = reference;
    return this;
  }

  @Override
  public SignatureBinary getSignature() {

    return this.signature;
  }

  @Override
  public ThankTransactionBean setSignature(SignatureBinary signature) {

    this.signature = signature;
    return this;
  }

}
