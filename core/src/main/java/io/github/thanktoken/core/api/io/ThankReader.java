package io.github.thanktoken.core.api.io;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * Interface to {@link #readHeader(InputStream) read} a {@link ThankToken} from an {@link InputStream}.
 */
public interface ThankReader {

  /**
   * Deserializes an entire {@link ThankToken}. This method is most likely to be used for testing.
   *
   * @param header the {@link #readHeader(Reader) serialized} {@link ThankTokenHeader}.
   * @param tx the {@link #readTransaction(ThankTokenHeader, Reader) serialized} {@link ThankTransaction}s.
   * @return the parsed {@link ThankToken}.
   */
  ThankToken read(String header, String... tx);

  /**
   * Reads a {@link ThankToken} from the given {@link InputStream}.
   *
   * @param in is the {@link InputStream} where to read the {@link ThankToken} from.
   * @return the parsed {@link ThankToken}.
   */
  default ThankTokenHeader readHeader(InputStream in) {

    InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
    return readHeader(reader);
  }

  /**
   * Reads a {@link ThankToken} from the given {@link Reader}.
   *
   * @param reader is the {@link Reader} where to read the {@link ThankToken} from.
   * @return the parsed {@link ThankToken}.
   */
  ThankTokenHeader readHeader(Reader reader);

  /**
   * Reads a {@link ThankTransaction} from the given {@link InputStream}.
   *
   * @param header the {@link ThankTokenHeader} of the owning {@link ThankToken}.
   * @param in is the {@link InputStream} where to read the {@link ThankTransaction} from.
   * @return the parsed {@link ThankTransaction}.
   */
  default ThankTransaction readTransaction(ThankTokenHeader header, InputStream in) {

    InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
    return readTransaction(header, reader);
  }

  /**
   * Reads a {@link ThankTransaction} from the given {@link Reader}.
   *
   * @param header the {@link ThankTokenHeader} of the owning {@link ThankToken}.
   * @param reader is the {@link Reader} where to read the {@link ThankTransaction} from.
   * @return the parsed {@link ThankTransaction}.
   */
  ThankTransaction readTransaction(ThankTokenHeader header, Reader reader);

}
