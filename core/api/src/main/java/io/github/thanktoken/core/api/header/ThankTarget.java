package io.github.thanktoken.core.api.header;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.datatype.StringType;

/**
 * Each {@link ThankToken} initially gets created for a specific
 * {@link io.github.thanktoken.core.api.header.ThankHeader#getTarget() target} that defines the {@link #getType() type}
 * and {@link #getLevel() level}. This {@link ThankTarget target} is the initial purpose the {@link ThankToken} has to
 * be spent for in order to become regular money.
 *
 * @see io.github.thanktoken.core.api.header.ThankHeader#getTarget()
 */
public class ThankTarget extends StringType {

  /**
   * The {@link #getType() type} for money that has initially been created for an individual person. A
   * {@link io.github.thanktoken.core.api.ThankToken}
   * {@link io.github.thanktoken.core.api.header.ThankHeader#getTarget() with} this target is only valid, if it has
   * passed all the way down to last {@link ThankLocation#getSegment(int) location segment} and then to the according
   * person. As each person can verify his personal income it will easily be assured that enough money gets created. As
   * anybody can verify the total money that gets created it can also be ensured that only the correct amount of money
   * is produced. This amount simply depends on the number of people registered in the according {@link ThankLocation
   * location}, what can also be verified.<br>
   * <b>ATTENTION:</b> The {@link #getLevel() level} of this type always has to be {@code 0} by definition.<br>
   * <br>
   * Each person is valuable and hence gets an equal basic income. However, the purchasing power of the money depends on
   * the amount and quality of the work people are willing to do. So if all people would think that they can do nothing
   * and just live from basic income it will be worth nothing. But assuming that people are good willing and motivated
   * to do something valuable for the community the value of the money will be given. The more motivated people are
   * especially because they are really free to decide what they want to work and think that it will be valuable for the
   * community the more wealth it will be.
   */
  public static final String TYPE_PERSON = "P";

  /**
   * This is the {@link #getType() type} for <em>community income</em> what is money that has initially been created for
   * the community of the {@link ThankLocation#getSegment(int) location segment} corresponding to its
   * {@link ThankTarget#getLevel() level}. Such money is for the purpose currently addressed with taxes. The government
   * acts in the will of the represented people when spending the money. As such money is simply created, it is just
   * there and allows to be spend for according services (education, health, public infrastructure, etc.). Tax does not
   * have to be taken away from people working for money. Hence, no laws are needed for taxes, no people need to control
   * tax payment and nobody can be fined for not paying taxes. All the saved potential can be used for jobs that provide
   * more value to the community.<br>
   * A {@link io.github.thanktoken.core.api.ThankToken}
   * {@link io.github.thanktoken.core.api.header.ThankHeader#getTarget() with} this target is only valid, if it has
   * passed all the way down to last {@link ThankLocation#getSegment(int) location segment}, from there to a
   * {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getRecipient() recipient} published by the
   * corresponding government and then to any public
   * {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getRecipient() recipient} (a company that earned
   * the tax for providing a service). This way it gets transparent where the government spent the money. As the people
   * represented by the according government actively get involved (e.g. by via
   * {@link io.github.thanktoken.core.api.currency.Vote}s) that the money is supposed to be spent for reasonable and
   * mainly regional things.
   */
  public static final String TYPE_COMMUNITY = "C";

  /**
   * This is the {@link #getType() type} for <em>sustainability income</em> what is money that has initially been
   * created as a fund for sustainability (mainly ecologic invests for the enviroment but also preservation order) on
   * the according {@link ThankTarget#getLevel() level}. This kind of money is like {@link #TYPE_COMMUNITY community
   * income} but has the requirement that it only gets valid money if the government transfers it to a public
   * {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getRecipient() recipient} that is certified as a
   * company serving the environment or other sustainable goals. There has to be an institute controlling such companies
   * and verifying that they spend this money solely to deal sustainability (e.g. to growing trees, renaturation, rescue
   * endangered species, preserve historic monuments, etc.). Public reports have to be published about all these
   * activities and all the money that has been spent. There will be no more argument anymore like "there is no money
   * left to save the environment". If there are not enough projects available to spend the money from this fund, it has
   * to be left in the fund (even though it will lose value over time). This potential will generate ideas and projects
   * unless the environment is clean and healthy (maybe in hundreds of years) and then it is fine to just let the money
   * be. As long as compliant with the government and its represented people, this money may also be spent to subsidize
   * sustainable products and projects.
   */
  public static final String TYPE_SUSTAINABILITY = "S";

  /** {@link ThankTarget} for {@link #TYPE_PERSON person} income. */
  public static final ThankTarget PERSON_INCOME = new ThankTarget("P3");

  /** {@link ThankTarget} for {@link #TYPE_COMMUNITY tax} of the entire {@link ThankLocation#getCountry() country}. */
  public static final ThankTarget COMMUNITY_COUNTRY = new ThankTarget("C0");

  /** {@link ThankTarget} for {@link #TYPE_COMMUNITY tax} of a {@link ThankLocation#getState() state}. */
  public static final ThankTarget COMMUNITY_STATE = new ThankTarget("C1");

  /** {@link ThankTarget} for {@link #TYPE_COMMUNITY tax} of a single {@link ThankLocation#getRegion() region}. */
  public static final ThankTarget COMMUNITY_REGION = new ThankTarget("C2");

  /**
   * {@link ThankTarget} for {@link #TYPE_SUSTAINABILITY sustainability} of an entire {@link ThankLocation#getCountry()
   * country}.
   */
  public static final ThankTarget SUSTAINABILITY_COUNTRY = new ThankTarget("S0");

  /**
   * {@link ThankTarget} for {@link #TYPE_SUSTAINABILITY sustainability} of a {@link ThankLocation#getState() state}.
   */
  public static final ThankTarget SUSTAINABILITY_STATE = new ThankTarget("S1");

  /**
   * {@link ThankTarget} for {@link #TYPE_SUSTAINABILITY sustainability} of a {@link ThankLocation#getRegion() region}.
   */
  public static final ThankTarget SUSTAINABILITY_REGION = new ThankTarget("S2");

  /** An {@link Collections#unmodifiableSet(Set) unmodifiable} {@link Set} with all official {@link ThankTarget}s. */
  public static final Set<ThankTarget> OFFICIAL_TARGETS = Collections.unmodifiableSet(new HashSet<>(
      Arrays.asList(PERSON_INCOME, COMMUNITY_COUNTRY, COMMUNITY_STATE, COMMUNITY_REGION, SUSTAINABILITY_COUNTRY, SUSTAINABILITY_STATE, SUSTAINABILITY_REGION)));

  private String type;

  private int level;

  /** The {@link #getMaxLength() maximum length} of a {@link ThankTarget} in bytes. */
  public static final int MAX_LENGTH = 10;

  /**
   * The constructor.
   *
   * @param value - see {@link #getValue()}.
   */
  protected ThankTarget(String value) {

    super(value);
    int length = value.length();
    if (length < 2) {
      throw new IllegalStateException("Invalid value '" + value + "' - has to be at least two charaters long.");
    }
    char lastChar = value.charAt(length - 1);
    if (!isDigit(lastChar)) {
      throw new IllegalStateException("Invalid value '" + value + "' - has to end with a digit.");
    }
    this.level = lastChar - '0';
    this.type = value.substring(0, length - 1);
  }

  /**
   * @param lastChar
   * @return
   */
  private boolean isDigit(char c) {

    return ((c >= '0') && (c <= '9'));
  }

  @Override
  public int getMaxLength() {

    return MAX_LENGTH;
  }

  /**
   * @return the type that is the initial purpose the {@link io.github.thanktoken.core.api.ThankToken} has to be spent
   *         for in order to become regular money. The exact meaning depends on {@link ThankVersion version} and
   *         {@link ThankCurrency currency} but the common targets are:
   *         <ul>
   *         <li>{@link #TYPE_PERSON Person} (P)</li>
   *         <li>{@link #TYPE_COMMUNITY Community (tax)} (C)</li>
   *         <li>{@link #TYPE_SUSTAINABILITY Sustainability} (S)</li>
   *         </ul>
   */
  public String getType() {

    return this.type;
  }

  /**
   * @return the level of this {@link ThankTarget} that typically represents the {@link ThankLocation#getSegment(int)
   *         segment index} the {@link io.github.thanktoken.core.api.ThankToken} was initially created for.
   */
  public int getLevel() {

    return this.level;
  }

  /**
   * @return {@link #TYPE_COMMUNITY}{@link #equals(Object) equals}({@link #getType() type})
   */
  public boolean isTypeCommunity() {

    return TYPE_COMMUNITY.equals(this.type);
  }

  /**
   * @return {@link #TYPE_PERSON}{@link #equals(Object) equals}({@link #getType() type})
   */
  public boolean isTypePerson() {

    return TYPE_PERSON.equals(this.type);
  }

  /**
   * @return {@link #TYPE_SUSTAINABILITY}{@link #equals(Object) equals}({@link #getType() type})
   */
  public boolean isTypeSustainability() {

    return TYPE_SUSTAINABILITY.equals(this.type);
  }

  /**
   * @param value - see {@link #getValue()}. May be <code>null</code>.
   * @return the {@link ThankTarget} or <code>null</code> if <code>value</code> was <code>null</code>.
   */
  public static ThankTarget of(String value) {

    if (value == null) {
      return null;
    }
    return new ThankTarget(value);
  }
}
