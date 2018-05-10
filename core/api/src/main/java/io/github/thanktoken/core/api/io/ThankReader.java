package io.github.thanktoken.core.api.io;

import java.io.InputStream;
import java.io.Reader;

import io.github.thanktoken.core.api.ThankToken;
import io.github.thanktoken.core.api.header.ThankHeader;
import io.github.thanktoken.core.api.header.ThankVersion;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * Interface to {@link #readHeader(InputStream) read} a {@link ThankToken} from an {@link InputStream}.
 */
public interface ThankReader {

  /**
   * Deserializes an entire {@link ThankToken}. This method is most likely to be used for testing.
   *
   * @param header the {@link #readHeader(Reader) serialized} {@link ThankHeader}.
   * @param tx the {@link #readTransaction(ThankVersion, Reader) serialized} {@link ThankTransaction}s.
   * @return the parsed {@link ThankToken}.
   */
  ThankToken read(String header, String... tx);

  /**
   * Reads a {@link ThankToken} from the given {@link InputStream}.
   *
   * @param in is the {@link InputStream} where to read the {@link ThankToken} from.
   * @return the parsed {@link ThankToken}.
   */
  ThankHeader readHeader(InputStream in);

  /**
   * Reads a {@link ThankToken} from the given {@link Reader}.
   *
   * @param reader is the {@link Reader} where to read the {@link ThankToken} from.
   * @return the parsed {@link ThankToken}.
   */
  ThankHeader readHeader(Reader reader);

  /**
   * Reads a {@link ThankTransaction} from the given {@link InputStream}.
   *
   * @param version the {@link ThankVersion} of the owning {@link ThankToken}.
   * @param in is the {@link InputStream} where to read the {@link ThankTransaction} from.
   * @return the parsed {@link ThankTransaction}.
   */
  ThankTransaction readTransaction(ThankVersion version, InputStream in);

  /**
   * Reads a {@link ThankTransaction} from the given {@link Reader}.
   *
   * @param version the {@link ThankVersion} of the owning {@link ThankToken}.
   * @param reader is the {@link Reader} where to read the {@link ThankTransaction} from.
   * @return the parsed {@link ThankTransaction}.
   */
  ThankTransaction readTransaction(ThankVersion version, Reader reader);

}
