/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Entity.User;
import Entity.UserController;
import Entity.UserFacade;
import javax.servlet.DispatcherType;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "logupServlet", urlPatterns = {"/logupServlet"})
public class LogupServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/log_up.html";
    private User userTang;

    public LogupServlet() {
        super();
        userTang = new User();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response)
        System.out.println("GET:" + request.getParameter("phonenumber"));
        System.out.println("GET:" + request.getParameter("password"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String name = request.getParameter("username");
        String tel = request.getParameter("phonenumber");
        String psw = request.getParameter("password");
        response.setContentType("text/html");
        UserController user = new UserController(tel,name,psw);
        String check = user.logup();
              
        PrintWriter out = response.getWriter();
        out.println(check);
        //out.println("<script>alert('log up sucessfully');window.location.href=check</script>");
       /* if(check==null){
            out.print("<script>alert('log up sucessfully');window.location.href='log_in.html'</script>");
        }
        else{
            out.print("<script>alert('log up failed');window.location.href='log_up.html'</script>");
        }*/
        // out.println(test);
        // RequestDispatcher view = request.getRequestDispatcher("/log_in.html");
        //request .getRequestDispatcher ( "log_in.html").forward( request , response);
        
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
