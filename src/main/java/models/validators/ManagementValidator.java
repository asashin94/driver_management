package models.validators;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import actions.views.EmployeeView;
import actions.views.ManagementView;
import constants.MessageConst;

public class ManagementValidator {
    public static List<String> validate(EmployeeView ev,ManagementView mv) {
        List<String>errors = new ArrayList<String>();

        //目的地のチェック
        String placeError = validatePlace(mv.getPlace());
        if (!placeError.equals("")) {
            errors.add(placeError);
        }

        //【追記】出発時間のチェック
        String goAtError = validateGoAt(mv.getGoAt());
        if (!goAtError.equals("")) {
            errors.add(goAtError);
        }

        //【追記】到着時間のチェック
        String arriveAtError = validateArriveAt(mv.getArriveAt());
        if (!arriveAtError.equals("")) {
            errors.add(arriveAtError);
        }

        //【追記】戻り時間のチェック
        String backAtError = validateBackAt(mv.getBackAt());
        if (!backAtError.equals("")) {
            errors.add(backAtError);
        }

        //【追記】出勤時間が退勤時間より遅くないかチェック
        String slowError=validateSlow(mv.getGoAt(),mv.getArriveAt());
        if(!slowError.equals("")) {
            errors.add(slowError);
        }
        return errors;

    }

    /**
     * 内容に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param place 目的地
     * @return エラーメッセージ
     */
    private static String validatePlace(String place){
        if (place==null||place.equals("")){
            return MessageConst.E_NOPLACE.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }


    /**
     * 出発時間が到着時間より遅くないかをチェックし、遅ければエラーメッセージを返却
     */
    private static String validateSlow(LocalDateTime goAt,LocalDateTime arriveAt) {
        if(goAt!=null && arriveAt!=null && goAt.isAfter(arriveAt)) {
            return MessageConst.E_SLOW.getMessage();
        }
        return "";
    }


    /**
     * 内容に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param goAt 出勤時間
     * @return エラーメッセージ
     */
    private static String validateGoAt(LocalDateTime goAt){

        if (goAt == null) {
         return MessageConst.E_NOGOAT.getMessage();
        }
        return "";
    }
    /**
     * 内容に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param arriveAt 到着時間
     * @return エラーメッセージ
     */
    private static String validateArriveAt(LocalDateTime arriveAt){
        if (arriveAt==null){
            return MessageConst.E_NOARRIVEAT.getMessage();
        }
        return "";
    }

    /**
     * 内容に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param backAt 戻り時間
     * @return エラーメッセージ
     */
    private static String validateBackAt(LocalDateTime backAt) {
        if(backAt==null) {
            return MessageConst.E_NOBACKAT.getMessage();
        }
        return "";
    }

}
