package io.github.thanktoken.core.api.address;

import net.sf.mmm.binary.api.Binary;

/**
 * Type of a {@link ThankAddress}. {@link #isPseudonymous()}, {@link #isNaturalPerson()},
 * {@link #isIdentityServiceOfficer()}, etc.
 *
 * @see ThankAddress#getType()
 * @since 1.0.0
 */
public class ThankAddressType {

  /** {@link ThankAddressType} for an {@link #isPseudonymous() anonymous} entity. */
  public static final ThankAddressType PSEUDONYMOUS = new ThankAddressType(0);

  /** {@link ThankAddressType} for a (certified) {@link #isNaturalPerson() natural person}. */
  public static final ThankAddressType NATURAL_PERSON_CHILD = new ThankAddressType(1);

  /** {@link ThankAddressType} for a (certified) {@link #isNaturalPerson() natural person}. */
  public static final ThankAddressType NATURAL_PERSON_ADULT = new ThankAddressType(2);

  /** {@link ThankAddressType} for a {@link #isIdentityServiceOfficer() identity service officer}. */
  public static final ThankAddressType IDENTIFICATION_SERVICE_OFFICER = new ThankAddressType(3);

  /**
   * {@link ThankAddressType} for a {@link #isCommunityRepresentative() community representative} of {@link #getLevel()
   * level} {@code 0} (world).
   */
  public static final ThankAddressType COMMUNITY_REPRESENTATIVE_L0 = new ThankAddressType(4);

  /**
   * {@link ThankAddressType} for a {@link #isCommunityRepresentative() community representative} of {@link #getLevel()
   * level} {@code 1} (sector).
   */
  public static final ThankAddressType COMMUNITY_REPRESENTATIVE_L1 = new ThankAddressType(5);

  /**
   * {@link ThankAddressType} for a {@link #isCommunityRepresentative() community representative} of {@link #getLevel()
   * level} {@code 2} (area).
   */
  public static final ThankAddressType COMMUNITY_REPRESENTATIVE_L2 = new ThankAddressType(6);

  /**
   * {@link ThankAddressType} for a {@link #isCommunityRepresentative() community representative} of {@link #getLevel()
   * level} {@code 3} (region).
   */
  public static final ThankAddressType COMMUNITY_REPRESENTATIVE_L3 = new ThankAddressType(7);

  /**
   * {@link ThankAddressType} for a {@link #isCommunityProject() community project} of {@link #getLevel() level}
   * {@code 0} (world).
   */
  public static final ThankAddressType COMMUNITY_PROJECT_L0 = new ThankAddressType(8);

  /**
   * {@link ThankAddressType} for a {@link #isCommunityProject() community project} of {@link #getLevel() level}
   * {@code 1} (sector).
   */
  public static final ThankAddressType COMMUNITY_PROJECT_L1 = new ThankAddressType(9);

  /**
   * {@link ThankAddressType} for a {@link #isCommunityProject() community project} of {@link #getLevel() level}
   * {@code 2} (area).
   */
  public static final ThankAddressType COMMUNITY_PROJECT_L2 = new ThankAddressType(10);

  /**
   * {@link ThankAddressType} for a {@link #isCommunityProject() community project} of {@link #getLevel() level}
   * {@code 3} (region).
   */
  public static final ThankAddressType COMMUNITY_PROJECT_L3 = new ThankAddressType(11);

  /**
   * {@link ThankAddressType} for a {@link #isSustainabilityRepresentative() sustainability representative} of
   * {@link #getLevel() level} {@code 0} (world).
   */
  public static final ThankAddressType SUSTAINABILITY_REPRESENTATIVE_L0 = new ThankAddressType(12);

  /**
   * {@link ThankAddressType} for a {@link #isSustainabilityRepresentative() sustainability representative} of
   * {@link #getLevel() level} {@code 1} (sector).
   */
  public static final ThankAddressType SUSTAINABILITY_REPRESENTATIVE_L1 = new ThankAddressType(13);

  /**
   * {@link ThankAddressType} for a {@link #isSustainabilityRepresentative() sustainability representative} of
   * {@link #getLevel() level} {@code 2} (area).
   */
  public static final ThankAddressType SUSTAINABILITY_REPRESENTATIVE_L2 = new ThankAddressType(14);

  /**
   * {@link ThankAddressType} for a {@link #isSustainabilityRepresentative() sustainability representative} of
   * {@link #getLevel() level} {@code 3} (region).
   */
  public static final ThankAddressType SUSTAINABILITY_REPRESENTATIVE_L3 = new ThankAddressType(15);

  /**
   * {@link ThankAddressType} for a {@link #isSustainabilityProject() sustainability project} of {@link #getLevel()
   * level} {@code 0} (world).
   */
  public static final ThankAddressType SUSTAINABILITY_PROJECT_L0 = new ThankAddressType(16);

  /**
   * {@link ThankAddressType} for a {@link #isSustainabilityProject() sustainability project} of {@link #getLevel()
   * level} {@code 1} (sector).
   */
  public static final ThankAddressType SUSTAINABILITY_PROJECT_L1 = new ThankAddressType(17);

  /**
   * {@link ThankAddressType} for a {@link #isSustainabilityProject() sustainability project} of {@link #getLevel()
   * level} {@code 2} (area).
   */
  public static final ThankAddressType SUSTAINABILITY_PROJECT_L2 = new ThankAddressType(18);

  /**
   * {@link ThankAddressType} for a {@link #isSustainabilityProject() sustainability project} of {@link #getLevel()
   * level} {@code 3} (region).
   */
  public static final ThankAddressType SUSTAINABILITY_PROJECT_L3 = new ThankAddressType(19);

  /** {@link ThankAddressType} for a {@link #isCompany() company}. */
  public static final ThankAddressType COMPANY = new ThankAddressType(20);

  private static final ThankAddressType[] TYPES = new ThankAddressType[] { PSEUDONYMOUS, NATURAL_PERSON_CHILD, NATURAL_PERSON_ADULT,
  IDENTIFICATION_SERVICE_OFFICER, COMMUNITY_REPRESENTATIVE_L0, COMMUNITY_REPRESENTATIVE_L1, COMMUNITY_REPRESENTATIVE_L2, COMMUNITY_REPRESENTATIVE_L3,
  COMMUNITY_PROJECT_L0, COMMUNITY_PROJECT_L1, COMMUNITY_PROJECT_L2, COMMUNITY_PROJECT_L3, SUSTAINABILITY_REPRESENTATIVE_L0,
  SUSTAINABILITY_REPRESENTATIVE_L1, SUSTAINABILITY_REPRESENTATIVE_L2, SUSTAINABILITY_REPRESENTATIVE_L3, SUSTAINABILITY_PROJECT_L0,
  SUSTAINABILITY_PROJECT_L1, SUSTAINABILITY_PROJECT_L2, SUSTAINABILITY_PROJECT_L3, COMPANY };

  static {
    for (int i = TYPES.length - 1; i >= 0; i--) {
      assert ((TYPES[i].value & 0xFF) == i) : TYPES[i].value + "!=" + i;
    }
  }

  private final byte value;

  private ThankAddressType(int value) {

    this((byte) value);
    assert (value <= 255);
  }

  private ThankAddressType(byte value) {

    super();
    this.value = value;
  }

  /**
   * @return {@code true} if pseudonymous address, {@code false} otherwise.
   */
  public boolean isPseudonymous() {

    return this.value == PSEUDONYMOUS.value;
  }

  /**
   * @return {@code true} if natural person (for token creation and person income), {@code false} otherwise.
   */
  public boolean isNaturalPerson() {

    return (this.value == NATURAL_PERSON_CHILD.value) || (this.value == NATURAL_PERSON_ADULT.value);
  }

  /**
   * @return {@code true} if address of identity service officer, {@code false} otherwise.
   */
  public boolean isIdentityServiceOfficer() {

    return this.value == IDENTIFICATION_SERVICE_OFFICER.value;
  }

  /**
   * @return {@code true} if address of community representative, {@code false} otherwise.
   */
  public boolean isCommunityRepresentative() {

    return ((this.value >= COMMUNITY_REPRESENTATIVE_L0.value) && (this.value <= COMMUNITY_REPRESENTATIVE_L3.value));
  }

  /**
   * @return {@code true} if address of community project, {@code false} otherwise.
   */
  public boolean isCommunityProject() {

    return ((this.value >= COMMUNITY_PROJECT_L0.value) && (this.value <= COMMUNITY_PROJECT_L3.value));
  }

  /**
   * @return {@code true} if address of sustainability representative, {@code false} otherwise.
   */
  public boolean isSustainabilityRepresentative() {

    return ((this.value >= SUSTAINABILITY_REPRESENTATIVE_L0.value) && (this.value <= SUSTAINABILITY_REPRESENTATIVE_L3.value));
  }

  /**
   * @return {@code true} if address of sustainability project, {@code false} otherwise.
   */
  public boolean isSustainabilityProject() {

    return ((this.value >= SUSTAINABILITY_PROJECT_L0.value) && (this.value <= SUSTAINABILITY_PROJECT_L3.value));
  }

  /**
   * @return {@code true} if company address, {@code false} otherwise.
   */
  public boolean isCompany() {

    return this.value == COMPANY.value;
  }

  /**
   * @return the {@link io.github.thanktoken.core.api.location.ThankLocation#getSegment(int) location segment index} of
   *         the {@link #isCommunityRepresentative() representative} or {@link #isCommunityProject() project} or
   *         {@code -1} for other types that are not assigned to such level.
   */
  public int getLevel() {

    if ((this.value >= 4) && (this.value <= 19)) {
      return this.value & 3;
    }
    return -1;
  }

  /**
   * This is an internal method that may change in future versions.
   *
   * @param blob the {@link Binary} to append.
   * @return the {@code byte[]} with this type followed by the given BLOB.
   */
  public byte[] appendBinary(Binary blob) {

    byte[] data = new byte[blob.getLength() + 1];
    blob.getData(data, 1);
    data[0] = this.value;
    return data;
  }

  /**
   * This is an internal method that may change in future versions.
   *
   * @param blob the {@code byte[]} to append.
   * @return the {@code byte[]} with this type followed by the given BLOB.
   */
  public byte[] appendBinary(byte[] blob) {

    byte[] data = new byte[blob.length + 1];
    System.arraycopy(blob, 0, data, 1, blob.length);
    data[0] = this.value;
    return data;
  }

  @Override
  public int hashCode() {

    return this.value;
  }

  @Override
  public boolean equals(Object obj) {

    if ((obj == null) || (obj.getClass() != getClass())) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    ThankAddressType other = (ThankAddressType) obj;
    if (this.value != other.value) {
      return false;
    }
    return true;
  }

  static ThankAddressType of(byte value) {

    int index = value & 0xFF;
    if (index < TYPES.length) {
      return TYPES[index];
    }
    return new ThankAddressType(value);
  }

}
