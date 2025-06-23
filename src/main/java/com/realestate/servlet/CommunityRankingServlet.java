package com.realestate.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.dao.CommunityRankingDao;
import com.realestate.dao.impl.CommunityRankingDaoImpl;
import com.realestate.domain.CommunityRanking;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/communityRanking")
public class CommunityRankingServlet extends HttpServlet {
    private CommunityRankingDao dao = new CommunityRankingDaoImpl();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");
        
        String district = req.getParameter("district");
        List<CommunityRanking> list;
        
        if (district != null && !district.isEmpty()) {
            list = dao.findByDistrict(district);
        } else {
            list = dao.findAll();
        }
        
        String json = mapper.writeValueAsString(list);
        resp.getWriter().write(json);
    }
}