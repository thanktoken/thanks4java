package io.github.thanktoken.core.impl.sign;

import java.util.List;
import java.util.Objects;

import net.sf.mmm.crypto.asymmetric.key.SecurityPrivateKey;
import net.sf.mmm.crypto.asymmetric.sign.SignatureBinary;
import net.sf.mmm.crypto.asymmetric.sign.SignatureSigner;
import net.sf.mmm.crypto.hash.HashCreator;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.header.ThankHeaderField;
import io.github.thanktoken.core.api.sign.ThankSigner;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.transaction.ThankTransactionField;
import io.github.thanktoken.core.impl.strategy.AbstractThankVersionStrategy;
import io.github.thanktoken.core.impl.strategy.AbstractThankVersionStrategyContainer;

/**
 * Implementation of {@link ThankSigner}.
 */
public class ThankSignerImpl extends AbstractThankVersionStrategyContainer implements ThankSigner {

  /**
   * The constructor.
   *
   * @param strategy the {@link AbstractThankVersionStrategy}.
   */
  public ThankSignerImpl(AbstractThankVersionStrategy strategy) {

    super(strategy);
  }

  @Override
  public SignatureBinary signHeader(ThankHeader header, SecurityPrivateKey privateKey) {

    return signHeader(header, privateKey, header.getAlgorithm().getHashFactory().newHashCreator());
  }

  private SignatureBinary signHeader(ThankHeader header, SecurityPrivateKey privateKey,
      HashCreator hashCreator) {

    Objects.requireNonNull(header, "Header");
    Objects.requireNonNull(privateKey, "PrivateKey");
    byte[] hash = ThankHeaderField.getFields().hash(header, hashCreator);
    SignatureSigner signer = getStrategy().getConfiguration().getSignatureFactory().newSigner(privateKey);
    byte[] signature = signer.sign(hash, false);
    return new SignatureBinary(signature);
  }

  @Override
  public SignatureBinary signTransaction(ThankToken token, ThankTransaction newTx, SecurityPrivateKey privateKey) {

    return signTransaction(token, newTx, privateKey,
        token.getHeader().getAlgorithm().getHashFactory().newHashCreator());
  }

  private SignatureBinary signTransaction(ThankToken token, ThankTransaction newTx, SecurityPrivateKey privateKey,
      HashCreator hashCreator) {

    Objects.requireNonNull(token, "Token");
    Objects.requireNonNull(newTx, "Transaction");
    Objects.requireNonNull(privateKey, "PrivateKey");
    try {
      List<? extends ThankTransaction> transactions = token.getTransactions();
      byte[] hash = hash(token, transactions.size() - 1, hashCreator);
      hashCreator.update(hash);
      hash = ThankTransactionField.getFields().hash(newTx, hashCreator);
      SignatureSigner signer = getStrategy().getConfiguration().getSignatureFactory().newSigner(privateKey);
      byte[] signature = signer.sign(hash, false);
      return new SignatureBinary(signature);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

}
