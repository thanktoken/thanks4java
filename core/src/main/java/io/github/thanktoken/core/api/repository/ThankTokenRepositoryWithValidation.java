package io.github.thanktoken.core.api.repository;

import io.github.thanktoken.core.api.id.ThankTokenIdType;
import io.github.thanktoken.core.api.io.ThankNetworkException;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.validate.ThankValidationException;

/**
 * {@link ThankTokenRepository} acting as local storage or cache of already validated {@link ThankToken}s.
 *
 * @since 1.0.0
 */
public interface ThankTokenRepositoryWithValidation extends ThankTokenRepository {

  /**
   * @param id the {@link ThankTokenIdType}.
   * @return {@link Boolean#TRUE} if the {@link ThankToken} specified by the given {@code id} is valid,
   *         {@link Boolean#FALSE} if that {@link ThankToken} is invalid, and {@code null} otherwise (token not
   *         available or not yet validated).
   */
  Boolean getValidity(ThankTokenIdType id);

  /**
   * @param id the {@link ThankTokenIdType} (see
   *        {@link io.github.thanktoken.core.api.reference.ThankTokenReference#resolve(io.github.thanktoken.core.api.token.header.ThankTokenHeader)}).
   * @return the {@link ThankToken} for the given {@link ThankTokenIdType} or {@code null} if not found or invalid.
   * @throws ThankNetworkException in case of a (temporary) network error.
   */
  ThankToken findValid(ThankTokenIdType id) throws ThankNetworkException;

  /**
   * @param id the {@link ThankTokenIdType} (see
   *        {@link io.github.thanktoken.core.api.reference.ThankTokenReference#resolve(io.github.thanktoken.core.api.token.header.ThankTokenHeader)}).
   * @return the {@link ThankToken} for the given {@link ThankTokenIdType} or {@code null} if not found.
   * @throws ThankValidationException if the
   *         {@link io.github.thanktoken.core.api.validate.ThankValidator#validate(ThankToken) validation} of the
   *         requested {@link ThankToken} failed.
   * @throws ThankNetworkException in case of a (temporary) network error.
   */
  ThankToken findAndValidate(ThankTokenIdType id) throws ThankNetworkException;

}
