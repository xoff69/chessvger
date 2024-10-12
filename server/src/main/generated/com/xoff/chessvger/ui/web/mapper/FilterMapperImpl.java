package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.filter.Filter;
import com.xoff.chessvger.ui.web.form.FilterForm;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-11T20:14:39-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class FilterMapperImpl extends FilterMapper {

    @Override
    public FilterForm entity2Dto(Filter filter) {
        if ( filter == null ) {
            return null;
        }

        FilterForm filterForm = new FilterForm();

        filterForm.setId( filter.getId() );
        filterForm.setSystem( filter.isSystem() );
        filterForm.setNomFiltre( filter.getNomFiltre() );
        filterForm.setCasGeneral( filter.isCasGeneral() );
        filterForm.setCasOtherCriteria( filter.isCasOtherCriteria() );
        filterForm.setCasPosition( filter.isCasPosition() );
        filterForm.setFavori( filter.getFavori() );
        filterForm.setInteret( filter.getInteret() );
        filterForm.setTheorique( filter.getTheorique() );
        filterForm.setNbJourSinceUpdate( String.valueOf( filter.getNbJourSinceUpdate() ) );
        filterForm.setCampIndifferent( filter.isCampIndifferent() );
        filterForm.setEvent( filter.getEvent() );
        filterForm.setSite( filter.getSite() );
        filterForm.setDate1( filter.getDate1() );
        filterForm.setDate2( filter.getDate2() );
        filterForm.setModeApproximatif( filter.isModeApproximatif() );
        filterForm.setSelectedModeDate( String.valueOf( filter.getSelectedModeDate() ) );
        filterForm.setSelectedModeCoups( String.valueOf( filter.getSelectedModeCoups() ) );
        filterForm.setSelectedModeEco( String.valueOf( filter.getSelectedModeEco() ) );
        filterForm.setRound( filter.getRound() );
        filterForm.setWhite( filter.getWhite() );
        filterForm.setBlack( filter.getBlack() );
        filterForm.setResult( filter.getResult() );
        filterForm.setWhiteTitle( filter.getWhiteTitle() );
        filterForm.setBlackTitle( filter.getBlackTitle() );
        filterForm.setWhiteElo( String.valueOf( filter.getWhiteElo() ) );
        filterForm.setBlackElo( String.valueOf( filter.getBlackElo() ) );
        filterForm.setEco1( filter.getEco1() );
        filterForm.setEco2( filter.getEco2() );
        filterForm.setWhiteFideId( String.valueOf( filter.getWhiteFideId() ) );
        filterForm.setBlackFideId( String.valueOf( filter.getBlackFideId() ) );
        filterForm.setIncludeDateNull( filter.isIncludeDateNull() );
        filterForm.setNbcoup1( String.valueOf( filter.getNbcoup1() ) );
        filterForm.setNbcoup2( String.valueOf( filter.getNbcoup2() ) );
        filterForm.setZobrist( filter.getZobrist() );
        filterForm.setNbdamenoir( filter.getNbdamenoir() );
        filterForm.setNbpionnoir( filter.getNbpionnoir() );
        filterForm.setNbtournoir( filter.getNbtournoir() );
        filterForm.setNbfounoir( filter.getNbfounoir() );
        filterForm.setNbcavaliernoir( filter.getNbcavaliernoir() );
        filterForm.setNbdameblanc( filter.getNbdameblanc() );
        filterForm.setNbpionblanc( filter.getNbpionblanc() );
        filterForm.setNbtourblanc( filter.getNbtourblanc() );
        filterForm.setNbfoublanc( filter.getNbfoublanc() );
        filterForm.setNbcavalierblanc( filter.getNbcavalierblanc() );
        filterForm.setMaterialPrecis( filter.isMaterialPrecis() );
        filterForm.setGrandRoqueBlanc( filter.isGrandRoqueBlanc() );
        filterForm.setGrandRoqueNoir( filter.isGrandRoqueNoir() );
        filterForm.setPetitRoqueBlanc( filter.isPetitRoqueBlanc() );
        filterForm.setPetitRoqueNoir( filter.isPetitRoqueNoir() );
        filterForm.setBxf7( filter.isBxf7() );
        filterForm.setBxh6( filter.isBxh6() );
        filterForm.setBxf2( filter.isBxf2() );
        filterForm.setBxh3( filter.isBxh3() );
        filterForm.setDoublecheck( filter.isDoublecheck() );
        filterForm.setEp( filter.isEp() );
        filterForm.setFoudecouleuropposee( filter.isFoudecouleuropposee() );
        filterForm.setPat( filter.isPat() );
        filterForm.setPionsdoubles( filter.isPionsdoubles() );
        filterForm.setPionstrples( filter.isPionstrples() );

        return filterForm;
    }

    @Override
    public Filter form2entity(FilterForm form) {
        if ( form == null ) {
            return null;
        }

        long id = 0L;

        id = form.getId();

        String nom = null;

        Filter filter = new Filter( nom, id );

        filter.setNbJourSinceUpdate( FilterMapper.mapString2Int( form.getNbJourSinceUpdate() ) );
        filter.setSelectedModeDate( FilterMapper.mapString2Int( form.getSelectedModeDate() ) );
        filter.setSelectedModeCoups( FilterMapper.mapString2Int( form.getSelectedModeCoups() ) );
        filter.setSelectedModeEco( FilterMapper.mapString2Int( form.getSelectedModeEco() ) );
        filter.setWhiteElo( FilterMapper.mapString2Int( form.getWhiteElo() ) );
        filter.setBlackElo( FilterMapper.mapString2Int( form.getBlackElo() ) );
        if ( form.getWhiteFideId() != null ) {
            filter.setWhiteFideId( FilterMapper.mapString2Int( form.getWhiteFideId() ) );
        }
        if ( form.getBlackFideId() != null ) {
            filter.setBlackFideId( FilterMapper.mapString2Int( form.getBlackFideId() ) );
        }
        filter.setNbcoup1( FilterMapper.mapString2Int( form.getNbcoup1() ) );
        filter.setNbcoup2( FilterMapper.mapString2Int( form.getNbcoup2() ) );
        filter.setSystem( form.isSystem() );
        filter.setNomFiltre( form.getNomFiltre() );
        filter.setCasGeneral( form.isCasGeneral() );
        filter.setCasOtherCriteria( form.isCasOtherCriteria() );
        filter.setCasPosition( form.isCasPosition() );
        filter.setFavori( form.getFavori() );
        filter.setInteret( form.getInteret() );
        filter.setTheorique( form.getTheorique() );
        filter.setCampIndifferent( form.isCampIndifferent() );
        filter.setEvent( form.getEvent() );
        filter.setSite( form.getSite() );
        filter.setDate1( form.getDate1() );
        filter.setDate2( form.getDate2() );
        filter.setModeApproximatif( form.isModeApproximatif() );
        filter.setRound( form.getRound() );
        filter.setWhite( form.getWhite() );
        filter.setBlack( form.getBlack() );
        filter.setResult( form.getResult() );
        filter.setWhiteTitle( form.getWhiteTitle() );
        filter.setBlackTitle( form.getBlackTitle() );
        filter.setEco1( form.getEco1() );
        filter.setEco2( form.getEco2() );
        filter.setIncludeDateNull( form.isIncludeDateNull() );
        filter.setZobrist( form.getZobrist() );
        filter.setNbdamenoir( form.getNbdamenoir() );
        filter.setNbpionnoir( form.getNbpionnoir() );
        filter.setNbtournoir( form.getNbtournoir() );
        filter.setNbfounoir( form.getNbfounoir() );
        filter.setNbcavaliernoir( form.getNbcavaliernoir() );
        filter.setNbdameblanc( form.getNbdameblanc() );
        filter.setNbpionblanc( form.getNbpionblanc() );
        filter.setNbtourblanc( form.getNbtourblanc() );
        filter.setNbfoublanc( form.getNbfoublanc() );
        filter.setNbcavalierblanc( form.getNbcavalierblanc() );
        filter.setMaterialPrecis( form.isMaterialPrecis() );
        filter.setGrandRoqueBlanc( form.isGrandRoqueBlanc() );
        filter.setGrandRoqueNoir( form.isGrandRoqueNoir() );
        filter.setPetitRoqueBlanc( form.isPetitRoqueBlanc() );
        filter.setPetitRoqueNoir( form.isPetitRoqueNoir() );
        filter.setBxf7( form.isBxf7() );
        filter.setBxh6( form.isBxh6() );
        filter.setBxf2( form.isBxf2() );
        filter.setBxh3( form.isBxh3() );
        filter.setDoublecheck( form.isDoublecheck() );
        filter.setEp( form.isEp() );
        filter.setFoudecouleuropposee( form.isFoudecouleuropposee() );
        filter.setPat( form.isPat() );
        filter.setPionsdoubles( form.isPionsdoubles() );
        filter.setPionstrples( form.isPionstrples() );

        return filter;
    }
}
