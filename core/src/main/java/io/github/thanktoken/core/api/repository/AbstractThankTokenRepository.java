package io.github.thanktoken.core.api.repository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import io.github.thanktoken.core.api.id.ThankTokenId;
import io.github.thanktoken.core.api.location.ThankLocation;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;

/**
 * Abstract base implementation of {@link ThankTokenRepository}.
 *
 * @since 1.0.0
 */
public abstract class AbstractThankTokenRepository implements ThankTokenRepository {

  /**
   * @param id the {@link ThankTokenId}.
   * @return the {@link List} of path segments.
   */
  protected List<String> getRelativePath(ThankTokenId id) {

    List<String> path = new ArrayList<>(8);
    path.add(id.requireAlgorithm().getValue());
    path.add(id.requireVersion().toString());
    path.add(id.requireCurrency().getValue());
    getRelativePath4Location(id.requireLocation(), path);
    getRelativePath4Timestamp(id.requireTimestamp(), path);
    return path;
  }

  private void getRelativePath4Timestamp(ThankTimestamp timestamp, List<String> path) {

    ZonedDateTime dateTime = timestamp.asZonedDateTime();
    path.add(Integer.toString(dateTime.getYear()));
    path.add(Integer.toString(dateTime.getMonthValue()));
    path.add(Integer.toString(dateTime.getDayOfMonth()));
    path.add(Integer.toString(dateTime.getHour()));
    path.add(Integer.toString(dateTime.getMinute()));
    path.add(Integer.toString(dateTime.getSecond()));
    path.add(Integer.toString(dateTime.getNano()));
  }

  private void getRelativePath4Location(ThankLocation location, List<String> path) {

    int segmentCount = location.getSegmentCount();
    for (int segmentIndex = 0; segmentIndex < segmentCount; segmentIndex++) {
      path.add(location.getSegment(segmentIndex));
    }
  }

}
