package com.hydrogarden.server.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hydrogarden.server.domain.dto.GeneratedTaskDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Table(name = "generated_task")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeneratedTask extends AbstractEntity implements Delayed {
    private LocalDateTime datetime;

    private boolean mode;

    private boolean done;


    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "circuit_id")
    private Circuit circuit;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "circuit_schedule_id")
    private CircuitSchedule circuitSchedule;

    public static GeneratedTask fromGeneratedTaskDTO(GeneratedTaskDTO dto) {
        GeneratedTask generatedTask = new GeneratedTask();
        generatedTask.setDatetime(dto.getDateTime());
        generatedTask.setMode(dto.getMode());
        generatedTask.setDone(dto.getDone());
        generatedTask.setUser(User.fromUserDTO(dto.getUserDTO()));
        generatedTask.setCircuit(Circuit.fromCircuitDto(dto.getCircuitDTO()));
        return generatedTask;
    }


    @Override
    public long getDelay(TimeUnit unit) {
        long diff = LocalDateTime.now().until(datetime, ChronoUnit.NANOS);
        return unit.convert(diff, TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if(o == this) return 0;
        return Long.compare(getDelay(TimeUnit.NANOSECONDS) , o.getDelay(TimeUnit.NANOSECONDS));
    }
}
