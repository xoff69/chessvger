package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.ui.web.form.DBForm;
import com.xoff.chessvger.view.DBView;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-24T21:01:40-0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250115-2156, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class DatabaseMapperImpl extends DatabaseMapper {

    @Override
    public DBView database2Dbview(Database database) {
        if ( database == null ) {
            return null;
        }

        DBView dBView = new DBView();

        dBView.setLastUpdate( DatabaseMapper.mapLastUpdate( database.getLastUpdate() ) );
        dBView.setDescription( database.getDescription() );
        dBView.setId( database.getId() );
        dBView.setName( database.getName() );
        dBView.setNbgames( database.getNbgames() );

        return dBView;
    }

    @Override
    public Database dbForm2Database(DBForm dbForm) {
        if ( dbForm == null ) {
            return null;
        }

        Database database = new Database();

        database.setDescription( dbForm.getDescription() );
        database.setId( dbForm.getId() );
        database.setName( dbForm.getName() );
        database.setNbgames( dbForm.getNbgames() );

        return database;
    }

    @Override
    public List<DBView> mapListEntity2Dto(List<Database> databases) {
        if ( databases == null ) {
            return null;
        }

        List<DBView> list = new ArrayList<DBView>( databases.size() );
        for ( Database database : databases ) {
            list.add( database2Dbview( database ) );
        }

        return list;
    }
}
