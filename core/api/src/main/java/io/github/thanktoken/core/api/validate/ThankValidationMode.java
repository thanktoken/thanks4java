package io.github.thanktoken.core.api.validate;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.header.ThankTarget;
import io.github.thanktoken.core.api.identity.ThankIdentityType;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * Represents the mode for {@link ThankTokenValidator#validate(ThankToken, ThankValidationMode) validation}. Each
 * {@link io.github.thanktoken.core.api.ThankToken} has a {@link io.github.thanktoken.core.api.ThankToken#getHeader()
 * header} containing a {@link io.github.thanktoken.core.api.header.ThankLocation location} and a
 * {@link io.github.thanktoken.core.api.header.ThankTarget target} it is initially created for. Also a
 * {@link ThankToken} may be created from forking or merging of other {@link ThankToken}(s).
 */
public class ThankValidationMode {

  /** {@link ThankValidationMode} for a full validation as {@link #isPublicValue() public value}. */
  public static final ThankValidationMode FULL = new ThankValidationMode(true, true, true, false);

  /**
   * {@link ThankValidationMode} for a validation as an official institute receiving non-{@link #isPublicValue() public
   * value} such as a "government" or a company supporting sustainability.
   */
  public static final ThankValidationMode INSTITUTE = new ThankValidationMode(false, true, true, false);

  /** {@link ThankValidationMode} for an offline validation. */
  public static final ThankValidationMode OFFLINE = new ThankValidationMode(true, true, true, true);

  /** {@link ThankValidationMode} for an minimal validation (only formal validation). */
  public static final ThankValidationMode MINIMAL = new ThankValidationMode(false, false, false, true);

  private final boolean publicValue;

  private final boolean validateCreation;

  private final boolean validatePurpose;

  private final boolean offline;

  /**
   * The constructor.
   */
  private ThankValidationMode(boolean publicValue, boolean validateCreation, boolean validatePurpose, boolean offline) {

    super();
    this.publicValue = publicValue;
    this.validateCreation = validateCreation;
    this.validatePurpose = validatePurpose;
    this.offline = offline;
  }

  /**
   * @return {@code true} to only accept public value, what means that a {@link ThankToken} must have passed its
   *         originated destination. E.g. if its {@link ThankHeader#getTarget() target} is of the
   *         {@link ThankTarget#getType() type} {@link ThankTarget#TYPE_COMMUNITY community} or
   *         {@link ThankTarget#TYPE_SUSTAINABILITY sustainability} then the {@link ThankToken} may only be accepted as
   *         public value after it was spent by the community or for sustainability. Otherwise if {@code false} this
   *         check will be omitted, what is e.g. required for receivers of the {@link ThankToken} on its way to its
   *         actual originated destination and purpose.
   */
  public boolean isPublicValue() {

    return this.publicValue;
  }

  /**
   * @return {@code true} to validate the creation of the {@link ThankToken}. Therefore the {@link ThankToken} has to be
   *         the generated as universal dividend by a {@link ThankHeader#getRecipient() creator} that is a
   *         {@link ThankIdentityType#NATURAL_PERSON certified natural person} or via the fork and merge process (what
   *         is only fully verified by online nodes). Otherwise if {@code false} the creation of the {@link ThankToken}
   *         is trusted without verification what should only be done in very specific situations.
   */
  public boolean isValidateCreation() {

    return this.validateCreation;
  }

  /**
   * @return {@code true} to validate the originated purpose of the {@link ThankToken}. E.g. if its
   *         {@link ThankHeader#getTarget() target} is of the {@link ThankTarget#getType() type}
   *         {@link ThankTarget#TYPE_COMMUNITY community} or {@link ThankTarget#TYPE_SUSTAINABILITY sustainability} and
   *         the {@link ThankToken} was already spent by for that purpose, the {@link ThankTransaction#getRecipient()
   *         recipient}(s) for that purpose will be validated. Otherwise if {@code false} the
   *         {@link ThankTransaction#getRecipient() recipient(s)} for the originated purpose will be trusted without
   *         verification what should only be done in very specific situations.
   */
  public boolean isValidatePurpose() {

    return this.validatePurpose;
  }

  /**
   * @return {@code true} if the validation should not fail due to network issues (because you are offline). In that
   *         case validation checks such as {@link #isValidateCreation() creation} and {@link #isValidatePurpose()
   *         purpose} can only be performed if the required data is available in cache. While offline validation is
   *         better than no validation you should only accept it for smaller {@link ThankToken#getValue() values} and if
   *         you trust the previous owner. Otherwise if {@code false} network issues will cause the validation to fail
   *         what is the recommended default.
   */
  public boolean isOffline() {

    return this.offline;
  }
}
