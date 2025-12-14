package com.xoff.chessvger.service;

import com.xoff.chessvger.chess.filter.Filter;

import java.util.ArrayList;
import java.util.List;

import static com.xoff.chessvger.util.MaterialUtil.*;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_CAVALIER_BLANC;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_CAVALIER_NOIR;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_DAME_BLANC;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_DAME_NOIR;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_FOU_BLANC;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_FOU_NOIR;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_TOUR_BLANC;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_TOUR_NOIR;
import static com.xoff.chessvger.util.MaterialUtil.encode;

public class MaterialService {

    public List<Long> search(Filter filter) {
        long materialValue = 0L;
        // TODO cas des champs a -1 = on n'a pas choisi
        materialValue = encode(materialValue, filter.getNbpionblanc(), DEBUT_PION_BLANC);
        materialValue |= encode(materialValue, filter.getNbpionnoir(), DEBUT_PION_NOIR);
        materialValue |= encode(materialValue, filter.getNbcavalierblanc(), DEBUT_CAVALIER_BLANC);
        materialValue |= encode(materialValue, filter.getNbcavaliernoir(), DEBUT_CAVALIER_NOIR);
        materialValue |= encode(materialValue, filter.getNbfoublanc(), DEBUT_FOU_BLANC);
        materialValue |= encode(materialValue, filter.getNbfounoir(), DEBUT_FOU_NOIR);
        materialValue |= encode(materialValue, filter.getNbdameblanc(), DEBUT_DAME_BLANC);
        materialValue |= encode(materialValue, filter.getNbdamenoir(), DEBUT_DAME_NOIR);
        materialValue |= encode(materialValue, filter.getNbtourblanc(), DEBUT_TOUR_BLANC);
        materialValue |= encode(materialValue, filter.getNbtournoir(), DEBUT_TOUR_NOIR);
        List<Long> all = new ArrayList<>();


        return all;
    }
}
