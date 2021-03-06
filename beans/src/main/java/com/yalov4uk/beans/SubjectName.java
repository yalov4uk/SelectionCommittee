package com.yalov4uk.beans;

import com.yalov4uk.abstracts.Bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "subject_names")
public class SubjectName extends Bean {

    private String name;

    private Set<Faculty> faculties;
    private Set<Subject> subjects;

    public SubjectName() {
        faculties = new HashSet<>();
        subjects = new HashSet<>();
    }

    public SubjectName(String name) {
        faculties = new HashSet<>();
        subjects = new HashSet<>();
        this.name = name;
    }

    @ManyToMany(mappedBy = "requiredSubjects", fetch = FetchType.LAZY)
    public Set<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(Set<Faculty> faculties) {
        this.faculties = faculties;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "subjectName", fetch = FetchType.LAZY)
    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubjectName)) return false;
        if (!super.equals(o)) return false;
        SubjectName that = (SubjectName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return "SubjectName{" +
                super.toString() +
                ", name='" + name + '\'' +
                '}';
    }
}
