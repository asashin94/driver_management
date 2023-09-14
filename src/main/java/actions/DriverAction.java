package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.DriverView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.DriverService;

public class DriverAction  extends ActionBase{

    private DriverService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException,IOException{

        service = new DriverService();

        //メソッドを実行
        invoke();

        service.close();
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException,IOException{

        //指定されたページ数の一覧画面に表示するデータを取得
        int page = getPage();
        List<DriverView> drivers=service.getPerPage(page);

        //全てのドライバーデータの件数を取得
        long driverCount = service.countAll();

        putRequestScope(AttributeConst.DRIVERS,drivers);//取得したドライバーデータ
        putRequestScope(AttributeConst.DRI_COUNT,driverCount);//全てのドライバーデータの件数
        putRequestScope(AttributeConst.PAGE,page);//ページ数
        putRequestScope(AttributeConst.MAX_ROW,JpaConst.ROW_PER_PAGE);//1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if(flush != null) {
            putRequestScope(AttributeConst.FLUSH,flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_DRI_INDEX);
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putRequestScope(AttributeConst.DRIVER, new DriverView()); //空のドライバーインスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_DRI_NEW);
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //パラメータの値を元に従業員情報のインスタンスを作成する
            DriverView dv = new DriverView(
                    null,
                    getRequestParam(AttributeConst.DRI_NAME),
                    getRequestParam(AttributeConst.DRI_TEL),
                    getRequestParam(AttributeConst.DRI_TEXT),
                    null,
                    null,
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());


            //従業員情報登録
            List<String> errors = service.create(dv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.EMPLOYEE, dv); //入力されたドライバー情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_EMP_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_DRI, ForwardConst.CMD_INDEX);
            }

        }
    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //idを条件に従業員データを取得する
        DriverView dv = service.findOne(toNumber(getRequestParam(AttributeConst.DRI_ID)));

        if (dv == null || dv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {

            //データが取得できなかった、または論理削除されている場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        putRequestScope(AttributeConst.DRIVER, dv); //取得したドライバー情報

        //詳細画面を表示
        forward(ForwardConst.FW_DRI_SHOW);
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //idを条件に従業員データを取得する
        DriverView dv = service.findOne(toNumber(getRequestParam(AttributeConst.DRI_ID)));

        if (dv == null || dv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {

            //データが取得できなかった、または論理削除されている場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putRequestScope(AttributeConst.DRIVER, dv); //取得したドライバー情報

        //編集画面を表示する
        forward(ForwardConst.FW_DRI_EDIT);

    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {
            //パラメータの値を元にドライバー情報のインスタンスを作成する
            DriverView dv = new DriverView(
                    toNumber(getRequestParam(AttributeConst.DRI_ID)),
                    getRequestParam(AttributeConst.DRI_NAME),
                    getRequestParam(AttributeConst.DRI_TEL),
                    getRequestParam(AttributeConst.DRI_TEXT),
                    null,
                    null,
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            //ドライバー情報更新
            List<String> errors = service.update(dv);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.DRIVER, dv); //入力されたドライバー情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_DRI_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_DRI, ForwardConst.CMD_INDEX);
            }
        }
    }

    /**
     * 論理削除を行う
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //idを条件にドライバーデータを論理削除する
            service.destroy(toNumber(getRequestParam(AttributeConst.DRI_ID)));

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_DRI, ForwardConst.CMD_INDEX);
        }
    }


}
