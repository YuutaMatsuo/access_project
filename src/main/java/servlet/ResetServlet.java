package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/reset")
public class ResetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		// アプリケーションスコープの取得
		ServletContext application = this.getServletContext();
		// アプリケーションスコープ内のインスタンスを削除
		application.removeAttribute("count");
		
		// 【問題】リセットボタンを押すと500エラーが発生する
		//			原因はremoveAttributeをするとインスタンスが削除され、doGetメソッド内で参照したいさいにぬるぽが出るため
		
		// 【解決方法】カウントを「0」にする処理を記述
		Integer count = 0;
		application.setAttribute("count", count);
		
		out.print("<html><head>");
		out.print("<title>リセット画面</title>");
		out.print("<style>body{text-align:center;margin-top:100px}</style></head><body>");
		out.print("<p>アクセス数をリセットしました。</p>");
		out.print("<input type=\"button\" value=\"アクセス画面に戻る\" onclick=\"location.href='/Access_Project/access'\">");
		out.print("</body></html>");		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
