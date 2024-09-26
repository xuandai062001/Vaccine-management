package com.vn.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Entity
@ToString
@Table(name = "VACCINE")
public class Vaccine {
    @Id
    @Column(name = "VACCINE_ID")
    @NotBlank(message = "Vaccine ID is required")
    private String vaccineId;

    @Column(name = "STATUS")
    private Boolean status;

    @Column(name = "CONTRAINDICATION",columnDefinition = "NVARCHAR(200)")
    private String contraindication;

    @Column(name = "INDICATION",columnDefinition = "NVARCHAR(50)")
    private String indication;

    @Column(name = "NUMBER_OF_INJECTION")
    @NotNull
    private Integer numberOfInjection;

    @Column(name = "ORIGIN",columnDefinition = "NVARCHAR(50)")
    @NotBlank
    private String origin;

    @Column(name = "TIME_BEGIN_NEXT_INJECTION")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Time of beginning next injection is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate timeBeginNextInjection;

    @Column(name = "TIME_END_NEXT_INJECTION")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Time of ending next injection is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate timeEndNextInjection;

    @Column(name = "USAGE",columnDefinition = "NVARCHAR(50)")
    private String usage;

    @Column(name = "VACCINE_NAME",columnDefinition = "NVARCHAR(50)")
    @NotBlank(message = "Vaccine name is required")
    private String vaccineName;

    @OneToMany(mappedBy = "vaccinePKInjection",cascade = CascadeType.ALL)
    private List<InjectionResult> injectionResultVaccine;

    @OneToMany(mappedBy = "vaccineSchedule",cascade = CascadeType.ALL)
    private List<InjectionSchedule> injectionScheduleVaccine;

    @ManyToOne
    @JoinColumn(name = "VACCINE_TYPE_ID", referencedColumnName = "VACCINE_TYPE_ID")
    @NotNull(message = "Vaccine type is required")
    private VaccineType vaccineVaccineType;


}










