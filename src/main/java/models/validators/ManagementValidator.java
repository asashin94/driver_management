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

        //ドライバーのチェック
        String driverError = validateDriver(mv.getDriver());
        if (!driverError.equals("")) {
            errors.add(driverError);
        }

        //目的地のチェック
        String placeError = validatePlace(mv.getPlace());
        if (!placeError.equals("")) {
            errors.add(placeError);
        }

        //出発時間のチェック
        String goAtError = validateGoAt(mv.getGoAt());
        if (!goAtError.equals("")) {
            errors.add(goAtError);
        }

        //到着時間のチェック
        String arriveAtError = validateArriveAt(mv.getArriveAt());
        if (!arriveAtError.equals("")) {
            errors.add(arriveAtError);
        }

        //戻り時間のチェック
        String backAtError = validateBackAt(mv.getBackAt());
        if (!backAtError.equals("")) {
            errors.add(backAtError);
        }

        //出発時間時間が退勤時間より遅くないかチェック
        String slowGoError=validateSlowGo(mv.getGoAt(),mv.getArriveAt());
        if(!slowGoError.equals("")) {
            errors.add(slowGoError);
        }

        //到着時間が戻り時間より遅くないかチェック
        String slowArriveError=validateSlowArrive(mv.getArriveAt(),mv.getBackAt());
        if(!slowArriveError.equals("")) {
            errors.add(slowArriveError);
        }

        //出発時間が戻り時間より遅くないかチェック
        String slowGoBackError=validateSlowGoBack(mv.getGoAt(),mv.getBackAt());
        if(!slowGoBackError.equals("")) {
            errors.add(slowGoBackError);
        }

        return errors;

    }

    /**
     * ドライバー名が選択されているか確認し、選択されていなければエラーメッセージを返却
     * @param driver ドライバー
     * @return エラーメッセージ
     */
    private static String validateDriver(String driver) {
        if(driver == null || driver.equals("")) {
            return MessageConst.E_NODRIVER.getMessage();
        }

        //選択されている場合は空文字を返却
        return "";
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
    private static String validateSlowGo(LocalDateTime goAt,LocalDateTime arriveAt) {
        if(goAt!=null && arriveAt!=null && goAt.isAfter(arriveAt)) {
            return MessageConst.E_SLOW_GO.getMessage();
        }
        return "";
    }

    /**
     * 到着時間が戻り時間より遅くないかをチェックし、遅ければエラーメッセージを返却
     */
    private static String validateSlowArrive(LocalDateTime arriveAt,LocalDateTime backAt) {
        if(arriveAt!=null && backAt!=null && arriveAt.isAfter(backAt)) {
            return MessageConst.E_SLOW_ARRIVE.getMessage();
        }
        return "";
    }

    /**
     * 出発時間が戻り時間より遅くないかをチェックし、遅ければエラーメッセージを返却
     */
    private static String validateSlowGoBack(LocalDateTime goAt,LocalDateTime backAt) {
        if(goAt!=null && backAt!=null && goAt.isAfter(backAt)) {
            return MessageConst.E_SLOW_GO_BACK.getMessage();
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
