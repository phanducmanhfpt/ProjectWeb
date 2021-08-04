/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao1.DAO;
import entity.Category;
import entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "BackControl", urlPatterns = {"/BackControl"})
public class BackControl extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String indexPage = request.getParameter("index");
        if(indexPage==null){
            indexPage="1";
        }
            Cookie arr[] = request.getCookies();
        for(Cookie o : arr){
            if(o.getName().equals("id")){
                o.setMaxAge(0);
                response.addCookie(o);
            }
        }
        int index = Integer.parseInt(indexPage);
        DAO dao = new DAO();
        int count = dao.getTotalProduct();
        List<Category> listC = dao.getAllCategory();
        Product last = dao.getExpensive();
        Product first= dao.getCheaper();
        int endPage = count / 6;
        if (count % 6 != 0) {
            endPage++;
        }
        List<Product> list = dao.pagingProduct(index);
        request.setAttribute("listP", list);
        request.setAttribute("listCC", listC);
        request.setAttribute("tag", index);
        request.setAttribute("p", last);
        request.setAttribute("q", first);
        request.setAttribute("endP", endPage);
        request.getRequestDispatcher("Home.jsp").forward(request, response);
        //b2: set data to jsp              

        //404 -> url
        //500 -> jsp properties
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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