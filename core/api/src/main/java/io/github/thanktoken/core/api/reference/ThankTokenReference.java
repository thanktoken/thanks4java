package io.github.thanktoken.core.api.reference;

import io.github.thanktoken.core.api.attribute.AttributeReadLocation;
import io.github.thanktoken.core.api.attribute.AttributeReadRecipient;
import io.github.thanktoken.core.api.attribute.AttributeReadTimestamp;
import io.github.thanktoken.core.api.attribute.AttributeReadVersion;
import net.sf.mmm.security.api.key.asymmetric.SecurityPublicKey;

/**
 * Identifier that references a {@link io.github.thanktoken.core.api.ThankToken}.
 */
public interface ThankTokenReference extends AttributeReadVersion, AttributeReadTimestamp, AttributeReadLocation, AttributeReadRecipient {

  /**
   * @return the {@link SecurityPublicKey} representing the original
   *         {@link io.github.thanktoken.core.api.header.ThankHeader#getRecipient() recipient} (creator) of the
   *         referenced {@link io.github.thanktoken.core.api.ThankToken}.
   */
  @Override
  SecurityPublicKey getRecipient();

}
