package com.sample.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class User {
	
	private Integer id;
	private String name;

	public User(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
}
