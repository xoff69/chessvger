package com.xoff.chessvger.chess.userpack;

import com.xoff.chessvger.common.CommonModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPack extends CommonModel {
  @Serial
  private static final long serialVersionUID = 7655075932033067467L;

  private long userId;
  private long packId;
  private long startDate;

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.writeLong(getId());
    out.writeLong(getUserId());
    out.writeLong(getPackId());
    out.writeLong(getStartDate());
  }

  private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {

    setId(in.readLong());
    setUserId(in.readLong());
    setPackId(in.readLong());
    setStartDate(in.readLong());
  }


}
