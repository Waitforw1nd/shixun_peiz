// prac/src/main/java/com/realestate/servlet/DistrictAreaAnalysisServlet.java
package com.realestate.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.dao.DistrictAreaAnalysisDao;
import com.realestate.dao.impl.DistrictAreaAnalysisDaoImpl;
import com.realestate.domain.DistrictAreaAnalysis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet; // 确认导入了正确的包
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/districtAreaAnalysis")
public class DistrictAreaAnalysisServlet extends HttpServlet {
    private final DistrictAreaAnalysisDao dao = new DistrictAreaAnalysisDaoImpl();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 编码设置已由 Filter 处理，但保留以确保安全
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");

        String district = req.getParameter("district");
        List<DistrictAreaAnalysis> list;

        if (district != null && !district.isEmpty()) {
            list = dao.findByDistrict(district);
        } else {
            list = dao.findAll();
        }

        String json = mapper.writeValueAsString(list);
        resp.getWriter().write(json);
    }
}