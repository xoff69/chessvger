package com.xoff.chessvger.chess.repertoire;

import com.xoff.chessvger.common.CommonModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Repertoire extends CommonModel {
  @Serial
  private static final long serialVersionUID = 5424741731756895241L;

  private long userId;
  private long databaseId;

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.writeLong(getId());
    out.writeLong(getUserId());
    out.writeLong(getDatabaseId());

  }

  private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
    setId(in.readLong());
    setUserId(in.readLong());
    setDatabaseId(in.readLong());
  }


}
