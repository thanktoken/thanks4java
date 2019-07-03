package io.github.thanktoken.core.api.repository;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import io.github.thanktoken.core.api.id.ThankTokenIdType;
import io.github.thanktoken.core.api.io.ThankNetworkException;
import io.github.thanktoken.core.api.token.ThankToken;

/**
 * Implementation of {@link ThankTokenRepositoryWithValidityWriteAccess}.
 *
 * @since 1.0.0
 */
public class ThankTokenRepositoryLocalCacheImpl extends AbstractThankTokenRepository implements ThankTokenRepositoryWithValidityWriteAccess {

  // 1st level cache (Memory)
  private final Map<ThankTokenIdType, ThankTokenContainer> firstLevelCache;

  private final ThankTokenRepositoryLocalDisk secondLevelCache;

  /**
   * The constructor.
   *
   * @param secondLevelCache the {@link ThankTokenRepositoryLocalDisk} used as 2nd level cache and only for valid
   *        tokens.
   */
  public ThankTokenRepositoryLocalCacheImpl(ThankTokenRepositoryLocalDisk secondLevelCache) {

    super();
    this.firstLevelCache = new ConcurrentHashMap<>();
    this.secondLevelCache = secondLevelCache;
  }

  @Override
  public ThankToken find(ThankTokenIdType id) throws ThankNetworkException {

    ThankTokenContainer container = load(id);
    if (container != null) {
      return container.getToken();
    }
    return null;
  }

  @Override
  public Boolean getValidity(ThankTokenIdType id) {

    ThankTokenContainer container = load(id);
    if (container != null) {
      return container.getValid();
    }
    return null;
  }

  @Override
  public ThankToken findValid(ThankTokenIdType id) throws ThankNetworkException {

    ThankTokenContainer container = load(id);
    if ((container != null) && (container.isValid())) {
      return container.getToken();
    }
    return null;
  }

  /**
   * @param id the {@link ThankTokenIdType} of the given {@link ThankToken}.
   * @param token the {@link ThankToken} to add to this repository (cache).
   */
  public void add(ThankToken token) {

    Objects.requireNonNull(token, "token");
    ThankTokenIdType id = token.requireHeader().getId();
    ThankTokenContainer container = load(id);
    if (container == null) {
      container = new ThankTokenContainer(token);
      this.firstLevelCache.put(id, container);
    } else {
      assert container.getToken().equals(token);
    }
  }

  @Override
  public ThankToken findAndValidate(ThankTokenIdType id) throws ThankNetworkException {

    throw new UnsupportedOperationException();
  }

  private ThankTokenContainer load(ThankTokenIdType id) {

    ThankTokenContainer container = this.firstLevelCache.get(id);
    if (container == null) {
      ThankToken token = this.secondLevelCache.find(id);
      if (token == null) {
        return null;
      }
      container = new ThankTokenContainer(token);
      container.setValid(true); // 2nd level cache only contains valid tokens!
      this.firstLevelCache.put(id, container);
    }
    return container;
  }

  @Override
  public void setValidity(ThankToken token, boolean valid) {

    Objects.requireNonNull(token, "token");
    ThankTokenIdType id = token.requireHeader().getId();
    ThankTokenContainer container = this.firstLevelCache.get(id);
    if (container == null) {
      container = new ThankTokenContainer(token);
      container.setValid(valid);
      this.firstLevelCache.put(id, container);
    } else {
      container.setValid(valid);
    }
    if (valid) {
      this.secondLevelCache.save(token);
    }
  }

}
