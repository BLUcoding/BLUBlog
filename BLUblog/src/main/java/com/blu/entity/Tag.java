package com.blu.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "t_tag")
public class Tag {
	@Id
	@GeneratedValue
	private Long id;
	@NotBlank(message = "标签名称不能为空")
	private String name;
	@ManyToMany(mappedBy = "tags")
	private List<Blog> blogs = new ArrayList<>();;
}
