package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.ui.web.form.DBForm;
import com.xoff.chessvger.ui.web.view.DBView;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-26T08:14:45-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
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
        dBView.setId( database.getId() );
        dBView.setName( database.getName() );
        dBView.setDescription( database.getDescription() );
        dBView.setNbgames( database.getNbgames() );

        return dBView;
    }

    @Override
    public Database dbForm2Database(DBForm dbForm) {
        if ( dbForm == null ) {
            return null;
        }

        Database database = new Database();

        database.setId( dbForm.getId() );
        database.setName( dbForm.getName() );
        database.setDescription( dbForm.getDescription() );
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
