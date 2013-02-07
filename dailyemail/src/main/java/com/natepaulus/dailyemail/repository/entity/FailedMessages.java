package com.natepaulus.dailyemail.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class FailedMessages. Used as the entity class for a failed message
 * queue.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "failed_message_queue")
public class FailedMessages implements Serializable {

	/** The id. */
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** The to address. */
	@Column
	private String toAddress;

	/** The to name. */
	@Column
	private String toName;

	/** The message. */
	@Column
	private String message;

	/** The error message. */
	@Column
	private String errorMessage;
	
	/** The number of failed attempts. */
	@Column(name = "number_attempts")
	private int numberFailedAttempts;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the to address.
	 *
	 * @return the to address
	 */
	public String getToAddress() {
		return toAddress;
	}

	/**
	 * Sets the to address.
	 *
	 * @param toAddress the new to address
	 */
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	/**
	 * Gets the to name.
	 *
	 * @return the to name
	 */
	public String getToName() {
		return toName;
	}

	/**
	 * Sets the to name.
	 *
	 * @param toName the new to name
	 */
	public void setToName(String toName) {
		this.toName = toName;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Sets the error message.
	 *
	 * @param errorMessage the new error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getNumberFailedAttempts() {
		return numberFailedAttempts;
	}

	public void setNumberFailedAttempts(int numberFailedAttempts) {
		this.numberFailedAttempts = numberFailedAttempts;
	}
}
