package com.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Pattern;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_has_activity")
public class ActivityUser {



    @NonNull
    private String status;

    @NonNull
    private String time_spent;

}
