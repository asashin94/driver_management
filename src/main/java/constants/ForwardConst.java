package constants;

/**
 * リクエストパラメーターの変数名、変数値、jspファイルの名前等画面遷移に関わる値を定義するEnumクラス
 *
 */

public enum ForwardConst {

    //action
    ACT("action"),
    ACT_TOP("Top"),
    ACT_EMP("Employee"),
    ACT_DRI("Driver"),
    ACT_MAN("Management"),
    ACT_AUTH("Auth"),

    //command
    CMD("command"),
    CMD_NONE(""),
    CMD_INDEX("index"),
    CMD_SHOW("show"),
    CMD_SHOW_LOGIN("showLogin"),
    CMD_LOGIN("login"),
    CMD_LOGOUT("logout"),
    CMD_NEW("entryNew"),
    CMD_CREATE("create"),
    CMD_EDIT("edit"),
    CMD_UPDATE("update"),
    CMD_DESTROY("destroy"),

    //jsp
    FW_ERR_UNKNOWN("error/unknown"),
    FW_TOP_INDEX("topPage/index"),
    FW_LOGIN("login/login"),
    FW_EMP_INDEX("employees/index"),
    FW_EMP_SHOW("employees/show"),
    FW_EMP_NEW("employees/new"),
    FW_EMP_EDIT("employees/edit"),
    FW_DRI_INDEX("drivers/index"),
    FW_DRI_SHOW("drivers/show"),
    FW_DRI_NEW("drivers/new"),
    FW_DRI_EDIT("drivers/edit"),
    FW_MAN_INDEX("managements/index"),
    FW_MAN_SHOW("managements/show"),
    FW_MAN_NEW("managements/new"),
    FW_MAN_EDIT("managements/edit");

    /**
     * 文字列
     */

    private final String text;

    /**
     * コンストラクタ
     */

    private ForwardConst(final String text) {
        this.text=text;
    }

    /**
     * 値(文字列)取得
     */
    public String getValue() {
        return this.text;
    }

}
