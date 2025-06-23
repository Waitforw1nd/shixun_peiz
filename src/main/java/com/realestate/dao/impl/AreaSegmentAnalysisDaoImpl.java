package com.realestate.dao.impl;

import com.realestate.dao.AreaSegmentAnalysisDao;
import com.realestate.domain.AreaSegmentAnalysis;
import com.realestate.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AreaSegmentAnalysisDaoImpl implements AreaSegmentAnalysisDao {
    @Override
    public List<AreaSegmentAnalysis> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<AreaSegmentAnalysis> list = new ArrayList<>();
        
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM area_segment_analysis";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                AreaSegmentAnalysis analysis = new AreaSegmentAnalysis();
                analysis.setAreaSegment(rs.getString("area_segment"));
                analysis.setCount(rs.getInt("count"));
                analysis.setAvgPrice(rs.getDouble("avg_price"));
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
    public List<AreaSegmentAnalysis> findByAreaSegment(String areaSegment) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<AreaSegmentAnalysis> list = new ArrayList<>();
        
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM area_segment_analysis WHERE area_segment = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, areaSegment);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                AreaSegmentAnalysis analysis = new AreaSegmentAnalysis();
                analysis.setAreaSegment(rs.getString("area_segment"));
                analysis.setCount(rs.getInt("count"));
                analysis.setAvgPrice(rs.getDouble("avg_price"));
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