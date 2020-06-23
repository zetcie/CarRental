package pl.lodz.p.it.carrental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.carrental.model.TraceLog;
import pl.lodz.p.it.carrental.model.TraceLogType;

import java.time.LocalDateTime;
import java.util.List;

public interface TraceLogRepository extends JpaRepository<TraceLog, Long> {
    List<TraceLog> findAllByCreatedDateGreaterThanEqualAndTypeEquals(LocalDateTime time, TraceLogType traceLogType);
}
