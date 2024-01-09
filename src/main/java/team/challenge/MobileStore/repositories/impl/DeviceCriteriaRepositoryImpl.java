package team.challenge.MobileStore.repositories.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import team.challenge.MobileStore.model.Device;
import team.challenge.MobileStore.repositories.DeviceCriteriaRepository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class DeviceCriteriaRepositoryImpl implements DeviceCriteriaRepository {
    private final MongoTemplate template;
    @Override
    public Page<Device> findAll(@NonNull Query query, Pageable pageable) {
        List<Device> devices = template.find(query, Device.class);
        long count = template.count(query, Device.class);

        return new PageImpl<>(devices, pageable, count);
    }
}
