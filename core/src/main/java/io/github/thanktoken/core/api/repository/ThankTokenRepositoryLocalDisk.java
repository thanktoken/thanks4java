package io.github.thanktoken.core.api.repository;

import io.github.thanktoken.core.api.token.ThankToken;

/**
 * The repository to {@link #find(io.github.thanktoken.core.api.id.ThankTokenIdType) load} and {@link #save(ThankToken)
 * save} {@link ThankToken}s on the local disc.
 *
 * @since 1.0.0
 */
public interface ThankTokenRepositoryLocalDisk extends ThankTokenRepository {

  /**
   * @param token the {@link ThankToken} to save on the local disc.
   */
  void save(ThankToken token);

}
