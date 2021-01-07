package com.hcl.mobileserviceprovider.service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Setter
@Getter
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long connectionId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    @JsonBackReference
    private Plan plan;

    @ManyToOne
    @JoinColumn(name = "mobile_id")
    @JsonBackReference
    private MobileInfo mobileInfo;

    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private LocalDate requestdate;
    @Column(nullable = false)
    private LocalDate updateDate;
    private String remark;
}
