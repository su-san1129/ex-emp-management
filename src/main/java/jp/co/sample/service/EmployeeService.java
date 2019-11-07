package jp.co.sample.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeRepository;

/**
 * 従業員を操作するサービスクラス.
 * @author takahiro.suzuki
 *
 */
@Service
@Transactional
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	/**
	 * 従業員を全件取得する.
	 * 
	 * @return 従業員リスト
	 */
	public List<Employee> showList(){
		return employeeRepository.findAll();
	}
	
	
	/**
	 * 従業員の詳細を表示する.
	 * @param id 従業員ID
	 * @return 従業員の詳細情報
	 */
	public Employee showDetail(Integer id) {
		return employeeRepository.load(id);
	}
	
	/**
	 * 従業員の扶養人数を更新する.
	 *
	 * @param employee 従業員
	 */
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}
	
	public Integer countData() {
		return employeeRepository.findAllCount();
	}
	
	public List<Integer> pageList(){
		Integer count = countData();
		List<Integer> pageList = new ArrayList<>();
		if( count % 10 == 0) {
			for (int i = 1; i <= count/10; i++) {
				pageList.add(i);
			} 
		} else {
			for (int i = 1; i <= count/10 + 1; i++) {
				pageList.add(i);
			}
		}
		return pageList;
	}
	
	public List<Employee> findAllEmployeeFromPageNumber(Integer index){
		return employeeRepository.findeAllpageNumberEmployee(index);
	}
}
