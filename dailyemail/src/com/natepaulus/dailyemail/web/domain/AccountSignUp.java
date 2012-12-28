package com.natepaulus.dailyemail.web.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.natepaulus.dailyemail.validation.FieldMatch;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "confirm_password"),
    @FieldMatch(first = "email", second = "confirm_email")
})
public class AccountSignUp {
	
	@NotEmpty
	private String first_name;
	
	@NotEmpty
	private String last_name;
	
	@Length(max=5, min = 5, message="Zip code must be 5 digits!")
    @Pattern(regexp="[0-9]+", message = "Zip code must be numbers only!")
    @NotNull
	private String zipcode;
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	@Email
	private String confirm_email;
	
	@NotNull
	private String password;
	
	@NotNull	
	private String confirm_password;
	
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getConfirm_email() {
		return confirm_email;
	}
	public void setConfirm_email(String confirm_email) {
		this.confirm_email = confirm_email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirm_password() {
		return confirm_password;
	}
	public void setConfirm_password(String confirm_password) {
		this.confirm_password = confirm_password;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	
	
	
}
