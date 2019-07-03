/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.impl.v002;

import net.sf.mmm.crypto.asymmetric.access.ec.bc.Curve25519;
import net.sf.mmm.crypto.asymmetric.key.AsymmetricKeyCreator;
import net.sf.mmm.crypto.asymmetric.key.AsymmetricKeyPair;

/**
 * {@link Curve25519} {@link AsymmetricKeyPair key pairs} for testing.
 */
public interface TestKeyPairsV002 {

  /** {@link Curve25519}. */
  Curve25519 CURVE25519 = Curve25519.create();

  /** {@link AsymmetricKeyCreator} for {@link Curve25519}. */
  AsymmetricKeyCreator KEY_CREATOR = CURVE25519.newKeyCreator();

  /** Test {@link AsymmetricKeyPair}. */
  AsymmetricKeyPair KEY_PERSON_1 = KEY_CREATOR.deserializeKeyPair("A61CGxrl79r91REWrDVHnkuIDXivMU6Xogfgl3mGJ+I=",
      "AjFP+4dtZBXTwGTQd8SSSWq2VRSmsRiPZdlwFx2tzu/v");

  /** Test {@link AsymmetricKeyPair}. */
  AsymmetricKeyPair KEY_PERSON_2 = KEY_CREATOR.deserializeKeyPair("D/MbFzUlVrOL/yNpUARoZHfAbNugtF0zynkt/Wq6lVE=",
      "A0wfOCzxVYtELPk27ELyFuZoIceUXVhCKjQN7JAkfJdm");

  /** Test {@link AsymmetricKeyPair}. */
  AsymmetricKeyPair KEY_PSEUDONYM = KEY_CREATOR.deserializeKeyPair("DsrUgobCOnKa4gqPaTQePYagHhMdF76oavHwKG2gB7M=",
      "Ajj2WOGKCftSsjSqWH8lJBqpK3kcEYFM9JWLJDZRvMG6");

  /** Test {@link AsymmetricKeyPair}. */
  AsymmetricKeyPair KEY_COUNTRY_DE = KEY_CREATOR.deserializeKeyPair("BOZMNd0DsUPjUhFX2ALV7TA4J7rEl8DBV+XGMVr89KY=",
      "Ax1IbFrB4nkIrgGhXwjoRjDHauW307l6yaBWJNI0OTY3");

  /** Test {@link AsymmetricKeyPair}. */
  AsymmetricKeyPair KEY_ORGANIZATION_1 = KEY_CREATOR.deserializeKeyPair("DEH7EQwDCopCCyMB5GGEHGq4GGuV3ZSfITF8LOT39ww=",
      "AlU1WDBNuJti+j3jyoHaRXp6G3xDZxOOXmJby1DR+xa/");

  /** Test {@link AsymmetricKeyPair}. */
  AsymmetricKeyPair KEY_SUSTAINANILITY_1 = KEY_CREATOR.deserializeKeyPair("B9sJu6I4mgQFENr4CkzCDuQa/sl2fdp05cW0GRDwV7U=",
      "Axq794j0nY3ja6HBeUqRUeibfG7g/5jjuBW/7E5yHc0N");

  /** Test {@link AsymmetricKeyPair}. */
  AsymmetricKeyPair KEY_STATE = KEY_CREATOR.deserializeKeyPair("BSybYZtuEbV77rmPR1SHEmSUhf37iqLD+w/N+xG9uZg=",
      "AnXYXEuldb0l75gwxfxtgWsZ7NAGi+gcmkdKgLy/2G9W");

  /** Test {@link AsymmetricKeyPair}. */
  AsymmetricKeyPair KEY_COMMUNITY = KEY_CREATOR.deserializeKeyPair("BfcsvGZPYZW+L85DFJMeEfBTTxO8cmIBfPGNY7jyNFQ=",
      "Ag5Mz+9zV79pKwOkJMLk2nqjZT2LG3ZzFSWZdBWICivI");

}
