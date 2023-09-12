package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Driver;

/**
 * ドライバーデータのDTOモデル↔Viewモデルの変換を行うクラス
 *
 */

public class DriverConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     *@param dv DriverViewのインスタンス
     *@return Driverのインスタンス
     */
    public static Driver toModel(DriverView dv) {
        return new Driver(
                dv.getId(),
                dv.getName(),
                dv.getTel(),
                dv.getText(),
                dv.getCreatedAt(),
                dv.getUpdatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param d Driverのインスタンス
     * @return DriverViewのインスタンス
     */
    public static DriverView toView(Driver d) {
        if(d == null) {
            return null;
        }

        return new DriverView(
                d.getId(),
                d.getName(),
                d.getTel(),
                d.getText(),
                d.getCreatedAt(),
                d.getUpdatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<DriverView> toViewList(List<Driver> list){
        List<DriverView> dvs = new ArrayList<>();

        for (Driver d : list) {
            dvs.add(toView(d));
        }

        return dvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param d DTOモデル(コピー先)
     * @param dv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Driver d,DriverView dv) {
        d.setId(dv.getId());
        d.setName(dv.getName());
        d.setTel(d.getTel());
        d.setText(dv.getText());
        d.setCreatedAt(dv.getCreatedAt());
        d.setUpdatedAt(dv.getUpdatedAt());
    }

}
