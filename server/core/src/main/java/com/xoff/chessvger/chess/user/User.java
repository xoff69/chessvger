package com.xoff.chessvger.chess.user;

import com.xoff.chessvger.common.CommonModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User extends CommonModel {
  @Serial
  private static final long serialVersionUID = 6586294231524442347L;


  private String login;
  private String password;
  private String name;
  private Boolean isAdmin;
  private String customPresentation;

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.writeLong(getId());
    out.writeUTF(getLogin());
    out.writeUTF(getPassword());
    out.writeUTF(getName());
    out.writeBoolean(getIsAdmin());
    out.writeUTF(getCustomPresentation());
  }

  private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
    setId(in.readLong());
    setLogin(in.readUTF());
    setPassword(in.readUTF());
    setName(in.readUTF());
    setIsAdmin(in.readBoolean());
    setCustomPresentation(in.readUTF());
  }


}
