package io.github.thanktoken.core.api.datatype;

import java.security.SecureRandom;

import org.junit.Test;

import io.github.thanktoken.core.api.TestCase;
import io.github.thanktoken.core.api.location.ThankLocationOlc;
import io.github.thanktoken.core.api.location.ThankLocationOlc.CoordinateMode;

/**
 * Test of {@link ThankLocationOlc}.
 */
public class ThankLocationOlcTest extends TestCase {

  /**
   * Test of {@link ThankLocationOlc#of(double, double) creation from WGS84 coordinates}
   */
  @Test
  public void testOfWgs84() {

    checkWgs84("Stature of Liberty, USA", 40.6892534, -74.0466891, "87G7MX");
    checkWgs84("Taj Mahal, India", 27.1750199, 78.0399665, "7JVW52");
    checkWgs84("Tiananmen Square, China", 39.9054936, 116.395443, "8PFRW9");
    checkWgs84("Ayers Rock, Australia", -25.3456376, 131.0283911, "5Q6HM2");
    checkWgs84("Chichén Itzá, Mexico", 20.6842899, -88.5699713, "76GHMC");
    checkWgs84("Meiji Jingu, Japan", 35.6764019, 139.6971372, "8Q7XMM");
    checkWgs84("Tour Eiffel, France", 48.8583736, 2.2922926, "8FW4V7");
    checkWgs84("Leaning Tower of Pisa, Italy", 43.7229559, 10.3944083, "8FMGP9");
    checkWgs84("Machu Picchu, Peru", -13.163136, -72.5471516, "57R9RF");
    checkWgs84("Pão de Açucar, Brasil", -22.9492383, -43.1633305, "589R3R");
    checkWgs84("The Great Pyramid of Giza, Egypt", 29.9792391, 31.1320132, "7GXHX4");
    checkWgs84("Desert Breath, Egypt", 27.3804303, 33.6299342, "7GVM9J");
  }

  private void checkWgs84(String name, double latitude, double longitude, String olc) {

    ThankLocationOlc location = ThankLocationOlc.of(latitude, longitude);
    assertThat(location.getValue()).as(name).isEqualTo(olc);
    assertThat(location.getSector()).isEqualTo(olc.substring(0, 2));
    assertThat(location.getArea()).isEqualTo(olc.substring(2, 4));
    assertThat(location.getRegion()).isEqualTo(olc.substring(4, 6));
    double northLatitude = location.getLatitude(CoordinateMode.NORTH_WEST);
    double westLongitude = location.getLongitude(CoordinateMode.NORTH_WEST);
    assertThat(northLatitude).isLessThanOrEqualTo(latitude);
    assertThat(westLongitude).isLessThanOrEqualTo(longitude);
    assertThat(ThankLocationOlc.of(northLatitude, westLongitude)).isEqualTo(location);
    double centerLatitude = location.getLatitude();
    double centerLongitude = location.getLongitude();
    assertThat(centerLatitude).isGreaterThan(northLatitude);
    assertThat(centerLongitude).isGreaterThan(westLongitude);
    assertThat(ThankLocationOlc.of(centerLatitude, centerLongitude)).isEqualTo(location);
    double southLatitude = location.getLatitude(CoordinateMode.SOUTH_EAST);
    double eastLongitude = location.getLongitude(CoordinateMode.SOUTH_EAST);
    assertThat(southLatitude).isGreaterThan(centerLatitude);
    assertThat(eastLongitude).isGreaterThan(westLongitude);
    assertThat(ThankLocationOlc.of(southLatitude, eastLongitude)).isEqualTo(location);

    assertThat(ThankLocationOlc.ofRegions(location.getLatitudeRegion(), location.getLongitudeRegion())).isEqualTo(location);
  }

  /** Test {@link ThankLocationOlc#getDistance(ThankLocationOlc)}. */
  @Test
  public void testGetDistance() {

    ThankLocationOlc statureOfLiberty = ThankLocationOlc.of("87G7MX");
    ThankLocationOlc tajMahal = ThankLocationOlc.of("7JVW52");
    assertThat(statureOfLiberty.getDistance(tajMahal)).isEqualTo(11937.038761055004, PRECISION_DOUBLE);
    assertThat(tajMahal.getDistance(statureOfLiberty)).isEqualTo(11937.038761055004, PRECISION_DOUBLE);

    ThankLocationOlc eiffelTower = ThankLocationOlc.of("8FW4V7");
    ThankLocationOlc pyramidOfGiza = ThankLocationOlc.of("7GXHX4");
    assertThat(eiffelTower.getDistance(pyramidOfGiza)).isEqualTo(3214.5767751762414, PRECISION_DOUBLE);
    assertThat(pyramidOfGiza.getDistance(eiffelTower)).isEqualTo(3214.5767751762414, PRECISION_DOUBLE);
  }

  /** Test {@link ThankLocationOlc#getDistanceRegions(ThankLocationOlc)}. */
  @Test
  public void testGetDistanceRegions() {

    ThankLocationOlc statureOfLiberty = ThankLocationOlc.of("87G7MX");
    ThankLocationOlc tajMahal = ThankLocationOlc.of("7JVW52");
    assertThat(statureOfLiberty.getDistanceRegions(tajMahal)).isEqualTo(3041);
    assertThat(tajMahal.getDistanceRegions(statureOfLiberty)).isEqualTo(3041);
    int latitudeRegion = statureOfLiberty.getLatitudeRegion();
    int longitudeRegion = statureOfLiberty.getLongitudeRegion();
    SecureRandom rnd = new SecureRandom();
    for (int i = 0; i < 100; i++) {
      int longitudeOffset = 0;
      if (i > 0) {
        longitudeOffset = rnd.nextInt(i);
      }
      assertThat(statureOfLiberty.getDistanceRegions(ThankLocationOlc.ofRegions(latitudeRegion, longitudeRegion + longitudeOffset))).isEqualTo(i);
      latitudeRegion++;
    }

    ThankLocationOlc eiffelTower = ThankLocationOlc.of("8FW4V7");
    ThankLocationOlc pyramidOfGiza = ThankLocationOlc.of("7GXHX4");
    assertThat(eiffelTower.getDistanceRegions(pyramidOfGiza)).isEqualTo(577);
    assertThat(pyramidOfGiza.getDistanceRegions(eiffelTower)).isEqualTo(577);
  }
}
