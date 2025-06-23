package com.realestate.dao.impl;

import com.realestate.dao.DecadePriceTrendDao;
import com.realestate.domain.DecadePriceTrend;
import com.realestate.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DecadePriceTrendDaoImpl implements DecadePriceTrendDao {
    @Override
    public List<DecadePriceTrend> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DecadePriceTrend> list = new ArrayList<>();
        
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM decade_price_trend";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                DecadePriceTrend trend = new DecadePriceTrend();
                trend.setBuiltDecade(rs.getString("built_decade"));
                trend.setCount(rs.getInt("count"));
                trend.setAvgPrice(rs.getDouble("avg_price"));
                trend.setAvgUnitPrice(rs.getDouble("avg_unit_price"));
                list.add(trend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return list;
    }

    @Override
    public List<DecadePriceTrend> findByDecade(String decade) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DecadePriceTrend> list = new ArrayList<>();
        
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM decade_price_trend WHERE built_decade = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, decade);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                DecadePriceTrend trend = new DecadePriceTrend();
                trend.setBuiltDecade(rs.getString("built_decade"));
                trend.setCount(rs.getInt("count"));
                trend.setAvgPrice(rs.getDouble("avg_price"));
                trend.setAvgUnitPrice(rs.getDouble("avg_unit_price"));
                list.add(trend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return list;
    }
}