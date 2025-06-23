package com.realestate.dao.impl;

import com.realestate.dao.CommunityRankingDao;
import com.realestate.domain.CommunityRanking;
import com.realestate.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommunityRankingDaoImpl implements CommunityRankingDao {
    @Override
    public List<CommunityRanking> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CommunityRanking> list = new ArrayList<>();
        
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM community_ranking";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                CommunityRanking ranking = new CommunityRanking();
                ranking.setCommunity(rs.getString("community"));
                ranking.setDistrict(rs.getString("district"));
                ranking.setListingCount(rs.getInt("listing_count"));
                ranking.setAvgPrice(rs.getDouble("avg_price"));
                ranking.setMinPrice(rs.getDouble("min_price"));
                ranking.setMaxPrice(rs.getDouble("max_price"));
                list.add(ranking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return list;
    }

    @Override
    public List<CommunityRanking> findByDistrict(String district) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CommunityRanking> list = new ArrayList<>();
        
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM community_ranking WHERE district = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, district);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                CommunityRanking ranking = new CommunityRanking();
                ranking.setCommunity(rs.getString("community"));
                ranking.setDistrict(rs.getString("district"));
                ranking.setListingCount(rs.getInt("listing_count"));
                ranking.setAvgPrice(rs.getDouble("avg_price"));
                ranking.setMinPrice(rs.getDouble("min_price"));
                ranking.setMaxPrice(rs.getDouble("max_price"));
                list.add(ranking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return list;
    }

    @Override
    public List<CommunityRanking> findTop10ByAvgPrice() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CommunityRanking> list = new ArrayList<>();
        
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM community_ranking ORDER BY avg_price DESC LIMIT 10";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                CommunityRanking ranking = new CommunityRanking();
                ranking.setCommunity(rs.getString("community"));
                ranking.setDistrict(rs.getString("district"));
                ranking.setListingCount(rs.getInt("listing_count"));
                ranking.setAvgPrice(rs.getDouble("avg_price"));
                ranking.setMinPrice(rs.getDouble("min_price"));
                ranking.setMaxPrice(rs.getDouble("max_price"));
                list.add(ranking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return list;
    }
}