package com.haechang.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;

	@Column(nullable = false, length = 100)
	private String title;

	@Lob
	private String content; // 섬머노트 라이브러리 <html> 태그가 섞여서 디자인

//	@ColumnDefault("0") // int기 때문에 " ' ' " 안해도됨.
	private int viewcount; // 조회수

	private int likecount; // 좋아요 수

	@ManyToOne(fetch = FetchType.EAGER) // Many = Board, User = One 한 유저가 보드를 많이 사용가능
	@JoinColumn(name = "userId")
	private User user; // DB는 오브젝트를 저장할 수 없다. -> FK 사용, 자바는 오브젝트 저장가능

	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) // mappedBy는 연관관계의 주인이 아니다.
																							// (난 FK아니다.) DB에 컬럼 만들지
																							// 마세요.
	@JsonIgnoreProperties({ "board" })
	@OrderBy("id desc")
	private List<Reply> replys;

	@CreationTimestamp
	private Timestamp createDate;
}
