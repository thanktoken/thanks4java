package io.github.thanktoken.core.impl.sign;

import java.util.List;
import java.util.Objects;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.header.ThankHeaderField;
import io.github.thanktoken.core.api.sign.ThankTokenSigner;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.transaction.ThankTransactionField;
import io.github.thanktoken.core.impl.strategy.AbstractThankVersionStrategy;
import io.github.thanktoken.core.impl.strategy.AbstractThankVersionStrategyContainer;
import net.sf.mmm.security.api.hash.SecurityHashCreator;
import net.sf.mmm.security.api.key.asymmetric.SecurityPrivateKey;
import net.sf.mmm.security.api.sign.SecuritySignature;
import net.sf.mmm.security.api.sign.SecuritySignatureSigner;

/**
 * Implementation of {@link ThankTokenSigner}.
 */
public class ThankTokenSignerImpl extends AbstractThankVersionStrategyContainer implements ThankTokenSigner {

  /**
   * The constructor.
   *
   * @param strategy the {@link AbstractThankVersionStrategy}.
   */
  public ThankTokenSignerImpl(AbstractThankVersionStrategy strategy) {

    super(strategy);
  }

  @Override
  public SecuritySignature signHeader(ThankHeader header, SecurityPrivateKey privateKey) {

    return signHeader(header, privateKey, getStrategy().getConfiguration().getHashFactory().newHashCreator());
  }

  private SecuritySignature signHeader(ThankHeader header, SecurityPrivateKey privateKey, SecurityHashCreator hashCreator) {

    Objects.requireNonNull(header, "Header");
    Objects.requireNonNull(privateKey, "PrivateKey");
    byte[] hash = ThankHeaderField.getFields().hash(header, hashCreator);
    SecuritySignatureSigner signer = getStrategy().getConfiguration().getSignatureFactory().newSigner(privateKey);
    byte[] signature = signer.sign(hash, false);
    return new SecuritySignature(signature);
  }

  @Override
  public SecuritySignature signTransaction(ThankToken token, ThankTransaction newTx, SecurityPrivateKey privateKey) {

    return signTransaction(token, newTx, privateKey, getStrategy().getConfiguration().getHashFactory().newHashCreator());
  }

  private SecuritySignature signTransaction(ThankToken token, ThankTransaction newTx, SecurityPrivateKey privateKey, SecurityHashCreator hashCreator) {

    Objects.requireNonNull(token, "Token");
    Objects.requireNonNull(newTx, "Transaction");
    Objects.requireNonNull(privateKey, "PrivateKey");
    try {
      List<? extends ThankTransaction> transactions = token.getTransactions();
      byte[] hash = hash(token, hashCreator, transactions.size());
      hashCreator.update(hash);
      hash = ThankTransactionField.getFields().hash(newTx, hashCreator);
      SecuritySignatureSigner signer = getStrategy().getConfiguration().getSignatureFactory().newSigner(privateKey);
      byte[] signature = signer.sign(hash, false);
      return new SecuritySignature(signature);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  private byte[] hash(ThankToken token, SecurityHashCreator hashCreator, int txCount) {

    byte[] hash = ThankHeaderField.getFields().hash(token.getHeader(), hashCreator);
    List<? extends ThankTransaction> transactions = token.getTransactions();
    for (int i = 0; i < txCount; i++) {
      ThankTransaction tx = transactions.get(i);
      hashCreator.update(hash);
      hash = ThankTransactionField.getFields().hash(tx, hashCreator);
    }
    return hash;
  }

}
