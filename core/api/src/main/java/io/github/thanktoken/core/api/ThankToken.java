/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.api;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import io.github.thanktoken.core.api.currency.ThankCurrency;
import io.github.thanktoken.core.api.currency.Thanks;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.header.ThankTarget;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.api.validate.ThankTokenValidator;

/**
 * A {@link ThankToken} represents an atomic unit of value. It has the following goals and principles:
 * <ul>
 * <li>A trade and exchange system that brings equity, freedom, humanity, and sustainability.</li>
 * <li>Each participating person can create positive value as {@link ThankToken}s according to strict rules.</li>
 * <li>Hence, a person is a <em>value factor</em> and not a <em>cost factor</em> - even if the person is not considered
 * <em>productive</em> in the sense of our current monetary system.</li>
 * <li>The {@link ThankToken}s per person are created for different {@link ThankHeader#getTarget() targets} and
 * {@link ThankTarget#getType() types}. This includes {@link ThankTarget#PERSON_INCOME person income},
 * {@link ThankTarget#TYPE_COMMUNITY tax}, and {@link ThankTarget#TYPE_SUSTAINABILITY environment fund}.</li>
 * <li>Like in nature a {@link ThankToken} looses its {@link #getValue() value} over time. This way money is not an
 * unlimited store of value and purchasing power. In our current monetary system especially with US dollars money is
 * entirely created via debt. Even worse for the debt you need to pay interest. Rich people can spare money and take it
 * out of the real economy. This results in a continuous need for more debt. The rich people get richer and the poor
 * people get poorer. This madness can be stopped with monetary systems like {@link Thanks} that have a small negative
 * interest on its {@link #getValue() value}. If this gets combined with a new definition of possession and ownership,
 * we can really create the values stated at the top.</li>
 * </ul>
 * A {@link ThankToken} is created with a fixed {@link #getHeader() header} containing data such as the
 * {@link ThankHeader#getAmount() amount} of the raw base unit of a {@link ThankHeader#getCurrency() currency}. However,
 * its actual {@link #getValue() value} varies depending on the time elapsed from the creation
 * {@link ThankHeader#getTimestamp() timestamp} to the current date. The default currency is {@link Thanks} that uses a
 * fixed percentage as negative interest per day. So with every day the {@link ThankToken} will loose a very small
 * amount of its {@link #getValue() value}. The {@link ThankHeader#getCurrency() currency} is not hard-coded as it was
 * easy to make it exchangeable by design. This allows people to experiment with other currencies by changing the
 * parameters of the {@link #getValue() value} {@link ThankCurrency#getValue(ThankToken, java.time.Instant)
 * calculation}. Still they can reuse the entire infrastructure of {@link ThankToken} including its wallets, creation
 * and transaction infrastructure.<br>
 * <br>
 * Further, each {@link ThankToken} has a {@link List} of {@link #getTransactions() transactions}. Each
 * {@link ThankTransaction transaction} transfers the {@link ThankToken} from one owner to the next.<br>
 * <br>
 * However, this so far does not solve the <em>double spending problem</em> solved by block-chains. Therefore,
 * {@link ThankToken}s are tracked in a distributed <em>public storage</em> in the cloud to solve this and prevent
 * somebody from transferring the same {@link ThankToken} to two different people. Such action is called <em>split</em>
 * and is illegal. It will be detected during the {@link ThankTokenValidator validation} in the public store. In such
 * case the {@link ThankToken} gets blocked and banned for further transactions for a predefined period (e.g. two
 * days).<br>
 * The advantages over regular block-chains are as following:
 * <ul>
 * <li>You (or a wallet) only need(s) to store the {@link ThankToken} owned by you and even more important your
 * key-pairs. In other words you only need more storage if you own more {@link ThankToken}s. You can even use your
 * mobile device for storing common amounts of money and performing transactions (payment).</li>
 * <li>Only the validation against the <em>public storage</em> requires more resources but is performed via a secure
 * cloud service using a peer-to-peer network where everybody can participate.</li>
 * <li>A regular block-chain quickly grows up to many giga- or even tera-bytes of data and can never shrink. However,
 * the public ledger of {@link ThankToken} is clustered by the {@link ThankHeader#getLocation() location} of the
 * {@link ThankToken}s. Therefore a node may only store and validate the {@link ThankToken}s for a small region (e.g. a
 * city) which means only a small percentage of the overall data. Still each node can decide which
 * {@link ThankHeader#getLocation() locations} to cover and support up to the entire world.</li>
 * <li>As the cryptographic chain for each transaction is appended to the corresponding token there are no problems
 * regarding the order of the transactions. This massively reduces complexity and makes things much more simple.</li>
 * <li>All this requires only little resources compared to the "waste" of hardware, energy and storage of regular
 * block-chains.</li>
 * <li>Unfortunately, cryptography is threatened by quantum computers. And this is especially true for asymmetric
 * cryptography as used by {@link ThankToken}. To be prepared for what may come, each {@link ThankToken} contains a
 * {@link ThankHeader#getVersion() version}. With each version the underlying cryptographic algorithm can be improved.
 * Further there is a <em>renewal process</em> for exchanging old {@link ThankToken}s with new ones with the same total
 * {@link #getValue() value}. New generations can follow as required. This makes {@link ThankToken}s future proof.</li>
 * <li>As {@link ThankToken}s continuously grow in size (by adding {@link #getTransactions() validity lines} for each
 * transaction), they can also be exchanged using the <em>renewal process</em> after a reasonable size is reached. This
 * additionally saves resources such as storage and bandwidth.</li>
 * </ul>
 */
public interface ThankToken {

  /**
   * @return the {@link ThankHeader header} with the main data of this token such as the {@link ThankHeader#getAmount()
   *         amount}.
   */
  ThankHeader getHeader();

  /**
   * A {@link ThankToken} is a digital coin that has one owner at a time. Therefore, it contains a {@link List} of
   * {@link ThankTransaction}s that represents the entire transaction history of the {@link ThankToken}. Each
   * {@link ThankTransaction} represents a transaction transferring the {@link ThankToken} from its previous owner to
   * the next owner.
   *
   * @return {@link List} of {@link ThankTransaction}s for trace and validation of {@link ThankToken}.
   */
  List<? extends ThankTransaction> getTransactions();

  /**
   * @return the current value of this {@link ThankToken} in the actual {@link ThankHeader#getCurrency() currency}. The
   *         value is determined from the original {@link ThankHeader#getAmount() amount} and changes over the time
   *         {@link ThankCurrency#getValue(ThankToken, Instant) according} to its {@link ThankHeader#getCurrency()
   *         currency}.
   * @see #getValue(Instant)
   */
  default BigDecimal getValue() {

    return getValue(Instant.now());
  }

  /**
   * @see #getValue()
   *
   * @param time is the point in time for which the value shall be calculated. Should not be before the
   *        {@link ThankHeader#getTimestamp() creation timestamp}.
   * @return the value of this {@link ThankToken} at the given {@link Instant}.
   */
  default BigDecimal getValue(Instant time) {

    ThankCurrency currency = getHeader().getCurrency();
    if (currency == null) {
      return null;
    }
    return currency.getValue(this, time);
  }

}
