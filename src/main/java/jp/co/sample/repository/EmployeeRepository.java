package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * employeesテーブルを操作するリポジトリ.
 * @author takahiro.suzuki
 *
 */

@Repository
public class EmployeeRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * Employeeオブジェクトに情報を挿入するマッパー
	 */
	private final RowMapper<Employee> Employee_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));
		return employee;
	};
	
	/**
	 * 全件検索.
	 * 
	 * @return employeeList 従業員一覧（1件もないばあいは0件のリストが返る)
	 */
	public List<Employee> findAll(){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, image, gender, hire_date, mail_address, zip_code, address, telephone, salary, characteristics, dependents_Count ");
		sql.append("FROM employees ");
		sql.append("ORDER BY hire_date ");
		sql.append("LIMIT 10");
		String paramsql = sql.toString();
		List<Employee> employeeList = template.query(paramsql, Employee_ROW_MAPPER);
		return employeeList;
	}
	
	/**
	 * 主キーから従業員情報を取得する.
	 * 
	 * @param id ID
	 * @return Employee 従業員情報（1件もないばあいは例外が発生する)
	 */
	public Employee load(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, image, gender, hire_date, mail_address, zip_code, address, telephone, salary, characteristics, dependents_count ");
		sql.append("FROM employees ");
		sql.append("WHERE id = :id;");
		String paramsql = sql.toString();
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Employee employee = template.queryForObject(paramsql, param, Employee_ROW_MAPPER);
		return employee;
	}
	
	/**
	 * 従業員情報を変更する(扶養人数).
	 * @param employee
	 */
	public void update(Employee employee) {
		String updateSql = "UPDATE employees SET dependents_count = :dependentsCount WHERE id = :id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", employee.getId()).addValue("dependentsCount", employee.getDependentsCount());
		template.update(updateSql, param);
	}
	
	public Integer findAllCount() {
		SqlParameterSource param = new MapSqlParameterSource();
		String countSql = "SELECT COUNT(*) FROM employees";
		Integer count = template.queryForObject(countSql,param ,Integer.class);
		return count;
	}
	
	public List<Employee> findeAllpageNumberEmployee(Integer pageIndex){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, image, gender, hire_date, mail_address, zip_code, address, telephone, salary, characteristics, dependents_count ");
		sql.append("FROM employees ");
		sql.append("ORDER BY hire_date ");
		sql.append("LIMIT 10 OFFSET :pageIndex");
		String paramSql = sql.toString();
		
		if( pageIndex*10 - 11 < 0 ) {
			pageIndex = 0;
		}else {
			pageIndex = pageIndex*10 -11;
		}
		SqlParameterSource param = new MapSqlParameterSource().addValue( "pageIndex", pageIndex);
		List<Employee> employeeList = template.query(paramSql, param, Employee_ROW_MAPPER);
		return employeeList;
	}
	
	
}
