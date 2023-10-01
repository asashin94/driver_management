package actions.views;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)

public class ManagementView {
    /**
     * id
     */
    private Integer id;

    /**
     * 運行管理を登録した従業員
     */
    private EmployeeView employee;

    /**
     * 担当ドライバー
     */
    private String driver;

    /**
     * 目的地
     */
    private String place;

    /**
     * 出発時間
     */
    private LocalDateTime goAt;

    /**
     * 到着時間
     */
    private LocalDateTime arriveAt;

    /**
     * 戻り時間
     */
    private LocalDateTime backAt;

    /**
     * 登録日時
     */
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    private LocalDateTime updatedAt;

    /**
     * 最終編集者
     */
    private EmployeeView employee_final;

}
