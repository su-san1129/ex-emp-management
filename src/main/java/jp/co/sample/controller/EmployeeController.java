package jp.co.sample.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

/**
 * 従業員情報を操作するコントローラー.
 * 
 * @author takahiro.suzuki
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	
	/**
	 * 従業員一覧を出力する.
	 * 
	 * 取得した従業員リストを従業員一覧ページに出力する。
	 * @param model
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = employeeService.showList();
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
		
	}
	
	
	/**
	 * 従業員詳細情報を出力する.
	 * 
	 * 従業員の詳細情報をサービスクラスから取得してリクエストスコープに格納。
	 * @param id 従業員ID
	 * @param model リクエストスコープ
	 * @return 従業員の詳細情報
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		return "employee/detail";
	}
	
	/**
	 * 従業員の扶養人数を更新する.
	 * 
	 * @param form フォーム
	 * @return 従業員の詳細情報
	 */
	@RequestMapping("/update")
	public String update(UpdateEmployeeForm form) {
		System.out.println(form);
		Employee employee = new Employee();
		employee.setId( Integer.parseInt(form.getId()) );
		employee.setDependentsCount( Integer.parseInt(form.getDependentsCount()));
		System.out.println(employee);
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}
	
	
	
	

}
