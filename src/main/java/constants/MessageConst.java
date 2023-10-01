package constants;

/**
 * 各出力メッセージを定義するEnumクラス
 *
 */
public enum MessageConst {

    //認証
    I_LOGINED("ログインしました"),
    E_LOGINED("ログインに失敗しました。"),
    I_LOGOUT("ログアウトしました。"),

    //DB更新
    I_REGISTERED("登録が完了しました。"),
    I_UPDATED("更新が完了しました。"),
    I_DELETED("削除が完了しました。"),

    //バリデーション
    E_NONAME("氏名を入力してください。"),
    E_NOTEL("電話番号を入力してください。"),
    E_NOPASSWORD("パスワードを入力してください。"),
    E_NOEMP_CODE("社員番号を入力してください。"),
    E_EMP_CODE_EXIST("入力された社員番号の情報は既に存在しています。"),
    E_NOPLACE("目的地を入力してください。"),
    E_NOCONTENT("内容を入力してください。"),
    E_SAMEDATE("同じ日付で登録されています"),
    E_FAST("到着時間が出発時間より早いです"),
    E_NODRIVER("ドライバーを選択してください"),
    E_NOGOAT("出発時間を入力してください"),
    E_NOARRIVEAT("到着時間を入力してください"),
    E_NOBACKAT("戻り時間を入力してください"),
    E_SLOW_GO("出発時間が到着時間より遅いです"),
    E_SLOW_ARRIVE("到着時間が戻り時間より遅いです"),
    E_SLOW_GO_BACK("出発時間が戻り時間より遅いです"),
    E_SAME_TIME("同じ時間が入力されています");


    /**
     * 文字列
     */
    private final String text;

    /**
     * コンストラクタ
     */
    private MessageConst(final String text) {
        this.text = text;
    }

    /**
     * 値(文字列)取得
     */
    public String getMessage() {
        return this.text;
    }
}