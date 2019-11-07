package jp.co.sample.form;

import javax.validation.constraints.NotBlank;

/**
 * ログインで使用するフォーム.
 * 
 * @author takahiro.suzuki
 *
 */
public class LoginForm {
	
	/** メールアドレス */
	@NotBlank(message="メールアドレスを入力してください")
	private String mailAddress;
	/** パスワード */
	@NotBlank(message="パスワードを入力してください")
	private String password;
	
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
