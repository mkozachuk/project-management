package com.mkozachuk.projectmanagement.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    private String companyName;

    @OneToMany(mappedBy="client")
    private Set<Project> projects = new HashSet<>();

    public Client() {
    }

    public Client(String companyName) {
        this.companyName = companyName;
    }

    public Client(String companyName, Set<Project> projects) {
        this.companyName = companyName;
        this.projects = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(clientId, client.clientId) &&
                Objects.equals(companyName, client.companyName) &&
                Objects.equals(projects, client.projects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, companyName, projects);
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", companyName='" + companyName + '\'' +
                ", projects=" + projects +
                '}';
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
