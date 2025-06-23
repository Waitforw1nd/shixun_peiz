package com.realestate.dao.impl;

import com.realestate.dao.OrientationAnalysisDao;
import com.realestate.domain.OrientationAnalysis;
import com.realestate.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrientationAnalysisDaoImpl implements OrientationAnalysisDao {
    @Override
    public List<OrientationAnalysis> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<OrientationAnalysis> list = new ArrayList<>();
        
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM orientation_analysis";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                OrientationAnalysis analysis = new OrientationAnalysis();
                analysis.setOrientation(rs.getString("orientation"));
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
    public List<OrientationAnalysis> findByOrientation(String orientation) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<OrientationAnalysis> list = new ArrayList<>();
        
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM orientation_analysis WHERE orientation = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, orientation);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                OrientationAnalysis analysis = new OrientationAnalysis();
                analysis.setOrientation(rs.getString("orientation"));
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