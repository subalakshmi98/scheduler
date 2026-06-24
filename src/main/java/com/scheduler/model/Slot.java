package com.scheduler.model;

import com.scheduler.constants.SlotStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "slots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Slot {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String slotNumber;

    @ManyToOne(optional = false)
    private User owner;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private SlotStatus status;

    @OneToOne(mappedBy = "slot")
    private Meeting meeting;
}
