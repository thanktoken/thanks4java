package io.github.thanktoken.core.api.location;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * {@link ThankLocation} based on the <a href="https://de.wikipedia.org/wiki/Open_Location_Code">Open Location
 * Code</a>(OLC) also known as <a href="https://plus.codes/">Plus Code</a>. However, it only uses the first six
 * characters of an OLC giving a geographic coordinate with a precision of 0.05˚ (5.5 km).<br>
 * It consists of three hierarchical {@link #getSegment(int) segments} with two characters each. these are:
 * <ol>
 * <li>{@link #getSector() sector}</li>
 * <li>{@link #getArea() area}</li>
 * <li>{@link #getRegion() region}</li>
 * </ol>
 *
 * @see io.github.thanktoken.core.api.token.header.ThankTokenHeader#getTarget()
 */
public class ThankLocationOlc extends ThankLocation {

  /** The {@link #getMaxLength() maximum length} of a {@link ThankLocationOlc} in bytes. */
  public static final int MAX_LENGTH = 6;

  /** The {@link List#indexOf(Object) index} of the {@link #getSector() sector}. */
  public static final int SEGMENT_INDEX_SECTOR = 1;

  /** The {@link List#indexOf(Object) index} of the {@link #getArea() area}. */
  public static final int SEGMENT_INDEX_AREA = 2;

  /** The {@link List#indexOf(Object) index} of the {@link #getRegion() region}. */
  public static final int SEGMENT_INDEX_REGION = 3;

  /** The segmentation of longitude and latitude in each step. */
  private static final BigDecimal SEGMENTATION = BigDecimal.valueOf(20d);

  /** A {@link String} with the valid characters of an OLC code "digit". */
  private static final String VALID_CHARS = "23456789CFGHJMPQRVWX";

  private static final Pattern PATTERN = Pattern.compile("[23456789C][23456789CFGHJMPQRV][23456789CFGHJMPQRVWX]{4}");

  private final String[] segments;

  private int latitudeRegion;

  private int longitudeRegion;

  private BigDecimal latitude;

  private BigDecimal longitude;

  /**
   * The constructor.
   *
   * @param value - see {@link #getValue()}.
   */
  public ThankLocationOlc(String value) {

    super(value);
    if (!PATTERN.matcher(value).matches()) {
      throw new IllegalArgumentException(value);
    }
    this.latitudeRegion = -1;
    this.longitudeRegion = -1;
    this.segments = new String[3];
    this.segments[0] = value.substring(0, 2);
    this.segments[1] = value.substring(2, 4);
    this.segments[2] = value.substring(4, 6);
  }

  @Override
  public String getPrefix(int index) {

    if (index == 0) {
      return "";
    } else if (index == 1) {
      return this.segments[0];
    } else if (index == 2) {
      return getValue().substring(0, 4);
    } else {
      return getValue();
    }
  }

  @Override
  public String getSegment(int index) {

    if (index > this.segments.length) {
      return null;
    }
    if (index == 0) {
      return "";
    }
    return this.segments[index - 1];
  }

  @Override
  public int getSegmentCount() {

    return this.segments.length + 1;
  }

  /**
   * @return one of the 162 (9*18) sectors of the world. The first two character pair of
   *         <a href="https://en.wikipedia.org/wiki/Open_Location_Code">OLC</a>.
   */
  public String getSector() {

    return getSegment(SEGMENT_INDEX_SECTOR);
  }

  /**
   * @return one of the 400 (20*20) areas within a {@link #getSector() sector}. The second two character pair of
   *         <a href="https://en.wikipedia.org/wiki/Open_Location_Code">OLC</a>.
   */
  public String getArea() {

    return getSegment(SEGMENT_INDEX_AREA);
  }

  /**
   * @return one of the 400 (20*20) regions within a {@link #getArea() area}. The third two character pair of
   *         <a href="https://en.wikipedia.org/wiki/Open_Location_Code">OLC</a>.
   */
  public String getRegion() {

    return getSegment(SEGMENT_INDEX_REGION);
  }

  @Override
  public int getMaxLength() {

    return MAX_LENGTH;
  }

  /**
   * @return the {@link #getLatitude() latitude} (y coordinate in direction south/north) as index of {@link #getRegion()
   *         regions} of the OLC matrix.
   */
  public int getLatitudeRegion() {

    if (this.latitudeRegion == -1) {
      int lat = VALID_CHARS.indexOf(this.value.charAt(0)) * 20;
      lat = (lat + VALID_CHARS.indexOf(this.value.charAt(2))) * 20;
      lat = lat + VALID_CHARS.indexOf(this.value.charAt(4));
      this.latitudeRegion = lat;
    }
    return this.latitudeRegion;
  }

  /**
   * @return the {@link #getLongitude() longitude} (x coordinate in direction west/east) as index of {@link #getRegion()
   *         regions} of the OLC matrix.
   */
  public int getLongitudeRegion() {

    if (this.longitudeRegion == -1) {
      int lng = VALID_CHARS.indexOf(this.value.charAt(1)) * 20;
      lng = (lng + VALID_CHARS.indexOf(this.value.charAt(3))) * 20;
      lng = lng + VALID_CHARS.indexOf(this.value.charAt(5));
      this.longitudeRegion = lng;
    }
    return this.longitudeRegion;
  }

  /**
   * @return the {@link CoordinateMode#CENTER center} latitude (y coordinate in direction south/north).
   * @see #getLatitude(CoordinateMode)
   */
  public double getLatitude() {

    return getLatitude(CoordinateMode.CENTER);
  }

  /**
   * @param mode the {@link CoordinateMode}.
   * @return the latitude (y coordinate in direction south/north) for the given {@link CoordinateMode}.
   */
  public double getLatitude(CoordinateMode mode) {

    if (this.latitude == null) {
      calculateWgs84();
    }
    return this.latitude.doubleValue() + mode.offset;
  }

  /**
   * @return the {@link CoordinateMode#CENTER center} longitude (x coordinate in direction west/east).
   * @see #getLongitude(CoordinateMode)
   */
  public double getLongitude() {

    return getLongitude(CoordinateMode.CENTER);
  }

  /**
   * @param mode the {@link CoordinateMode}.
   * @return the longitude (x coordinate in direction west/east) for the given {@link CoordinateMode}.
   */
  public double getLongitude(CoordinateMode mode) {

    if (this.longitude == null) {
      calculateWgs84();
    }
    return this.longitude.doubleValue() + mode.offset;
  }

  private void calculateWgs84() {

    if (this.longitude != null) {
      assert (this.latitude != null);
      return;
    }
    int latDigit = VALID_CHARS.indexOf(this.value.charAt(0));
    int lngDigit = VALID_CHARS.indexOf(this.value.charAt(1));
    BigDecimal lat = SEGMENTATION.multiply(BigDecimal.valueOf(latDigit));
    BigDecimal lng = SEGMENTATION.multiply(BigDecimal.valueOf(lngDigit));
    latDigit = VALID_CHARS.indexOf(this.value.charAt(2));
    lngDigit = VALID_CHARS.indexOf(this.value.charAt(3));
    lat = lat.add(BigDecimal.valueOf(latDigit));
    lng = lng.add(BigDecimal.valueOf(lngDigit));
    latDigit = VALID_CHARS.indexOf(this.value.charAt(4));
    lngDigit = VALID_CHARS.indexOf(this.value.charAt(5));
    lat = lat.add(BigDecimal.valueOf(latDigit).divide(SEGMENTATION));
    lng = lng.add(BigDecimal.valueOf(lngDigit).divide(SEGMENTATION));
    lat = lat.subtract(BigDecimal.valueOf(90));
    lng = lng.subtract(BigDecimal.valueOf(180));
    this.latitude = lat;
    this.longitude = lng;
  }

  /**
   * @param target the target {@link ThankLocationOlc OLC location} to determine the distance to.
   * @return the distance from {@code this} location to the given location in kilometers.
   */
  public double getDistance(ThankLocationOlc target) {

    Objects.requireNonNull(target, "to");
    if (equals(target)) {
      return 0;
    }
    double lat1 = getLatitude();
    double lng1 = getLongitude();
    double lat2 = target.getLatitude();
    double lng2 = target.getLongitude();

    double theta = lng1 - lng2;
    double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
        + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
    dist = Math.acos(dist);
    dist = Math.toDegrees(dist);
    dist = dist * 60 * 1.1515;
    dist = dist * 1.609344;
    return dist;
  }

  /**
   * @param target the target {@link ThankLocationOlc OLC location} to determine the distance to.
   * @return the distance as the number of {@link #getRegion() region}-squares to pass. A value of {@code 0} means that
   *         the two locations are {@link #equals(Object) equal}, a value of {@code 1} means that the given location is
   *         one of the eight neighbour {@link #getRegion() regions} of this location, etc.
   */
  public int getDistanceRegions(ThankLocationOlc target) {

    int deltaLatitude = getLatitudeRegion() - target.getLatitudeRegion();
    if (deltaLatitude < 0) {
      deltaLatitude = -deltaLatitude;
    }
    int deltaLongitude = getLongitudeRegion() - target.getLongitudeRegion();
    if (deltaLongitude < 0) {
      deltaLongitude = -deltaLongitude;
    }
    if (deltaLatitude > deltaLongitude) {
      return deltaLatitude;
    }
    return deltaLongitude;
  }

  /**
   * @param value - see {@link #getValue()}. May be <code>null</code>.
   * @return the {@link ThankLocationOlc} or <code>null</code> if <code>value</code> was <code>null</code>.
   */
  public static ThankLocationOlc of(String value) {

    if (value == null) {
      return null;
    }
    return new ThankLocationOlc(value);
  }

  /**
   * @param latitude the {@link #getLatitude() latitude} in degrees of WGS84 (GPS).
   * @param longitude the {@link #getLongitude() longitude} in degrees of WGS84 (GPS).
   * @return the corresponding {@link ThankLocationOlc}.
   */
  public static ThankLocationOlc of(double latitude, double longitude) {

    char[] code = new char[6];
    BigDecimal lat = new BigDecimal(normalizeLatitude(latitude) + 90d);
    BigDecimal lng = new BigDecimal(normalizeLongitude(longitude) + 180d);
    BigDecimal latDigit = lat.divide(SEGMENTATION, 0, BigDecimal.ROUND_FLOOR);
    BigDecimal lngDigit = lng.divide(SEGMENTATION, 0, BigDecimal.ROUND_FLOOR);
    code[0] = VALID_CHARS.charAt(latDigit.intValue());
    code[1] = VALID_CHARS.charAt(lngDigit.intValue());
    lat = lat.subtract(SEGMENTATION.multiply(latDigit));
    lng = lng.subtract(SEGMENTATION.multiply(lngDigit));
    latDigit = lat.divide(BigDecimal.ONE, 0, BigDecimal.ROUND_FLOOR);
    lngDigit = lng.divide(BigDecimal.ONE, 0, BigDecimal.ROUND_FLOOR);
    code[2] = VALID_CHARS.charAt(latDigit.intValue());
    code[3] = VALID_CHARS.charAt(lngDigit.intValue());
    lat = lat.subtract(BigDecimal.ONE.multiply(latDigit));
    lng = lng.subtract(BigDecimal.ONE.multiply(lngDigit));
    latDigit = lat.multiply(SEGMENTATION);
    lngDigit = lng.multiply(SEGMENTATION);
    code[4] = VALID_CHARS.charAt(latDigit.intValue());
    code[5] = VALID_CHARS.charAt(lngDigit.intValue());
    return of(new String(code));
  }

  /**
   * @param latitudeRegion the {@link #getLatitudeRegion() latitude region index}.
   * @param longitudeRegion the {@link #getLongitudeRegion() longitude region index}.
   * @return the corresponding {@link ThankLocationOlc}.
   */
  public static ThankLocationOlc ofRegions(int latitudeRegion, int longitudeRegion) {

    if ((latitudeRegion < 0) || (latitudeRegion > 3599)) {
      throw new IllegalArgumentException("latitude: " + latitudeRegion);
    }
    // actually longitudeRegion needs to be >= 2 for reasonable coordinate
    if ((longitudeRegion < 0) || (longitudeRegion > 7199)) {
      throw new IllegalArgumentException("longitude: " + longitudeRegion);
    }
    char[] code = new char[6];
    int lat = latitudeRegion;
    code[4] = VALID_CHARS.charAt(lat % 20);
    lat = lat / 20;
    code[2] = VALID_CHARS.charAt(lat % 20);
    lat = lat / 20;
    code[0] = VALID_CHARS.charAt(lat);
    int lng = longitudeRegion;
    code[5] = VALID_CHARS.charAt(lng % 20);
    lng = lng / 20;
    code[3] = VALID_CHARS.charAt(lng % 20);
    lng = lng / 20;
    code[1] = VALID_CHARS.charAt(lng);
    ThankLocationOlc location = of(new String(code));
    if (location.latitudeRegion == -1) {
      location.latitudeRegion = latitudeRegion;
      location.longitudeRegion = longitudeRegion;
    }
    return location;
  }

  private static double normalizeLongitude(double longitude) {

    double lng = longitude;
    while (lng < -180) {
      lng = lng + 360;
    }
    while (lng >= 180) {
      lng = lng - 360;
    }
    return lng;
  }

  private static double normalizeLatitude(double latitude) {

    double lat = Math.min(Math.max(latitude, -90), 90);
    if (lat == 90d) {
      lat = 89.955d;
    }
    return lat;
  }

  /**
   * Mode to retrieve {@link ThankLocationOlc#getLatitude(CoordinateMode)}
   */
  public static enum CoordinateMode {

    /**
     * North latitude and west longitude of rectangle (bottom right). <br>
     * <b>ATTENTION:</b><br>
     * To assure that the north/west coordinate is within the OLC rectangle even considering mathematical inaccuracy the
     * rectangle is reduced by a little epsilon.
     */
    NORTH_WEST(0.0000000000001),

    /** Center latitude and longitude of the rectangle. */
    CENTER(0.025),

    /**
     * South latitude and east longitude of rectangle (top left). <br>
     * <b>ATTENTION:</b><br>
     * To assure that the south/east coordinate is within the OLC rectangle even considering mathematical inaccuracy the
     * rectangle is reduced by a little epsilon.
     */
    SOUTH_EAST(0.0499999999999);

    private final double offset;

    CoordinateMode(double offset) {

      this.offset = offset;
    }
  }
}
