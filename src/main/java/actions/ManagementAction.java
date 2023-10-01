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

        //日報情報の空インスタンスに、日報の日付＝今日の日付を設定する
        ManagementView mv = new ManagementView();
        putRequestScope(AttributeConst.MANAGEMENT, mv); //日付のみ設定済みの日報インスタンス
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

          //出発時間の設定
            LocalDateTime go=null;
            if(getRequestParam(AttributeConst.MAN_GO)==null
                ||getRequestParam(AttributeConst.MAN_GO).equals("")){
            }else {
                go =LocalDateTime.parse(getRequestParam(AttributeConst.MAN_GO));
            }

          //到着時間の設定
            LocalDateTime arrive=null;
            if(getRequestParam(AttributeConst.MAN_ARRIVE)==null
                ||getRequestParam(AttributeConst.MAN_ARRIVE).equals("")){
            }else {
                arrive =LocalDateTime.parse(getRequestParam(AttributeConst.MAN_ARRIVE));
            }

          //戻り時間の設定
            LocalDateTime back=null;
            if(getRequestParam(AttributeConst.MAN_BACK)==null
                ||getRequestParam(AttributeConst.MAN_BACK).equals("")){
            }else {
                back =LocalDateTime.parse(getRequestParam(AttributeConst.MAN_BACK));
            }

            //パラメータの値をもとに日報情報のインスタンスを作成する
            ManagementView mv = new ManagementView(
                    null,
                    ev, //ログインしている従業員を、日報作成者として登録する
                    getRequestParam(AttributeConst.DRIVER),
                    getRequestParam(AttributeConst.MAN_PLACE),
                    go,
                    arrive,
                    back,
                    null,
                    null,
                    ev);

            //日報情報登録
            List<String> errors = service.create(ev,mv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.MANAGEMENT, mv);//入力された日報情報
                List<String> driverNames = service.driverNames(); //ドライバーのプルリスト
                putRequestScope(AttributeConst.DRIVERS,driverNames);
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

        //idを条件に日報データを取得する
        ManagementView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MAN_ID)));

        if (mv == null) {
            //該当の日報データが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.MANAGEMENT, mv); //取得した日報データ

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

        //idを条件に日報データを取得する
        ManagementView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MAN_ID)));

        if (mv == null) {
            //該当の日報データが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.MANAGEMENT, mv); //取得した日報データ

            //編集画面を表示
            forward(ForwardConst.FW_MAN_EDIT);
        }

    }

}
