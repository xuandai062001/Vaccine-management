package com.vn.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Entity
@ToString
@Table(name = "VACCINE_TYPE")
public class VaccineType {

    @Id
    @Column(name = "VACCINE_TYPE_ID")
    @NotBlank(message = "Vaccine ID is required")
    private String vaccineTypeId;


    @Column(name = "DESCRIPTION",columnDefinition = "NVARCHAR(500)")
    private String description;


    @Column(name = "VACCINE_TYPE_NAME",columnDefinition = "NVARCHAR(100)")
    @NotBlank(message = "Vaccine name is required")
    private String vaccineTypeName;

    @Column(name = "ACTIVE")
    private Boolean active;

    @OneToMany(mappedBy = "vaccineVaccineType",cascade = CascadeType.ALL)
    private List<Vaccine> vaccineList;

}




