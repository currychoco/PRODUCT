package com.currychoco.product.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import com.currychoco.product.domain.Member;
import com.currychoco.product.domain.Product;

public class JdbcProductRepository implements ProductRepository{
	private final DataSource dataSource;
	
	public JdbcProductRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Product save(Product product) {
		String sql = "insert into product(name) values(?)";
		
		Connection conn = null;
		PreparedStatement pstmt = null; //커넥션을 통해서 쿼리를 날리고 결과를 받아옴
		ResultSet rs = null;//쿼리의 실행결과값을 저장하는 객체
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //쿼리를 이런 형태로 넣을게 하고 정함
			
			pstmt.setString(1, product.getName());//sql문자열의 ?에 값을 넣음, 1은 첫번째 물음표를 뜻함
			pstmt.executeUpdate();//쿼리를 날림
			
			rs = pstmt.getGeneratedKeys(); // insert 문 실행을 통해 생성된 row 의 key 값(generated key)를 가져온다
			
			if(rs.next()) {
				product.setId(rs.getLong(1));
			}else {
				throw new SQLException("id 조회 실패");
			}
			return product;
		} catch(Exception e) {
			throw new IllegalStateException(e);
		} finally {
			close(conn, pstmt, rs);
		}
	}
	
	private void close(Connection conn) throws SQLException{
		DataSourceUtils.releaseConnection(conn, dataSource);
	}
	
	private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		try {
			if(pstmt != null) {
				pstmt.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		try {
			if(conn != null) {
				close(conn);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private Connection getConnection() {
		return DataSourceUtils.getConnection(dataSource);
	}

	@Override
	public Optional<Product> findById(Long id) {
		String sql = "select * from product where id = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Product product = new Product();
				product.setId(rs.getLong("id"));
				product.setName(rs.getString("name"));
				return Optional.of(product);
			}else {
				return Optional.empty();
			}	
		}catch(Exception e) {
			throw new IllegalStateException(e);
		}finally {
			close(conn, pstmt, rs);
		}
	}

	@Override
	public Optional<Product> findByName(String name) {
		String sql = "select * from product where name = ?"; 
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql); //쿼리를 sql문자열의 형태로 넣을게
			pstmt.setString(1, name); // sql문자열의 첫번째 '?'에 매개변수로 받은 name을 넣을게
			
			rs = pstmt.executeQuery(); //실행한 쿼리 결과값을 rs에 저장
			
			if(rs.next()) {
				Product product = new Product();
				product.setId(rs.getLong("id"));
				product.setName(rs.getString("name"));
				return Optional.of(product);
			}
			return Optional.empty();
		}catch(Exception e) {
			throw new IllegalStateException(e);
		}finally {
			close(conn, pstmt, rs);
		}
	}

	@Override
	public List<Product> findAll() {
		String sql = "select * from product";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			List<Product> products = new ArrayList<>();
			while(rs.next()) {
				Product product = new Product();
				product.setId(rs.getLong("id"));
				product.setName(rs.getString("name"));
				products.add(product);
			}
			return products;
			
		}catch(Exception e) {
			throw new IllegalStateException(e);
		}finally {
			close(conn, pstmt, rs);
		}
	}

	@Override
	public boolean deleteById(Long id) {
		String sql = "delete from product where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, id);
			int rowCount = pstmt.executeUpdate();
			
			return rowCount > 0;
			
		}catch(Exception e) {
			throw new IllegalStateException(e);
		}finally {
			close(conn, pstmt, null);
		}
	}
	
}
