package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.database.Database;
import com.xoff.chessvger.ui.web.form.DBForm;
import com.xoff.chessvger.ui.web.view.DBView;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.context.i18n.LocaleContextHolder;

@Mapper(componentModel = "spring")
public abstract class DatabaseMapper {
  @Named("formatDate")
  static String mapLastUpdate(long dt) {
    Locale locale = LocaleContextHolder.getLocale();
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
    String date = dateFormat.format(new Date(dt));
    return date;

  }

  @Mapping(source = "lastUpdate", target = "lastUpdate", qualifiedByName = "formatDate")
  public abstract DBView database2Dbview(Database database);

  public abstract Database dbForm2Database(DBForm dbForm);

  public abstract List<DBView> mapListEntity2Dto(List<Database> databases);
}
