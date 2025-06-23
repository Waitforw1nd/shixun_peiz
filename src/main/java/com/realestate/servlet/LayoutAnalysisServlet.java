package com.realestate.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.dao.LayoutAnalysisDao;
import com.realestate.dao.impl.LayoutAnalysisDaoImpl;
import com.realestate.domain.LayoutAnalysis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/layoutAnalysis")
public class LayoutAnalysisServlet extends HttpServlet {
    private LayoutAnalysisDao dao = new LayoutAnalysisDaoImpl();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");
        
        String layout = req.getParameter("layout");
        List<LayoutAnalysis> list;
        
        if (layout != null && !layout.isEmpty()) {
            list = dao.findByLayout(layout);
        } else {
            list = dao.findAll();
        }
        
        String json = mapper.writeValueAsString(list);
        resp.getWriter().write(json);
    }
}