package io.github.thanktoken.core.api.reference;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import io.github.thanktoken.core.api.algorithm.ThankAlgorithm;
import io.github.thanktoken.core.api.attribute.ThankAttributeField;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.data.ThankDataObject;
import io.github.thanktoken.core.api.id.ThankTokenIdType;
import io.github.thanktoken.core.api.location.ThankLocation;
import io.github.thanktoken.core.api.timestamp.ThankTimestamp;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.version.ThankVersion;

/**
 * Abstract base implementation of {@link ThankTokenReference}.
 *
 * @since 1.0.0
 */
public abstract class AbstractThankTokenReference implements ThankTokenReference {

  private static final char TYPE_SUFFIX = '#';

  private static final char VERSION_SUFFIX = '$';

  private static final char ALGORITHM_SUFFIX = '§';

  private static final char CURRENCY_SUFFIX = '¤';

  private static final char LOCATION_SUFFIX = '@';

  @Override
  public ThankTokenIdType resolve(ThankTokenHeader header) {

    ThankTimestamp timestamp = getTimestamp();
    if (timestamp == null) {
      timestamp = header.requireTimestamp().next();
    }
    ThankLocation location = getLocation();
    if (location == null) {
      location = header.getLocation();
    }
    ThankVersion version = getVersion();
    if (version == null) {
      version = header.getVersion();
    }
    ThankAlgorithm algorithm = getAlgorithm();
    if (algorithm == null) {
      algorithm = header.getAlgorithm();
    }
    ThankCurrency currency = getCurrency();
    if (currency == null) {
      currency = header.getCurrency();
    }
    return new ThankTokenIdType(timestamp, location, version, algorithm, currency);
  }

  @Override
  public void verify(ThankTokenHeader header) {

    Objects.requireNonNull(header, "header");
    Set<String> invalidProperties = null;
    invalidProperties = verifyProperty(this, header, ThankAttributeField.TIMESTAMP, invalidProperties);
    invalidProperties = verifyProperty(this, header, ThankAttributeField.LOCATION, invalidProperties);
    invalidProperties = verifyProperty(this, header, ThankAttributeField.VERSION, invalidProperties);
    invalidProperties = verifyProperty(this, header, ThankAttributeField.ALGORITHM, invalidProperties);
    invalidProperties = verifyProperty(this, header, ThankAttributeField.CURRENCY, invalidProperties);
    if (invalidProperties != null) {
      throw new IllegalArgumentException("Invalid reference " + toString() + " missmatching for properties: " + invalidProperties);
    }
  }

  private <T extends ThankDataObject> Set<String> verifyProperty(T self, T header, ThankAttributeField<?, T, ? extends T> field,
      Set<String> invalidProperties) {

    Object value = field.get(self);
    if ((value == null) || value.equals(field.get(header))) {
      if (invalidProperties == null) {
        invalidProperties = new HashSet<>();
      }
      invalidProperties.add(field.getName());
    }
    return invalidProperties;
  }

  @Override
  public int hashCode() {

    return Objects.hash(getType(), getTimestamp(), getLocation(), getVersion(), getAlgorithm());
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ThankTokenReferenceType other = (ThankTokenReferenceType) obj;
    if (!Objects.equals(getType(), other.getType())) {
      return false;
    } else if (!Objects.equals(getTimestamp(), other.getTimestamp())) {
      return false;
    } else if (!Objects.equals(getLocation(), other.getLocation())) {
      return false;
    } else if (!Objects.equals(getVersion(), other.getVersion())) {
      return false;
    } else if (!Objects.equals(getAlgorithm(), other.getAlgorithm())) {
      return false;
    } else if (!Objects.equals(getCurrency(), other.getCurrency())) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {

    return toString(this);
  }

  static String toString(ThankTokenReference reference) {

    StringBuilder sb = new StringBuilder();
    sb.append(reference.getType());
    sb.append(TYPE_SUFFIX);
    ThankVersion version = reference.getVersion();
    if (version != null) {
      sb.append(version);
      sb.append(VERSION_SUFFIX);
    }
    ThankAlgorithm algorithm = reference.getAlgorithm();
    if (algorithm != null) {
      sb.append(algorithm);
      sb.append(ALGORITHM_SUFFIX);
    }
    ThankCurrency currency = reference.getCurrency();
    if (currency != null) {
      sb.append(currency);
      sb.append(CURRENCY_SUFFIX);
    }
    sb.append(reference.getLocation());
    sb.append(LOCATION_SUFFIX);
    sb.append(reference.getTimestamp());
    return sb.toString();
  }

}
