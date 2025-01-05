package com.xoff.chessvger.backoffice.reconciliation;

import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Reconciliation {
  Set<ReconciliationType> reconciliationTypes;
  // une structure qui contient les differents points d entree a checker pour un game
  private long gameId;

}
