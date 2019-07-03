package io.github.thanktoken.core.api.transaction;

import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.attribute.AttributeReadEncryptedPurpose;
import io.github.thanktoken.core.api.attribute.AttributeReadHash;
import io.github.thanktoken.core.api.attribute.AttributeReadPublicPurpose;
import io.github.thanktoken.core.api.attribute.AttributeReadReference;
import io.github.thanktoken.core.api.attribute.AttributeReadTimestamp;
import io.github.thanktoken.core.api.data.ThankSignedObject;
import io.github.thanktoken.core.api.token.ThankToken;

/**
 * A {@link ThankTransaction} reflects a transaction on a {@link ThankToken} when it is transferred from one owner to
 * the next. The {@link ThankToken#getTransactions() transactions} of a {@link ThankToken} form a secure cryptographic
 * chain. The {@link java.security.PublicKey public key} of the new owner is added as {@link #getRecipient() recipient}
 * while the {@link #getSignature() signature} is signed with the {@link java.security.PrivateKey private key} of the
 * previous {@link #getRecipient() owner}. Therefore each {@link #getSignature() signature} can be validated with the
 * {@link #getRecipient() recipient public key} from the previous {@link ThankTransaction}.<br>
 * This ensures that only the current owner who has the correct {@link java.security.PrivateKey private key} is able to
 * add a valid transaction in order to transfer the {@link ThankToken} to a new owner. To ensure privacy you can use
 * different key pairs (in the most extreme case a new pair per transaction and even in combination with each
 * transferred {@link ThankToken}).
 *
 * @see ThankToken
 * @see ThankToken#getTransactions()
 */
public interface ThankTransaction extends ThankSignedObject, AttributeReadTimestamp, AttributeReadReference, AttributeReadPublicPurpose,
    AttributeReadEncryptedPurpose, AttributeReadHash {

  /**
   * @return the {@link ThankAddress} of the next owner the {@link ThankToken} is transferred to. May be {@code null} if
   *         the {@link ThankToken} is destroyed within a fork or merge process.
   */
  @Override
  ThankAddress getRecipient();

}
