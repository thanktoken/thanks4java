package io.github.thanktoken.core.api.validate;

import io.github.thanktoken.core.api.target.ThankTarget;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * Represents the mode for {@link ThankValidator#validate(ThankToken, ThankValidationMode) validation}. Each
 * {@link io.github.thanktoken.core.api.token.ThankToken} has a
 * {@link io.github.thanktoken.core.api.token.ThankToken#getHeader() header} containing a
 * {@link io.github.thanktoken.core.api.location.ThankLocation location} and a
 * {@link io.github.thanktoken.core.api.target.ThankTarget target} it is initially created for. Also a
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

  private final ThankValidationFailureMode failureMode;

  private ThankValidationMode(boolean publicValue, boolean validateCreation, boolean validatePurpose, boolean offline) {

    this(publicValue, validateCreation, validatePurpose, offline, ThankValidationFailureMode.RETURN_RESULT);
  }

  private ThankValidationMode(boolean publicValue, boolean validateCreation, boolean validatePurpose, boolean offline,
      ThankValidationFailureMode failureMode) {

    super();
    this.publicValue = publicValue;
    this.validateCreation = validateCreation;
    this.validatePurpose = validatePurpose;
    this.offline = offline;
    this.failureMode = failureMode;
  }

  /**
   * @return {@code true} to only accept public value, what means that a {@link ThankToken} must have passed its
   *         originated destination. E.g. if its {@link ThankTokenHeader#getTarget() target} is of the
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
   * @param publicValue the new value of {@link #isPublicValue()}.
   * @return a (new) {@link ThankValidationMode} with {@link #isPublicValue() publicValue} set to the given value. Will
   *         be this instance if unchanged.
   */
  public ThankValidationMode setPublicValue(boolean publicValue) {

    if (this.validatePurpose == publicValue) {
      return this;
    } else {
      return new ThankValidationMode(this.publicValue, this.validateCreation, this.validatePurpose, this.offline, this.failureMode);
    }
  }

  /**
   * @return {@code true} to validate the creation of the {@link ThankToken}. Therefore the {@link ThankToken} has to be
   *         the generated as universal dividend by a {@link ThankTokenHeader#getRecipient() creator} that is a
   *         {@link io.github.thanktoken.core.api.address.ThankAddressType#isNaturalPerson() certified natural person}
   *         or via the fork and merge process (what is only fully verified by online nodes). Otherwise if {@code false}
   *         the creation of the {@link ThankToken} is trusted without verification what should only be done in very
   *         specific situations.
   */
  public boolean isValidateCreation() {

    return this.validateCreation;
  }

  /**
   * @param validateCreation the new value of {@link #isValidateCreation()}.
   * @return a (new) {@link ThankValidationMode} with {@link #isValidateCreation() validateCreation} set to the given
   *         value. Will be this instance if unchanged.
   */
  public ThankValidationMode setValidateCreation(boolean validateCreation) {

    if (this.validatePurpose == validateCreation) {
      return this;
    } else {
      return new ThankValidationMode(this.publicValue, validateCreation, this.validatePurpose, this.offline, this.failureMode);
    }
  }

  /**
   * @return {@code true} to validate the originated purpose of the {@link ThankToken}. E.g. if its
   *         {@link ThankTokenHeader#getTarget() target} is of the {@link ThankTarget#getType() type}
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
   * @param validatePurpose the new value of {@link #isValidatePurpose()}.
   * @return a (new) {@link ThankValidationMode} with {@link #isValidatePurpose() validatePurpose} set to the given
   *         value. Will be this instance if unchanged.
   */
  public ThankValidationMode setValidatePurpose(boolean validatePurpose) {

    if (this.validatePurpose == validatePurpose) {
      return this;
    } else {
      return new ThankValidationMode(this.publicValue, this.validateCreation, validatePurpose, this.offline, this.failureMode);
    }
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

  /**
   * @param offline the new value of {@link #isOffline()}.
   * @return a (new) {@link ThankValidationMode} with {@link #isOffline() offline} set to the given value. Will be this
   *         instance if unchanged.
   */
  public ThankValidationMode setOffline(boolean offline) {

    if (this.offline == offline) {
      return this;
    } else {
      return new ThankValidationMode(this.publicValue, this.validateCreation, this.validatePurpose, offline, this.failureMode);
    }
  }

  /**
   * @return {@code true} if a validation failure should immediately result in throwing an
   *         {@link io.github.thanktoken.core.api.validate.ThankValidationException}, {@code false} otherwise (to
   *         collect all validation errors and return them as
   *         {@link io.github.thanktoken.core.api.validate.ThankValidationResult}).
   */
  public ThankValidationFailureMode getFailureMode() {

    return this.failureMode;
  }

  /**
   * @param failureMode the new value of {@link #getFailureMode()}.
   * @return a (new) {@link ThankValidationMode} with {@link #getFailureMode() failureMode} set to the given value. Will
   *         be this instance if unchanged.
   */
  public ThankValidationMode setFailureMode(ThankValidationFailureMode failureMode) {

    if (this.failureMode == failureMode) {
      return this;
    } else {
      return new ThankValidationMode(this.publicValue, this.validateCreation, this.validatePurpose, this.offline, failureMode);
    }
  }
}
