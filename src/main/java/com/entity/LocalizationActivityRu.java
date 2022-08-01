package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "localization_en_ru")
public class LocalizationActivityRu {

    @Id
    @Column(name = "activity_name_en", nullable = false)
    private String activityNameEn;

    @Column(name = "activity_name_ru")
    private String activityNameRu;

    @Column(name = "activity_description_ru")
    private String activityDescriptionRu;

    public String getActivityNameEn() {
        return activityNameEn;
    }

    public void setActivityNameEn(String activityNameEn) {
        this.activityNameEn = activityNameEn;
    }

    public String getActivityNameRu() {
        return activityNameRu;
    }

    public void setActivityNameRu(String activityNameRu) {
        this.activityNameRu = activityNameRu;
    }

    public String getActivityDescriptionRu() {
        return activityDescriptionRu;
    }

    public void setActivityDescriptionRu(String activityDescriptionRu) {
        this.activityDescriptionRu = activityDescriptionRu;
    }
}
