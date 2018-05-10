package io.github.thanktoken.core.api.strategy;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;

import io.github.thanktoken.core.api.header.ThankVersion;

/**
 * This class {@link #getStrategy(ThankVersion) gives access} to retrieve a {@link ThankStrategyFactory} for a given
 * {@link ThankVersion}.
 */
public class ThankStrategyFactory {

  private static final ThankStrategyFactory INSTANCE = new ThankStrategyFactory();

  private final ThankGlobalStrategy globalStrategy;

  private final Map<ThankVersion, ThankVersionStrategy> strategyMap;

  private final Collection<ThankVersion> versions;

  private final ThankVersion latestVersion;

  /**
   * The constructor.
   */
  protected ThankStrategyFactory() {
    super();
    this.strategyMap = new HashMap<>();
    ServiceLoader<ThankStrategy> strategiesLoader = ServiceLoader.load(ThankStrategy.class);
    ThankGlobalStrategy global = null;
    ThankVersion latest = null;
    for (ThankStrategy strategy : strategiesLoader) {
      if (strategy instanceof ThankVersionStrategy) {
        ThankVersionStrategy versionStrategy = (ThankVersionStrategy) strategy;
        ThankVersion version = versionStrategy.getVersion();
        if ((latest == null) || (version.isNewer(latest))) {
          latest = version;
        }
        ThankVersionStrategy duplicate = this.strategyMap.put(version, versionStrategy);
        if (duplicate != null) {
          throw new IllegalStateException("Strategy for version " + version + " " + duplicate.getClass().getName()
              + " is duplicated by " + versionStrategy.getClass().getName());
        }
      } else if (global == null) {
        global = (ThankGlobalStrategy) strategy;
      } else {
        throw new IllegalStateException(
            "Global strategy " + global.getClass().getName() + " is duplicated by " + strategy.getClass().getName());
      }
    }
    this.globalStrategy = global;
    this.latestVersion = latest;
    this.versions = Collections.unmodifiableCollection(this.strategyMap.keySet());
  }

  /**
   * @return the singleton instance of this {@link ThankStrategyFactory}.
   */
  public static ThankStrategyFactory get() {

    return INSTANCE;
  }

  /**
   * @return the {@link ThankGlobalStrategy}.
   */
  public ThankGlobalStrategy getGlobalStrategy() {

    return this.globalStrategy;
  }

  /**
   * @param version the {@link ThankVersion} to handle.
   * @return the {@link ThankVersionStrategy} to handle the requested version.
   * @see #getGlobalStrategy()
   */
  public ThankVersionStrategy getStrategy(ThankVersion version) {

    Objects.requireNonNull(version, "version");
    ThankVersionStrategy thankVersionStrategy = this.strategyMap.get(version);
    if (thankVersionStrategy == null) {
      throw new IllegalArgumentException(version.toString());
    }
    return thankVersionStrategy;
  }

  /**
   * @return the {@link ThankVersion}s supported by this {@link ThankStrategyFactory}. For each {@link ThankVersion} in
   *         the returned {@link Collection} you can {@link #getStrategy(ThankVersion) request a strategy} that is
   *         guaranteed to exist. The order of the returned {@link Collection} is unspecified.
   */
  public Collection<ThankVersion> getVersions() {

    return this.versions;
  }

  /**
   * @see #getLatestStrategy()
   * @return the {@link ThankVersion#isNewer(ThankVersion) newest} {@link ThankVersion} {@link #getVersions()
   *         available}.
   */
  public ThankVersion getLatestVersion() {

    return this.latestVersion;
  }

  /**
   * @see #getLatestVersion()
   * @see #getStrategy(ThankVersion)
   * @return the {@link #getStrategy(ThankVersion) strategy} for the {@link #getLatestVersion() latest version}.
   */
  public ThankVersionStrategy getLatestStrategy() {

    if (this.latestVersion == null) {
      return null;
    }
    return getStrategy(this.latestVersion);
  }

}
