package jp.co.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@ModelAttribute
	private UpdateEmployeeForm setUpUpdateForm() {
		return new UpdateEmployeeForm();
	}
	
	
	/**
	 * 従業員一覧を出力する.
	 * 
	 * 取得した従業員リストを従業員一覧ページに出力する。
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = employeeService.showList();
		model.addAttribute("employeeList", employeeList);
		model.addAttribute("pageList", employeeService.pageList());
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
	public String update(
			@Validated UpdateEmployeeForm form
			, BindingResult result
			, Model model
			) {
		if(result.hasErrors()) {
			 return showDetail(form.getId(), model);
//			return "forward:/employee/showDetail;
		}
		Employee employee = new Employee();
		employee.setId( Integer.parseInt(form.getId()) );
		employee.setDependentsCount( Integer.parseInt(form.getDependentsCount()));
		System.out.println(employee);
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}
	
	@RequestMapping("/findAllPageNumber/{index}")
	public String findAllPageNumber(@PathVariable("index") Integer index, Model model) {
		List<Employee> employeeList = employeeService.findAllEmployeeFromPageNumber(index);
		model.addAttribute("employeeList", employeeList);
		System.out.println(employeeService.pageList().size());
		model.addAttribute("pageList", employeeService.pageList());
		model.addAttribute("index", index);
		model.addAttribute("preIndex", (index-1));
		model.addAttribute("nextIndex", (index+1));
		
		return "employee/list";
	}
	
	
	
	

}
