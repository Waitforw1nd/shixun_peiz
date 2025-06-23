package com.realestate.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.dao.AreaSegmentAnalysisDao;
import com.realestate.dao.impl.AreaSegmentAnalysisDaoImpl;
import com.realestate.domain.AreaSegmentAnalysis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/areaSegmentAnalysis")
public class AreaSegmentAnalysisServlet extends HttpServlet {
    private AreaSegmentAnalysisDao dao = new AreaSegmentAnalysisDaoImpl();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");
        
        String areaSegment = req.getParameter("areaSegment");
        List<AreaSegmentAnalysis> list;
        
        if (areaSegment != null && !areaSegment.isEmpty()) {
            list = dao.findByAreaSegment(areaSegment);
        } else {
            list = dao.findAll();
        }
        
        String json = mapper.writeValueAsString(list);
        resp.getWriter().write(json);
    }
}