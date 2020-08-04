package com.blu.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_type")
public class Type {
	@Id
	@GeneratedValue
	private Long id;
	@NotBlank(message = "分类名称不能为空")
	private String name;
	@OneToMany(mappedBy = "type")
	private List<Blog> blogs = new ArrayList<>();

}
