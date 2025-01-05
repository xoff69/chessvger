package com.xoff.chessvger.chess.database;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@Data
public class Database implements Serializable {
  @Serial
  private static final long serialVersionUID = 2336169645652729695L;

  private long id;
  // FIXME ajouter icon
  private String name;
  private String description;
  private int nbgames;
  private long lastUpdate;

  // virtual folder+ icon
  private String view = StringUtils.EMPTY;

  @Serial
  private void writeObject(ObjectOutputStream out) throws IOException {
    out.writeLong(getId());
    out.writeUTF(getName());
    out.writeUTF(getDescription());
    out.writeInt(getNbgames());
    out.writeLong(getLastUpdate());
    out.writeUTF(getView());
  }

  @Serial
  private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
    setId(in.readLong());
    setName(in.readUTF());
    setDescription(in.readUTF());
    setNbgames(in.readInt());
    setLastUpdate(in.readLong());
    setView(in.readUTF());
  }
}
