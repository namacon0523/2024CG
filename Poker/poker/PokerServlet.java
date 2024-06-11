package poker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class PokerServlet
 */
@WebServlet("/PokerServlet")
public class PokerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	PokerModel model;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PokerServlet() {
        super();
        model=new PokerModel();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		model.reset();
		model.nextgame();
		request.setAttribute("model", model);
		RequestDispatcher dispatcher=request.getRequestDispatcher("/poker.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charest=UTF-8");
		
	    String[] checkedIndexes = request.getParameterValues("change");
	    
	    if (model.getButtonLabel().equals("交換") && checkedIndexes != null && checkedIndexes.length > 0) {
	        List<Integer> indexesToChange = new ArrayList<>();
	        for (String idx : checkedIndexes) {
	            indexesToChange.add(Integer.parseInt(idx));
	        }
	        model.change(indexesToChange);
	    } else if (model.getButtonLabel().equals("次のゲーム")) {
	        model.nextgame();
	    } else {
	    	model.evaluateHand();
	    	model.evaluate();
	    	model.nextgame();
	    }

		request.setAttribute("model", model);
		RequestDispatcher dispatcher=request.getRequestDispatcher("/poker.jsp");
		dispatcher.forward(request, response);
	}

}