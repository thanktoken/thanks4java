package io.github.thanktoken.core.api.repository;

import io.github.thanktoken.core.api.id.ThankTokenIdType;
import io.github.thanktoken.core.api.io.ThankNetworkException;
import io.github.thanktoken.core.api.token.ThankToken;

/**
 * Implementation of {@link ThankTokenRepository} that will only {@link #find(ThankTokenIdType) find} valid tokens.
 *
 * @since 1.0.0
 */
public class ThankTokenRepositoryFacade extends AbstractThankTokenRepository {

  private final ThankTokenRepositoryWithValidation repository;

  /**
   * The constructor.
   *
   * @param repository the {@link ThankTokenRepositoryImpl}.
   */
  public ThankTokenRepositoryFacade(ThankTokenRepositoryWithValidation repository) {

    super();
    this.repository = repository;
  }

  @Override
  public ThankToken find(ThankTokenIdType id) throws ThankNetworkException {

    return this.repository.findValid(id);
  }

}
