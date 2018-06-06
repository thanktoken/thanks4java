package io.github.thanktoken.core.api.transaction;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import io.github.thanktoken.core.api.attribute.AttributeWriteEncryptedPurpose;
import io.github.thanktoken.core.api.attribute.AttributeWritePublicPurpose;
import io.github.thanktoken.core.api.attribute.AttributeWriteRecipient;
import io.github.thanktoken.core.api.attribute.AttributeWriteReference;
import io.github.thanktoken.core.api.attribute.AttributeWriteSignature;
import io.github.thanktoken.core.api.attribute.AttributeWriteTimestamp;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;
import net.sf.mmm.security.api.crypt.SecurityEncryptedData;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;
import net.sf.mmm.security.api.sign.SecuritySignature;

/**
 * This is the implementation of {@link ThankTransaction} as Java bean.
 */
public class ThankTransactionBean extends AbstractThankTransaction
    implements AttributeWriteTimestamp<ThankTransactionBean>, AttributeWriteSignature<ThankTransactionBean>, AttributeWriteReference<ThankTransactionBean>,
    AttributeWriteRecipient<ThankTransactionBean>, AttributeWritePublicPurpose<ThankTransactionBean>, AttributeWriteEncryptedPurpose<ThankTransactionBean> {

  private Instant timestamp;

  private SecurityPublicKey recipient;

  private String publicPurpose;

  private SecurityEncryptedData encryptedPurpose;

  private List<ThankTokenReferenceType> referenceList;

  private SecuritySignature signature;

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
    this.referenceList = tx.getReferenceList();
    this.encryptedPurpose = tx.getEncryptedPurpose();
  }

  @Override
  public Instant getTimestamp() {

    return this.timestamp;
  }

  @Override
  public ThankTransactionBean setTimestamp(Instant timestamp) {

    this.timestamp = timestamp;
    return this;
  }

  @Override
  public SecurityPublicKey getRecipient() {

    return this.recipient;
  }

  @Override
  public ThankTransactionBean setRecipient(SecurityPublicKey recipient) {

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
  public SecurityEncryptedData getEncryptedPurpose() {

    return this.encryptedPurpose;
  }

  @Override
  public ThankTransactionBean setEncryptedPurpose(SecurityEncryptedData privatePurpose) {

    this.encryptedPurpose = privatePurpose;
    return this;
  }

  @Override
  public List<ThankTokenReferenceType> getReferenceList() {

    if (this.referenceList == null) {
      this.referenceList = new ArrayList<>();
    }
    return this.referenceList;
  }

  @Override
  public ThankTransactionBean setReference(ThankTokenReferenceType reference) {

    if (reference == null) {
      this.referenceList = null;
    } else {
      if ((this.referenceList == null) || !this.referenceList.isEmpty()) {
        this.referenceList = new ArrayList<>();
      }
      this.referenceList.add(reference);
    }
    return this;
  }

  /**
   * @param referenceList the new {@link #getReferenceList() reference list}.
   * @return this.
   */
  public ThankTransactionBean setReferenceList(List<ThankTokenReferenceType> referenceList) {

    this.referenceList = referenceList;
    return this;
  }

  @Override
  public SecuritySignature getSignature() {

    return this.signature;
  }

  @Override
  public ThankTransactionBean setSignature(SecuritySignature signature) {

    this.signature = signature;
    return this;
  }

}
