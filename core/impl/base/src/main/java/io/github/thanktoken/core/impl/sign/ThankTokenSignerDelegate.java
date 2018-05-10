package io.github.thanktoken.core.impl.sign;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.sign.ThankTokenSigner;
import io.github.thanktoken.core.api.strategy.ThankStrategyFactory;
import io.github.thanktoken.core.api.transaction.ThankTransaction;
import io.github.thanktoken.core.impl.ThankDelegate;
import net.sf.mmm.security.api.key.asymmetric.SecurityPrivateKey;
import net.sf.mmm.security.api.sign.SecuritySignature;

/**
 * This is an implementation of {@link ThankTokenSigner} that uses {@link ThankStrategyFactory} in order to sign
 * {@link ThankToken}s for arbitrary {@link ThankHeader#getVersion() versions}.
 */
public class ThankTokenSignerDelegate extends ThankDelegate implements ThankTokenSigner {

  private static final ThankTokenSignerDelegate INSTANCE = new ThankTokenSignerDelegate();

  /**
   * The constructor.
   */
  public ThankTokenSignerDelegate() {

    super();
  }

  /**
   * @return the singleton instance of this {@link ThankTokenSignerDelegate}.
   */
  public static final ThankTokenSignerDelegate get() {

    return INSTANCE;
  }

  @Override
  public SecuritySignature signHeader(ThankHeader header, SecurityPrivateKey privateKey) {

    return getStrategy(header).getSigner().signHeader(header, privateKey);
  }

  @Override
  public SecuritySignature signTransaction(ThankToken token, ThankTransaction newLine, SecurityPrivateKey privateKey) {

    return getStrategy(token).getSigner().signTransaction(token, newLine, privateKey);
  }

}
