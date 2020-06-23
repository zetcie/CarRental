package pl.lodz.p.it.carrental.model;

import pl.lodz.p.it.carrental.model.users.SystemUser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class TraceLog extends TimestampedAbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;

    @NotNull
    @ManyToOne
    private SystemUser createdBy;

    @NotNull
    private TraceLogType type;

    public TraceLog() {
    }

    public TraceLog(String message, SystemUser createdBy, TraceLogType type) {
        this.message = message;
        this.createdBy = createdBy;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SystemUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(SystemUser createdBy) {
        this.createdBy = createdBy;
    }

    public TraceLogType getType() {
        return type;
    }

    public void setType(TraceLogType type) {
        this.type = type;
    }
}
