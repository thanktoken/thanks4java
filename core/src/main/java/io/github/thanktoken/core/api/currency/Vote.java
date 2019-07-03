package io.github.thanktoken.core.api.currency;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import io.github.thanktoken.core.api.target.ThankTarget;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.value.ThankValue;

/**
 * This {@link ThankCurrency currency} is called <em>Vote</em> and represents a voting {@link ThankToken token}. It has
 * a static {@link #getValue(ThankToken, Instant) amount value} that does not change over time. With this
 * {@link ThankToken token} you are permitted to vote for a particular decision like an election. In order to place your
 * vote you simply perform a transaction that transfers the token to the
 * {@link io.github.thanktoken.core.api.address.ThankAddress} of the voting decision as
 * {@link io.github.thanktoken.core.api.transaction.ThankTransaction#getRecipient() recipient}. For a vote the actual
 * decision is publicly announced together with all available options and their
 * {@link io.github.thanktoken.core.api.address.ThankAddress}es as well as the voting start and deadline. Further, the
 * voting tokens are created and distributed for all involved people. For anonymity the assignment of tokens to people
 * is done with random and additional exchange is possible to avoid traceability from voting {@link ThankToken tokens}
 * to people. When the voting deadline is reached all the votes can be counted as they are all public and anyone can
 * verify that his particular vote has been included correctly. However, nobody can trace down a voting
 * {@link ThankToken token} except his own. All voting {@link ThankToken tokens} transferred after the voting deadline
 * are considered invalid and will be ignored. The same applies for voting {@link ThankToken tokens} with a
 * {@link ThankTokenHeader#getTimestamp() timestamp} that differs from the voting start day (time is ignored).
 */
public class Vote extends ThankCurrency {

  /** The {@link #getValue() name} of this "currency". */
  public static final String VOTE = "Vote";

  /** The singleton instance. */
  public static final Vote INSTANCE = new Vote();

  private static final Set<ThankTarget> VALID_TARGETS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(ThankTarget.PERSON_INCOME)));

  /**
   * The constructor.
   */
  public Vote() {

    super(VOTE);
  }

  @Override
  protected Set<ThankTarget> getValidTargets() {

    return VALID_TARGETS;
  }

  @Override
  public ThankValue getValue(ThankToken token, Instant time) {

    return token.requireHeader().getAmount();
  }

  @Override
  public ThankValue getMinAmount() {

    return ThankValue.VALUE_1;
  }

  @Override
  public ThankValue getMaxAmount() {

    return ThankValue.VALUE_1;
  }

}
