package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/access")
public class AccessCounterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// サーブレットクラスはブラウザからの初回リクエスト時に、サーバ内で自動インスタンス化されている。
	// サーブレットクラスがインスタンス化された直後に、init(イニシャライズ)メソッドが呼び出されている。
	// これは親クラスである「httpServletクラス」から継承されたメソッドであり、オーバーライドすることで、
	// 「リクエストを受けたときに一度だけ必ず行いたい初期化処理」を定義できる！
	// 通常のJavaクラスでいう「コンストラクタ」のような動きをするイメージ
	// (例)データベース接続など
	public void init(ServletConfig config) throws ServletException {
		
		// 必ず先頭で親クラスのinit()を呼び出す
		super.init(config);
		// スコープに基本データ型は格納できないので、ラッパークラスのInteger型でカウント数を管理する
		Integer count = 0;
		// アプリケーションスコープを生成する
		// init()でアプリケーションスコープを生成する際はconfigインスタンスのgetServletContext()を用いてインスタンスの生成をする
		// thisを使うと例外が発生する恐れがあるため
		ServletContext application = config.getServletContext();
		// アプリケーションスコープに「アクセス数」としてのcountを格納する
		application.setAttribute("count", count);
		
		// ブラウザには表示されないが、コンソールに文字列が出力されるので動作確認用として記述
		System.out.println("init()が実行されました");
	}
	
	// サーブレットクラスのインスタンスは、リクエスト後でもメモリ上に残り、次のリクエストでも再利用される。
	// webアプリケーションの終了とともにインスタンスが破棄されるが、その際に、destroy()が呼び出される。
	// オーバーライドすることで「破棄される直前に1度だけ必ず行いたい終了処理」を定義する！
	// （例）データベースの切断など
	public void destroy() {
		System.out.println("destroy()が実行されました");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// initメソッドで生成したアプリケーションスコープを取得する
		ServletContext application = this.getServletContext();
		// initメソッドで格納したcountをアプリケーションスコープから取得する
		Integer count = (Integer) application.getAttribute("count");
		// カウント+１
		count++;
		// 改めてcount変数に入っている値を"count"としてアプリケーションスコープへ格納
		application.setAttribute("count", count);
		
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<html><head>");
		out.print("<title>アクセス数表示画面</title>");
		out.print("<style>body{text-align:center;margin-top:100px;}</style>");
		out.print("</head><body>");
		out.print("<h1>総アクセス数：" + count +"回</h1>");
		out.print("<input type=\"button\" value=\"更新\" onclick=\"location.href='/Access_Project/access'\">");
		out.print("<input type=\"button\" value=\"リセット\" onclick=\"location.href='/Access_Project/reset'\">");
		out.print("</body></html>");
	}
}
