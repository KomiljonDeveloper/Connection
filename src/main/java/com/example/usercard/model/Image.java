package com.example.usercard.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@NamedQuery(
        name = "deleting image",
        query = "select i from Image as i where deletedAt is not null"
)
public class Image {
    @Id
    @SequenceGenerator(name = "file_seq",sequenceName = "file_sequence",allocationSize = 1)
    @GeneratedValue(generator = "file_seq")
    private Integer imageId;
    private String imageName;
    private String path;
    private String ext;
    @Column(name = "user_id",unique = true)
    private Integer userId;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
