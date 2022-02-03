package com.web.utils;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;


@JsonInclude(Include.NON_NULL)
public class AjaxResponseBody {
	@ApiModelProperty(notes = "Message related to API")
	String msg;
	@ApiModelProperty(notes = "Code related to API")
	String code;
	@ApiModelProperty(notes = "message flag")
	List<?> result;

	public AjaxResponseBody() {
		super();
	}

	public AjaxResponseBody(String message) {
		super();
		setMsg("Message.");
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
