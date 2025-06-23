package com.realestate.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.dao.PriceAreaCorrelationDao;
import com.realestate.dao.impl.PriceAreaCorrelationDaoImpl;
import com.realestate.domain.PriceAreaCorrelation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/priceAreaCorrelation")
public class PriceAreaCorrelationServlet extends HttpServlet {
    private PriceAreaCorrelationDao dao = new PriceAreaCorrelationDaoImpl();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");

        PriceAreaCorrelation correlation = dao.getCorrelation();
        if (correlation == null) {
            // 这里根据实际情况设置默认值，暂时使用空字符串和 0
            correlation = new PriceAreaCorrelation("", 0, 0.0, 0.0);
        }
        String json = mapper.writeValueAsString(correlation);
        resp.getWriter().write(json);
    }
}