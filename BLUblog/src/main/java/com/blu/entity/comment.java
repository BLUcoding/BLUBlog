package com.blu.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "t_comment")
public class comment {
	@Id
	@GeneratedValue
	private Long id;
	private String nickname;
	private String email;
	private String content;
	private String avatar;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@ManyToOne
	private Blog blog;
	@OneToMany(mappedBy = "parentComment")
	private List<comment> replyComments = new ArrayList<>();
	@ManyToOne
	private comment parentComment;
	private boolean IsblogerComment;
	
}
