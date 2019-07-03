package io.github.thanktoken.core.api.location;

import java.util.List;

import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.datatype.StringType;
import io.github.thanktoken.core.api.target.ThankTarget;

/**
 * {@link ThankLocation} represents the regional {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader#getLocation()
 * location} for which a {@link io.github.thanktoken.core.api.token.ThankToken} was originally created. It consists of
 * {@link #getSegment(int) segments}. Typically there are three {@link #getSegment(int) segments} corresponding to the
 * {@link ThankTarget#getLevel() levels} of {@link ThankTarget}s. Any further segment is unusual and unspecified but
 * allow additional flexibility for future extensions or custom {@link ThankCurrency currencies}.
 *
 * @see io.github.thanktoken.core.api.token.header.ThankTokenHeader#getTarget()
 */
public abstract class ThankLocation extends StringType {

  /**
   * The constructor.
   *
   * @param value - see {@link #getValue()}.
   */
  public ThankLocation(String value) {

    super(value);
  }

  /**
   * @param index the {@link List#get(int) index} of the requested segment. The {@code index} corresponds to the
   *        {@link ThankTarget#getLevel() target level}. Therefore an {@code index} of {@code 0} represents the entire
   *        world and will return the empty string (""). The {@code index} for actual segments is therefore
   *        {@code 1}-based.
   * @return the prefix of this location for the given {@code index}. Will be equal to the {@link #toString() string
   *         representation} of this location in case the {@code index} is greater or equal to {@link #getSegmentCount()
   *         segment count}.
   */
  public abstract String getPrefix(int index);

  /**
   * @param index the {@link List#get(int) index} of the requested segment. The {@code index} corresponds to the
   *        {@link ThankTarget#getLevel() target level}. Therefore an {@code index} of {@code 0} represents the entire
   *        world and will return the empty string (""). The {@code index} for actual segments is therefore
   *        {@code 1}-based.
   * @return the segment for the given {@code index}. Will be {@code null} in case there is no such index
   *         (<code>index >= {@link #getSegmentCount()}</code>).
   */
  public abstract String getSegment(int index);

  /**
   * @return the maximum number of {@link #getSegment(int) segments} available.
   */
  public abstract int getSegmentCount();

}
