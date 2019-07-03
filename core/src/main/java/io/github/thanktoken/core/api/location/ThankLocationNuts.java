package io.github.thanktoken.core.api.location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import io.github.thanktoken.core.api.currency.ThankCurrency;

/**
 * {@link ThankLocation} implementation for
 * <a href="https://en.wikipedia.org/wiki/Nomenclature_of_Territorial_Units_for_Statistics">NUTS</a> hierarchical and
 * political structure. It consists of {@link #getSegment(int) segments} that are separated by {@link #SEPARATOR_CHAR
 * slashes}. The first three segments are mandatory and these are:
 * <ol>
 * <li>{@link #getCountry() country}</li>
 * <li>{@link #getState() state}</li>
 * <li>{@link #getRegion() region}</li>
 * </ol>
 * Any further segment is unusual and unspecified but allow additional flexibility for future extensions or custom
 * {@link ThankCurrency currencies}. Each {@link #getSegment(int) segment} has to be reflected as
 * {@link io.github.thanktoken.core.api.transaction.ThankTransaction}.
 *
 * @see io.github.thanktoken.core.api.token.header.ThankTokenHeader#getTarget()
 */
public class ThankLocationNuts extends ThankLocation {

  /** The character used to separate the {@link #getSegment(int) segments} in the {@link #getValue() value}. */
  public static final char SEPARATOR_CHAR = '/';

  /** The {@link #SEPARATOR_CHAR} as {@link String}. */
  public static final String SEPARATOR_STRING = Character.toString(SEPARATOR_CHAR);

  /** The {@link List#indexOf(Object) index} of the {@link #getCountry() country}. */
  public static final int SEGMENT_INDEX_COUNTRY = 1;

  /** The {@link List#indexOf(Object) index} of the {@link #getState() state}. */
  public static final int SEGMENT_INDEX_STATE = 2;

  /** The {@link List#indexOf(Object) index} of the {@link #getRegion() region}. */
  public static final int SEGMENT_INDEX_REGION = 3;

  private static final Pattern SEGMENT_PATTERN = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9.-_]*");

  private final List<String> segments;

  /** The {@link #getMaxLength() maximum length} of a {@link ThankLocationNuts} in bytes. */
  public static final int MAX_LENGTH = 20;

  /**
   * The constructor.
   *
   * @param value - see {@link #getValue()}.
   */
  public ThankLocationNuts(String value) {

    super(value);
    String[] segmentArray = value.split(SEPARATOR_STRING);
    List<String> segmentList = Arrays.asList(segmentArray);
    this.segments = Collections.unmodifiableList(segmentList);
    validateSegments();
  }

  /**
   * The constructor.
   *
   * @param segments the {@link #getSegment(int) segments}.
   */
  public ThankLocationNuts(List<String> segments) {

    super(String.join(SEPARATOR_STRING, segments));
    this.segments = Collections.unmodifiableList(new ArrayList<>(segments));
    validateSegments();
  }

  private void validateSegments() {

    if (this.segments.size() < 3) {
      throw new IllegalArgumentException("Invalid value '" + getValue() + "' for location: at least 3 segments are required.");
    }
    for (String segment : this.segments) {
      if (!SEGMENT_PATTERN.matcher(segment).matches()) {
        throw new IllegalArgumentException(
            "Invalid value '" + getValue() + "' for location: segment '" + segment + "' does not match " + SEGMENT_PATTERN.pattern() + ".");
      }
    }
  }

  @Override
  public String getPrefix(int index) {

    if (index == 0) {
      return "";
    }
    int limit = this.segments.size();
    if (index < limit) {
      limit = index;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < limit; i++) {
      if (i > 0) {
        sb.append(SEPARATOR_CHAR);
      }
      sb.append(this.segments.get(i));
    }
    return sb.toString();
  }

  @Override
  public String getSegment(int index) {

    if (index > this.segments.size()) {
      return null;
    }
    if (index == 0) {
      return "";
    }
    return this.segments.get(index - 1);
  }

  @Override
  public int getSegmentCount() {

    return this.segments.size() + 1;
  }

  /**
   * @return the country that produced the {@link io.github.thanktoken.core.api.token.header.ThankTokenHeader#getTarget() owning}
   *         {@link io.github.thanktoken.core.api.token.ThankToken} represented as
   *         <a href="https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2">ISO 3166-1 2 letter country code</a>. Must not
   *         be {@code null} or {@link String#isEmpty() empty}.
   */
  public String getCountry() {

    return getSegment(SEGMENT_INDEX_COUNTRY);
  }

  /**
   * @return the state or province of the {@link #getCountry() country}. Is only guaranteed to be unique within the same
   *         {@link #getCountry() country}.
   */
  public String getState() {

    return getSegment(SEGMENT_INDEX_STATE);
  }

  /**
   * @return the region of the {@link #getState() state}. Is only guaranteed to be unique within the same
   *         {@link #getState() state} and {@link #getCountry() country}.
   */
  public String getRegion() {

    return getSegment(SEGMENT_INDEX_REGION);
  }

  @Override
  public int getMaxLength() {

    return MAX_LENGTH;
  }

  /**
   * @param value - see {@link #getValue()}. May be <code>null</code>.
   * @return the {@link ThankLocationNuts} or <code>null</code> if <code>value</code> was <code>null</code>.
   */
  public static ThankLocationNuts of(String value) {

    if (value == null) {
      return null;
    }
    return new ThankLocationNuts(value);
  }

}
