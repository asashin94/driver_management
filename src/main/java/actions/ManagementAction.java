package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.ManagementView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
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

}
