package io.github.thanktoken.core.api;

import net.sf.mmm.crypto.asymmetric.sign.SignatureBinary;

import io.github.thanktoken.core.api.sign.ThankSignerImpl;
import io.github.thanktoken.core.api.token.ThankTokenBean;
import io.github.thanktoken.core.api.token.header.ThankTokenHeaderBean;
import io.github.thanktoken.core.api.transaction.ThankTransactionBean;

/**
 * Main class to update signatures for {@link TestData}.
 */
public class TestDataUpdater {

  public static void main(String[] args) {

    ThankSignerImpl signer = new ThankSignerImpl();
    ThankTokenHeaderBean header = new ThankTokenHeaderBean(TestData.TEST_TOKEN_P4_HEADER);
    header.setSignature(null);
    SignatureBinary signature = signer.signHeader(header, TestData.TEST_KEY_PAIR_NP.getPrivateKey());
    header.setSignature(signature);
    System.out.println("TEST_TOKEN_P4_HEADER_SIGNATURE = " + signature);

    ThankTokenBean token = new ThankTokenBean(header);
    ThankTransactionBean tx = new ThankTransactionBean(TestData.TEST_TOKEN_P4_TX_0);
    tx.setSignature(null);
    token.addTransaction(tx);
    signature = signer.signTransaction(token, tx, TestData.TEST_KEY_PAIR_NP.getPrivateKey());
    System.out.println("TEST_TOKEN_P4_TX_0_SIGNATURE = " + signature);
  }

}
