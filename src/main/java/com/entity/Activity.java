package com.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String duration;

    private int reward;

    private int taken_by;

    private int requested_times;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getTaken_by() {
        return taken_by;
    }

    public void setTaken_by(int taken_by) {
        this.taken_by = taken_by;
    }

    public int getRequested_times() {
        return requested_times;
    }

    public void setRequested_times(int requested_times) {
        this.requested_times = requested_times;
    }
}
