package com.xoff.chessvger.chess.engine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@Data
public class Engine implements Serializable {
  private static final long serialVersionUID = -8619632950548966427L;

  private long id;
  private String description;
  private String name;
  private String path;
  private int prof;
  private int pv;

  private String fen;


  public Engine(long id) {
    this.id = id;
    prof = 15;
    pv = 2;
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.writeLong(getId());
    out.writeUTF(getName());
    out.writeUTF(getDescription());
    out.writeUTF(getPath());
    out.writeInt(getProf());
    out.writeInt(getPv());
  }

  private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {

    setId(in.readLong());
    setName(in.readUTF());
    setDescription(in.readUTF());
    setPath(in.readUTF());
    setProf(in.readInt());
    setPv(in.readInt());

  }


  public EvalAndBest evalue(String fen, int temps) {
    EvalAndBest ev = new EvalAndBest();
    StockFish client = new StockFish(pv, prof, path);
    if (client.startEngine()) {
      //   log.info("Demarrage du moteur " + client);
    } else {
      log.error("erreur moteur.." + name);
      return ev;
    }
    ev.setBestmove(client.getBestMove(fen, temps));
    ev.setEval(client.getEvalScore(fen, temps));

    client.stopEngine();
    //  log.info("termine evalue");
    return ev;
  }


  public boolean check() {

    EvalAndBest eb = evalue("1k6/3K1B2/8/2N5/8/8/8/8 b - - 0 9", 100);
    return StringUtils.isEmpty(eb.getBestmove());

  }


}
