package io.github.thanktoken.core.api.transaction;

import net.sf.mmm.crypto.asymmetric.sign.SignatureBinary;
import net.sf.mmm.crypto.crypt.EncryptedData;

import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;

/**
 * This is the implementation of {@link ThankTransaction} as immutable type.
 */
public class ThankTransactionType extends AbstractThankTransaction {

  private final ThankTimestamp timestamp;

  private final ThankAddress recipient;

  private final String publicPurpose;

  private final EncryptedData encryptedPurpose;

  private final ThankTokenReferenceType reference;

  private final SignatureBinary signature;

  /**
   * The constructor.
   *
   * @param timestamp - see {@link #getTimestamp()}.
   * @param recipient - see {@link #getRecipient()}.
   * @param publicPurpose - see {@link #getPublicPurpose()}.
   * @param encryptedPurpose - see {@link #getEncryptedPurpose()}.
   * @param reference - see {@link #getReference()}.
   * @param signature - see {@link #getSignature()}.
   */
  public ThankTransactionType(ThankTimestamp timestamp, ThankAddress recipient, String publicPurpose, EncryptedData encryptedPurpose,
      ThankTokenReferenceType reference, SignatureBinary signature) {

    super();
    this.timestamp = timestamp;
    this.recipient = recipient;
    this.publicPurpose = publicPurpose;
    this.encryptedPurpose = encryptedPurpose;
    this.reference = reference;
    this.signature = signature;
  }

  /**
   * The constructor.
   *
   * @param tx the {@link ThankTransaction} to copy.
   */
  public ThankTransactionType(ThankTransaction tx) {

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
  public ThankAddress getRecipient() {

    return this.recipient;
  }

  @Override
  public String getPublicPurpose() {

    return this.publicPurpose;
  }

  @Override
  public EncryptedData getEncryptedPurpose() {

    return this.encryptedPurpose;
  }

  @Override
  public ThankTokenReferenceType getReference() {

    return this.reference;
  }

  @Override
  public SignatureBinary getSignature() {

    return this.signature;
  }

}
