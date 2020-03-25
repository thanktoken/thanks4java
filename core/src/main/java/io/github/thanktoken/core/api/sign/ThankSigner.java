package io.github.thanktoken.core.api.sign;

import java.security.PrivateKey;

import io.github.mmm.crypto.asymmetric.sign.SignatureBinary;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * Interface for creating a {@link SignatureBinary} for a {@link ThankToken}.
 */
public interface ThankSigner {

  /**
   * Creates the {@link SignatureBinary} for the given {@link ThankToken} and the given new but yet unsigned
   * {@link ThankTransaction} using the given {@link PrivateKey}.
   *
   * @param header is the {@link ThankTokenHeader} to sign.
   * @param privateKey is the {@link PrivateKey} used for creating the signature. It has to correspond to the
   *        {@link ThankTokenHeader#getRecipient() recipient} of the given {@link ThankTokenHeader}.
   * @return the {@link SignatureBinary} that is to be used for {@link ThankTokenHeader#getSignature()} of the
   *         {@link ThankTokenHeader}.
   */
  SignatureBinary signHeader(ThankTokenHeader header, PrivateKey privateKey);

  /**
   * Creates the {@link SignatureBinary} for the given {@link ThankToken} and the given new but yet unsigned
   * {@link ThankTransaction} using the given {@link PrivateKey}.
   *
   * @param token is the {@link ThankToken} to sign.
   * @param tx is the new {@link ThankTransaction} that is not yet signed. It is to be appended to the
   *        {@link ThankToken#getTransactions() validity lines} of the {@link ThankToken} (after this method call).
   * @param privateKey is the private key used for creating the signature. It has to correspond to the
   *        {@link ThankTransaction#getRecipient() recipient} of the {@link ThankToken#getTransactions() last}
   *        {@link ThankTransaction} of the given {@link ThankToken}.
   * @return the {@link SignatureBinary} that is to be used for {@link ThankTransaction#getSignature()} of the new
   *         {@link ThankTransaction}.
   */
  SignatureBinary signTransaction(ThankToken token, ThankTransaction tx, PrivateKey privateKey);

}
