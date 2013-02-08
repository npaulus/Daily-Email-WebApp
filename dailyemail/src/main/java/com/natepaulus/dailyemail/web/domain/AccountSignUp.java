package com.natepaulus.dailyemail.web.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.natepaulus.dailyemail.validation.FieldMatch;

/**
 * The Class AccountSignUp mirrors the register form for signing up a new user.
 */
@FieldMatch.List({
    @FieldMatch(first = "password", second = "confirm_password"),
    @FieldMatch(first = "email", second = "confirm_email")
})
public class AccountSignUp {
	
	/** The first_name. */
	@NotEmpty
	private String first_name;
	
	/** The last_name. */
	@NotEmpty
	private String last_name;
	
	/** The zipcode. */
	@Length(max=5, min = 5)
    @Pattern(regexp="[0-9]+")
    @NotNull
	private String zipcode;
	
	/** The email. */
	@NotEmpty
	@Email
	private String email;
	
	/** The confirm_email. */
	@NotEmpty
	@Email
	private String confirm_email;
	
	/** The password. */
	@NotEmpty
	private String password;
	
	/** The confirm_password. */
	@NotEmpty	
	private String confirm_password;
	
	/**
	 * Gets the first_name.
	 *
	 * @return the first_name
	 */
	public String getFirst_name() {
		return first_name;
	}
	
	/**
	 * Sets the first_name.
	 *
	 * @param first_name the new first_name
	 */
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	
	/**
	 * Gets the last_name.
	 *
	 * @return the last_name
	 */
	public String getLast_name() {
		return last_name;
	}
	
	/**
	 * Sets the last_name.
	 *
	 * @param last_name the new last_name
	 */
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	
	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Gets the confirm_email.
	 *
	 * @return the confirm_email
	 */
	public String getConfirm_email() {
		return confirm_email;
	}
	
	/**
	 * Sets the confirm_email.
	 *
	 * @param confirm_email the new confirm_email
	 */
	public void setConfirm_email(String confirm_email) {
		this.confirm_email = confirm_email;
	}
	
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Gets the confirm_password.
	 *
	 * @return the confirm_password
	 */
	public String getConfirm_password() {
		return confirm_password;
	}
	
	/**
	 * Sets the confirm_password.
	 *
	 * @param confirm_password the new confirm_password
	 */
	public void setConfirm_password(String confirm_password) {
		this.confirm_password = confirm_password;
	}
	
	/**
	 * Gets the zipcode.
	 *
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}
	
	/**
	 * Sets the zipcode.
	 *
	 * @param zipcode the new zipcode
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	
	
	
}
