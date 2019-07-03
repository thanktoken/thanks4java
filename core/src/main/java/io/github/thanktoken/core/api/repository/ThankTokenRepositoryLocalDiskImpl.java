package io.github.thanktoken.core.api.repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import io.github.thanktoken.core.api.id.ThankTokenId;
import io.github.thanktoken.core.api.id.ThankTokenIdType;
import io.github.thanktoken.core.api.io.ThankNetworkException;
import io.github.thanktoken.core.api.io.ThankReader;
import io.github.thanktoken.core.api.io.ThankReaderImpl;
import io.github.thanktoken.core.api.io.ThankWriter;
import io.github.thanktoken.core.api.io.ThankWriterImpl;
import io.github.thanktoken.core.api.token.ThankToken;
import io.github.thanktoken.core.api.token.ThankTokenType;
import io.github.thanktoken.core.api.token.header.ThankTokenHeader;
import io.github.thanktoken.core.api.transaction.ThankTransaction;

/**
 * TODO hohwille This type ...
 *
 * @since 1.0.0
 */
public class ThankTokenRepositoryLocalDiskImpl extends AbstractThankTokenRepository implements ThankTokenRepositoryLocalDisk {

  private static final String FILENAME_HEADER = "h.json";

  private final Path repositoryPath;

  private final ThankReader reader;

  private final ThankWriter writer;

  /**
   * The constructor.
   *
   * @param repositoryPath
   */
  public ThankTokenRepositoryLocalDiskImpl(Path repositoryPath) {

    this(repositoryPath, ThankReaderImpl.get(), ThankWriterImpl.get());
  }

  /**
   * The constructor.
   *
   * @param repositoryPath
   */
  public ThankTokenRepositoryLocalDiskImpl(Path repositoryPath, ThankReader reader, ThankWriter writer) {

    super();
    this.repositoryPath = repositoryPath;
    this.reader = reader;
    this.writer = writer;
  }

  protected Path getPath(ThankTokenId id) {

    Path path = this.repositoryPath;
    List<String> relativePath = getRelativePath(id);
    for (String segment : relativePath) {
      path = path.resolve(segment);
    }
    return path;
  }

  @Override
  public ThankToken find(ThankTokenIdType id) throws ThankNetworkException {

    Objects.requireNonNull(id, "id");
    Path folder = getPath(id);
    if (Files.isDirectory(folder)) {
      Path headerFile = folder.resolve(FILENAME_HEADER);
      ThankTokenHeader header;
      try (InputStream in = Files.newInputStream(headerFile)) {
        header = this.reader.readHeader(in);
      } catch (IOException e) {
        throw new IllegalStateException("Failed to read token header from " + headerFile, e);
      }
      ThankTokenType token = new ThankTokenType(header);
      int txIndex = 0;
      while (true) {
        Path txFile = folder.resolve(getTxFilename(txIndex));
        if (!Files.isRegularFile(txFile)) {
          break;
        }
        try (InputStream in = Files.newInputStream(txFile)) {
          ThankTransaction tx = this.reader.readTransaction(header, in);
          token.addTransaction(tx);
        } catch (IOException e) {
          throw new IllegalStateException("Failed to read token tx from " + txFile, e);
        }
      }
      return token;
    }
    return null;
  }

  private String getTxFilename(int txIndex) {

    return "tx" + txIndex + ".json";
  }

  @Override
  public void save(ThankToken token) {

    Objects.requireNonNull(token, "token");
    ThankTokenHeader header = token.requireHeader();
    Path folder = getPath(header);
    try {
      Files.createDirectories(folder);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to create directory " + folder, e);
    }
    Path headerFile = folder.resolve(FILENAME_HEADER);
    try (OutputStream out = Files.newOutputStream(headerFile)) {
      this.writer.writeHeader(header, out);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to write token header to " + headerFile, e);
    }
    List<? extends ThankTransaction> transactions = token.getTransactions();
    int txCount = transactions.size();
    for (int txIndex = 0; txIndex < txCount; txIndex++) {
      ThankTransaction tx = transactions.get(txIndex);
      Path txFile = folder.resolve(getTxFilename(txIndex));
      try (OutputStream out = Files.newOutputStream(txFile)) {
        this.writer.writeTransaction(token, tx, out);
      } catch (IOException e) {
        throw new IllegalStateException("Failed to write token tx to " + txFile, e);
      }
    }
  }

}
