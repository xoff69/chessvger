package com.xoff.chessvger.chess.repertoire;

import com.xoff.chessvger.common.ICommonManager;
import java.util.List;

public interface IRepertoireManager extends ICommonManager<Long, Repertoire> {


  List<Repertoire> findRepertoire(long userId);


}
