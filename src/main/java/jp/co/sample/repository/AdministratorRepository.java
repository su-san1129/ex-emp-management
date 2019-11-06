package jp.co.sample.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

@Repository
public class AdministratorRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;

	/** RowMapper 
	 *  administratorに情報をセットして返す
	 * */
	private final RowMapper<Administrator> ADMINISTRACTOR_ROW_MAPPER = (rs, i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mail_address"));
		administrator.setPassword(rs.getString("password"));
		return administrator;
	};

	/**
	 * 管理者情報をSQLに挿入する
	 * @param administrator
	 */
	public void insert(Administrator administrator) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
		
		if ( administrator.getId() == null ) {
			String insertSql = "INSERT INTO administrators (name, mail_address, password) VALUES (:name, :mailAddress, :password);";
			template.update(insertSql, param);
		} else {
			String updateSql = "UPDATE administrator SET name = :name, mail_address = :mailAddress, password = :password WHERE id = :id;";
			template.update(updateSql, param);
		}

	}

	/**
	 * メールアドレスとパスワードから管理者情報を取得する
	 * @param mailAddress
	 * @param password
	 * @return
	 */
	public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
		String sql = "SELECT id, name, mail_address, password FROM administrators WHERE mail_address = :mailAddress AND password = :password;";
		try {
			SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password", password);
			Administrator administrator = template.queryForObject(sql, param, ADMINISTRACTOR_ROW_MAPPER);
			return administrator;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}

}
