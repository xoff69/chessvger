package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.pack.Pack;
import com.xoff.chessvger.view.PackDto;
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

        packDto.setEndDate( pack.getEndDate() );
        packDto.setId( pack.getId() );
        packDto.setName( pack.getName() );
        packDto.setPrice( pack.getPrice() );
        packDto.setStartDate( pack.getStartDate() );

        return packDto;
    }
}
