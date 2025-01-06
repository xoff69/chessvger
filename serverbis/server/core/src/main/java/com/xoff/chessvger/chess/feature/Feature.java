package com.xoff.chessvger.chess.feature;

import com.xoff.chessvger.common.CommonModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Feature extends CommonModel {
  @Serial
  private static final long serialVersionUID = 1836124958630016382L;


  private String name;

  private Boolean enabled;

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.writeLong(getId());
    out.writeUTF(getName());
    out.writeBoolean(getEnabled());

  }

  private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
    setId(in.readLong());
    setName(in.readUTF());
    setEnabled(in.readBoolean());
  }


}
