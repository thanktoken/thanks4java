package io.github.thanktoken.core.api;

import io.github.thanktoken.core.api.address.ThankAddressType;
import io.github.thanktoken.core.api.algorithm.ThankAlgorithmSecp256k1;

/**
 * TODO hohwille This type ...
 *
 * @since 1.0.0
 */
public class Demo {

  public static void main(String[] args) {

    ThankAlgorithmSecp256k1 algorithm = ThankAlgorithmSecp256k1.get();
    TestKeyPair keyPair = new TestKeyPair(ThankAddressType.NATURAL_PERSON_ADULT, algorithm);
    System.out.println(algorithm.getKeyCreator().asBinary(keyPair.getPrivateKey()).formatBase64());
    System.out.println(algorithm.getKeyCreator().asBinary(keyPair.getPublicKey()).formatBase64());
    System.out.println(keyPair.getAddress().formatBase64());
  }

}
