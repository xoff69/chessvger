package com.xoff.chessvger.chess.database;


import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
public class DatabaseManager  {

/*


    public long duplicate(long userId) {
        // TODO
        log.info("databasemanager.duplicate FIXME " + userId);

        // creer une database dans le repertoire target,
        // FIXME le probleme c est que ca s appuie sur le create name

        Database newDatabase = new Database();
        newDatabase.setId(DbKeyManager.getInstance().getDbKeyGenerator().getNext());
        newDatabase.setName(this.database.getName());
        newDatabase.setDescription(this.database.getDescription() + "  copie");
        newDatabase.setLastUpdate(System.currentTimeMillis());
        GlobalManager.getInstance().getDatabaseMap().add(newDatabase.getId(), newDatabase);
        GlobalManager.getInstance().getFiliationMap().add(this.getDatabaseId(), newDatabase.getId());

        // creation du repertoire
        DatabaseManager databaseManager = new DatabaseManager(newDatabase);
        GlobalManager.getInstance().addDatabaseManager(databaseManager);
        List<CommonGame> games = this.getGlobalGameManager().getAllGamesReadOnly();
        for (CommonGame commonGame : games) {
            databaseManager.upsert(commonGame, DBOperation.AJOUT);
        }
        newDatabase.setNbgames(games.size());
        databaseManager.postUpdateGameAndStat();
        log.info("duplicate postUpdateGameAndStat PGN" + games.size());
        // duplicate gameOfBbPlayer
        List<Long> playersIds = getPlayerOfDbManager().listIdsOfPlayer();
        for (Long l : playersIds) {
            databaseManager.getPlayerOfDbManager().add(l);
        }

        databaseManager.finish();

        GlobalManager.getInstance().getDatabaseMap().add(newDatabase.getId(), newDatabase);
        return newDatabase.getId();
    }



    public boolean parseMoves2(CommonGame game) {

        OneGameTree ogt = new OneGameTree(game.getMoves());

        //TraceUtils.traceInFile("ogt="+ogt.toString());
        Position position = new Position();
        ItemGameTree courant = ogt.getParent();
        int compteur = 0;
        while (courant != null) {

            ResultInterpretation rer = BoardManager.play(position, courant.getCurrentMove());
            //  TraceUtils.traceInFile("\n "+(compteur+1)+ " je joue : "+courant.getCurrentMove()+" "+position.toString());

            if (rer.isInvalide()) {
                log.error(game.toString());
                return false;
            }
            // tres important ce OU, en effet, ce n est qu a la fin du parsin des coups que l information est complete
            game.setInformationsFaitDeJeu(game.getInformationsFaitDeJeu() | rer.getFaitsdejeu());
            CoupleZobristMaterial czm = position.evaluateZobristAndMaterial();
            game.setLastPosition(czm.getZobrist());

            GlobalManager.getInstance().getDatabaseManager(database.getId()).getPositionManager()
                    .add(czm.getZobrist(), game.getId());


            GlobalManager.getInstance().getDatabaseManager(database.getId()).getMaterialManager()
                    .add(czm.getMaterial(), game.getId());

            courant = courant.getNextMove();
            compteur++;
        }
        game.setNbcoups(compteur);

        return true;
    }

    public String exportePgn() {

        String result = "";
        for (String s : Constants.ALL_FIRST_MOVE) {

            ICommonGameManager g =
                    GlobalManager.getInstance().getDatabaseManager(database.getId()).getGlobalGameManager()
                            .get(s);
            List<CommonGame> l = g.getGames();
            for (CommonGame c : l) {
                // TODO result.append(c.toPGN()).append(System.lineSeparator());
            }
        }
        return result;
    }




    public List<CommonGame> search(Filter filter) {

        List<CommonGame> listRes = new ArrayList();
        if (filter.isCasGeneral()) {
            ExecutorService taskExecutor = Executors.newFixedThreadPool(Constants.ALL_FIRST_MOVE.size());
            SearchRun[] tab = new SearchRun[Constants.ALL_FIRST_MOVE.size()];
            int i = 0;
            //    log.info(">>>>>>>>>>>recherche +"+filter);
            for (String move : Constants.ALL_FIRST_MOVE) {
                SearchRun runEntity = new SearchRun(this, filter, move);
                tab[i++] = runEntity;
                taskExecutor.execute(runEntity);
            }
            taskExecutor.shutdown();
            try {
                taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException ex) {
                log.error(ex.getMessage());
            }

            for (i = 0; i < tab.length; i++) {
                //     log.info("avant addDatabaseManager all:" + i + "-" + tab[i]);
                if (tab[i].getResultat() != null) {
                    listRes.addAll(tab[i].getResultat());
                }
            }
        }
        // on ajoute ici la recherche par position et par material
        if (filter.isCasOtherCriteria()) {
            //   log.info(">>>>>>>>>>>recherche  isCasOtherCriteria +");
            //     log.info("material is like " + materialValue);
            List<CommonGame> listResultatMaterial = searchMaterial(filter);

            listRes.addAll(listResultatMaterial);
        }
        // @TODO faire des ET ou Ou selon le filtre
        if (filter.isCasPosition()) {
            // si ce qu'il y a dans lpost n'est pas dans liste resultat, on supprime de list resultat
            // si ce qu'il y a dans lpost

            //   log.info(">>>>>>>>>>>recherche  position +" + listeResultat.size() + "---" + lPost.size());

            listRes.addAll(searchPosition(filter));
            //    log.info("<<<<recherche  position +" + listeResultat.size() + "---" + lPost.size());
        }


        listRes = CommonGameManager.removeDuplicate(listRes);
        listRes = CommonGameManager.restrictSize(listRes, ParamConstants.MAX_RESULT);
        //    log.info("<<<<recherche +" + listRes.size());
        return listRes;
    }


    private List<CommonGame> searchMaterial(Filter filter) {
        List<CommonGame> result = new ArrayList<>();
        List<Long> lo =
                GlobalManager.getInstance().getDatabaseManager(database.getId()).getMaterialManager()
                        .search(filter);
        for (long l : lo) {
            result.add(getGameById(l));
        }
        System.out.println("search searchMaterial " + lo.size());
        return result;
    }

    private List<CommonGame> searchPosition(Filter filter) {

        List<CommonGame> result = new ArrayList<>();
        List<Long> lo =
                GlobalManager.getInstance().getDatabaseManager(database.getId()).getPositionManager()
                        .search(filter);
        for (long l : lo) {
            result.add(getGameById(l));
        }
        System.out.println("search position" + lo.size());
        return result;
    }




    public List<JoueurView> getPlayersWithGames(String param, Pageable paging) {
        log.info(">getPlayersWithGames ");
        List<JoueurView> playersView = new ArrayList<>();
        List<Long> playersId = getPlayerOfDbManager().listIdsOfPlayer();
        log.info(">getPlayersWithGames  playersId " + playersId.size());
        int cpt = 0;
        // FIXME on ne ramene que les n premiers
        // FIXNE avec le param
        for (int i = 0; i < playersId.size() && i < paging.pageSize; i++) {
            CommonPlayer p =
                    GlobalManager.getInstance().getCommonPlayerManager().findById(playersId.get(i));
            JoueurView jv = new JoueurView();
            jv.setJoueur(p);

            int nb = getGameOfAPlayerManager().countGameOfAPlayer(p.getId());

            jv.setNbgames(nb);
            playersView.add(jv);
        }
        log.info(">getPlayersWithGames  playersView " + playersView.size());
        Collections.sort(playersView, new Comparator<JoueurView>() {
            @Override
            public int compare(JoueurView o1, JoueurView o2) {
                return o2.getNbgames() - o1.getNbgames();
            }
        });
        log.info("<getPlayersWithGames " + playersView.size());
        return playersView;

    }


    public void postUpdateGameAndStat(CommonGame game) {

        log.info(">>>postUpdateGameAndStat");

        String s = game.getFirstMove();
        IBrowserStatManager bs = GlobalManager.getInstance().getDatabaseManager(database.getId())
                .getGlobalBrowserStatManager().get(s);    // on ne met à jour qu'un seul game
        // et encore c'est un max, s'il existait deja sans doute qu'on ne doit rien faire
        // FIXME
        List<CommonGame> li = new ArrayList();
        li.add(game);
        bs.browseFirstMove(li);

        database.setLastUpdate(System.currentTimeMillis());
    }


    public int postUpdateGameAndStat() {

        int total = 0;
        long bdId = database.getId();

        for (String firstmove : Constants.ALL_FIRST_MOVE) {


            ICommonGameManager commonGameManager =
                    GlobalManager.getInstance().getDatabaseManager(bdId).getGlobalGameManager()
                            .get(firstmove);
            commonGameManager.update();
            total += commonGameManager.nbgames();
            // log.info("post update " + g.nbgames());
            IBrowserStatManager bs =
                    GlobalManager.getInstance().getDatabaseManager(bdId).getGlobalBrowserStatManager()
                            .get(firstmove);
            if (bs != null) {
                bs.clear();
                List<CommonGame> liste = commonGameManager.getGames();
                //  log.info(firstmove+" nouvelles parties to browse:-" + liste.size()+ "/"+commonGameManager.getGames().size());
                bs.browseFirstMove(liste);
            }


        }
        database.setLastUpdate(System.currentTimeMillis());
        return total;
    }


    public long getLastUpdate() {
        return database.getLastUpdate();
    }


    public CommonGame upsert(CommonGame item, DBOperation operation) {

        //  log.info("a-0 ajout de :" + item);
        //       log.info("findOrAdd ou met à jour : " + operation + "-" + item);
        ICommonGameManager gameManager =
                GlobalManager.getInstance().getDatabaseManager(database.getId()).getGlobalGameManager()
                        .get(item.getFirstMove());
        if (gameManager == null) {
            // FIXME exception
            log.error(" error " + item);
            return item;
        }
        //   log.info("a-1) ajout de :" + item);
        switch (operation) {
            case AJOUT:
            case UPDATE:

                if (item.getId() == 0) {
                    item.setId(DbKeyManager.getInstance().getDbKeyGenerator().getNext());
                }
                item.setLastUpdate(System.currentTimeMillis());
                if (parseMoves2(item)) {
                    gameManager.upsert(item, operation);
                    GlobalManager.getInstance().getDatabaseManager(database.getId()).getGameOfAPlayerManager()
                            .ajoute(item.getBlackFideId(), item.getId());
                    GlobalManager.getInstance().getDatabaseManager(database.getId()).getGameOfAPlayerManager()
                            .ajoute(item.getWhiteFideId(), item.getId());
                    //    log.info("ajout ok"+item);
                } else {
                    log.error(item.toString());
                    rejetUtil.ecritRejet(item, "INT");
                }

                break;

            case DUPLICATE:


                break;
            case DELETE:
                item.setDeleted(true);
                item.setLastUpdate(System.currentTimeMillis());
                gameManager.upsert(item, operation);
                log.info("efface ok");
                break;
            case UNDELETE:
                item.setDeleted(false);
                item.setLastUpdate(System.currentTimeMillis());
                gameManager.upsert(item, operation);
                break;

            default:
        }

        return item;
    }


    public boolean optimiser(boolean withDeleted, boolean deleteDoublon) {
        //sauvegarde les fichiers

        log.info(">>>optimiser:" + withDeleted + "-" + deleteDoublon);

        IDatabaseManager dm = GlobalManager.getInstance().getDatabaseManager(database.getId());
        List<CommonGame> listAGarder = new ArrayList();
        for (String s : Constants.ALL_FIRST_MOVE) {
            ICommonGameManager gameManager = dm.getGlobalGameManager().get(s);

            List<CommonGame> allGames = gameManager.getGames();
            int compteur = 0;
            for (CommonGame game : allGames) {
                // log.info("game a optimiser:" + game);
                boolean toRemove = false;
                if (withDeleted) {
                    if (game.isDeleted()) {
                        toRemove = true;
                    }
                }
                if (deleteDoublon && compteur > 0) {
                    List<CommonGame> sousList = allGames.subList(0, compteur);
                    for (CommonGame gameBefore : sousList) {

            if (game.isDoublon(gameBefore)) {
              toRemove = true;
              break;
            } // TODO
                    }
                }

                if (!toRemove) {

                    listAGarder.add(game);

                }
                compteur++;
                //   log.info("on a vu:" + compteur);
            }
        } // fin for sur s
        clear();
        //    log.info("optimiser apres init:");

        //save all
        for (CommonGame g : listAGarder) {
            //    log.info("on update:" + g);
            upsert(g, DBOperation.AJOUT);
        }
        postUpdateGameAndStat();

        log.info("optimisation terminee");
        return true;
    }

*/
}
