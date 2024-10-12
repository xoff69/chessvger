package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.repertoire.Repertoire;
import com.xoff.chessvger.ui.web.view.RepertoireDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-11T20:14:39-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class RepertoireMapperImpl implements RepertoireMapper {

    @Override
    public List<RepertoireDto> mapListEntity2Dto(List<Repertoire> repertoires) {
        if ( repertoires == null ) {
            return null;
        }

        List<RepertoireDto> list = new ArrayList<RepertoireDto>( repertoires.size() );
        for ( Repertoire repertoire : repertoires ) {
            list.add( repertoireToRepertoireDto( repertoire ) );
        }

        return list;
    }

    protected RepertoireDto repertoireToRepertoireDto(Repertoire repertoire) {
        if ( repertoire == null ) {
            return null;
        }

        RepertoireDto repertoireDto = new RepertoireDto();

        repertoireDto.setUserId( repertoire.getUserId() );
        repertoireDto.setDatabaseId( repertoire.getDatabaseId() );

        return repertoireDto;
    }
}
