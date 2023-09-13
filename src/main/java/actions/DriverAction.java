package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.DriverView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
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
        putRequestScope(AttributeConst.DRIVER_COUNT,driverCount);//全てのドライバーデータの件数
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


}
