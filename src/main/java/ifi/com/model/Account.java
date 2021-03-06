package ifi.com.model;
// Generated Nov 1, 2017 10:05:12 PM by Hibernate Tools 5.2.5.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Account generated by hbm2java
 */
@Entity
@Table(name = "account", catalog = "customercard")
public class Account implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AccountId id;
	private Card card;
	private Customer customer;
	private String accountName;
	private float currentBlance;
	private String otherDetail;

	public Account() {
	}

	public Account(AccountId id, Card card, Customer customer, String accountName, float currentBlance) {
		this.id = id;
		this.card = card;
		this.customer = customer;
		this.accountName = accountName;
		this.currentBlance = currentBlance;
	}

	public Account(AccountId id, Card card, Customer customer, String accountName, float currentBlance,
			String otherDetail) {
		this.id = id;
		this.card = card;
		this.customer = customer;
		this.accountName = accountName;
		this.currentBlance = currentBlance;
		this.otherDetail = otherDetail;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "customerId", column = @Column(name = "Customer_ID", nullable = false)),
			@AttributeOverride(name = "cardId", column = @Column(name = "Card_ID", nullable = false)),
			@AttributeOverride(name = "accountId", column = @Column(name = "Account_ID", nullable = false)) })
	public AccountId getId() {
		return this.id;
	}

	public void setId(AccountId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "Card_ID", nullable = false, insertable = false, updatable = false)
	public Card getCard() {
		return this.card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "Customer_ID", nullable = false, insertable = false, updatable = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Column(name = "account_name", nullable = false, length = 100)
	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Column(name = "current_blance", nullable = false, precision = 12, scale = 0)
	public float getCurrentBlance() {
		return this.currentBlance;
	}

	public void setCurrentBlance(float currentBlance) {
		this.currentBlance = currentBlance;
	}

	@Column(name = "other_detail")
	public String getOtherDetail() {
		return this.otherDetail;
	}

	public void setOtherDetail(String otherDetail) {
		this.otherDetail = otherDetail;
	}

}
