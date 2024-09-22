package com.xoff.chessvger.chess.position;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class AnalyzedPosition implements Serializable {
  private static final long serialVersionUID = 7993947225164175262L;
  private long zobrist;
  private float eval;
  private long evaluationTimeMillis;
  private String engineName;
  private String meilleurCoup;


  public AnalyzedPosition(long zobrist, float eval, long evatime, String engineName) {

    this.zobrist = zobrist;
    this.eval = eval;
    this.evaluationTimeMillis = evatime;
    this.engineName = engineName;
    this.meilleurCoup = StringUtils.EMPTY;

  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.writeLong(getZobrist());
    out.writeFloat(getEval());
    out.writeLong(getEvaluationTimeMillis());
    out.writeUTF(getEngineName());
    out.writeUTF(getMeilleurCoup());
  }

  private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
    setZobrist(in.readLong());

    setEval(in.readFloat());
    setEvaluationTimeMillis(in.readLong());
    setEngineName(in.readUTF());

    setMeilleurCoup(in.readUTF());

  }


}
