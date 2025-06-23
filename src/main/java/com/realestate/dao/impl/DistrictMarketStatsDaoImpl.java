package com.realestate.dao.impl;

import com.realestate.dao.DistrictMarketStatsDao;
import com.realestate.domain.DistrictMarketStats;
import com.realestate.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DistrictMarketStatsDaoImpl implements DistrictMarketStatsDao {
    @Override
    public List<DistrictMarketStats> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DistrictMarketStats> list = new ArrayList<>();
        
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM district_market_stats";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                DistrictMarketStats stats = new DistrictMarketStats();
                stats.setDistrict(rs.getString("district"));
                stats.setTotalListings(rs.getInt("total_listings"));
                stats.setAvgTotalPrice(rs.getDouble("avg_total_price"));
                stats.setAvgUnitPrice(rs.getDouble("avg_unit_price"));
                stats.setAvgArea(rs.getDouble("avg_area"));
                list.add(stats);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return list;
    }

    @Override
    public List<DistrictMarketStats> findByDistrict(String district) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DistrictMarketStats> list = new ArrayList<>();
        
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM district_market_stats WHERE district = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, district);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                DistrictMarketStats stats = new DistrictMarketStats();
                stats.setDistrict(rs.getString("district"));
                stats.setTotalListings(rs.getInt("total_listings"));
                stats.setAvgTotalPrice(rs.getDouble("avg_total_price"));
                stats.setAvgUnitPrice(rs.getDouble("avg_unit_price"));
                stats.setAvgArea(rs.getDouble("avg_area"));
                list.add(stats);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return list;
    }
}