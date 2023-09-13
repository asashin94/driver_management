package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 従業員データのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_DRI)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_DRI_GET_ALL,
            query = JpaConst.Q_DRI_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_DRI_COUNT,
            query = JpaConst.Q_DRI_COUNT_DEF)
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Driver {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.DRI_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 氏名
     */
    @Column(name = JpaConst.DRI_COL_NAME, nullable = false)
    private String name;

    /**
     * 電話番号
     */
    @Column(name = JpaConst.DRI_COL_TEL, length = 11, nullable = false)
    private Integer tel;

    /**
     * テキスト
     */
    @Column(name = JpaConst.DRI_COL_TEXT,nullable = true)
    private String text;

    /**
     *登録日時
     */
    @Column(name = JpaConst.DRI_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = JpaConst.DRI_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 削除された従業員かどうか（現役：0、削除済み：1）
     */
    @Column(name = JpaConst.DRI_COL_DELETE_FLAG, nullable = false)
    private Integer deleteFlag;

}