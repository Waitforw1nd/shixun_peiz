package com.realestate.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.dao.DistrictMarketStatsDao;
import com.realestate.dao.impl.DistrictMarketStatsDaoImpl;
import com.realestate.domain.DistrictMarketStats;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/districtMarketStats")
public class DistrictMarketStatsServlet extends HttpServlet {
    private DistrictMarketStatsDao dao = new DistrictMarketStatsDaoImpl();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");
        
        String district = req.getParameter("district");
        List<DistrictMarketStats> list;
        
        if (district != null && !district.isEmpty()) {
            list = dao.findByDistrict(district);
        } else {
            list = dao.findAll();
        }
        
        String json = mapper.writeValueAsString(list);
        resp.getWriter().write(json);
    }
}