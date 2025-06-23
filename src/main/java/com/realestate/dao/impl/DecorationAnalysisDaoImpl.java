package com.realestate.dao.impl;

import com.realestate.dao.DecorationAnalysisDao;
import com.realestate.domain.DecorationAnalysis;
import com.realestate.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DecorationAnalysisDaoImpl implements DecorationAnalysisDao {
    @Override
    public List<DecorationAnalysis> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DecorationAnalysis> list = new ArrayList<>();
        
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM decoration_analysis";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                DecorationAnalysis analysis = new DecorationAnalysis();
                analysis.setDecoration(rs.getString("decoration"));
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
    public List<DecorationAnalysis> findByDecoration(String decoration) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DecorationAnalysis> list = new ArrayList<>();
        
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM decoration_analysis WHERE decoration = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, decoration);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                DecorationAnalysis analysis = new DecorationAnalysis();
                analysis.setDecoration(rs.getString("decoration"));
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