package com.blu.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "t_blog")
public class Blog {
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	@Basic(fetch = FetchType.LAZY)
	@Lob
	private String content;
	private String firstPicture;
	private String flag;
	private String description;
	private Integer views;
	private boolean appreciation;
	private boolean shareStatement;
	private boolean commentabled;
	private boolean published;
	private boolean recommend;
	@ManyToOne
	private User user;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	@ManyToOne
	private Type type;
	@ManyToMany(cascade = {CascadeType.PERSIST})
	private List<Tag> tags = new ArrayList<>();
	@OneToMany(mappedBy = "blog")
	private List<comment> comments = new ArrayList<>();
	@Transient
	private String tagIds;
	
	public void init() {
		this.tagIds = tagsToIds(this.getTags());
	}
	
	private String tagsToIds(List<Tag> tags) {
		if(!tags.isEmpty()) {
			StringBuffer ids = new StringBuffer();
			boolean flag = false;
			for (Tag tag : tags) {
				if (flag) {
					ids.append(",");
				} else {
					flag = true;
				}
				ids.append(tag.getId());
			}
			return ids.toString();
		} else {
			return tagIds;
		}
	}
	
}
