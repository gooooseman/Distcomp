package com.example.distcomp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_issue")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 64, unique = true, nullable = false)
    @Size(min = 2, max = 64)
    private String title;

    @Column(length = 2048, nullable = false)
    @Size(min = 4, max = 2048)
    private String content;

    @Column(nullable = false)
    private Timestamp created;

    @Column(nullable = false)
    private Timestamp modified;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private Writer writer;

    @ManyToMany
    @JoinTable(
            name = "tbl_issue_label",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private Set<Label> labels = new HashSet<>();
    public void addLabel(Label label) {
        this.labels.add(label);
        label.getIssues().add(this);
    }

    public void removeLabel(Label label) {
        this.labels.remove(label);
        label.getIssues().remove(this);
    }
}