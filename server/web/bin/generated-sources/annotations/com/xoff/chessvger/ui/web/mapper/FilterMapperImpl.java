package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.filter.Filter;
import com.xoff.chessvger.ui.web.form.FilterForm;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:58:43-0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250115-2156, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class FilterMapperImpl extends FilterMapper {

    @Override
    public FilterForm entity2Dto(Filter filter) {
        if ( filter == null ) {
            return null;
        }

        FilterForm filterForm = new FilterForm();

        filterForm.setBlack( filter.getBlack() );
        filterForm.setBlackElo( String.valueOf( filter.getBlackElo() ) );
        filterForm.setBlackFideId( String.valueOf( filter.getBlackFideId() ) );
        filterForm.setBlackTitle( filter.getBlackTitle() );
        filterForm.setBxf2( filter.isBxf2() );
        filterForm.setBxf7( filter.isBxf7() );
        filterForm.setBxh3( filter.isBxh3() );
        filterForm.setBxh6( filter.isBxh6() );
        filterForm.setCampIndifferent( filter.isCampIndifferent() );
        filterForm.setCasGeneral( filter.isCasGeneral() );
        filterForm.setCasOtherCriteria( filter.isCasOtherCriteria() );
        filterForm.setCasPosition( filter.isCasPosition() );
        filterForm.setDate1( filter.getDate1() );
        filterForm.setDate2( filter.getDate2() );
        filterForm.setDoublecheck( filter.isDoublecheck() );
        filterForm.setEco1( filter.getEco1() );
        filterForm.setEco2( filter.getEco2() );
        filterForm.setEp( filter.isEp() );
        filterForm.setEvent( filter.getEvent() );
        filterForm.setFavori( filter.getFavori() );
        filterForm.setFoudecouleuropposee( filter.isFoudecouleuropposee() );
        filterForm.setGrandRoqueBlanc( filter.isGrandRoqueBlanc() );
        filterForm.setGrandRoqueNoir( filter.isGrandRoqueNoir() );
        filterForm.setId( filter.getId() );
        filterForm.setIncludeDateNull( filter.isIncludeDateNull() );
        filterForm.setInteret( filter.getInteret() );
        filterForm.setMaterialPrecis( filter.isMaterialPrecis() );
        filterForm.setModeApproximatif( filter.isModeApproximatif() );
        filterForm.setNbJourSinceUpdate( String.valueOf( filter.getNbJourSinceUpdate() ) );
        filterForm.setNbcavalierblanc( filter.getNbcavalierblanc() );
        filterForm.setNbcavaliernoir( filter.getNbcavaliernoir() );
        filterForm.setNbcoup1( String.valueOf( filter.getNbcoup1() ) );
        filterForm.setNbcoup2( String.valueOf( filter.getNbcoup2() ) );
        filterForm.setNbdameblanc( filter.getNbdameblanc() );
        filterForm.setNbdamenoir( filter.getNbdamenoir() );
        filterForm.setNbfoublanc( filter.getNbfoublanc() );
        filterForm.setNbfounoir( filter.getNbfounoir() );
        filterForm.setNbpionblanc( filter.getNbpionblanc() );
        filterForm.setNbpionnoir( filter.getNbpionnoir() );
        filterForm.setNbtourblanc( filter.getNbtourblanc() );
        filterForm.setNbtournoir( filter.getNbtournoir() );
        filterForm.setNomFiltre( filter.getNomFiltre() );
        filterForm.setPat( filter.isPat() );
        filterForm.setPetitRoqueBlanc( filter.isPetitRoqueBlanc() );
        filterForm.setPetitRoqueNoir( filter.isPetitRoqueNoir() );
        filterForm.setPionsdoubles( filter.isPionsdoubles() );
        filterForm.setPionstrples( filter.isPionstrples() );
        filterForm.setResult( filter.getResult() );
        filterForm.setRound( filter.getRound() );
        filterForm.setSelectedModeCoups( String.valueOf( filter.getSelectedModeCoups() ) );
        filterForm.setSelectedModeDate( String.valueOf( filter.getSelectedModeDate() ) );
        filterForm.setSelectedModeEco( String.valueOf( filter.getSelectedModeEco() ) );
        filterForm.setSite( filter.getSite() );
        filterForm.setSystem( filter.isSystem() );
        filterForm.setTheorique( filter.getTheorique() );
        filterForm.setWhite( filter.getWhite() );
        filterForm.setWhiteElo( String.valueOf( filter.getWhiteElo() ) );
        filterForm.setWhiteFideId( String.valueOf( filter.getWhiteFideId() ) );
        filterForm.setWhiteTitle( filter.getWhiteTitle() );
        filterForm.setZobrist( filter.getZobrist() );

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
        filter.setBlack( form.getBlack() );
        filter.setBlackTitle( form.getBlackTitle() );
        filter.setBxf2( form.isBxf2() );
        filter.setBxf7( form.isBxf7() );
        filter.setBxh3( form.isBxh3() );
        filter.setBxh6( form.isBxh6() );
        filter.setCampIndifferent( form.isCampIndifferent() );
        filter.setCasGeneral( form.isCasGeneral() );
        filter.setCasOtherCriteria( form.isCasOtherCriteria() );
        filter.setCasPosition( form.isCasPosition() );
        filter.setDate1( form.getDate1() );
        filter.setDate2( form.getDate2() );
        filter.setDoublecheck( form.isDoublecheck() );
        filter.setEco1( form.getEco1() );
        filter.setEco2( form.getEco2() );
        filter.setEp( form.isEp() );
        filter.setEvent( form.getEvent() );
        filter.setFavori( form.getFavori() );
        filter.setFoudecouleuropposee( form.isFoudecouleuropposee() );
        filter.setGrandRoqueBlanc( form.isGrandRoqueBlanc() );
        filter.setGrandRoqueNoir( form.isGrandRoqueNoir() );
        filter.setIncludeDateNull( form.isIncludeDateNull() );
        filter.setInteret( form.getInteret() );
        filter.setMaterialPrecis( form.isMaterialPrecis() );
        filter.setModeApproximatif( form.isModeApproximatif() );
        filter.setNbcavalierblanc( form.getNbcavalierblanc() );
        filter.setNbcavaliernoir( form.getNbcavaliernoir() );
        filter.setNbdameblanc( form.getNbdameblanc() );
        filter.setNbdamenoir( form.getNbdamenoir() );
        filter.setNbfoublanc( form.getNbfoublanc() );
        filter.setNbfounoir( form.getNbfounoir() );
        filter.setNbpionblanc( form.getNbpionblanc() );
        filter.setNbpionnoir( form.getNbpionnoir() );
        filter.setNbtourblanc( form.getNbtourblanc() );
        filter.setNbtournoir( form.getNbtournoir() );
        filter.setNomFiltre( form.getNomFiltre() );
        filter.setPat( form.isPat() );
        filter.setPetitRoqueBlanc( form.isPetitRoqueBlanc() );
        filter.setPetitRoqueNoir( form.isPetitRoqueNoir() );
        filter.setPionsdoubles( form.isPionsdoubles() );
        filter.setPionstrples( form.isPionstrples() );
        filter.setResult( form.getResult() );
        filter.setRound( form.getRound() );
        filter.setSite( form.getSite() );
        filter.setSystem( form.isSystem() );
        filter.setTheorique( form.getTheorique() );
        filter.setWhite( form.getWhite() );
        filter.setWhiteTitle( form.getWhiteTitle() );
        filter.setZobrist( form.getZobrist() );

        return filter;
    }
}
