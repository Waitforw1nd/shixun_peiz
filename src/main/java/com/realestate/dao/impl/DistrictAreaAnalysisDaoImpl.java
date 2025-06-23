package com.realestate.dao.impl;

import com.realestate.dao.DistrictAreaAnalysisDao;
import com.realestate.domain.DistrictAreaAnalysis;
import com.realestate.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DistrictAreaAnalysisDaoImpl implements DistrictAreaAnalysisDao {
    @Override
    public List<DistrictAreaAnalysis> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DistrictAreaAnalysis> list = new ArrayList<>();
        
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM district_area_analysis";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                DistrictAreaAnalysis analysis = new DistrictAreaAnalysis();
                analysis.setDistrict(rs.getString("district"));
                analysis.setAreaSegment(rs.getString("area_segment"));
                analysis.setCount(rs.getInt("count"));
                analysis.setAvgPrice(rs.getDouble("avg_price"));
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
    public List<DistrictAreaAnalysis> findByDistrict(String district) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DistrictAreaAnalysis> list = new ArrayList<>();
        
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM district_area_analysis WHERE district = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, district);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                DistrictAreaAnalysis analysis = new DistrictAreaAnalysis();
                analysis.setDistrict(rs.getString("district"));
                analysis.setAreaSegment(rs.getString("area_segment"));
                analysis.setCount(rs.getInt("count"));
                analysis.setAvgPrice(rs.getDouble("avg_price"));
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