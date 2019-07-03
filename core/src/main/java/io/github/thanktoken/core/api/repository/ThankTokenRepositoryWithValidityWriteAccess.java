package io.github.thanktoken.core.api.repository;

import io.github.thanktoken.core.api.token.ThankToken;

/**
 * {@link ThankTokenRepository} acting as local storage or cache of already validated {@link ThankToken}s.
 *
 * @since 1.0.0
 */
public interface ThankTokenRepositoryWithValidityWriteAccess extends ThankTokenRepositoryWithValidation {

  /**
   * @param token the validated {@link ThankToken} to add to this local cache.
   * @param valid - {@code true} if the given {@link ThankToken} has been successfully validated, {@code false}
   *        otherwise.
   */
  void setValidity(ThankToken token, boolean valid);

}
