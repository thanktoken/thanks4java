package io.github.thanktoken.core.api.repository;

import io.github.thanktoken.core.api.id.ThankTokenIdType;
import io.github.thanktoken.core.api.io.ThankNetworkException;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.validate.ThankValidationException;
import io.github.thanktoken.core.api.validate.ThankValidationFailureMode;
import io.github.thanktoken.core.api.validate.ThankValidationResult;
import io.github.thanktoken.core.api.validate.ThankValidationResultImpl;
import io.github.thanktoken.core.api.validate.ThankValidator;
import io.github.thanktoken.core.api.validate.failure.ThankValidationFailureException;

/**
 * TODO hohwille This type ...
 *
 * @since 1.0.0
 */
public class ThankTokenRepositoryImpl extends AbstractThankTokenRepository
    implements ThankTokenRepositoryWithValidation {

  private final ThankTokenRepositoryRemoteService remoteService;

  private final ThankTokenRepositoryLocalCacheImpl cache;

  private final ThankValidator validator;

  /**
   * The constructor.
   *
   * @param remoteService
   * @param cache
   * @param validator
   */
  public ThankTokenRepositoryImpl(ThankTokenRepositoryRemoteService remoteService,
      ThankTokenRepositoryLocalCacheImpl cache, ThankValidator validator) {

    super();
    this.remoteService = remoteService;
    this.cache = cache;
    this.validator = validator;
  }

  @Override
  public ThankToken find(ThankTokenIdType id) throws ThankNetworkException {

    ThankToken token = this.cache.find(id);
    if (token == null) {
      token = this.remoteService.find(id);
      if (token != null) {
        this.cache.add(token);
      }
    }
    return token;
  }

  @Override
  public ThankToken findValid(ThankTokenIdType id) throws ThankNetworkException {

    return findValid(id, false);
  }

  @Override
  public ThankToken findAndValidate(ThankTokenIdType id) throws ThankNetworkException {

    return findValid(id, true);
  }

  private ThankToken findValid(ThankTokenIdType id, boolean throwValidationException) throws ThankNetworkException {

    ThankToken token = find(id);
    if (token != null) {
      Boolean validity = this.cache.getValidity(id);
      if (validity == Boolean.TRUE) {
        return token;
      } else if (validity == null) {
        ThankValidationResult validationResult = null;
        try {
          validationResult = this.validator.validate(token);
        } catch (ThankValidationException e) {
          ThankValidationResultImpl resultImpl = new ThankValidationResultImpl(
              ThankValidationFailureMode.RETURN_RESULT);
          resultImpl.add(new ThankValidationFailureException(token, e));
          validationResult = resultImpl;
        }
        if (!validationResult.isValid()) {
          // LOG.warn()
        }
        this.cache.setValidity(token, validationResult.isValid());
        return token;
      }
    }
    return null;
  }

  @Override
  public Boolean getValidity(ThankTokenIdType id) {

    ThankToken token = find(id);
    if (token != null) {
      return this.cache.getValidity(id);
    }
    return null;
  }

}
