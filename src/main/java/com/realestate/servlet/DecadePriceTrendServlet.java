package com.realestate.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.dao.DecadePriceTrendDao;
import com.realestate.dao.impl.DecadePriceTrendDaoImpl;
import com.realestate.domain.DecadePriceTrend;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/decadePriceTrend")
public class DecadePriceTrendServlet extends HttpServlet {
    private DecadePriceTrendDao dao = new DecadePriceTrendDaoImpl();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");
        
        String decade = req.getParameter("decade");
        List<DecadePriceTrend> list;
        
        if (decade != null && !decade.isEmpty()) {
            list = dao.findByDecade(decade);
        } else {
            list = dao.findAll();
        }
        
        String json = mapper.writeValueAsString(list);
        resp.getWriter().write(json);
    }
}