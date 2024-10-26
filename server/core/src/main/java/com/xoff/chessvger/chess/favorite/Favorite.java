package com.xoff.chessvger.chess.favorite;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Favorite implements Serializable {
  @Serial
  private static final long serialVersionUID = 7438048643499938558L;
  private long id;
  private long bdId;
  private long idGame;
  private long idUser;

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.writeLong(getId());
    out.writeLong(getBdId());
    out.writeLong(getIdGame());
    out.writeLong(getIdUser());

  }

  private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {

    setId(in.readLong());
    setBdId(in.readLong());
    setIdGame(in.readLong());
    setIdUser(in.readLong());

  }


}
