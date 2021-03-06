package io.github.thanktoken.core.impl.sign;

import net.sf.mmm.crypto.asymmetric.key.SecurityPrivateKey;
import net.sf.mmm.crypto.asymmetric.sign.SignatureBinary;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.sign.ThankSigner;
import io.github.thanktoken.core.api.strategy.ThankStrategyFactory;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.impl.ThankDelegate;

/**
 * This is an implementation of {@link ThankSigner} that uses {@link ThankStrategyFactory} in order to sign
 * {@link ThankToken}s for arbitrary {@link ThankHeader#getVersion() versions}.
 */
public class ThankSignerDelegate extends ThankDelegate implements ThankSigner {

  private static final ThankSignerDelegate INSTANCE = new ThankSignerDelegate();

  /**
   * The constructor.
   */
  public ThankSignerDelegate() {

    super();
  }

  /**
   * @return the singleton instance of this {@link ThankSignerDelegate}.
   */
  public static final ThankSignerDelegate get() {

    return INSTANCE;
  }

  @Override
  public SignatureBinary signHeader(ThankHeader header, SecurityPrivateKey privateKey) {

    return getStrategy(header).getSigner().signHeader(header, privateKey);
  }

  @Override
  public SignatureBinary signTransaction(ThankToken token, ThankTransaction newLine, SecurityPrivateKey privateKey) {

    return getStrategy(token).getSigner().signTransaction(token, newLine, privateKey);
  }

}
