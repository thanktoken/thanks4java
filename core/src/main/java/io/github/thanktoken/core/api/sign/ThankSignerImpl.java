package io.github.thanktoken.core.api.sign;

import java.security.PrivateKey;
import java.util.List;
import java.util.Objects;

import io.github.mmm.crypto.asymmetric.sign.SignatureBinary;
import io.github.mmm.crypto.asymmetric.sign.SignatureSigner;
import io.github.mmm.crypto.hash.Hash;
import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.context.ThankTokenContextHelper;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.token.header.AbstractThankTokenHeader;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.transaction.AbstractThankTransaction;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * Implementation of {@link ThankSigner}.
 *
 * @since 1.0.0
 */
public class ThankSignerImpl implements ThankSigner {

  @Override
  public SignatureBinary signHeader(ThankTokenHeader header, PrivateKey privateKey) {

    Objects.requireNonNull(header, "header");
    Objects.requireNonNull(privateKey, "privateKey");
    AbstractThankTokenHeader thankHeader = (AbstractThankTokenHeader) header;
    thankHeader.createHashes();
    Hash hash2Sign = thankHeader.getHash2Sign();
    ThankAlgorithm algorithm = header.requireAlgorithm();
    SignatureSigner<?> signer = algorithm.getSignatureFactory().newSignerUnsafe(privateKey);
    return signer.sign(hash2Sign, true);
  }

  @Override
  public SignatureBinary signTransaction(ThankToken token, ThankTransaction tx, PrivateKey privateKey) {

    Objects.requireNonNull(token, "token");
    Objects.requireNonNull(tx, "transaction");
    Objects.requireNonNull(privateKey, "privateKey");
    List<? extends ThankTransaction> transactions = token.getTransactions();
    int txIndex = transactions.size() - 1;
    if (txIndex >= 0) {
      ThankTransaction lastTx = transactions.get(txIndex);
      if (lastTx == tx) {
        txIndex--;
      }
    }
    Hash predecessorHash = createHash(token, txIndex);
    assert (tx.getHash2Sign() == null);
    ((AbstractThankTransaction) tx).createHashes(token, predecessorHash);
    Hash hash = tx.getHash2Sign();
    ThankAlgorithm algorithm = ThankTokenContextHelper.requireAlgorithm(token.getHeader());
    SignatureSigner<?> signer = algorithm.getSignatureFactory().newSignerUnsafe(privateKey);
    return signer.sign(hash, true);
  }

  private Hash createHash(ThankToken token, int txIndex) {

    Hash hash;
    if (txIndex < 0) {
      AbstractThankTokenHeader header = (AbstractThankTokenHeader) token.requireHeader();
      header.createHashes();
      hash = header.getHash2Chain();
    } else {
      ThankTransaction tx = token.getTransactions().get(txIndex);
      hash = tx.getHash2Chain();
      if (hash == null) {
        Hash previousHash = createHash(token, txIndex - 1);
        ((AbstractThankTransaction) tx).createHashes(token, previousHash);
        hash = tx.getHash2Chain();
      }
    }
    if (hash == null) {
      throw new IllegalArgumentException("Token " + token + " is not signed at #tx=" + txIndex);
    }
    return hash;
  }

}
