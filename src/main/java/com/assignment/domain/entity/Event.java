package com.assignment.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Builder
@Table(name = "event")
public class Event {

    @Id
    private String eventId;

    @Column(nullable = false)
    private long eventDuration;

    private String type;

    private String host;

    private boolean alert;

    //Hibernate requires this and Builder doesn't allow it so tolerating a non args constructor
    @Tolerate
    public Event(){}
}