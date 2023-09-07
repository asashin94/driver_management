package constants;

/**
 * DB関連の項目値を定義するインターフェース
 * ※インターフェイスに定義した変数は public static final 修飾子がついているとみなされる
 */

public interface JpaConst {

    //persistence-unit名
    String PERSISTENCE_UNIT_NAME = "driver_management";

    //データ取得件数の最大値
    int ROW_PER_PAGE=15;//1ページに表示するレコードの数

    //従業員テーブル
    String TABLE_EMP="employees";//テーブル名
    //従業員テーブルカラム
    String EMP_COL_ID="id";//id
    String EMP_COL_CODE="code";//登録コード
    String EMP_COL_NAME="name";//氏名
    String EMP_COL_PASS="password";//パスワード
    String EMP_COL_ADMIN_FLAG="admin_flag";//管理者権限
    String EMP_COL_CREATED_AT="created_at";//登録日
    String EMP_COL_UPDATED_AT="updated_at";//更新日時
    String EMP_COL_DELETE_FLAG="delete_flag";//削除フラグ


    int ROLE_ADMIN=1;//管理者権限ON(管理者)
    int ROLE_GENERAL=0;//管理者権限OFF(一般)
    int EMP_DEL_TRUE=1;//削除フラグON(削除済み)
    int EMP_DEL_FALSE=0;//削除フラグOFF(現役)

    //運行管理テーブル
    String TABLE_MAN="manegements";//テーブル名
    //運行管理テーブルカラム
    String MAN_COL_ID="id";//id
    String MAN_COL_EMP="employee_id";//日報を作成した従業員のid
    String MAN_COL_CREATED_AT="created_at";//登録日時
    String MAN_COL_UPDATED_AT="updated_at";//更新日時
    String MAN_COL_DRI="driver";//担当ドライバー
    String MAN_COL_PLACE="place";//目的地
    String MAN_COL_GO_AT="go_at";//出発時間
    String MAN_COL_ARRIVE_AT="arrive_at";//到着時間
    String MAN_COL_BACK_AT="back_at";//戻り時間
    String MAN_COL_EMP_FINAL="employee_final";//最終編集者

    //Entity名
    String ENTITY_EMP="employee";//従業員
    String ENTITY_DRI="driver";//ドライバー
    String ENTITY_MAN="management";//運行管理

    //JPQL内パラメータ
    String JPQL_PARM_CODE="code";//社員番号
    String JPQL_PARM_PASSWORD="password";//パスワード
    String JPQL_PARM_EMPLOYEE="employee";//従業員
    String JPQL_PARM_REPDATE="reportDate";

    //NamedQueryのnameとquery
    //全ての従業員をidの降順に取得する
    String Q_EMP_GET_ALL=ENTITY_EMP+".getAll";//name
    String Q_EMP_GET_ALL_DEF="SELECT e FROM Employee AS e ORDER BY e.id DESC";//query
    //全ての従業員の件数を取得する
    String Q_EMP_COUNT=ENTITY_EMP+".count";
    String Q_EMP_COUNT_DEF="SELECT COUNT(e) FROM Employee AS e";
    //社員番号とハッシュ化済パスワードを条件に未削除の従業員を取得する
    String Q_EMP_GET_BY_CODE_AND_PASS=ENTITY_EMP+".getByCodeAndPass";
    String Q_EMP_GET_BY_CODE_AND_PASS_DEF="SELECT e FROM Employee AS e WHERE e.deleteFlag = 0 AND e.code = :" + JPQL_PARM_CODE + " AND e.password = :" + JPQL_PARM_PASSWORD;
    //指定した社員番号を保持する従業員の件数を取得する
    String Q_EMP_COUNT_REGISTERED_BY_CODE = ENTITY_EMP + ".countRegisteredByCode";
    String Q_EMP_COUNT_REGISTERED_BY_CODE_DEF = "SELECT COUNT(e) FROM Employee AS e WHERE e.code = :" + JPQL_PARM_CODE;
    //全ての日報をidの降順に取得する
    String Q_REP_GET_ALL = ENTITY_DRI + ".getAll";
    String Q_REP_GET_ALL_DEF = "SELECT d FROM Report AS d ORDER BY d.id DESC";
    //全ての日報の件数を取得する
    String Q_REP_COUNT = ENTITY_DRI + ".count";
    String Q_REP_COUNT_DEF = "SELECT COUNT(d) FROM Report AS d";
    //指定した従業員が作成した日報を全件idの降順で取得する
    String Q_REP_GET_ALL_MINE = ENTITY_DRI + ".getAllMine";
    String Q_REP_GET_ALL_MINE_DEF = "SELECT d FROM Report AS d WHERE d.employee = :" + JPQL_PARM_EMPLOYEE + " ORDER BY d.id DESC";
    //指定した従業員が作成した日報の件数を取得する
    String Q_REP_COUNT_ALL_MINE = ENTITY_DRI + ".countAllMine";
    String Q_REP_COUNT_ALL_MINE_DEF = "SELECT COUNT(d) FROM Report AS d WHERE d.employee = :" + JPQL_PARM_EMPLOYEE;
    //指定した従業員が作成した指定した日時のものを取得する
    String Q_REP_COL_SPECIFIED_CREATED_AT = ENTITY_DRI + ".specifiedCreatedAt";
    String Q_REP_COL_SPECIFIED_CREATED_AT_DEF = "SELECT d FROM Report AS d WHERE d.employee = :" + JPQL_PARM_EMPLOYEE + " AND d.reportDate = :" + JPQL_PARM_REPDATE;



}
