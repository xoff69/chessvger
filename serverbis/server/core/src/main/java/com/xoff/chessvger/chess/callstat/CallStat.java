package com.xoff.chessvger.chess.callstat;

import com.xoff.chessvger.common.CommonModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CallStat extends CommonModel {
  @Serial
  private static final long serialVersionUID = 1541079262729646692L;


  private String name;

  private long timestamp;

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.writeLong(getId());
    out.writeUTF(getName());
    out.writeLong(getTimestamp());

  }

  private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
    setId(in.readLong());
    setName(in.readUTF());
    setTimestamp(in.readLong());
  }


}
