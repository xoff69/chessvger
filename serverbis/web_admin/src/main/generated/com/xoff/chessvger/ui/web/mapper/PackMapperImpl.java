package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.pack.Pack;
import com.xoff.chessvger.ui.web.view.PackDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-14T19:14:26-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class PackMapperImpl implements PackMapper {

    @Override
    public List<PackDto> mapListEntity2Dto(List<Pack> packs) {
        if ( packs == null ) {
            return null;
        }

        List<PackDto> list = new ArrayList<PackDto>( packs.size() );
        for ( Pack pack : packs ) {
            list.add( packToPackDto( pack ) );
        }

        return list;
    }

    protected PackDto packToPackDto(Pack pack) {
        if ( pack == null ) {
            return null;
        }

        PackDto packDto = new PackDto();

        packDto.setId( pack.getId() );
        packDto.setName( pack.getName() );
        packDto.setStartDate( pack.getStartDate() );
        packDto.setEndDate( pack.getEndDate() );
        packDto.setPrice( pack.getPrice() );

        return packDto;
    }
}
