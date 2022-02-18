package com.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique=true)
    private String name;

    private String duration;

    private double reward = 0;

    @Column(name="taken_by")
    private int takenBy = 0;

    @Column(name="requested_times")
    private int requestedTimes = 0;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="activity_description_id")
    private Description description;

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

    public double getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getTakenBy() {
        return takenBy;
    }

    public void setTakenBy(int taken_by) {
        this.takenBy = taken_by;
    }

    public int getRequestedTimes() {
        return requestedTimes;
    }

    public void setRequestedTimes(int requested_times) {
        this.requestedTimes = requested_times;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;
        return id == activity.id && name.equals(activity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }
}
