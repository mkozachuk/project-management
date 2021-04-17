package com.mkozachuk.projectmanagement.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    private String projectName;
    private Date startDate;
    private Date finishDate;

    @ManyToOne
    @JoinColumn(name="client_id", nullable=false)
    private Client client;

    @ManyToMany(mappedBy = "projects", fetch = FetchType.LAZY)
    private Set<Employee> employees = new HashSet<>();

    public Project() {
    }

    public Project(Client client) {
        this.client = client;
    }

    public Project(String projectName, Date startDate, Date finishDate) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public Project(String projectName, Date startDate, Date finishDate, Client client) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.client = client;
    }

    public Project(String projectName, Date startDate, Date finishDate, Client client, Set<Employee> employees) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.client = client;
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(projectId, project.projectId) &&
                Objects.equals(projectName, project.projectName) &&
                Objects.equals(startDate, project.startDate) &&
                Objects.equals(finishDate, project.finishDate) &&
                Objects.equals(client, project.client) &&
                Objects.equals(employees, project.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, projectName, startDate, finishDate, client, employees);
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", client=" + client +
                ", employees=" + employees +
                '}';
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}

