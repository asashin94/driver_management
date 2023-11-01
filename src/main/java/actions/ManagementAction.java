package actions;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.ManagementView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.ManagementService;

/**
 * 運行管理に関する処理を行うActionクラス
 *
 */

public class ManagementAction extends ActionBase{

    private ManagementService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException,IOException{

        service =new ManagementService();

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
        //指定されたページ数の一覧画面に表示する運行管理データを取得
        int page = getPage();
        List<ManagementView> managements =service.getPerPage(page);

        //全運行管理データの件数を取得
        long managementsCount = service.countAll();

        putRequestScope(AttributeConst.MANAGEMENTS,managements);//取得した運行管理データ
        putRequestScope(AttributeConst.MAN_COUNT,managementsCount);//全ての運行管理データの件数
        putRequestScope(AttributeConst.PAGE,page);//ページ数
        putRequestScope(AttributeConst.MAX_ROW,JpaConst.ROW_PER_PAGE);//1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if(flush != null) {
            putRequestScope(AttributeConst.FLUSH,flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_MAN_INDEX);
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putRequestScope(AttributeConst.MANAGEMENT,new ManagementView());//空の運行管理インスタンス
        List<String> driverNames = service.driverNames(); //ドライバーのプルリスト
        putRequestScope(AttributeConst.DRIVERS,driverNames);
        //新規登録画面を表示
        forward(ForwardConst.FW_MAN_NEW);

    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

           //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            LocalDateTime defaultDateTime = LocalDateTime.of(2000, 1, 1, 0, 0);  // 2000-01-01T00:00

           //出発時間の設定
            LocalDateTime go=null;
            if(getRequestParam(AttributeConst.MAN_GO) == null ||
                    getRequestParam(AttributeConst.MAN_GO).equals("")) {
                go = defaultDateTime;
            }else {
            go =LocalDateTime.parse(getRequestParam(AttributeConst.MAN_GO));
            }
           //到着時間の設定
            LocalDateTime arrive=null;
            if(getRequestParam(AttributeConst.MAN_ARRIVE) == null||
                    getRequestParam(AttributeConst.MAN_ARRIVE).equals("")) {
                arrive =defaultDateTime;
            }else {
            arrive =LocalDateTime.parse(getRequestParam(AttributeConst.MAN_ARRIVE));
            }

           //戻り時間の設定
            LocalDateTime back=null;
            if(getRequestParam(AttributeConst.MAN_BACK) == null ||
                    getRequestParam(AttributeConst.MAN_BACK).equals("")) {
                back = defaultDateTime;
            }else {
            back =LocalDateTime.parse(getRequestParam(AttributeConst.MAN_BACK));
            }

            //パラメータの値をもとに運行管理情報のインスタンスを作成する
            ManagementView mv = new ManagementView(
                    null,
                    ev, //ログインしている従業員を、運行管理作成者として登録する
                    getRequestParam(AttributeConst.DRIVER),
                    getRequestParam(AttributeConst.MAN_PLACE),
                    go,
                    arrive,
                    back,
                    null,
                    null,
                    ev);

            //運行管理情報登録
            List<String> errors = service.create(ev,mv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                if(go.equals(defaultDateTime)) {
                    go=null;
                }
                if(arrive.equals(defaultDateTime)) {
                    arrive=null;
                }
                if(back.equals(defaultDateTime)) {
                    back=null;
                }

                ManagementView mv2 = new ManagementView(
                        null,
                        ev, //ログインしている従業員を、運行管理作成者として登録する
                        getRequestParam(AttributeConst.DRIVER),
                        getRequestParam(AttributeConst.MAN_PLACE),
                        go,
                        arrive,
                        back,
                        null,
                        null,
                        ev);

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                List<String> driverNames = service.driverNames(); //ドライバーのプルリスト
                putRequestScope(AttributeConst.DRIVERS,driverNames);
                putRequestScope(AttributeConst.MANAGEMENT, mv2);//入力された運行管理情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_MAN_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_MAN, ForwardConst.CMD_INDEX);
            }
        }
    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //idを条件に運行管理データを取得する
        ManagementView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MAN_ID)));

        if (mv == null) {
            //該当の運行管理データが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.MANAGEMENT, mv); //取得した運行管理データ

            //詳細画面を表示
            forward(ForwardConst.FW_MAN_SHOW);
        }
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //idを条件に運行管理データを取得する
        ManagementView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MAN_ID)));

        if (mv == null) {
            //該当の運行管理データが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            List<String> driverNames = service.driverNames(); //ドライバーのプルリスト
            putRequestScope(AttributeConst.DRIVERS,driverNames);
            putRequestScope(AttributeConst.MANAGEMENT, mv); //取得した運行管理データ

            //編集画面を表示
            forward(ForwardConst.FW_MAN_EDIT);
        }

    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //idを条件に運行管理データを取得する
            ManagementView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MAN_ID)));

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            //入力された運行管理内容を設定する
            mv.setDriver(getRequestParam(AttributeConst.MAN_DRIVER));
            mv.setPlace(getRequestParam(AttributeConst.MAN_PLACE));
            mv.setGoAt(toLocalDateTime(getRequestParam(AttributeConst.MAN_GO)));
            mv.setArriveAt(toLocalDateTime(getRequestParam(AttributeConst.MAN_ARRIVE)));
            mv.setBackAt(toLocalDateTime(getRequestParam(AttributeConst.MAN_BACK)));

            //運行管理データを更新する
            List<String> errors = service.update(ev,mv);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                List<String> driverNames = service.driverNames(); //ドライバーのプルリスト
                putRequestScope(AttributeConst.DRIVERS,driverNames);
                putRequestScope(AttributeConst.MANAGEMENT, mv); //入力された運行管理情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_MAN_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_MAN, ForwardConst.CMD_INDEX);

            }
        }
    }

}
