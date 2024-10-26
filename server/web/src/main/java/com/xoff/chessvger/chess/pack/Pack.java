package com.xoff.chessvger.chess.pack;

import com.xoff.chessvger.common.CommonModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Pack extends CommonModel {
  @Serial
  private static final long serialVersionUID = 5730267369063215547L;

  private String name;
  private long startDate;
  private long endDate;
  private double price;

  private List<PackDatabase> packBds=new ArrayList<>();


  private void writeObject(ObjectOutputStream out) throws IOException {
    out.writeLong(getId());
    out.writeUTF(getName());

  }

  private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
    setId(in.readLong());
    setName(in.readUTF());
  }


}
