package io.github.thanktoken.core.api.validate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.mmm.crypto.asymmetric.sign.SignatureBinary;
import io.github.mmm.crypto.hash.Hash;
import io.github.thanktoken.core.api.address.ThankAddress;
import io.github.thanktoken.core.api.address.ThankAddressHeader;
import io.github.thanktoken.core.api.address.ThankAddressType;
import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.attribute.AttributeReadReferenceType;
import io.github.thanktoken.core.api.attribute.ThankAttributeField;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.id.ThankTokenIdType;
import io.github.thanktoken.core.api.identity.ThankIdentity;
import io.github.thanktoken.core.api.identity.ThankIdentityProvider;
import io.github.thanktoken.core.api.io.ThankNetworkException;
import io.github.thanktoken.core.api.message.ThankMessage;
import io.github.thanktoken.core.api.message.ThankMessageType;
import io.github.thanktoken.core.api.reference.ThankTokenReference;
import io.github.thanktoken.core.api.reference.ThankTokenReferenceType;
import io.github.thanktoken.core.api.repository.ThankTokenRepository;
import io.github.thanktoken.core.api.target.ThankTarget;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.token.header.AbstractThankTokenHeader;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.token.header.ThankTokenHeaderField;
import io.github.thanktoken.core.api.transaction.AbstractThankTransaction;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.validate.failure.ThankValidationFailureCurrencyMixed;
import io.github.thanktoken.core.api.validate.failure.ThankValidationFailureException;
import io.github.thanktoken.core.api.validate.failure.ThankValidationFailureInFuture;
import io.github.thanktoken.core.api.validate.failure.ThankValidationFailureMismatch;
import io.github.thanktoken.core.api.validate.failure.ThankValidationFailureReferenceNotClosed;
import io.github.thanktoken.core.api.validate.failure.ThankValidationFailureReferenceNotFound;
import io.github.thanktoken.core.api.validate.failure.ThankValidationFailureRequired;
import io.github.thanktoken.core.api.validate.failure.ThankValidationFailureSignature;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * Implementation of {@link ThankValidator}.
 */
public class ThankValidatorImpl implements ThankValidator {

  private final ThankIdentityProvider identityProvider;

  private final ThankTokenRepository repository;

  /**
   * The constructor.
   *
   * @param identityProvider the {@link ThankIdentityProvider} used to resolve identities of addresses.
   * @param repository the {@link ThankTokenRepository} used to resolve referenced tokens.
   */
  public ThankValidatorImpl(ThankIdentityProvider identityProvider, ThankTokenRepository repository) {

    super();
    this.identityProvider = identityProvider;
    this.repository = repository;
  }

  @Override
  public ThankValidationResult validate(ThankToken token, ThankValidationMode mode) {

    Objects.requireNonNull(mode, "mode");
    ThankValidationResultImpl result = new ThankValidationResultImpl(mode.getFailureMode());
    try {
      if (token == null) {
        result.add(new ThankValidationFailureRequired("token", null));
      } else {
        validateHeader(token.requireHeader(), mode, result);
        validateToken(token, mode, result);
        validateTransactions(token, mode, result);
      }
    } catch (ThankNetworkException e) {
      throw e;
    } catch (Exception e) {
      result.add(new ThankValidationFailureException(token, e));
    }
    result.complete();
    return result;
  }

  private void validateHeader(ThankTokenHeader header, ThankValidationMode mode,
      ThankValidationFailureReceiver receiver) {

    ThankValidationFailureRequired.validateAll(ThankTokenHeaderField.getFields(), header, receiver);
    ThankValidationFailureInFuture.validateTimestamp(ThankTokenHeaderField.TIMESTAMP, header, receiver);
    ThankCurrency currency = ThankValidationFailureRequired.validate(ThankTokenHeaderField.CURRENCY, header, receiver);
    if (currency != null) {
      currency.validate(header, receiver);
    }

    ThankAddress recipient = header.requireRecipient();
    SignatureBinary signature = header.requireSignature();

    ((AbstractThankTokenHeader) header).createHashes();
    ThankAlgorithm algorithm = header.requireAlgorithm();
    algorithm.verifySignature(signature, recipient, header.getHash2Sign());
  }

  private void validateToken(ThankToken token, ThankValidationMode mode, ThankValidationFailureReceiver receiver) {

    ThankTokenHeader header = token.requireHeader();
    ThankTokenReference reference = header.getReference();
    if (reference == null) {
      if (mode.isValidateCreation()) {
        ThankAddress recipient = header.requireRecipient();
        ThankAddressHeader recipientHeader = recipient.getHeader();
        ThankAddressType type = recipientHeader.getType();
        if (!type.isNaturalPerson()) {
          // create custom failure?
          receiver
              .add(new ThankValidationFailureMismatch("receipient.header.type", "" + type, "natural person", header));
        }
        ThankIdentity identity = this.identityProvider.findIdentity(recipient, header);
        if ((identity == null) || !identity.isValid(header.requireTimestamp().getInstant())) {
          // create custom failure?
          receiver.add(new ThankValidationFailureMismatch("receipient", "" + recipient, "certified identity", header));
        } else if (!header.getLocation().equals(identity.getLocation())) {
          // create custom failure?
          receiver.add(new ThankValidationFailureMismatch("receipient.location", "" + identity.getLocation(),
              "" + header.getLocation(), header));
        }
      }
    } else {
      String refType = reference.requireType();
      switch (refType) {
        case AttributeReadReferenceType.TYPE_FORK_WITH:
          validateFork(token, mode, false, receiver);
          break;
        case AttributeReadReferenceType.TYPE_FORK_FROM:
          validateFork(token, mode, false, receiver);
          break;
        case AttributeReadReferenceType.TYPE_MERGE_FROM:
          validateMerge(token, mode, false, receiver);
          break;
        default:
          throw new IllegalArgumentException(refType);
      }
    }
  }

  /**
   * @param token
   * @param mode
   * @param b
   */
  private void validateMerge(ThankToken token, ThankValidationMode mode, boolean source,
      ThankValidationFailureReceiver receiver) {

    // TODO Auto-generated method stub

  }

  protected void validateFork(ThankToken token, ThankValidationMode mode, boolean source,
      ThankValidationFailureReceiver receiver) {

    ThankToken sourceToken = null;
    List<ThankToken> targetTokens = new ArrayList<>();

    if (source) {
      sourceToken = token;
      ThankTransaction lastTx = token.getLastTransaction();
      Objects.requireNonNull(lastTx, "token.lastTransaction");

    }
    ThankToken currentToken = token;
    while (currentToken != null) {
      ThankTokenHeader currentHeader = currentToken.requireHeader();
      ThankTokenReferenceType currentReference = currentHeader.requireReference();
      ThankTokenIdType currentId = resolveReference(currentReference, currentHeader, receiver);
      String type = currentReference.requireType();
      ThankToken nextToken = null;
      if (AttributeReadReferenceType.TYPE_FORK_WITH.equals(type)) {
        ThankTimestamp refTimestamp = currentId.requireTimestamp();
        if (!refTimestamp.isNext(currentHeader.getTimestamp())) {
          throw new IllegalArgumentException("");
        }
        nextToken = this.repository.find(currentId);
        if (nextToken == null) {
          receiver.add(new ThankValidationFailureReferenceNotFound(currentReference, currentToken));
        }
      } else if (AttributeReadReferenceType.TYPE_FORK_FROM.equals(type)) {
        if (sourceToken == null) {
          nextToken = this.repository.find(currentId);
          sourceToken = nextToken;
        } else {
          if (!currentId.matches(sourceToken.requireHeader())) {
            receiver.add(new ThankValidationFailureReferenceNotClosed(currentReference, currentToken));
          }
        }
      } else {
        throw new IllegalArgumentException("Illegal reference type " + type + " in " + currentHeader);
      }
      currentToken = nextToken;
    }
  }

  private ThankTokenIdType resolveReference(ThankTokenReference reference, ThankTokenHeader header,
      ThankValidationFailureReceiver receiver) {

    ThankValidationFailureCurrencyMixed.validate(header.getCurrency(), reference.getCurrency(), header, receiver);
    return reference.resolve(header);
  }

  protected void validateFork(ThankToken sourceToken, List<ThankToken> targetTokens, ThankValidationMode mode) {

  }

  private void validateTransactions(ThankToken token, ThankValidationMode mode,
      ThankValidationFailureReceiver receiver) {

    ThankTokenHeader header = token.requireHeader();
    List<? extends ThankTransaction> transactions = token.getTransactions();
    int txSize = transactions.size();
    ThankTarget target = header.requireTarget();
    if (mode.isPublicValue()) {
      if (!target.isTypePerson() && (txSize < 2)) {
        throw new IllegalArgumentException("Target (" + target + ") not reached.");
      }
    }
    ThankAlgorithm algorithm = header.requireAlgorithm();
    int index = 0;
    ThankTimestamp previousTimestamp = header.getTimestamp();
    ThankAddress previousOwner = header.getRecipient();
    Hash previousHash = header.getHash2Chain();
    for (ThankTransaction tx : transactions) {
      try {
        validateTransaction(tx, mode, token, index, previousOwner, previousTimestamp, previousHash, algorithm,
            receiver);
        previousTimestamp = tx.getTimestamp();
        previousOwner = tx.getRecipient();
        previousHash = tx.getHash2Chain();
      } catch (Exception e) {
        IllegalArgumentException error = new IllegalArgumentException(
            "Invalid transaction #" + (index + 1) + " (" + tx + "): " + e.getMessage(), e);
        receiver.add(new ThankValidationFailureException(token, error));
        throw error;
      }
      index++;
    }
  }

  private void validateTransaction(ThankTransaction tx, ThankValidationMode mode, ThankToken token, int index,
      ThankAddress previousOwner, ThankTimestamp previousTimestamp, Hash previousHash, ThankAlgorithm algorithm,
      ThankValidationFailureReceiver receiver) {

    if (!tx.requireTimestamp().isAfter(previousTimestamp)) {
      throw new IllegalArgumentException("before previous timestamp (" + previousTimestamp + ").");
    }
    Hash hash2Sign = tx.getHash2Sign();
    if (hash2Sign == null) {
      ((AbstractThankTransaction) tx).createHashes(token, previousHash);
      hash2Sign = tx.getHash2Sign();
    }
    ThankValidationFailureSignature.validate(tx.requireSignature(), previousOwner, algorithm, tx, receiver);
    // algorithm.verifySignature(tx.requireSignature(), previousOwner, hash2Sign);
    if ((index == 0) && mode.isValidatePurpose()) {
      ThankTarget target = token.requireHeader().requireTarget();
      if (!target.isPersonIncome()) {
        ThankIdentity identity = this.identityProvider.findIdentity(previousOwner, token);
        if ((identity == null) || !previousOwner.getHeader().getType().isNaturalPerson()) {
          throw new IllegalArgumentException("not created by a certified natural person.");
        }

      }
    }
  }

  @Override
  public ThankValidationResult validateMessage(ThankMessage message, ThankValidationMode mode)
      throws ThankValidationException {

    Objects.requireNonNull(mode, "mode");
    ThankValidationResultImpl result = new ThankValidationResultImpl(mode.getFailureMode());
    try {
      if (message == null) {
        result.add(new ThankValidationFailureRequired("message", null));
      } else {
        ThankVersion version = ThankValidationFailureRequired.validate(ThankAttributeField.VERSION, message, result);
        ThankAddress recipient = ThankValidationFailureRequired.validate(ThankAttributeField.RECIPIENT, message,
            result);
        ThankMessageType<?> type = message.getType();
        Objects.requireNonNull(type, "message.type");
      }
    } catch (ThankNetworkException e) {
      throw e;
    } catch (Exception e) {
      result.add(new ThankValidationFailureException(message, e));
    }
    result.complete();
    return result;
  }
}
