package com.xoff.chessvger.repository;

import com.xoff.chessvger.util.Constants;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Persistable;

@Data
@Entity
@Table(name = "common_player")
public class CommonPlayerEntity  implements Persistable<Long> {


  @Id
  private Long id;

  private String name;
  private String nakedName;
  private String fex;
  private String sex;
  private String tit;
  private String wtit;
  private String otit;
  private String foa;
  private String ssrtng;
  private String sgm;
  private String sk;
  private String rrtng;
  private String rgm;
  private String rk;
  private String brtng;
  private String bgm;
  private String bk;
  private String bday;
  private String flag;

  @Override
  public boolean isNew() {
    return true;
  }

  public CommonPlayerEntity() {
    id = 0L;
    name = StringUtils.EMPTY;
    nakedName = StringUtils.EMPTY;
    fex = StringUtils.EMPTY;
    sex = StringUtils.EMPTY;
    tit = StringUtils.EMPTY;
    wtit = StringUtils.EMPTY;
    otit = StringUtils.EMPTY;
    foa = StringUtils.EMPTY;
    ssrtng = StringUtils.EMPTY;
    sgm = StringUtils.EMPTY;
    sk = StringUtils.EMPTY;
    rrtng = StringUtils.EMPTY;
    rgm = StringUtils.EMPTY;
    rk = StringUtils.EMPTY;
    brtng = StringUtils.EMPTY;
    bgm = StringUtils.EMPTY;
    bk = StringUtils.EMPTY;
    bday = StringUtils.EMPTY;
    flag = StringUtils.EMPTY;
  }

  public boolean isFide() {
    return id < Constants.ID_MIN_NON_FIDE_PLAYER;
  }

}
