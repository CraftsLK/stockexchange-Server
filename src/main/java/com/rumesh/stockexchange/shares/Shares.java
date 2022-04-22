package com.rumesh.stockexchange.shares;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@RequiredArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name="shares")
public class Shares implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(100) UNIQUE")
    private String code;

    @Column(name = "UserId", columnDefinition = "float default 0 NOT NULL")
    private Float userId;

    @Column(name = "shares")
    private String shares;

}
