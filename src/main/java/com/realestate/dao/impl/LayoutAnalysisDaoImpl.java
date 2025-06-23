package com.realestate.dao.impl;

import com.realestate.dao.LayoutAnalysisDao;
import com.realestate.domain.LayoutAnalysis;
import com.realestate.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LayoutAnalysisDaoImpl implements LayoutAnalysisDao {
    @Override
    public List<LayoutAnalysis> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<LayoutAnalysis> list = new ArrayList<>();
        
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM layout_analysis";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                LayoutAnalysis analysis = new LayoutAnalysis();
                analysis.setLayout(rs.getString("layout"));
                analysis.setCount(rs.getInt("count"));
                analysis.setAvgPrice(rs.getDouble("avg_price"));
                analysis.setAvgArea(rs.getDouble("avg_area"));
                analysis.setAvgUnitPrice(rs.getDouble("avg_unit_price"));
                list.add(analysis);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return list;
    }

    @Override
    public List<LayoutAnalysis> findByLayout(String layout) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<LayoutAnalysis> list = new ArrayList<>();
        
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM layout_analysis WHERE layout = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, layout);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                LayoutAnalysis analysis = new LayoutAnalysis();
                analysis.setLayout(rs.getString("layout"));
                analysis.setCount(rs.getInt("count"));
                analysis.setAvgPrice(rs.getDouble("avg_price"));
                analysis.setAvgArea(rs.getDouble("avg_area"));
                analysis.setAvgUnitPrice(rs.getDouble("avg_unit_price"));
                list.add(analysis);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return list;
    }
}