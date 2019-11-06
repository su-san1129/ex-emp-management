package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

@Controller
@RequestMapping("/")
public class AdministratorController {
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private HttpSession session;

	@ModelAttribute
	private InsertAdministratorForm setUpInsertAdministratorForm(){
		return new InsertAdministratorForm();
	}
	
	@ModelAttribute
	private LoginForm setUpLoginForm() {
		return new LoginForm();
	}
	
	/**
	 * insertページを表示する
	 * @return
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}
	
	/**
	 * ログインページ
	 * @return
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}
	
	/**
	 * 管理者情報を挿入する
	 * @param form
	 * @return
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		administratorService.insert(administrator);
		System.out.println(administrator);
		return "redirect:/";
	}
	
	/**
	 * 
	 * 管理者のログイン機能
	 * @param form
	 * @param model
	 * @return
	 * 
	 * メールアドレスとパスワードが一致しなければ、nullを返す。
	 * 一致すれば、管理者情報を返す。
	 */
	@RequestMapping("/login")
	public String login(LoginForm form, Model model) {
		Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());
		if (administrator == null) {
			model.addAttribute("loginError", "メールアドレスまたはパスワードが不正です。");
			System.out.println("error");
			return toLogin();
		} else {
			session.setAttribute("administratorName", administrator);
			return "forward:/employee/showList";
		}
		
	}
}
