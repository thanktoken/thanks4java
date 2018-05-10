package io.github.thanktoken.core.api.sign;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import net.sf.mmm.security.api.key.asymmetric.SecurityPrivateKey;
import net.sf.mmm.security.api.sign.SecuritySignature;

/**
 * Interface for creating a {@link SecuritySignature} for a {@link ThankToken}.
 */
public interface ThankTokenSigner {

  /**
   * Creates the {@link SecuritySignature} for the given {@link ThankToken} and the given new but yet unsigned
   * {@link ThankTransaction} using the given {@link SecurityPrivateKey}.
   *
   * @param header is the {@link ThankHeader} to sign.
   * @param privateKey is the {@link SecurityPrivateKey} used for creating the signature. It has to correspond to the
   *        {@link ThankHeader#getRecipient() recipient} of the given {@link ThankHeader}.
   * @return the {@link SecuritySignature} that is to be used for {@link ThankHeader#getSignature()} of the
   *         {@link ThankHeader}.
   */
  SecuritySignature signHeader(ThankHeader header, SecurityPrivateKey privateKey);

  /**
   * Creates the {@link SecuritySignature} for the given {@link ThankToken} and the given new but yet unsigned
   * {@link ThankTransaction} using the given {@link SecurityPrivateKey}.
   *
   * @param token is the {@link ThankToken} to sign.
   * @param tx is the new {@link ThankTransaction} that is not yet signed. It is to be appended to the
   *        {@link ThankToken#getTransactions() validity lines} of the {@link ThankToken} (after this method call).
   * @param privateKey is the private key used for creating the signature. It has to correspond to the
   *        {@link ThankTransaction#getRecipient() recipient} of the {@link ThankToken#getTransactions() last}
   *        {@link ThankTransaction} of the given {@link ThankToken}.
   * @return the {@link SecuritySignature} that is to be used for {@link ThankTransaction#getSignature()} of the new
   *         {@link ThankTransaction}.
   */
  SecuritySignature signTransaction(ThankToken token, ThankTransaction tx, SecurityPrivateKey privateKey);

}
