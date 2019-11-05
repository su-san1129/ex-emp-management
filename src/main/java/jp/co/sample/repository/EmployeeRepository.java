package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import jp.co.sample.domain.Employee;

public class EmployeeRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
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
		employee.setDependentsCount(rs.getInt("dependentsCount"));
		return employee;
	};
	
	public List<Employee> findAll(){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, image, gender, hire_date, mail_address, zip_code, address, telephone, salary, characteristics, dependentsCount ");
		sql.append("FROM employees ");
		sql.append("ORDER BY hire_date;");
		String paramsql = sql.toString();
		List<Employee> employeeList = template.query(paramsql, Employee_ROW_MAPPER);
		return employeeList;
	}
	
	public Employee load(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, image, gender, hire_date, mail_address, zip_code, address, telephone, salary, characteristics, dependentsCount ");
		sql.append("FROM employees ");
		sql.append("WHERE id = :id;");
		String paramsql = sql.toString();
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Employee employee = template.queryForObject(paramsql, param, Employee_ROW_MAPPER);
		return employee;
	}

}
