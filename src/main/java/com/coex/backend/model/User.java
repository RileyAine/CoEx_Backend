package com.coex.backend.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.coex.backend.util.EnumValidator;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.UUID;

@Document(indexName="users")
public class User {

	public enum AccessLevel {
		USER,MODERATOR,ADMIN
	}

	@Id
	@Field(type = FieldType.Keyword)
	@org.hibernate.validator.constraints.UUID(message = "Field id is not a valid UUID.")
	private String id;
	
	@Field(type = FieldType.Text, name = "email")
	@NotBlank(message = "Field email cannot be blank.")
	@Email(message = "Field email is not well formed.")
	private String email;
	
	@Field(type = FieldType.Date, name = "createdAt")
	@Past(message = "Field createdAt cannot be a future Date.")
	private Date createdAt;
	
	@Field(type = FieldType.Date, name = "updatedAt")
	@Past(message = "Field updatedAt cannot be a future Date.")
	private Date updatedAt;

	@Field(type = FieldType.Keyword, name = "accessLevel")
	@NotBlank(message = "Field accessLevel cannot be blank.")
	@EnumValidator(
			enumClass = AccessLevel.class,
			message = "Value must be an AccessLevel type."
			)
	private String accessLevel;
		
	@Field(type = FieldType.Boolean, name = "isVerified")
	@NotBlank(message = "Field isVerified cannot be blank.")
	private String isVerified;

	@Field(type = FieldType.Text, name = "password")
	@NotBlank(message = "Field password cannot be blank.")
	private String password;

	@Field(type = FieldType.Text, name = "firstName")
	@NotBlank(message = "Field firstName cannot be blank.")
	@Pattern(regexp = "^[A-Za-z\\x{00C0}-\\x{00FF}][A-Za-z\\x{00C0}-\\x{00FF}\\'\\-]+([\\ A-Za-z\\x{00C0}-\\x{00FF}][A-Za-z\\x{00C0}-\\x{00FF}\\'\\-]+)*",
		message = "Field firstName contains invalid character(s).")
	@Size(min = 2, max = 20, message = "Field firstName must be more than 2 and less than 20 characters.")
	private String firstName;

	@Field(type = FieldType.Text, name = "lastName")
	@NotBlank(message = "Field lastName cannot be blank.")
	@Pattern(regexp = "^[A-Za-z\\x{00C0}-\\x{00FF}][A-Za-z\\x{00C0}-\\x{00FF}\\'\\-]+([\\ A-Za-z\\x{00C0}-\\x{00FF}][A-Za-z\\x{00C0}-\\x{00FF}\\'\\-]+)*",
		message = "Field lastName contains invalid character(s).")
	@Size(min = 2, max = 20, message = "Field lastName must be more than 2 and less than 20 characters.")
	private String lastName;
	
	
	public User() {
		this.setId(UUID.randomUUID().toString());
		this.setAccessLevel(AccessLevel.USER.toString());
		this.setCreatedAt(new Date());
		this.setUpdatedAt(new Date());
		this.setIsVerified("false");
	}
	
	public User(@NotBlank(message = "Field accessLevel cannot be blank.") String accessLevel,
			@NotBlank(message = "Field email cannot be blank.") @Email(message = "Field email is not well formed.") String email,
			@NotBlank(message = "Field isVerified cannot be blank.") String isVerified,
			@NotBlank(message = "Field password cannot be blank.") String password,
			@NotBlank(message = "Field firstName cannot be blank.") @Pattern(regexp = "^[A-Za-z\\x{00C0}-\\x{00FF}][A-Za-z\\x{00C0}-\\x{00FF}\\'\\-]+([\\ A-Za-z\\x{00C0}-\\x{00FF}][A-Za-z\\x{00C0}-\\x{00FF}\\'\\-]+)*", message = "Field firstName contains invalid character(s).") @Size(min = 2, max = 20, message = "Field firstName must be more than 2 and less than 20 characters.") String firstName,
			@NotBlank(message = "Field lastName cannot be blank.") @Pattern(regexp = "^[A-Za-z\\x{00C0}-\\x{00FF}][A-Za-z\\x{00C0}-\\x{00FF}\\'\\-]+([\\ A-Za-z\\x{00C0}-\\x{00FF}][A-Za-z\\x{00C0}-\\x{00FF}\\'\\-]+)*", message = "Field lastName contains invalid character(s).") @Size(min = 2, max = 20, message = "Field lastName must be more than 2 and less than 20 characters.") String lastName) {
		super();
		this.id = UUID.randomUUID().toString();
		this.createdAt = new Date();
		this.updatedAt = new Date();
		this.accessLevel = accessLevel;
		this.email = email;
		this.isVerified = isVerified;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public Date getUpdatedAt() {
		return updatedAt;
	}
	
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public String getAccessLevel() {
		return accessLevel;
	}
	
	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getIsVerified() {
		return isVerified;
	}
	
	public void setIsVerified(String isVerified) {
		this.isVerified = isVerified;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "User "
				+ "[id=" + id
				+ "email=" + email 
				+ ", createdAt=" + createdAt 
				+ ", updatedAt=" + updatedAt 
				+ ", accessLevel=" + accessLevel 
				+ ", isVerified=" + isVerified 
				+ ", password=" + password
				+ ", firstName=" + firstName 
				+ ", lastName=" + lastName + "]";
	}
}
