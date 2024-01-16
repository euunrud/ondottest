package com.ondot.ondot_back.domain.available.entity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ondot.ondot_back.domain.interview.entity.Interview;
import com.ondot.ondot_back.global.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "availability")
public class Availability extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	@Column(name = "availability_date")
	private LocalDate date;

	@NonNull
	@Column(name = "availability_day_of_week")
	private DayOfWeek dayOfWeek;

	@NonNull
	@Column(name = "availability_start_time")
	private LocalTime startTime;

	@NonNull
	@Column(name = "availability_end_time")
	private LocalTime endTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INTERVIEW_ID")
	private Interview interview;
}
