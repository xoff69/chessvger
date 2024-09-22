package com.xoff.chessvger.ui.web.navigation;

import com.xoff.chessvger.ui.Pageable;
import com.xoff.chessvger.ui.web.view.PageView;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Page {

  public static PageView compute(Pageable pageable, List data) {
    PageView pageView = new PageView();

    int pageSize = pageable.getPageSize();
    int size = data.size();
    log.info("compute page " + pageSize + " - " + size);
    if (size == 0) {
      return pageView;
    }
    int nbpages = (size / pageSize) + 1;
    log.info("compute page nbpages " + nbpages + " - " + size);
    pageView.setTotalElements(size);
    pageView.setTotalPages(nbpages);

    pageView.setCurrentPage(pageable.getPageNumber());

    log.info("compute page pageable.getPageNumber() " + pageable.getPageNumber());
    int debut = (pageable.getPageNumber() - 1) * pageSize;
    if (debut < 0) {
      debut = 0;
    }

    int fin = debut + pageSize;
    fin = Math.min(fin, size);

    if (debut > fin) {

      return pageView;
    }
    log.error("debut:" + debut + " " + pageSize + "  " + pageable.getPageNumber() + ":" + fin);
    pageView.setItems(data.subList(debut, fin));
    return pageView;
  }


}
