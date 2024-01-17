package com.ondot.ondot_back.domain.interview.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.ondot.ondot_back.domain.interview.enums.InterviewStatus;
import com.ondot.ondot_back.domain.organization.entity.Organization;
import com.ondot.ondot_back.global.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "interview")
public class Interview extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	@Length(max = 20)
	@Column(name = "interview_name")
	private String name;

	@Length(max = 180)
	@Column(name = "interview_description")
	private String description;

	@NonNull
	@Column(name = "interview_start_date")
	private LocalDate startDate;

	@NonNull
	@Column(name = "interview_end_date")
	private LocalDate endDate;

	@NonNull
	@Column(name = "interview_required_time")
	private LocalTime requiredTime;

	@NonNull
	@Column(name = "interview_interviewer_count")
	private int interviewerCount;

	@NonNull
	@Column(name = "interview_candidate_count")
	private int candidateCount;

	@NonNull
	@Column(name = "interview_location")
	private String location;

	@NonNull
	@Enumerated(EnumType.STRING)
	@Column(name = "interview_status")
	private InterviewStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGANIZATION_ID")
	private Organization organization;
}
