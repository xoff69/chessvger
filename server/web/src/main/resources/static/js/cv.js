


function formatDate(date) {
  date = new Date(date);

  var day = ('0' + date.getDate()).slice(-2);
  var month = ('0' + (date.getMonth() + 1)).slice(-2);
  var year = date.getFullYear();

  return year + '-' + month + '-' + day;
}
function pasteGames(bdid) {

    var parameter = {
        bdid: bdid
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/pasteGames",
        data: JSON.stringify(parameter),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (resp) {

            alert(resp)

        },
        error: function (e) {

            manageError(e);


        }
    });
}
function copyGames(selectscope, bdid) {

    var gamesid = "";

    var checks = document.querySelectorAll('input[name="'+selectscope+'"]');
    for (var i = 0; i < checks.length; i++) {
        var check = checks[i];

        if (!check.disabled && check.checked) {
            gamesid = gamesid + ";" + check.id;
        }
    }

    // il faut le game id
    // il faut le current move id
    //{ bdid: '[[${bdid}]]', gameid: gameid,currentMoveId:currentMoveId },
    var parameter = {
        bdid: bdid,
        gamesid: gamesid
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/copyGames",
        data: JSON.stringify(parameter),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (resp) {

            alert(resp.data);

        },
        error: function (e) {

            manageError(e);


        }
    });

}
function deleteGames(selectscope, bdid) {

    var gamesid = "";

    var checks = document.querySelectorAll('input[name="'+selectscope+'"]');
    for (var i = 0; i < checks.length; i++) {
        var check = checks[i];

        if (!check.disabled && check.checked) {
            gamesid = gamesid + ";" + check.id;
        }
    }

    // il faut le game id
    // il faut le current move id
    //{ bdid: '[[${bdid}]]', gameid: gameid,currentMoveId:currentMoveId },
    var parameter = {
        bdid: bdid,
        gamesid: gamesid
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/deleteGames",
        data: JSON.stringify(parameter),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (resp) {

            alert(resp.data);

        },
        error: function (e) {

            manageError(e);


        }
    });

}
function checkUcheckAll(bx, divid) {


    var ischecked = bx.checked;
    var checks = document.querySelectorAll('#' + divid + ' input[type="checkbox"]');
    for (var i = 0; i < checks.length; i++) {
        var check = checks[i];
        if (!check.disabled) {
            check.checked = ischecked;
        }
    }
}
function manageError(e) {
    var json = "<h4>Ajax Response KO</h4><pre>" + e.responseText + "</pre>";
    $('#error').html(json);
}

function sortTable(table, col, reverse) {

    var tb = table.tBodies[0], // use `<tbody>` to ignore `<thead>` and `<tfoot>` rows
        tr = Array.prototype.slice.call(tb.rows, 0), // put rows into array
        i;
    reverse = -((+reverse) || -1);
    tr = tr.sort(function (a, b) { // sort rows
        return reverse // `-1 *` if want opposite order
            * (a.cells[col].textContent.trim() // using `.textContent.trim()` for test
                .localeCompare(b.cells[col].textContent.trim())
               );
    });
    for(i = 0; i < tr.length; ++i) tb.appendChild(tr[i]); // append each row in order
}

function makeSortable(table) {
    var th = table.tHead, i;
    th && (th = th.rows[0]) && (th = th.cells);
    if (th) i = th.length;
    else return; // if no `<thead>` then do nothing
    while (--i >= 0) (function (i) {
        var dir = 1;
     //   if (!th[i].sortable=="false"){
            th[i].addEventListener('click', function () {sortTable(table, i, (dir = 1 - dir))});
  //      }
    }(i));
}
function makeAllSortable() {
    parent =  document.body;
    var t = parent.getElementsByTagName('table'), i = t.length;
    while (--i >= 0) makeSortable(t[i]);
}
function buildImage(name){
  return '<img src="images/icons/'+name+'.png"  style="width:20px;height:20px;">';
}
function buildCheckBox(classId,typ,value){
    return "<input class=\""+classId+"\" type=\"checkbox\" id=\"checkbox_"
    + value+"\" value=\""+value+"\" name=\""+typ+"\"/>";
}
function collecCheck(name){
    var items = [];
    var t=name;

    $.each($("input[name='"+t+"']:checked"), function(){
        items.push($(this).val());
    });
  return items.join(", ");
}
function buildPagination(response,functionPage,items="items"){
        var currentPage=response.currentPage;
        var fn=functionPage.name;
        var totalElements=response.totalElements;
        var totalPages=response.totalPages;
        var imgBegin= buildImage("tobegin");
        var imgPrev= buildImage("back");
        var imgNext= buildImage("go");
        var imgEnd= buildImage("toend");
        var page='<nav aria-label="Pagination">';
        page=page+'<ul class="pagination justify-content-end">';
        if (totalPages>1){
            if (currentPage!=1){
                page=page+'<li class="page-item"><a class="page-link" href="javascript:'+functionPage+'(1)">'+imgBegin+'</a></li>';
                page=page+'<li class="page-item"><a class="page-link" href="javascript:'+functionPage+'('+(currentPage-1)+')">'+imgPrev+'</a></li>';
                }
            page=page+'<li class="page-item"><a class="page-link" href="javascript:'+functionPage+'('+currentPage+')">'+currentPage+'/'+totalPages+' pages</a></li>';
            if (currentPage!=totalPages){
                page=page+'<li class="page-item"> <a class="page-link" href="javascript:'+functionPage+'('+(currentPage+1)+')">'+imgNext+'</a></li>';

                page=page+'<li class="page-item"> <a class="page-link" href="javascript:'+functionPage+'('+totalPages+')">'+imgEnd+'</a></li>';
            }
        }
        page=page+'<li><p> '+totalElements+' '+items+"</p></li>";
        page=page+'</ul>';
        page=page+'</nav>';

        makeAllSortable();

        return page;
}
function getGameDetail(bdId,gameId,callbackFunction){
        $.ajax({
            url: 'game?bdId='+bdId+'&gameId='+gameId,
            type: 'get',
            dataType: 'JSON',
            success: function(response){
                callbackFunction(response);
            }
        });
}
function ecritGames(bdId,data,msg){
            var gamesTableId='gamesTable_'+bdId;
            var gamesPaginationId='gamesPagination_'+bdId;
          console.log("cv.js " +bdId+ "ecritGames "+data);
            var d=data.items;
            var len = d.length;
            $("#"+gamesTableId+"   tbody tr").remove();

            for(var i=0; i<len; i++){

                var id = d[i].id;
                var event = d[i].event;
                var site = d[i].site;
                var dt = d[i].date;
                var resultat = d[i].resultat;
                var joueurBlanc = d[i].joueurBlanc;
                var joueurNoir = d[i].joueurNoir;
                // actions
                var url="<a href='gameOpen?bdId=" + bdId + "&gameId="+id+"'>"+buildImage("oeil")+"</a>";

                var tr_str = "<tr id=" +id+">"+
                "<td>"+buildCheckBox("gamescb_"+bdId,"games_"+bdId,id)+"</td>"+
            //    "<td align='center'>" + id + "</td>" +
                "<td align='center'>" + event + "</td>" +
                "<td align='center'>" + site + "</td>" +
                "<td align='center'>" + dt + "</td>" +
                "<td align='center'>" + joueurBlanc + "</td>" +
                "<td align='center'>" + joueurNoir + "</td>" +
                "<td align='center'>" + resultat + "</td>" +
                "<td align='center'>" + url + "</td>" +
                "</tr>";

                $("#"+gamesTableId+" tbody").append(tr_str);
            }

            var funcupdate="updateGames"+bdId;
            pageGame=buildPagination(data,funcupdate,msg);
            $("#"+gamesPaginationId).html(pageGame);
}
function ecritGamesFavorites(bdId,data){
            var gamesTableId='gamesTableFavorites_'+bdId;
            var gamesPaginationId='gamesPaginationFavorites_'+bdId;

            var d=data.items;
            var len = d.length;
            $("#"+gamesTableId+"   tbody tr").remove();

            for(var i=0; i<len; i++){

                var id = d[i].id;
                var event = d[i].event;
                var site = d[i].site;
                var dt = d[i].date;
                var resultat = d[i].resultat;
                var joueurBlanc = d[i].joueurBlanc;
                var joueurNoir = d[i].joueurNoir;
                // actions
                var url="<a href='gameOpen?bdId=" + bdId + "&gameId="+id+"'>"+buildImage("open")+"</a>";

                var tr_str = "<tr id=" +id+">"+

                "<td align='center'>" + id + "</td>" +
                "<td align='center'>" + event + "</td>" +
                "<td align='center'>" + site + "</td>" +
                "<td align='center'>" + dt + "</td>" +
                "<td align='center'>" + joueurBlanc + "</td>" +
                "<td align='center'>" + joueurNoir + "</td>" +
                "<td align='center'>" + resultat + "</td>" +
                "<td align='center'>" + url + "</td>" +
                "</tr>";

                $("#"+gamesTableId+" tbody").append(tr_str);
            }
            var msg="[[#{pagination.games}]]";
            var funcupdate="updateGamesHFavorites"+bdId;
            pageGame=buildPagination(data,funcupdate,msg);
            $("#"+gamesPaginationId).html(pageGame);
}
function writeUserPacks(data,tableId,paginationId){
                            var d=data.items;
                            var len = d.length;
                            $("#"+tableId+"   tbody tr").remove();

                            for(var i=0; i<len; i++){

                                var id = d[i].id;
                                var packname = d[i].packName;
                                var username = d[i].userName;

                                var tr_str = "<tr id=" +id+">"+

                                "<td align='center'>" + id + "</td>" +
                                "<td align='center'>" + packname + "</td>" +
                                "<td align='center'>" + username + "</td>"
                                "</tr>";

                                $("#"+tableId+" tbody").append(tr_str);
                            }

                            var msg="[[#{pagination.pack}]]";
                            var funcupdate="updateAdminPack";
                            paged=buildPagination(data,funcupdate,msg);
                            console.log("user pack "+paged);
                            $("#"+paginationId).html(paged);
                }
  function writePacks(data,tableId,paginationId){
                            var d=data.items;
                            var len = d.length;
                            $("#"+tableId+"   tbody tr").remove();

                            for(var i=0; i<len; i++){

                                var id = d[i].id;
                                var name = d[i].name;
                                var startDate = d[i].startDate;
                                var endDate = d[i].endDate;
                                var price = d[i].price;


                                var tr_str = "<tr id=" +id+">"+

                                "<td align='center'>" + id + "</td>" +
                                "<td align='center'>" + name + "</td>" +
                                "<td align='center'>" + startDate + "</td>" +
                                "<td align='center'>" + endDate + "</td>" +
                                "<td align='center'>" + price + "</td>" +
                                "</tr>";

                                $("#"+tableId+" tbody").append(tr_str);
                            }
                            var msg="[[#{pagination.pack}]]";
                            var funcupdate="updateAdminPack";
                            paged=buildPagination(data,funcupdate,msg);
                            $("#"+paginationId).html(paged);
                }
function writeGamesHistory(bdId,data){
            var gamesTableId='gamesTableHistory_'+bdId;
            var gamesPaginationId='gamesPaginationHistory_'+bdId;

            var d=data.items;
            var len = d.length;
            $("#"+gamesTableId+"   tbody tr").remove();

            for(var i=0; i<len; i++){

                var id = d[i].id;
                var event = d[i].event;
                var site = d[i].site;
                var dt = d[i].date;
                var resultat = d[i].resultat;
                var joueurBlanc = d[i].joueurBlanc;
                var joueurNoir = d[i].joueurNoir;

                var last = new Date(d[i].lastSeen).toLocaleString()
                // actions
                var url="<a href='gameOpen?bdId=" + bdId + "&gameId="+id+"'>"+buildImage("open")+"</a>";

                var tr_str = "<tr id=" +id+">"+

                "<td align='center'>" + id + "</td>" +
                "<td align='center'>" + event + "</td>" +
                "<td align='center'>" + site + "</td>" +
                "<td align='center'>" + dt + "</td>" +
                "<td align='center'>" + joueurBlanc + "</td>" +
                "<td align='center'>" + joueurNoir + "</td>" +
                "<td align='center'>" + resultat + "</td>" +
                "<td align='center'>" + last + "</td>" +
                "<td align='center'>" + url + "</td>" +
                "</tr>";

                $("#"+gamesTableId+" tbody").append(tr_str);
            }
            var msg="[[#{pagination.games}]]";
            var funcupdate="updateGamesHistory"+bdId;
            pageGame=buildPagination(data,funcupdate,msg);
            $("#"+gamesPaginationId).html(pageGame);
}
function closeGame(bdId,gameId){
     $.ajax({
                    url: 'game/close/'+bdId+'/'+gameId,
                    type: 'patch',
                    dataType: 'JSON',
                    success: function(response){
                        $("#li_menu_"+response.tabtoClose).remove();
                        $("#sub"+response.tabtoClose).remove(); // content

                        $('#tab'+response.tabfirst).click();

                    }
                    });
}
function closeBd(bdId){
 $.ajax({
                    url: 'bd/close/'+bdId,
                    type: 'patch',
                    dataType: 'JSON',
                    success: function(response){
                        $("#li_menu_"+response.tabtoClose).remove();
                        $("#set"+response.tabtoClose).remove(); // content

                        $('#tab'+response.tabfirst).click();

                    }
                    });
}
function buildHeaderGame(data,key){

    $('#gameDetailHeaderEvent_'+key).html(data.event);
    $('#gameDetailHeaderSite_'+key).html(data.site);
    $('#gameDetailHeaderRound_'+key).html(data.round);

    $('#gameDetailHeaderPlayerWhite_'+key).html(data.joueurBlanc+"<br>"+"("+data.whiteTitle+" "+data.whiteElo+")");
    $('#gameDetailHeaderPlayerBlack_'+key).html(data.joueurNoir+"<br>"+"("+data.blackTitle+" "+data.blackElo+")");

    $('#gameDetailHeaderDate_'+key).html(data.date);
    $('#gameDetailHeaderEventDate_'+key).html(data.eventDate);
    $('#gameDetailHeaderLastUpdate_'+key).html(data.lastUpdate);

    $('#gameDetailHeaderEco_'+key).html(data.eco);
    $('#gameDetailHeaderOpening_'+key).html(data.opening);

    $('#gameDetailHeaderCcommentairePartie_'+key).html(data.commentairePartie);
    $('#gameDetailHeaderSource_'+key).html(data.source);
    $('#gameDetailHeaderCommentateurPrincipal_'+key).html(data.commentateurPrincipal);
// FIXME
    $('#gameDetailHeaderInteret_'+key).html(""); //data.interet);
    $('#gameDetailHeaderTheorique_'+key).html(""); //data.interet);
    $('#gameDetailHeaderFavori_'+key).html(""); //data.interet);

}
function addFavorite(bdId,gameId,userId){

       var url='favorite/updateFavorite/'+bdId+'/'+gameId+'/'+userId;
          $.ajax({
                  url: url,
                  type: 'patch',
                  dataType: 'JSON',
                  success: function(response){
                          alert(" response favorite "+response)
                  }
              });

}
function updateBoardFromNavig(bdId,gameId,moveId,board,where){
       var url='gameGetFenNavigation/'+bdId+'/'+gameId+'/'+moveId+'/'+where;
       console.log("moveId="+moveId);
    $.ajax({
            url: url,
            type: 'patch',
            dataType: 'JSON',
            success: function(response){
                    board.position(response.fen);
                    window['currentMoveId_' + bdId+'_'+gameId]=response.moveId;
                    $("#link_"+response.moveId).css({"font-weight": "bold" });
                    $("#link_"+moveId).css({"font-weight": "normal" });
            }
        });
}
function showAnalyze(data,where) {
    $(where).append(data);
}