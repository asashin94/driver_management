package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 運行管理データのDTOモデル
 *
 */

@Table(name=JpaConst.TABLE_MAN)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_MAN_GET_ALL,
            query = JpaConst.Q_MAN_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_MAN_COUNT,
            query = JpaConst.Q_MAN_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_MAN_GET_DRIVER_ALL,
            query = JpaConst.Q_MAN_GET_DRIVER_ALL_DEF)

})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity

public class Management {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.MAN_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 運行管理を登録した従業員
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.MAN_COL_EMP, nullable=false)
    private Employee employee;

    /**
     * 担当ドライバー
     */

    @Column(name = JpaConst.MAN_COL_DRI,nullable=false)
    private String driver;

    /**
     * 目的地
     */
    @Column(name = JpaConst.MAN_COL_PLACE,length = 30,nullable=false)
    private String place;

    /**
     * 出発時間
     */
    @Column(name = JpaConst.MAN_COL_GO_AT,nullable=false)
    private LocalDateTime goAt;

    /**
     * 到着時間
     */
    @Column(name = JpaConst.MAN_COL_ARRIVE_AT,nullable=false)
    private LocalDateTime arriveAt;

    /**
     * 戻り時間
     */
    @Column(name = JpaConst.MAN_COL_BACK_AT,nullable=false)
    private LocalDateTime backAt;

    /**
     * 登録日時
     */
    @Column(name = JpaConst.MAN_COL_CREATED_AT, nullable=false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = JpaConst.MAN_COL_UPDATED_AT, nullable=false)
    private LocalDateTime updatedAt;

    /**
     * 最終編集者
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.MAN_COL_EMP_FINAL,nullable=false)
    private Employee employee_final;


}
