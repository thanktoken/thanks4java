package io.github.thanktoken.core.api.identity.confirm;

import io.github.mmm.crypto.asymmetric.sign.SignatureBinary;
import io.github.mmm.crypto.hash.HashCreator;
import io.github.thanktoken.core.api.data.AbstractThankDataObjectWithHash;

/**
 * TODO hohwille This type ...
 *
 * @since 1.0.0
 */
public class AbstractThankIdentityConfirmation extends AbstractThankDataObjectWithHash {

  @Override
  public SignatureBinary getSignature() {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected byte[] createHash2Sign(HashCreator hashCreator) {

    // TODO Auto-generated method stub
    return null;
  }

}
