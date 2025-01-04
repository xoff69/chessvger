package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.feature.Feature;
import com.xoff.chessvger.view.FeatureDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-04T15:19:15-0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20241217-1506, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class FeatureMapperImpl extends FeatureMapper {

    @Override
    public FeatureDto entity2Dto(Feature feature) {
        if ( feature == null ) {
            return null;
        }

        FeatureDto featureDto = new FeatureDto();

        if ( feature.getEnabled() != null ) {
            featureDto.setEnabled( String.valueOf( feature.getEnabled() ) );
        }
        featureDto.setId( feature.getId() );
        featureDto.setName( feature.getName() );

        return featureDto;
    }

    @Override
    public List<Feature> map(List<FeatureDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Feature> list = new ArrayList<Feature>( dtos.size() );
        for ( FeatureDto featureDto : dtos ) {
            list.add( dto2entity( featureDto ) );
        }

        return list;
    }

    @Override
    public Feature dto2entity(FeatureDto dto) {
        if ( dto == null ) {
            return null;
        }

        Feature feature = new Feature();

        feature.setEnabled( FeatureMapper.mapChecked( dto.getEnabled() ) );
        if ( dto.getId() != null ) {
            feature.setId( dto.getId() );
        }
        feature.setName( dto.getName() );

        return feature;
    }

    @Override
    public List<FeatureDto> mapListEntity2Dto(List<Feature> features) {
        if ( features == null ) {
            return null;
        }

        List<FeatureDto> list = new ArrayList<FeatureDto>( features.size() );
        for ( Feature feature : features ) {
            list.add( entity2Dto( feature ) );
        }

        return list;
    }
}
