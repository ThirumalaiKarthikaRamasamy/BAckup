package com.ge.seawolf.ingestion.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "semaphores")
public class Semaphore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "key")
    private String key;

    @NotNull
    @Column(name = "timestamp")
    private Long timestamp;

    @NotNull
    @Column(name = "ms_to_live")
    private Integer msToLive;
}
