package com.realestate.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.dao.DecorationAnalysisDao;
import com.realestate.dao.impl.DecorationAnalysisDaoImpl;
import com.realestate.domain.DecorationAnalysis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/decorationAnalysis")
public class DecorationAnalysisServlet extends HttpServlet {
    private DecorationAnalysisDao dao = new DecorationAnalysisDaoImpl();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");
        
        String decoration = req.getParameter("decoration");
        List<DecorationAnalysis> list;
        
        if (decoration != null && !decoration.isEmpty()) {
            list = dao.findByDecoration(decoration);
        } else {
            list = dao.findAll();
        }
        
        String json = mapper.writeValueAsString(list);
        resp.getWriter().write(json);
    }
}