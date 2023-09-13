package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.DriverView;
import constants.MessageConst;
import services.DriverService;

/**
 * ドライバーインスタンスに設定されている値のバリデーションを行うクラス
 */

public class DriverValidator {
    /**
     * ドライバーインスタンスの各項目についてバリデーションを行う
     * @param service 呼び出し元Serviceクラスのインスタンス
     * @param dv DriverViewのインスタンス
     * @return エラーのリスト
     */
    public static List<String> validate(
            DriverService service,DriverView dv){

        List<String> errors = new ArrayList<String>();

        //氏名のチェック
        String nameError = validateName(dv.getName());
        if (!nameError.equals("")) {
            errors.add(nameError);
        }

        //電話番号のチェック
        String telError = validateTel(dv.getTel());
        if(!telError.equals("")) {
            errors.add(telError);
        }

        return errors;
    }

    /**
     * 氏名に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param name 氏名
     * @return エラーメッセージ
     */
    private static String validateName(String name) {

        if (name == null || name.equals("")) {
            return MessageConst.E_NONAME.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * 電話番号に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param tel 電話番号
     * @return エラーメッセージ
     */
    private static String validateTel(Integer tel) {
        if (tel == null || tel.equals("")) {
            return MessageConst.E_NOTEL.getMessage();
        }
        return "";
    }

}
