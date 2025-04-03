package com.xoff.chessvger.chess.position;


import com.xoff.chessvger.model.AnalyzedPosition;

public class AnalysedPositionManager implements IAnalysedPositionManager {


    private final AnalysePositionMap map;

    public AnalysedPositionManager() {
        map = new AnalysePositionMap();
    }


    public void add(AnalyzedPosition f) {
        map.add(f.getZobrist(), f);
    }

    public void del(AnalyzedPosition f) {
        map.remove(f.getZobrist());
    }

    public AnalyzedPosition get(long id) {
        return map.findById(id);
    }


    public void finish() {
        map.commit();
    }


    public void clear() {
        map.clear();
    }
}