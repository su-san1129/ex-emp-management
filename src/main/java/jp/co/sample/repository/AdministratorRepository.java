package jp.co.sample.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

@Repository
public class AdministratorRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private SimpleJdbcInsert insert;
	
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("employees");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}

	/** RowMapper 
	 *  administratorに情報をセットして返す
	 * */
	private final RowMapper<Administrator> ADMINISTRACTOR_ROW_MAPPER = (rs, i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mail_adress"));
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
			Number key = insert.executeAndReturnKey(param);
			administrator.setId(key.intValue());
			System.out.println( key + "が割り当てられました。");
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
		String sql = "SELECT id, name, mail_address, password FROM administrator WHERE mail_address = :mailAddress  AND password = :password;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password", password);
		Administrator administrator = template.queryForObject(sql, param, ADMINISTRACTOR_ROW_MAPPER);
		if( administrator.getId() == null ) {
			return null;
		} else {
			return administrator;
		}
	}

}
