package com.example.usercard.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users",
        uniqueConstraints = {
        @UniqueConstraint(name = "email_unique_seq",columnNames = "email")},
        indexes = {
        @Index(name = "index_email",columnList = "email"),
        @Index(name = "index_id",columnList = "id")
        }
)
@NamedQueries(value = {
        @NamedQuery(name = "existsByEmail",
                query = "select case when count(u) > 0 " +
                        "then true else false end from User as u where u.email like :email and u.deletedAt is null"),
})

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private Boolean enabled;
    private String birthday;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
