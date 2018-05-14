package io.github.thanktoken.core.impl.validate;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.config.ThankConfiguration;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.datatype.ThankValue;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.header.ThankLocation;
import io.github.thanktoken.core.api.header.ThankTarget;
import io.github.thanktoken.core.api.header.ThankVersion;
import io.github.thanktoken.core.api.identity.ThankIdentity;
import io.github.thanktoken.core.api.identity.ThankIdentityType;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.validate.ThankTokenValidationException;
import io.github.thanktoken.core.api.validate.ThankTokenValidator;
import io.github.thanktoken.core.api.validate.ThankValidationMode;
import io.github.thanktoken.core.impl.strategy.AbstractThankVersionStrategy;
import io.github.thanktoken.core.impl.strategy.AbstractThankVersionStrategyContainer;
import net.sf.mmm.security.api.hash.SecurityHashCreator;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;
import net.sf.mmm.security.api.sign.SecuritySignature;
import net.sf.mmm.security.api.sign.SecuritySignatureFactory;
import net.sf.mmm.security.api.sign.SecuritySignatureVerifier;

/**
 * Implementation of {@link ThankTokenValidator}.
 */
public class ThankTokenValidatorImpl extends AbstractThankVersionStrategyContainer implements ThankTokenValidator {

  /**
   * The constructor.
   *
   * @param strategy the {@link AbstractThankVersionStrategy}.
   */
  public ThankTokenValidatorImpl(AbstractThankVersionStrategy strategy) {

    super(strategy);
  }

  @Override
  public void validate(ThankToken token, ThankValidationMode mode) {

    try {
      Objects.requireNonNull(token, "token");
      ThankConfiguration configuration = getStrategy().getConfiguration();
      SecurityHashCreator hashCreator = configuration.getHashFactory().newHashCreator();
      byte[] hash = validateHeader(token.getHeader(), mode, hashCreator);
      validateTransactions(token, mode, hash, hashCreator);
    } catch (Exception e) {
      throw new ThankTokenValidationException(e, token);
    }
  }

  @Override
  public void validateHeader(ThankToken token, ThankValidationMode mode) throws ThankTokenValidationException {

    try {
      Objects.requireNonNull(token, "token");
      ThankConfiguration configuration = getStrategy().getConfiguration();
      SecurityHashCreator hashCreator = configuration.getHashFactory().newHashCreator();
      validateHeader(token.getHeader(), mode, hashCreator);
    } catch (Exception e) {
      throw new ThankTokenValidationException(e, token);
    }
  }

  @Override
  public void validateTransaction(ThankToken token, ThankTransaction tx, ThankValidationMode mode) throws ThankTokenValidationException {

    try {
      Objects.requireNonNull(token, "token");
      List<? extends ThankTransaction> transactions = token.getTransactions();
      Objects.requireNonNull(transactions, "token.transactions");
      int index = transactions.indexOf(tx);
      if (index < 0) {
        throw new IllegalArgumentException("Transaction does not belong to token " + token);
      }
      ThankConfiguration configuration = getStrategy().getConfiguration();
      SecurityHashCreator hashCreator = configuration.getHashFactory().newHashCreator();
      Instant timestamp;
      SecurityPublicKey previousOwner;
      if (index == 0) {
        ThankHeader header = token.getHeader();
        Objects.requireNonNull(transactions, "token.header");
        timestamp = header.getTimestamp();
        previousOwner = header.getRecipient();
      } else {
        ThankTransaction previousTx = transactions.get(index - 1);
        if (previousTx == null) {
          Objects.requireNonNull(previousTx, "token.transactions[" + (index - 1) + "]");
        }
        timestamp = previousTx.getTimestamp();
        previousOwner = previousTx.getRecipient();
      }
      byte[] hash = hash(token, index - 1, hashCreator);
      validateTransaction(tx, index, previousOwner, mode, token, timestamp, hash, hashCreator);

    } catch (Exception e) {
      throw new ThankTokenValidationException(e, token);
    }
  }

  private byte[] validateHeader(ThankHeader header, ThankValidationMode mode, SecurityHashCreator hashCreator) {

    ThankVersion version = header.getVersion();
    Objects.requireNonNull(version, "header.version");
    verifyVersion(version);
    ThankValue amount = header.getAmount();
    Objects.requireNonNull(amount, "header.amount");
    // if (amount.getUnscaledValue() > MAX_AMOUNT) {
    // throw new IllegalArgumentException("Amount has to be less than maximum: " + amount);
    // }
    ThankCurrency currency = header.getCurrency();
    Objects.requireNonNull(currency, "header.currency");
    currency.validate(header);
    Instant timestamp = header.getTimestamp();
    Objects.requireNonNull(timestamp, "header.timestamp");
    if (timestamp.isAfter(Instant.now())) {
      throw new IllegalArgumentException("Token timestamp is in the future: " + timestamp);
    }
    ThankTarget target = header.getTarget();
    Objects.requireNonNull(target, "header.target");
    ThankLocation location = header.getLocation();
    Objects.requireNonNull(location, "header.location");

    SecurityPublicKey recipient = header.getRecipient();
    Objects.requireNonNull(recipient, "header.recipient");

    SecuritySignature signature = header.getSignature();
    Objects.requireNonNull(signature, "header.signature");
    SecuritySignatureFactory signatureFactory = getStrategy().getConfiguration().getSignatureFactory();
    SecuritySignatureVerifier verifier = signatureFactory.newVerifier(recipient);
    byte[] hash = hash(header, hashCreator);
    boolean valid = verifier.verify(hash, signature);
    if (!valid) {
      throw new IllegalArgumentException("Signature of token header (" + header + ") is not valid!");
    }
    if (mode.isValidateCreation()) {
      if (header.getReference() == null) {
        ThankIdentity identity = getStrategy().getIdentityProvider().findIdentity(recipient);
        if ((identity == null) || (!ThankIdentityType.NATURAL_PERSON.equals(identity.getType()))) {
          throw new IllegalArgumentException("Token was not created by a certified natural person.");
        }
      }
    }
    return hash;
  }

  private void validateTransactions(ThankToken token, ThankValidationMode mode, byte[] hash, SecurityHashCreator hashCreator) throws Exception {

    ThankHeader header = token.getHeader();
    List<? extends ThankTransaction> transactions = token.getTransactions();
    int size = transactions.size();
    ThankTarget target = header.getTarget();
    if (mode.isPublicValue()) {
      String type = target.getType();
      if (!ThankTarget.TYPE_PERSON.equals(type) && (size < 2)) {
        throw new IllegalArgumentException("Target (" + target + ") not reached.");
      }
    }
    Instant timestamp = header.getTimestamp();
    int index = 1;
    byte[] currentHash = hash;
    SecurityPublicKey previousOwner = header.getRecipient();
    for (ThankTransaction tx : transactions) {
      try {
        currentHash = validateTransaction(tx, index, previousOwner, mode, token, timestamp, currentHash, hashCreator);
        timestamp = tx.getTimestamp();
        previousOwner = tx.getRecipient();
      } catch (Exception e) {
        throw new IllegalArgumentException("Invalid transaction #" + index + " (" + tx + "): " + e.getMessage(), e);
      }
      index++;
    }
  }

  private byte[] validateTransaction(ThankTransaction tx, int index, SecurityPublicKey previousOwner, ThankValidationMode mode, ThankToken token,
      Instant timestamp, byte[] hash, SecurityHashCreator hashCreator) {

    if (tx.getTimestamp().isBefore(timestamp)) {
      throw new IllegalArgumentException("before previous timestamp (" + timestamp + ").");
    }
    hashCreator.update(hash);
    byte[] newHash = hash(tx, hashCreator);
    ThankConfiguration configuration = getStrategy().getConfiguration();
    SecuritySignatureFactory signatureFactory = configuration.getSignatureFactory();
    boolean valid = signatureFactory.newVerifier(previousOwner).verify(newHash, tx.getSignature());
    if (!valid) {
      throw new IllegalArgumentException("invalid signature.");
    }
    if ((index == 1) && mode.isValidatePurpose()) {
      ThankTarget target = token.getHeader().getTarget();
      if (!target.isTypePerson()) {
        ThankIdentity identity = getStrategy().getIdentityProvider().findIdentity(previousOwner);
        if ((identity == null) || (!ThankIdentityType.NATURAL_PERSON.equals(identity.getType()))) {
          throw new IllegalArgumentException("not created by a certified natural person.");
        }

      }
    }
    return newHash;
  }
}
