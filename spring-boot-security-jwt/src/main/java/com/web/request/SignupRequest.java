package com.web.request;

import java.util.Set;

import javax.validation.constraints.*;

public class SignupRequest {
	
  
  private String name;
  
  
  private String age;
  
  
  private String photo;
	
  private String username;

  private String email;

  private Set<String> role;

  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<String> getRole() {
    return this.role;
  }

  public void setRole(Set<String> role) {
    this.role = role;
  }

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAge() {
		return age;
	}
	
	public void setAge(String age) {
		this.age = age;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "SignupRequest [name=" + name + ", age=" + age + ", photo=" + photo + ", username=" + username
				+ ", email=" + email + ", role=" + role + ", password=" + password + "]";
	}
	

  
}