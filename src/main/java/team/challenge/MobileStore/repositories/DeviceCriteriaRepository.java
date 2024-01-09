package team.challenge.MobileStore.repositories;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import team.challenge.MobileStore.model.Device;

import java.util.List;
import java.util.Map;

public interface DeviceCriteriaRepository {
    Page<Device> findAll(@NonNull final Query query, Pageable pageable);
}
