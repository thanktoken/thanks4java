package io.github.thanktoken.core.api.transaction;

import java.time.Instant;
import java.util.List;

import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;
import net.sf.mmm.security.api.crypt.SecurityEncryptedData;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;
import net.sf.mmm.security.api.sign.SecuritySignature;

/**
 * This is the implementation of {@link ThankTransaction} as immutable type.
 */
public class ThankTransactionType extends AbstractThankTransaction {

  private final Instant timestamp;

  private final SecurityPublicKey recipient;

  private final String publicPurpose;

  private final SecurityEncryptedData encryptedPurpose;

  private final List<ThankTokenReferenceType> referenceList;

  private final SecuritySignature signature;

  /**
   * The constructor.
   *
   * @param timestamp - see {@link #getTimestamp()}.
   * @param recipient - see {@link #getRecipient()}.
   * @param publicPurpose - see {@link #getPublicPurpose()}.
   * @param encryptedPurpose - see {@link #getEncryptedPurpose()}.
   * @param referenceList - see {@link #getReferenceList()}.
   * @param signature - see {@link #getSignature()}.
   */
  public ThankTransactionType(Instant timestamp, SecurityPublicKey recipient, String publicPurpose, SecurityEncryptedData encryptedPurpose,
      List<ThankTokenReferenceType> referenceList, SecuritySignature signature) {

    super();
    this.timestamp = timestamp;
    this.recipient = recipient;
    this.publicPurpose = publicPurpose;
    this.encryptedPurpose = encryptedPurpose;
    this.referenceList = referenceList;
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
    this.referenceList = tx.getReferenceList();
    this.encryptedPurpose = tx.getEncryptedPurpose();
  }

  @Override
  public Instant getTimestamp() {

    return this.timestamp;
  }

  @Override
  public SecurityPublicKey getRecipient() {

    return this.recipient;
  }

  @Override
  public String getPublicPurpose() {

    return this.publicPurpose;
  }

  @Override
  public SecurityEncryptedData getEncryptedPurpose() {

    return this.encryptedPurpose;
  }

  @Override
  public List<ThankTokenReferenceType> getReferenceList() {

    return this.referenceList;
  }

  @Override
  public SecuritySignature getSignature() {

    return this.signature;
  }

}
