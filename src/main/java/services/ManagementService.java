package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.EmployeeView;
import actions.views.ManagementConverter;
import actions.views.ManagementView;
import constants.JpaConst;
import models.Management;
import models.validators.ManagementValidator;

public class ManagementService extends ServiceBase {
    /**
     * 作成した運行管理データを、指定されたページ数の一覧画面に表示する分取得し、ManagementViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<ManagementView> getPerPage(int page){
        List<Management> managements = em.createNamedQuery(JpaConst.Q_DRI_GET_ALL,Management.class)
                                       .setFirstResult(JpaConst.ROW_PER_PAGE*(page - 1 ))
                                       .setMaxResults(JpaConst.ROW_PER_PAGE)
                                       .getResultList();
        return ManagementConverter.toViewList(managements);
    }

    /**
     * 作成した運行管理データの件数を取得し、返却する
     * @return 運行管理データの件数
     */
    long countAll() {
        long management_count = (long)em.createNamedQuery(JpaConst.Q_DRI_COUNT,Long.class)
                                .getSingleResult();
        return management_count;
    }

    /**
     * idを条件に取得したデータをManagementViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public ManagementView findOne(int id) {
        return ManagementConverter.toView(findOneInternal(id));
    }

    /**
     * 画面から入力された運行管理の内容を元にデータを1件作成し、運行管理テーブルに登録する
     * @param mv 運行管理の登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(EmployeeView ev,ManagementView mv){
        List<String> errors =ManagementValidator.validate(ev, mv);
        if(errors.size() == 0) {
            LocalDateTime ldt=LocalDateTime.now();
            mv.setCreatedAt(ldt);
            mv.setUpdatedAt(ldt);
            createInternal(mv);
        }

        //バリデーションで発生したエラーを返却(エラーがなければ0件のリスト)
        return errors;
    }

    public List<String> update(EmployeeView ev,ManagementView mv){
        List<String> errors = ManagementValidator.validate(ev, mv);

        if(errors.size() == 0) {
            //更新日時を現在時刻に設定
            LocalDateTime ldt=LocalDateTime.now();
            mv.setUpdatedAt(ldt);

            updateInternal(mv);
        }

        //バリデーションで発生したエラーを返却(エラーがなければ0件のリスト)
        return errors;
    }

    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Management findOneInternal(int id) {
        return em.find(Management.class,id);
    }

    /**
     * 運行管理データを1件登録する
     * @param mv 運行管理データ
     */
    private void createInternal(ManagementView mv) {

        em.getTransaction().begin();
        em.persist(ManagementConverter.toModel(mv));
        em.getTransaction().commit();
    }

    /**
     * 運行管理データを更新する
     * @param mv 運行管理データ
     */
    private void updateInternal(ManagementView mv) {
        em.getTransaction().begin();
        Management m = findOneInternal(mv.getId());
        ManagementConverter.copyViewToModel(m, mv);
        em.getTransaction().commit();

    }
}
