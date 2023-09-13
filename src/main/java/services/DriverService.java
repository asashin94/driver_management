package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.DriverConverter;
import actions.views.DriverView;
import constants.JpaConst;
import models.Driver;
import models.validators.DriverValidator;

/**
 * ドライバーテーブルの捜査にかかわる処理を行うクラス
 */

public class DriverService extends ServiceBase {

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、DriverViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<DriverView> getPerPage(int page){
        List<Driver> drivers = em.createNamedQuery(JpaConst.Q_DRI_GET_ALL,Driver.class)
                                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                                .setMaxResults(JpaConst.ROW_PER_PAGE)
                                .getResultList();

        return DriverConverter.toViewList(drivers);
    }

    /**
     * ドライバーテーブルのデータの件数を取得し、返却する
     */
    public long countAll() {
        long driCount = (long)em.createNamedQuery(JpaConst.Q_DRI_COUNT, Long.class)
                        .getSingleResult();

        return driCount;
    }

    /**
     * idを条件に取得したデータをDriverViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public DriverView findOne(int id) {
        Driver d = findOneInternal(id);
        return DriverConverter.toView(d);
    }

    /**
     * 画面から入力されたドライバーの登録内容を元にデータを1件作成し、ドライバーテーブルに登録する
     * @param dv 画面から入力された従業員の登録内容
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(DriverView dv){
        //登録日時、更新日時は現在時刻を設定する
        LocalDateTime now=LocalDateTime.now();
        dv.setCreatedAt(now);
        dv.setUpdatedAt(now);

        //登録内容のバリデーションを行う
        List<String> errors = DriverValidator.validate(this, dv);

        //バリデーションエラーがなければデータを登録する
        if (errors.size() == 0) {
                create(dv);
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;

    }

    /**
     * 画面から入力されたドライバーの更新内容を元にデータを1件作成し、ドライバーテーブルを更新する
     * @param ev 画面から入力された従業員の登録内容
     * @return バリデーションや更新処理中に発生したエラーのリスト
     */
    public List<String> update(DriverView dv){
        DriverView savedDri=findOne(dv.getId());

        savedDri.setName(dv.getName());
        savedDri.setTel(dv.getTel());

        //更新日時に現在時刻を設定する
        LocalDateTime today = LocalDateTime.now();
        savedDri.setUpdatedAt(today);

        //更新内容についてバリデーションを行う
        List<String> errors =DriverValidator.validate(this, dv);

      //バリデーションエラーがなければデータを更新する
        if (errors.size() == 0) {
            update(savedDri);
        }

      //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件にドライバーデータを論理削除する
     * @param id
     */
    public void destroy(Integer id) {

        //idを条件に登録済みの従業員情報を取得する
        DriverView savedDri = findOne(id);

        //更新日時に現在時刻を設定する
        LocalDateTime today = LocalDateTime.now();
        savedDri.setUpdatedAt(today);

        //論理削除フラグをたてる
        savedDri.setDeleteFlag(JpaConst.EMP_DEL_TRUE);

        //更新処理を行う
        update(savedDri);

    }

    /**
     * idを条件にデータを1件取得し、Driverのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Driver findOneInternal(int id) {
        Driver d = em.find(Driver.class, id);

        return d;
    }

    /**
     * ドライバーデータを1件登録する
     * @param dv ドライバーデータ
     * @return 登録結果(成功:true 失敗:false)
     */
    private void createOnly(DriverView dv) {

        em.getTransaction().begin();
        em.persist(DriverConverter.toModel(dv));
        em.getTransaction().commit();

    }

    /**
     * ドライバーデータを更新する
     * @param ｄv 画面から入力されたドライバーの登録内容
     */
    private void updateOnly(DriverView dv) {

        em.getTransaction().begin();
        Driver d = findOneInternal(dv.getId());
        DriverConverter.copyViewToModel(d, dv);
        em.getTransaction().commit();

    }

}
