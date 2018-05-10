package io.github.thanktoken.core.api.transaction;

import java.time.Instant;
import java.util.List;

import io.github.thanktoken.core.api.ThankDataObject;
import io.github.thanktoken.core.api.ThankSignedObject;
import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;
import net.sf.mmm.security.api.crypt.SecurityEncryptedData;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;
import net.sf.mmm.security.api.sign.SecuritySignature;

/**
 * A {@link ThankTransaction} reflects a transaction on a {@link ThankToken} when it is transferred from one owner to
 * the next. The {@link ThankToken#getTransactions() transactions} of a {@link ThankToken} form a secure cryptographic
 * chain. The {@link SecurityPublicKey public key} of the new owner is added as {@link #getRecipient() recipient} while
 * the {@link #getSignature() signature} is signed with the
 * {@link net.sf.mmm.security.api.key.asymmetric.SecurityPrivateKey private key} of the previous {@link #getRecipient()
 * owner}. Therefore each {@link #getSignature() signature} can be validated with the {@link #getRecipient() recipient
 * public key} from the previous {@link ThankTransaction}.<br>
 * This ensures that only the current owner who has the correct
 * {@link net.sf.mmm.security.api.key.asymmetric.SecurityPrivateKey private key} is able to add a valid transaction in
 * order to transfer the {@link ThankToken} to a new owner. To ensure privacy you can use different key pairs (in the
 * most extreme case a new pair per transaction and even in combination with each transferred {@link ThankToken}).
 *
 * @see ThankToken
 * @see ThankToken#getTransactions()
 */
public interface ThankTransaction extends ThankSignedObject, ThankDataObject {

  /**
   * @return the exact {@link Instant} when this {@link ThankTransaction} was created and the according transaction was
   *         prepared.
   */
  Instant getTimestamp();

  /**
   * @return the {@link SecurityPublicKey} of the next owner the {@link ThankToken} is transferred to. May be
   *         {@code null} if the {@link ThankToken} is destroyed within a fork or merge process.
   */
  @Override
  SecurityPublicKey getRecipient();

  /**
   * @return the public purpose of this transaction or {@code null} if not available.
   */
  String getPublicPurpose();

  /**
   * @return the private purpose of this transaction or {@code null} if not available.
   */
  SecurityEncryptedData getEncryptedPurpose();

  /**
   * @return the {@link ThankTokenReferenceType} from {@link #getReferenceList() reference list} if it contains exactly
   *         one element, {@code null} otherwise.
   */
  default ThankTokenReferenceType getReference() {

    List<ThankTokenReferenceType> referenceList = getReferenceList();
    if (referenceList.size() == 1) {
      return referenceList.get(0);
    }
    return null;
  }

  /**
   * @return the {@link #getReferenceList() reference list} if it contains more than one
   *         {@link ThankTokenReferenceType}s, {@code null} otherwise.
   */
  default List<ThankTokenReferenceType> getReferences() {

    List<ThankTokenReferenceType> referenceList = getReferenceList();
    if (referenceList.size() > 1) {
      return referenceList;
    }
    return null;
  }

  /**
   * @return the {@link List} of {@link ThankTokenReferenceType}s.
   */
  List<ThankTokenReferenceType> getReferenceList();

  @Override
  SecuritySignature getSignature();

}
