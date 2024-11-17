package com.xoff.chessvger.chess.pack;

import com.xoff.chessvger.common.CommonModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PackDatabase extends CommonModel {
  @Serial
  private static final long serialVersionUID = 927878041727638881L;

  private long packId;
  private long bdId;

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.writeLong(getId());
    out.writeLong(getPackId());
    out.writeLong(getBdId());
  }

  private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
    setId(in.readLong());
    setPackId(in.readLong());
    setBdId(in.readLong());
  }


}
