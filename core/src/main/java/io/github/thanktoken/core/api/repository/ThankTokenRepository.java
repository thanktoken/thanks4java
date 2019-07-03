package io.github.thanktoken.core.api.repository;

import io.github.thanktoken.core.api.id.ThankTokenIdType;
import io.github.thanktoken.core.api.io.ThankNetworkException;
import io.github.thanktoken.core.api.token.ThankToken;

/**
 * Repository to {@link #find(ThankTokenIdType) retrieve} {@link ThankToken}s via {@link ThankTokenIdType}.
 *
 * @since 1.0.0
 */
public interface ThankTokenRepository {

  /**
   * @param id the {@link ThankTokenIdType} (see
   *        {@link io.github.thanktoken.core.api.reference.ThankTokenReference#resolve(io.github.thanktoken.core.api.token.header.ThankTokenHeader)}).
   * @return the {@link ThankToken} for the given {@link ThankTokenIdType} or {@code null} if not found.
   * @throws ThankNetworkException in case of a (temporary) network error.
   */
  ThankToken find(ThankTokenIdType id) throws ThankNetworkException;

}
