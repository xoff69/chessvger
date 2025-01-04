package com.xoff.chessvger.builder;

import com.xoff.chessvger.common.ParamConstants;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class PgnBuilder {
  public static final String FILEPGN = ParamConstants.PATH_IMPORT + "test.pgn";
  private static final String DATA_PGN_FILE = """
      [Event "Airthings Masters Div 1 W"]
      [Site "chess.com INT"]
      [Date "2023.02.06"]
      [Round "1.1"]
      [White "Carlsen,M"]
      [Black "Sarana,A"]
      [Result "1-0"]
      [WhiteTitle "GM"]
      [BlackTitle "GM"]
      [WhiteElo "2852"]
      [BlackElo "2668"]
      [ECO "E10"]
      [Opening "Queen's pawn game"]
      [WhiteFideId "1503014"]
      [BlackFideId "24133795"]
      [EventDate "2023.02.06"]
                  
      1. d4 Nf6 2. c4 e6 3. Nf3 d5 4. g3 Bb4+ 5. Bd2 Be7 6. Bg2 c6 7. Qc2 Nbd7 8. Bf4
      O-O 9. h4 b6 10. Nc3 Bb7 11. e4 dxe4 12. Ng5 c5 13. d5 exd5 14. cxd5 Nh5 15. Be3
      f5 16. Ne6 Qb8 17. g4 fxg4 18. Bxe4 Ndf6 19. Nxf8 Qxf8 20. O-O-O Bd6 21. Bf5 Re8
      22. Rhe1 Kh8 23. Kb1 a6 24. Bg5 b5 25. Re6 Rd8 26. Ne4 Bf4 27. Bxf6 Nxf6 28.
      Nxf6 gxf6 29. Qe4 Be5 30. Qxg4 c4 31. Qh5 Qg8 32. Re7 1-0
                  
      [Event "Airthings Masters Div 1 W"]
      [Site "chess.com INT"]
      [Date "2023.02.06"]
      [Round "1.2"]
      [White "Sarana,A"]
      [Black "Carlsen,M"]
      [Result "0-1"]
      [WhiteTitle "GM"]
      [BlackTitle "GM"]
      [WhiteElo "2668"]
      [BlackElo "2852"]
      [ECO "E47"]
      [Opening "Nimzo-Indian"]
      [Variation "4.e3 O-O, 5.Bd3"]
      [WhiteFideId "24133795"]
      [BlackFideId "1503014"]
      [EventDate "2023.02.06"]
                  
      1. d4 Nf6 2. c4 e6 3. Nc3 Bb4 4. e3 O-O 5. Bd3 c5 6. Ne2 d5 7. a3 cxd4 8. axb4
      dxc3 9. Nxc3 Nc6 10. b5 Ne5 11. c5 Nxd3+ 12. Qxd3 e5 13. O-O d4 14. exd4 Qxd4
      15. Qe3 Rd8 16. Ra4 Qxe3 17. Bxe3 a6 18. Rfa1 Nd5 19. Ne4 Rb8 20. Nd6 Bd7 21.
      bxa6 Bxa4 22. a7 Ra8 23. Rxa4 Ne7 24. g4 Nc6 25. Nb5 Rd3 26. b4 Rb3 27. Bd2 Rb2
      28. Bc3 Rb1+ 29. Kg2 f6 30. Ra2 Rb3 31. h4 h5 32. gxh5 Kh7 33. Bd2 f5 34. Bc3
      Kh6 35. Rd2 Nxa7 36. Nc7 Rg8 37. Bxe5 Rxb4 38. Kg3 Rg4+ 39. Kh3 Kxh5 40. Bg3 f4
      41. Rd5+ g5 42. Bh2 Rxh4+ 43. Kg2 Nc6 44. Ne6 f3+ 45. Kg1 Re4 46. Nf4+ Kg4 47.
      Nd3 Nb4 48. Ne5+ Kf5 49. Rd1 Rxe5 50. Bxe5 Kxe5 51. Rb1 Nd3 52. Rxb7 Nxc5 53.
      Rf7 g4 54. Kh2 Ne4 55. Re7+ Kf4 56. Rf7+ Kg5 57. Re7 Nxf2 58. Kg3 Nh1+ 59. Kh2
      Rh8+ 60. Kg1 Kf4 0-1
                  
      [Event "Airthings Masters Div 1 W"]
      [Site "chess.com INT"]
      [Date "2023.02.06"]
      [Round "1.3"]
      [White "Carlsen,M"]
      [Black "Sarana,A"]
      [Result "1/2-1/2"]
      [WhiteTitle "GM"]
      [BlackTitle "GM"]
      [WhiteElo "2852"]
      [BlackElo "2668"]
      [ECO "D37"]
      [Opening "QGD"]
      [Variation "4.Nf3"]
      [WhiteFideId "1503014"]
      [BlackFideId "24133795"]
      [EventDate "2023.02.06"]
                  
      1. d4 Nf6 2. c4 e6 3. Nf3 d5 4. Nc3 Nbd7 5. Bg5 h6 6. Bh4 Bb4 7. cxd5 exd5 8.
      Qa4 Bxc3+ 9. bxc3 g5 10. Bg3 Ne4 11. Nd2 Nxg3 12. hxg3 c6 13. e3 Nb6 14. Qc2 Qe7
      15. Bd3 Be6 16. a4 a5 17. Rb1 Nc8 18. c4 dxc4 19. Nxc4 Bxc4 20. Qxc4 Nd6 21. Qc5
      Qe6 22. O-O O-O 23. Rfe1 f5 24. Rb6 Rf6 25. Reb1 Raf8 26. Rxb7 Nxb7 27. Rxb7 Kh8
      28. Bc4 Qe4 29. Qxa5 f4 30. Qc7 Qg6 31. exf4 gxf4 32. gxf4 Rxf4 33. Bd3 Qf7 34.
      Qxc6 Qf6 35. Rh7+ Kg8 36. Qd5+ Rf7 37. Qa8+ Rf8 38. Qd5+ Rf7 39. Qa8+ Rf8 40.
      Qd5+ 1/2-1/2
                  
      [Event "Airthings Masters Div 1 W"]
      [Site "chess.com INT"]
      [Date "2023.02.06"]
      [Round "1.1"]
      [White "Firouzja,Alireza"]
      [Black "Erigaisi,Arjun"]
      [Result "0-1"]
      [WhiteTitle "GM"]
      [BlackTitle "GM"]
      [WhiteElo "2785"]
      [BlackElo "2701"]
      [ECO "C50"]
      [Opening "Giuoco Pianissimo"]
      [WhiteFideId "12573981"]
      [BlackFideId "35009192"]
      [EventDate "2023.02.06"]
                  
      1. e4 e5 2. Nf3 Nc6 3. Bc4 Nf6 4. d3 Bc5 5. c3 O-O 6. b4 Be7 7. Nbd2 d5 8. Bb3
      Be6 9. O-O a6 10. Re1 Qd7 11. Bb2 dxe4 12. dxe4 Rad8 13. a4 Nh5 14. g3 Bh3 15.
      Kh1 Nf6 16. Qe2 Bg4 17. Nc4 Bd6 18. Kg2 b5 19. axb5 axb5 20. Na3 Rb8 21. Qe3 Ne7
      22. Ng5 Qc8 23. f4 h6 24. Nf3 exf4 25. gxf4 Bh3+ 26. Kh1 Qg4 27. Rg1 Qxf4 28.
      Rae1 Ng4 29. Qe2 Qxe4 30. Bc1 Ng6 31. Bc2 Qc6 32. c4 Rbe8 0-1
                  
      [Event "Airthings Masters Div 1 W"]
      [Site "chess.com INT"]
      [Date "2023.02.06"]
      [Round "1.2"]
      [White "Erigaisi,Arjun"]
      [Black "Firouzja,Alireza"]
      [Result "1-0"]
      [WhiteTitle "GM"]
      [BlackTitle "GM"]
      [WhiteElo "2701"]
      [BlackElo "2785"]
      [ECO "A45"]
      [Opening "Queen's pawn game"]
      [WhiteFideId "35009192"]
      [BlackFideId "12573981"]
      [EventDate "2023.02.06"]
                  
      1. d4 Nf6 2. Bf4 b6 3. e3 Bb7 4. Nd2 d6 5. h3 Nbd7 6. Ngf3 g6 7. Be2 Bg7 8. O-O
      O-O 9. a4 a6 10. c3 Re8 11. Bh2 e5 12. Nc4 exd4 13. cxd4 a5 14. Rc1 Nd5 15. Bxd6
      cxd6 16. Nxd6 Qb8 17. Nxe8 Qxe8 18. Bb5 Rd8 19. Qb3 h6 20. Rfe1 Rc8 21. e4 Nf4
      22. Qe3 g5 23. h4 Rxc1 24. Qxc1 Qe6 25. hxg5 Qg4 26. g3 Nh3+ 27. Kg2 hxg5 28.
      Nh2 Bxe4+ 29. Rxe4 Qxe4+ 30. Kxh3 Qxd4 31. Qxg5 Nc5 32. Ng4 Ne6 33. Qf5 Qd1 34.
      Bc4 Qh1+ 35. Nh2 Qc6 36. b3 Nd8 37. Bd5 Qd6 38. Ng4 Kf8 39. Kg2 Bd4 40. Qe4 Qc5
      41. Qf3 Ke7 42. Bc4 Qc6 43. Bd5 Qc5 44. Nh6 Bxf2 45. Nxf7 Bd4 46. Qe4+ Kd7 47.
      Qf5+ Ke7 48. Qe4+ Kd7 49. Qg4+ Ke7 50. Qh4+ Bf6 51. Qe4+ Kd7 52. Qf5+ Ke7 53.
      Nxd8 1-0
                  
      [Event "Airthings Masters Div 1 W"]
      [Site "chess.com INT"]
      [Date "2023.02.06"]
      [Round "1.3"]
      [White "Firouzja,Alireza"]
      [Black "Erigaisi,Arjun"]
      [Result "0-1"]
      [WhiteTitle "GM"]
      [BlackTitle "GM"]
      [WhiteElo "2785"]
      [BlackElo "2701"]
      [ECO "A35"]
      [Opening "English"]
      [Variation "symmetrical, four knights system"]
      [WhiteFideId "12573981"]
      [BlackFideId "35009192"]
      [EventDate "2023.02.06"]
                  
      1. c4 c5 2. Nc3 Nc6 3. Nf3 Nf6 4. g3 d5 5. cxd5 Nxd5 6. Bg2 g6 7. Ne4 c4 8. Qc2
      b5 9. b3 Bg7 10. Bb2 Ndb4 11. Qc1 Bxb2 12. Qxb2 O-O 13. a3 Bf5 14. Nh4 Qd4 15.
      Rb1 Bxe4 16. Qxd4 Nc2+ 17. Kd1 N6xd4 18. Bxe4 Nxa3 19. Ra1 b4 20. Bxa8 Nxb3 21.
      Ra2 Rxa8 22. e3 e5 23. Ke2 e4 24. f3 exf3+ 25. Nxf3 a5 26. Rf1 f6 27. Nh4 Ra6
      28. Ng2 Rd6 29. Nf4 c3 30. dxc3 Nb5 31. Rc2 Nxc3+ 32. Kf2 a4 33. Ne2 Rd2 34.
      Rxd2 Nxd2 35. Rc1 Nde4+ 36. Ke1 b3 37. Nxc3 Nxc3 38. Rxc3 b2 39. Rc8+ Kg7 40.
      Rb8 a3 0-1
                  
      [Event "Airthings Masters Div 1 W"]
      [Site "chess.com INT"]
      [Date "2023.02.06"]
      [Round "1.1"]
      [White "So,W"]
      [Black "Mamedov,Rau"]
      [Result "1/2-1/2"]
      [WhiteTitle "GM"]
      [BlackTitle "GM"]
      [WhiteElo "2766"]
      [BlackElo "2646"]
      [ECO "E94"]
      [Opening "King's Indian"]
      [Variation "orthodox variation"]
      [WhiteFideId "5202213"]
      [BlackFideId "13401653"]
      [EventDate "2023.02.06"]
                  
      1. d4 Nf6 2. c4 g6 3. Nc3 Bg7 4. e4 d6 5. Be2 O-O 6. Nf3 e5 7. O-O exd4 8. Nxd4
      Re8 9. f3 c6 10. Kh1 d5 11. cxd5 cxd5 12. Bg5 Nc6 13. Bb5 h6 14. Bh4 g5 15. Bf2
      Bd7 16. exd5 Nxd5 17. Nxd5 Nxd4 18. Bxd4 Bxb5 19. Bxg7 Kxg7 20. Qd4+ f6 21. Rfd1
      Bc6 22. Ne3 Qxd4 23. Nf5+ Kg6 24. Nxd4 Rad8 25. Kg1 Rd6 26. Rd2 Red8 27. Rad1
      Ba4 28. b3 Bb5 29. Kf2 h5 30. g3 a5 31. a3 Be8 32. f4 Bf7 33. h4 g4 34. b4 axb4
      35. axb4 Rd5 36. Nb3 Rxd2+ 37. Rxd2 Rc8 38. Nc5 b6 39. Nd7 Rc3 40. Rd6 Rf3+ 41.
      Kg2 Kg7 42. Nxf6 Rb3 43. Rxb6 Bg6 44. f5 Bf7 45. Rd6 Rxb4 46. Nd7 Rb2+ 47. Kg1
      Rb1+ 48. Kf2 Rb5 49. f6+ Kg6 50. Ke3 Kf5 51. Rd4 Ke6 52. Re4+ Kxd7 53. Re7+ Kd6
      54. Rxf7 Ke6 55. Rf8 Rf5 56. Ke4 Rf3 57. f7 Rxg3 58. Kf4 Rf3+ 59. Kg5 Rxf7 60.
      Re8+ Re7 61. Rf8 g3 62. Rf1 g2 63. Rg1 Rg7+ 64. Kf4 Rg4+ 65. Kf3 Kf5 66. Rxg2
      Rxg2 67. Kxg2 Kg4 68. Kh2 Kxh4 69. Kg2 Kg4 70. Kh2 h4 71. Kg2 h3+ 72. Kh2 Kh4
      73. Kh1 Kg3 74. Kg1 h2+ 75. Kh1 Kh3 1/2-1/2
                  
      [Event "Airthings Masters Div 1 W"]
      [Site "chess.com INT"]
      [Date "2023.02.06"]
      [Round "1.2"]
      [White "Mamedov,Rau"]
      [Black "So,W"]
      [Result "1/2-1/2"]
      [WhiteTitle "GM"]
      [BlackTitle "GM"]
      [WhiteElo "2646"]
      [BlackElo "2766"]
      [ECO "C44"]
      [Opening "Scotch gambit"]
      [Variation "Cochrane-Shumov defence"]
      [WhiteFideId "13401653"]
      [BlackFideId "5202213"]
      [EventDate "2023.02.06"]
                  
      1. e4 e5 2. Nf3 Nc6 3. d4 exd4 4. Bc4 Bc5 5. Ng5 Nh6 6. Nxf7 Nxf7 7. Bxf7+ Kxf7
      8. Qh5+ g6 9. Qxc5 d5 10. O-O dxe4 11. c3 Qd6 12. Qxd6 cxd6 13. cxd4 Nxd4 14.
      Nc3 Bg4 15. Nxe4 Nc2 16. Nxd6+ Ke6 17. Bf4 g5 18. Bg3 Nxa1 19. Rxa1 Rhd8 20. f3
      Bf5 21. Re1+ Kf6 22. h4 h6 23. Be5+ Ke6 24. g4 Bh7 25. Bg3+ Kf6 26. Be5+ Ke6 27.
      Bg3+ Kf6 28. Be5+ Ke6 1/2-1/2
                  
      [Event "Airthings Masters Div 1 W"]
      [Site "chess.com INT"]
      [Date "2023.02.06"]
      [Round "1.3"]
      [White "So,W"]
      [Black "Mamedov,Rau"]
      [Result "1-0"]
      [WhiteTitle "GM"]
      [BlackTitle "GM"]
      [WhiteElo "2766"]
      [BlackElo "2646"]
      [ECO "E94"]
      [Opening "King's Indian"]
      [Variation "orthodox variation"]
      [WhiteFideId "5202213"]
      [BlackFideId "13401653"]
      [EventDate "2023.02.06"]
                  
      1. d4 Nf6 2. c4 g6 3. Nc3 Bg7 4. e4 d6 5. Be2 O-O 6. Nf3 e5 7. O-O exd4 8. Nxd4
      Re8 9. f3 c6 10. Kh1 d5 11. cxd5 cxd5 12. Bg5 Nc6 13. Bb5 h6 14. Nxc6 bxc6 15.
      Bxc6 hxg5 16. Bxa8 d4 17. Ne2 Bd7 18. Bd5 Nxd5 19. exd5 Bb5 20. Re1 Qxd5 21. Ng3
      Rxe1+ 22. Qxe1 d3 23. Qd2 g4 24. fxg4 Bc6 25. g5 Bxb2 26. Rg1 Be5 27. a3 a6 28.
      Nf1 Qe4 29. Ng3 Qd5 30. Nf1 Qd4 31. Qe3 Qc3 32. Qb6 Bd4 33. Qb4 Qxb4 34. axb4
      Kf8 35. Nd2 Bxg1 36. Kxg1 Ke7 37. Kf2 Kd6 38. g4 Ke5 39. Ke3 Bb5 40. h4 Bd7 41.
      Nf3+ Kd6 42. h5 Ke7 43. h6 Kf8 44. Ne5 Be6 45. Kxd3 Kg8 46. Kd4 Bb3 47. Kc5 Bd1
      48. Kb6 Be2 49. Kc7 Bd1 50. Kd6 Kf8 51. Nd7+ Kg8 52. Ke7 Bb3 53. Ne5 Bd1 54. Kf6 1-0
                  
      [Event "Airthings Masters Div 1 W"]
      [Site "chess.com INT"]
      [Date "2023.02.06"]
      [Round "1.4"]
      [White "Mamedov,Rau"]
      [Black "So,W"]
      [Result "1/2-1/2"]
      [WhiteTitle "GM"]
      [BlackTitle "GM"]
      [WhiteElo "2646"]
      [BlackElo "2766"]
      [ECO "C50"]
      [Opening "Giuoco Pianissimo"]
      [WhiteFideId "13401653"]
      [BlackFideId "5202213"]
      [EventDate "2023.02.06"]
                  
      1. e4 e5 2. Nf3 Nc6 3. Bc4 Bc5 4. d3 d6 5. c3 Nf6 6. Nbd2 a5 7. h3 O-O 8. Bb3
      Be6 9. O-O Bxb3 10. Nxb3 h6 11. Nxc5 dxc5 12. Be3 c4 13. dxc4 Qxd1 14. Raxd1
      Nxe4 15. Rfe1 Nf6 16. g4 Rfd8 17. Rxd8+ Rxd8 18. g5 Nh7 19. gxh6 g6 20. b4 f6
      21. a3 g5 22. h4 g4 23. Nd2 axb4 24. axb4 Ne7 25. Ne4 Nf5 26. Nxf6+ Nxf6 27. Bg5
      Rf8 28. Rxe5 Nd7 29. Re4 Kh7 30. c5 Rf7 31. Rxg4 Nxh6 32. Bxh6 Kxh6 33. Kf1 Kh5
      34. Re4 c6 35. Ke2 Nf6 36. Rf4 Re7+ 37. Kf3 Nd5 38. Rf5+ Kxh4 39. b5 Nxc3 40.
      bxc6 bxc6 41. Rf6 Rc7 42. Rf5 Re7 43. Rf6 Rc7 44. Rf5 Rc8 45. Kf4 Re8 46. Rf7
      Ne2+ 47. Kf5 Nd4+ 48. Kf4 Ne6+ 49. Kf5 Nd4+ 50. Kf4 Kh5 51. Rf6 Ne2+ 52. Kf5
      Nd4+ 53. Kf4 Rd8 54. Ke5 Rd5+ 55. Ke4 Rd8 56. f4 Kg4 57. Rg6+ Kh5 58. Rd6 Rxd6
      59. cxd6 Ne6 60. Ke5 Nd8 61. d7 Kg6 62. Kd6 Kf6 63. Kc7 Ke7 64. f5 Nf7 65. Kxc6
      Ne5+ 66. Kd5 Nxd7 67. f6+ Nxf6+ 1/2-1/2
                  
      [Event "Airthings Masters Div 1 W"]
      [Site "chess.com INT"]
      [Date "2023.02.06"]
      [Round "1.1"]
      [White "Gukesh,D"]
      [Black "Nakamura,Hi"]
      [Result "0-1"]
      [WhiteTitle "GM"]
      [BlackTitle "GM"]
      [WhiteElo "2718"]
      [BlackElo "2768"]
      [ECO "E29"]
      [Opening "Nimzo-Indian"]
      [Variation "Saemisch, Capablanca variation"]
      [WhiteFideId "46616543"]
      [BlackFideId "2016192"]
      [EventDate "2023.02.06"]
                  
      1. d4 Nf6 2. c4 e6 3. Nc3 Bb4 4. e3 O-O 5. a3 Bxc3+ 6. bxc3 c5 7. Bd3 Nc6 8. Ne2
      b6 9. e4 Ne8 10. h4 e5 11. h5 d6 12. h6 g6 13. Be3 Na5 14. Ng3 Ba6 15. Qe2 cxd4
      16. cxd4 Nb3 17. Rb1 Nxd4 18. Bxd4 exd4 19. Qb2 Rc8 20. Qxd4 Qf6 21. Ne2 Qxd4
      22. Nxd4 Nf6 23. Kd2 Bxc4 24. f3 d5 25. e5 Bxd3 26. Kxd3 Nd7 27. f4 Rc4 28. Rhc1
      Rfc8 29. Ne2 f6 30. exf6 Kf7 31. Rxc4 Rxc4 32. Ke3 Nc5 33. Rd1 Ra4 34. Rxd5
      Rxa3+ 35. Kd4 Rd3+ 36. Kc4 Rxd5 37. Kxd5 Kxf6 38. g4 a5 39. Nd4 a4 40. g5+ Kf7
      41. Nb5 Ne6 42. Ke5 Nc7 43. Nc3 a3 44. f5 gxf5 45. Kxf5 b5 46. Na2 Na6 47. Nc1
      b4 48. Ke5 Nc5 0-1
                  
      [Event "Airthings Masters Div 1 W"]
      [Site "chess.com INT"]
      [Date "2023.02.06"]
      [Round "1.2"]
      [White "Nakamura,Hi"]
      [Black "Gukesh,D"]
      [Result "1-0"]
      [WhiteTitle "GM"]
      [BlackTitle "GM"]
      [WhiteElo "2768"]
      [BlackElo "2718"]
      [ECO "A07"]
      [Opening "Reti"]
      [Variation "King's Indian attack (Barcza system)"]
      [WhiteFideId "2016192"]
      [BlackFideId "46616543"]
      [EventDate "2023.02.06"]
                  
      1. Nf3 d5 2. g3 c6 3. Bg2 Bg4 4. h3 Bh5 5. d3 Nf6 6. Nbd2 Nbd7 7. g4 Bg6 8. Nh4
      e5 9. e3 Bd6 10. b3 a5 11. a3 Nf8 12. Bb2 Ne6 13. Qe2 Qc7 14. c4 O-O 15. Nxg6
      hxg6 16. h4 Rfe8 17. g5 Nf4 18. Qf1 Nxg2+ 19. Qxg2 Nh5 20. cxd5 cxd5 21. Rc1 Qe7
      22. Qxd5 Bxa3 23. Bxa3 Qxa3 24. Qc5 Qxc5 25. Rxc5 a4 26. Ke2 axb3 27. Rb1 Ra4
      28. Rxb3 Rxh4 29. Rxb7 Rg4 30. Ne4 f5 31. gxf6 gxf6 32. Rcc7 Rg1 33. Nd6 Ra8 34.
      Nf7 Ng7 35. Rc6 Nf5 36. Rxf6 Rf8 37. Kf3 Kg7 38. Ng5+ Kxf6 39. Nh7+ Ke6 40.
      Nxf8+ Kf6 41. Rb6+ Kg7 42. Nd7 Nh4+ 43. Ke4 Rg4+ 44. Kxe5 Nf3+ 45. Ke6 Rg2 46.
      Rb2 Ng5+ 47. Ke5 Nh3 48. Nf6 Rxf2 49. Rb7+ Kf8 50. e4 Nf4 51. d4 Nd3+ 52. Ke6
      Nf4+ 53. Kd6 Ne2 54. Nd7+ Kg7 55. d5 Nc3 56. Nc5+ Kh6 57. Ke5 g5 58. d6 Rd2 59.
      d7 g4 60. Rb6+ 1-0
                  
      [Event "Airthings Masters Div 1 W"]
      [Site "chess.com INT"]
      [Date "2023.02.06"]
      [Round "1.3"]
      [White "Gukesh,D"]
      [Black "Nakamura,Hi"]
      [Result "1/2-1/2"]
      [WhiteTitle "GM"]
      [BlackTitle "GM"]
      [WhiteElo "2718"]
      [BlackElo "2768"]
      [ECO "A46"]
      [Opening "Queen's pawn game"]
      [WhiteFideId "46616543"]
      [BlackFideId "2016192"]
      [EventDate "2023.02.06"]
                  
      1. d4 Nf6 2. Nf3 e6 3. Bf4 c5 4. e3 d5 5. Nbd2 Qb6 6. Rb1 Bd7 7. c3 Bb5 8. c4
      dxc4 9. Nxc4 Bxc4 10. Bxc4 Qb4+ 11. Nd2 cxd4 12. exd4 Nc6 13. O-O Rd8 14. a3 Qb6
      15. Be3 Qc7 16. Qe2 Bd6 17. g3 O-O 18. Rbc1 Qb8 19. Rfd1 Be7 20. Nf3 Nd5 21. b4
      Bf6 22. Bd3 Nxe3 23. fxe3 g6 24. Be4 Ne7 25. g4 Rc8 26. g5 Bg7 27. Qb5 b6 28.
      Qd7 Nf5 29. Bxf5 exf5 30. d5 Bb2 31. Rxc8 Qxc8 32. Qxa7 Qc2 33. Rd2 Qe4 34. Kf2
      Bc1 35. Re2 Re8 36. Qxb6 f4 37. Qb5 fxe3+ 38. Kg3 Bxa3 39. d6 Bxb4 40. Qb6 Rf8
      41. d7 Qe7 42. Rxe3 Bd6+ 43. Kg2 Qxd7 44. Qd4 Qc6 45. Qe4 Qxe4 46. Rxe4 Ra8 1/2-1/2
                  
      [Event "Airthings Masters Div 2 W"]
      [Site "chess.com INT"]
      [Date "2023.02.06"]
      [Round "1.1"]
      [White "Nepomniachtchi,I"]
      [Black "Pichot,A"]
      [Result "1/2-1/2"]
      [WhiteTitle "GM"]
      [BlackTitle "GM"]
      [WhiteElo "2793"]
      [BlackElo "2638"]
      [ECO "C55"]
      [Opening "Two knights defence (Modern bishop's opening)"]
      [WhiteFideId "4168119"]
      [BlackFideId "110973"]
      [EventDate "2023.02.06"]
                  
      1. e4 e5 2. Nf3 Nc6 3. Bc4 Nf6 4. d3 h6 5. Nc3 Bc5 6. h3 a6 7. Be3 d6 8. d4 exd4
      9. Nxd4 Ne5 10. Bb3 O-O 11. O-O Re8 12. Qd2 Ng6 13. f3 Nh5 14. Rae1 Nhf4 15. Nd5
      Nxd5 16. Bxd5 c6 17. Bb3 Qf6 18. c3 Bd7 19. Bc2 Re7 20. f4 Rae8 21. f5 Ne5 22.
      b3 d5 23. exd5 cxd5 24. Kh1 Nc6 25. Bg1 Nxd4 26. Rxe7 Rxe7 27. Bxd4 Bxd4 28.
      cxd4 Qg5 29. Qf2 Bb5 30. Re1 Rxe1+ 31. Qxe1 Qf4 32. a4 Bd7 33. Qe7 Qf1+ 34. Kh2
      Qf4+ 35. Kh1 Qf1+ 36. Kh2 Qf4+ 37. Kh1 1/2-1/2
                  
      [Event "Airthings Masters Div 2 W"]
      [Site "chess.com INT"]
      [Date "2023.02.06"]
      [Round "1.2"]
      [White "Pichot,A"]
      [Black "Nepomniachtchi,I"]
      [Result "0-1"]
      [WhiteTitle "GM"]
      [BlackTitle "GM"]
      [WhiteElo "2638"]
      [BlackElo "2793"]
      [ECO "C72"]
      [Opening "Ruy Lopez"]
      [Variation "modern Steinitz defence, 5.O-O"]
      [WhiteFideId "110973"]
      [BlackFideId "4168119"]
      [EventDate "2023.02.06"]
                  
      1. e4 e5 2. Nf3 Nc6 3. Bb5 a6 4. Ba4 d6 5. O-O Bd7 6. c4 g5 7. d4 g4 8. d5 Nce7
      9. Bxd7+ Qxd7 10. Ne1 f5 11. exf5 h5 12. Nc3 Nxf5 13. Ne4 O-O-O 14. b4 Nd4 15.
      Rb1 Qf5 16. Qd3 Nf6 17. Nxf6 Qxf6 18. Be3 Bg7 19. b5 e4 20. Qa3 Ne2+ 21. Kh1 Qh4
      22. bxa6 Be5 23. axb7+ Kd7 24. g3 Qh3 25. Qa4+ Ke7 26. Ng2 h4 27. Bg5+ Bf6 28.
      Bxf6+ Kxf6 29. gxh4 Nd4 0-1
                  
      [Event "Airthings Masters Div 2 W"]
      [Site "chess.com INT"]
      [Date "2023.02.06"]
      [Round "1.3"]
      [White "Nepomniachtchi,I"]
      [Black "Pichot,A"]
      [Result "1-0"]
      [WhiteTitle "GM"]
      [BlackTitle "GM"]
      [WhiteElo "2793"]
      [BlackElo "2638"]
      [ECO "C55"]
      [Opening "Two knights defence (Modern bishop's opening)"]
      [WhiteFideId "4168119"]
      [BlackFideId "110973"]
      [EventDate "2023.02.06"]
                  
      1. e4 e5 2. Nf3 Nc6 3. Bc4 Nf6 4. d3 h6 5. Nc3 Bc5 6. h3 a6 7. Be3 d6 8. Bxc5
      dxc5 9. O-O O-O 10. Nd5 Be6 11. Ne3 Bxc4 12. Nxc4 Re8 13. c3 Qd7 14. Qe2 Rad8
      15. Rfd1 b6 16. Ne3 Qe6 17. a3 a5 18. Kh2 Rd7 19. g4 Nh7 20. h4 Red8 21. Rd2 Ne7
      22. h5 Qf6 23. Rg1 Ng5 24. Nxg5 Qxg5 25. Rg3 Rd6 26. Rd1 b5 27. Nf5 Nxf5 28.
      gxf5 Qh4+ 29. Kg2 c4 30. d4 exd4 31. e5 Rd5 32. f6 dxc3 33. Rxd5 Rxd5 34. Qf3
      Qd4 35. Rxg7+ Kf8 36. Rh7 Ke8 37. Qf5 1-0
                  
      [Event "Airthings Masters Div 2 W"]
      [Site "chess.com INT"]
      [Date "2023.02.06"]
      [Round "1.1"]
      [White "Lazavik,Denis"]
      [Black "Predke,A"]
      [Result "0-1"]
      [WhiteTitle "GM"]
      [BlackTitle "GM"]
      [WhiteElo "2541"]
      [BlackElo "2684"]
      [ECO "E11"]
      [Opening "Bogo-Indian defence, Nimzovich variation"]
      [WhiteFideId "13515110"]
      [BlackFideId "24107581"]
      [EventDate "2023.02.06"]
                  
      1. d4 Nf6 2. c4 e6 3. Nf3 Bb4+ 4. Bd2 Qe7 5. g3 Nc6 6. Nc3 Bxc3 7. Bxc3 Ne4 8.
      Rc1 O-O 9. Bg2 d6 10. d5 Nd8 11. O-O f5 12. Be1 e5 13. Qc2 a5 14. Nh4 Nc5 15. f4
      exf4 16. gxf4 Bd7 17. Kh1 b6 18. Bf3 Nf7 19. Rg1 Nh6 20. h3 g6 21. Ng2 Rae8 22.
      Bh4 Qg7 23. e3 Ne4 24. Kh2 Kh8 25. Be1 Ng8 26. Bxe4 Rxe4 27. Bc3 Nf6 28. Qf2 Qh6
      29. Nh4 Kg8 30. b3 Re7 31. Ba1 Ne4 32. Qe1 Kf7 33. Nf3 Ke8 34. Bd4 Qh5 35. Ng5
      h6 36. Nxe4 fxe4 37. Rg3 g5 38. Qd2 Ref7 39. Rf1 Kd8 40. Kg2 Kc8 41. a3 Bf5 42.
      b4 axb4 43. axb4 g4 44. hxg4 Bxg4 45. Rg1 Bf3+ 46. Kf1 Kd7 47. Rg7 Rxg7 48.
      Rxg7+ Rf7 49. Rxf7+ Qxf7 50. Qh2 b5 51. Qh3+ Kd8 52. cxb5 Qxd5 53. Kf2 h5 54. b6
      cxb6 55. Bxb6+ Ke7 56. Qh4+ Kf7 57. Kg3 Qd2 58. f5 Qe1+ 59. Kh3 Bg2+ 60. Kxg2
      Qxh4 0-1
                  
                  
      [Event "75th ch-MNE 2023"]
      [Site "Podgorica MNE"]
      [Date "2023.02.05"]
      [Round "1.9"]
      [White "Andjelic,Djordje"]
      [Black "Pecurica,M"]
      [Result "0-1"]
      [BlackTitle "FM"]
      [WhiteElo "1918"]
      [BlackElo "2320"]
      [ECO "D00"]
      [Opening "Levitsky attack (Queen's bishop attack)"]
      [WhiteFideId "16502094"]
      [BlackFideId "944963"]
      [EventDate "2023.02.05"]
                  
      1. d4 d5 2. Bg5 f6 3. Bh4 h5 4. f3 Nc6 5. Nc3 e5 6. dxe5 d4 7. exf6 Nxf6 8. Ne4
      Be7 9. Nxf6+ Bxf6 10. Bxf6 Qxf6 11. Qd2 Be6 12. Nh3 Bxh3 13. gxh3 Qh4+ 14. Kd1
      O-O-O 15. Kc1 Rhe8 16. b3 Re3 17. a3 Qf4 18. h4 Qxh4 19. Kb2 Qf4 20. Rd1 g5 21.
      Qe1 h4 22. Rd3 Kb8 23. Rg1 Ne5 24. Rxe3 Qxe3 25. Bh3 d3 26. exd3 Qd4+ 27. Qc3
      Nxd3+ 0-1
          
                  
      """;

  public static void writeToFile() {
    try (PrintStream out = new PrintStream(new FileOutputStream(FILEPGN))) {
      out.print(PgnBuilder.DATA_PGN_FILE);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
