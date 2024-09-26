package com.vn.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "INJECTION_SCHEDULE")
public class InjectionSchedule {

    @Id
    @Column(name = "INJECTION_SCHEDULE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer injectionScheduleId;

    @Column(name = "DESCRIPTION",columnDefinition = "NVARCHAR(200)")
    private String description;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "End date injection is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Column(name = "PLACE",columnDefinition = "NVARCHAR(100)")
    private String place;

    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Start date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(name = "STATUS",columnDefinition = "NVARCHAR(50)")
    private String status;

    @Column(name = "NOTE",columnDefinition = "NVARCHAR(200)")
    private String note;

    @ManyToOne
    @JoinColumn(name = "VACCINE_ID", referencedColumnName = "VACCINE_ID")
    @NotNull(message = "Vaccine is required")
    private Vaccine vaccineSchedule;


    public String getCalculatedStatus() {
        LocalDate now = LocalDate.now();
        if (this.startDate == null || this.endDate == null) {
            return "invalid date";
        }

        if (now.isBefore(this.startDate)) {
            return "not yet";
        } else if (now.isAfter(this.endDate)) {
            return "over";
        } else {
            return "open";
        }
    }
}


