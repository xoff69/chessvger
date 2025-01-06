package com.xoff.chessvger.chess.player;

import com.xoff.chessvger.util.Constants;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;


@Data
public class CommonPlayer implements Serializable {

  private static final long serialVersionUID = 3019214335943458171L;
  private long idnumber;

  private String name;
  private String nakedName;
  private String fex;
  private String sex;
  private String tit;
  private String wtit;
  private String otit;
  private String foa;
  private String ssrtng;
  private String sgm;
  private String sk;
  private String rrtng;
  private String rgm;
  private String rk;
  private String brtng;
  private String bgm;
  private String bk;
  private String bday;
  private String flag;

  public CommonPlayer() {
    idnumber = 0L;
    name = StringUtils.EMPTY;
    nakedName = StringUtils.EMPTY;
    fex = StringUtils.EMPTY;
    sex = StringUtils.EMPTY;
    tit = StringUtils.EMPTY;
    wtit = StringUtils.EMPTY;
    otit = StringUtils.EMPTY;
    foa = StringUtils.EMPTY;
    ssrtng = StringUtils.EMPTY;
    sgm = StringUtils.EMPTY;
    sk = StringUtils.EMPTY;
    rrtng = StringUtils.EMPTY;
    rgm = StringUtils.EMPTY;
    rk = StringUtils.EMPTY;
    brtng = StringUtils.EMPTY;
    bgm = StringUtils.EMPTY;
    bk = StringUtils.EMPTY;
    bday = StringUtils.EMPTY;
    flag = StringUtils.EMPTY;
  }


  public boolean isFide() {
    return idnumber < Constants.ID_MIN_NON_FIDE_PLAYER;
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.writeLong(getIdnumber());
    out.writeUTF(getName());
    out.writeUTF(getNakedName());
    out.writeUTF(getFex());
    out.writeUTF(getSex());
    out.writeUTF(getTit());
    out.writeUTF(getWtit());
    out.writeUTF(getOtit());
    out.writeUTF(getFoa());
    out.writeUTF(getSsrtng());
    out.writeUTF(getSgm());
    out.writeUTF(getSk());
    out.writeUTF(getRrtng());
    out.writeUTF(getRgm());
    out.writeUTF(getRk());
    out.writeUTF(getBrtng());
    out.writeUTF(getBgm());
    out.writeUTF(getBk());
    out.writeUTF(getBday());
    out.writeUTF(getFlag());
  }

  private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {

    setIdnumber(in.readLong());
    setName(in.readUTF());
    setNakedName(in.readUTF());

    setFex(in.readUTF());
    setSex(in.readUTF());
    setTit(in.readUTF());
    setWtit(in.readUTF());
    setOtit(in.readUTF());
    setFoa(in.readUTF());
    setSsrtng(in.readUTF());
    setSgm(in.readUTF());
    setSk(in.readUTF());
    setRrtng(in.readUTF());
    setRgm(in.readUTF());
    setRk(in.readUTF());
    setRrtng(in.readUTF());
    setBgm(in.readUTF());
    setBk(in.readUTF());
    setBday(in.readUTF());
    setFlag(in.readUTF());

  }

}
