package com.realestate.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.dao.OrientationAnalysisDao;
import com.realestate.dao.impl.OrientationAnalysisDaoImpl;
import com.realestate.domain.OrientationAnalysis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/orientationAnalysis")
public class OrientationAnalysisServlet extends HttpServlet {
    private OrientationAnalysisDao dao = new OrientationAnalysisDaoImpl();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");
        
        String orientation = req.getParameter("orientation");
        List<OrientationAnalysis> list;
        
        if (orientation != null && !orientation.isEmpty()) {
            list = dao.findByOrientation(orientation);
        } else {
            list = dao.findAll();
        }
        
        String json = mapper.writeValueAsString(list);
        resp.getWriter().write(json);
    }
}