package com.xoff.chessvger.ui.web.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
class ApiRequest{
  private String databaseId;
  private String tenantId;
}