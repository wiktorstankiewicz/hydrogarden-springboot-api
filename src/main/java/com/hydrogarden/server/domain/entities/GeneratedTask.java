package com.hydrogarden.server.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "circuit_id")
    private Circuit circuit;


    @Override
    public long getDelay(TimeUnit unit) {
        long diff = LocalDateTime.now().until(datetime, ChronoUnit.SECONDS);
        return unit.convert(diff, TimeUnit.SECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Math.toIntExact(getDelay(TimeUnit.SECONDS) - o.getDelay(TimeUnit.SECONDS));
    }
}
