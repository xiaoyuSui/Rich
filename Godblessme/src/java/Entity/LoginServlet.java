
package Entity;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import Entity.User;
import Entity.UserController;
import Entity.UserFacade;

@WebServlet(name = "loginServlet", urlPatterns = {"/loginServlet"})
public class LoginServlet extends HttpServlet {
     private static final long serialVersionUID=1L;
    
    public LoginServlet(){
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("GET:"+request.getParameter("phonenumber"));
        System.out.println("GET:"+request.getParameter("password"));
        //processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String phonenumber = request.getParameter("user_tel");
        String password = request.getParameter("user_psw");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String name = "test";
        UserController user = new UserController(phonenumber,name,password);
        String test = user.login();
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
