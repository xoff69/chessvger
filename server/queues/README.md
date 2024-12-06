# chessvger-queues
- load player 
- load games

Exception in thread "Thread-1" java.lang.NullPointerException: Cannot invoke "java.util.List.add(Object)" because the return value of "com.xoff.chessvger.queues.position.PositionEntity.getGames()" is null
2024-12-05 07:26:03     at com.xoff.chessvger.queues.materialposition.PositionMaterialProducer.enqueuePositionMaterial(PositionMaterialProducer.java:31)
2024-12-05 07:26:03     at com.xoff.chessvger.queues.game.AppConsumerGame.run(AppConsumerGame.java:45)
2024-12-05 07:26:03     at java.base/java.lang.Thread.run(Thread.java:1583)