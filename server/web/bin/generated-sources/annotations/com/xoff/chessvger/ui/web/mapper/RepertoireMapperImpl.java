package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.repertoire.Repertoire;
import com.xoff.chessvger.view.RepertoireDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-04T21:27:35-0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20241217-1506, environment: Java 17.0.13 (Eclipse Adoptium)"
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

        repertoireDto.setDatabaseId( repertoire.getDatabaseId() );
        repertoireDto.setUserId( repertoire.getUserId() );

        return repertoireDto;
    }
}
