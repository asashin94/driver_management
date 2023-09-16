package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Management;


/**
 * 運行管理データのDTOモデル↔Viewモデルの変換を行うクラス
 */

public class ManagementConverter {
    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param mv ManagementViewのインスタンス
     * @return Managementのインスタンス
     */
    public static Management toModel(ManagementView mv) {
        return new Management(
                mv.getId(),
                EmployeeConverter.toModel(mv.getEmployee()),
                DriverConverter.toModel(mv.getDriver()),
                mv.getPlace(),
                mv.getGoAt(),
                mv.getArriveAt(),
                mv.getBackAt(),
                mv.getCreatedAt(),
                mv.getUpdatedAt(),
                EmployeeConverter.toModel(mv.getEmployee_final()));

    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param m Managementのインスタンス
     * @return ManagementViewのインスタンス
     */
    public static ManagementView toView(Management m) {
        if(m == null) {
            return null;
        }

        return new ManagementView(
                m.getId(),
                EmployeeConverter.toView(m.getEmployee()),
                DriverConverter.toView(m.getDriver()),
                m.getPlace(),
                m.getGoAt(),
                m.getArriveAt(),
                m.getBackAt(),
                m.getCreatedAt(),
                m.getUpdatedAt(),
                EmployeeConverter.toView(m.getEmployee_final()));
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<ManagementView> toViewList(List<Management> list){
        List<ManagementView> mvs=new ArrayList<>();
        for (Management m : list) {
            mvs.add(toView(m));
        }

        return mvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param m DTOモデル(コピー先)
     * @param mv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Management m,ManagementView mv) {
        m.setId(mv.getId());
        m.setEmployee(EmployeeConverter.toModel(mv.getEmployee()));
        m.setDriver(DriverConverter.toModel(mv.getDriver()));
        m.setPlace(mv.getPlace());
        m.setGoAt(mv.getGoAt());
        m.setArriveAt(mv.getArriveAt());
        m.setBackAt(mv.getBackAt());
        m.setCreatedAt(mv.getCreatedAt());
        m.setUpdatedAt(mv.getUpdatedAt());
        m.setEmployee_final(EmployeeConverter.toModel(mv.getEmployee_final()));
    }

}
