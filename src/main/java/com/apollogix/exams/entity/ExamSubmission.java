package com.apollogix.exams.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "exam_submission")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExamSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitter_id", nullable = false, referencedColumnName = "id")
    private User submitter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "submit_time", nullable = false)
    @Builder.Default
    private Date submitTime = new Date();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "exam_submission_answer",
            joinColumns = @JoinColumn(name = "exam_submission_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_id")
    )
    private List<Answer> selectedAnswers;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActivated = true;
}
