package team.challenge.MobileStore.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import team.challenge.MobileStore.dto.DeviceRequest;
import team.challenge.MobileStore.exception.ModelNotFoundException;
import team.challenge.MobileStore.model.*;
import team.challenge.MobileStore.repositories.DeviceCriteriaRepository;
import team.challenge.MobileStore.repositories.DeviceRepository;
import team.challenge.MobileStore.service.DeviceService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private static final String CATALOGUE = "catalogue";
    private static final String BRAND = "brand";
    private static final String PRICE = "price";
    private static final String DIAGONAL = "screen diagonal";
    private static final String SPECIFICATION = "specificationGroups.specifications";
    private final DeviceRepository deviceRepository;
    private final DeviceCriteriaRepository deviceCriteriaRepository;
    @Override
    public Page<Device> getAll(Map<String, String> params) {
        int page = 1;
        int size = 20;
        if (params.containsKey("page") && params.containsKey("size")){
            page = Integer.parseInt(params.get("page"));
            size = Integer.parseInt(params.get("size"));
        }
        PageRequest pageRequest = PageRequest.of(page -1, size);
        if (params.isEmpty() || (params.size() == 2 && (params.containsKey("page") && params.containsKey("size")))) {
            return deviceRepository.findAll(pageRequest);
        }
        List<Criteria> criteriaList = new ArrayList<>();
        for (Map.Entry<String, String> entry: params.entrySet()){
            switch (entry.getKey()){
                case "catalogue" -> {
                    ObjectId id = new ObjectId(entry.getValue());
                    criteriaList.add(createCriteriaIs(CATALOGUE, id));
                }
                case "brand" -> {
                    List<String> paramList = List.of(entry.getValue().split(","));
                    criteriaList.add(createCriteriaIn(BRAND, paramList));
                }
                case "price" -> {
                    List<String> prices = List.of(entry.getValue().split("-"));
                    criteriaList.add(createCriteriaBetween(PRICE, prices.get(0), prices.get(1)));
                }
                case "screen diagonal" -> {

                    List<String> diagonals = List.of(entry.getValue().split(","));
                    Set<Double> diagonalsSet = new HashSet<>();
                    for (String diagonalString :
                            diagonals) {
                        String[] diagonalsToParse = diagonalString.split("-");
                        diagonalsSet.add(Double.parseDouble(diagonalsToParse[0].replaceAll("'","")));
                        diagonalsSet.add(Double.parseDouble(diagonalsToParse[1].replaceAll("'","")));
                    }
                    String diagonalMin = String.valueOf(Collections.min(diagonalsSet));
                    String diagonalMax = String.valueOf(Collections.max(diagonalsSet));
                    criteriaList.add(Criteria.where(SPECIFICATION).elemMatch(createCriteriaBetween(DIAGONAL, diagonalMin, diagonalMax)));

                }
                default -> {
                    if (entry.getKey().equals("page") || entry.getKey().equals("size")){
                        continue;
                    }
                    List<String> paramList = List.of(entry.getValue().split(","));
                    if (paramList.size() == 1) {
                        criteriaList.add(Criteria.where(SPECIFICATION).elemMatch(createCriteriaIs("value", entry.getValue())));
                    } else {
                        criteriaList.add(Criteria.where(SPECIFICATION).elemMatch(createCriteriaIn("value", paramList)));
                    }
                }
            }
        }
        return deviceCriteriaRepository.findAll(new Query().addCriteria(new Criteria().andOperator(criteriaList)), pageRequest);
    }


    private Criteria createCriteriaIs(String key, Object param){
        return Criteria.where(key).is(param);
    }
    private Criteria createCriteriaIn(String key, List<String> params){
        return key.equals("brand") ? Criteria.where(key).in(params.stream().map(ObjectId::new).toList()) : Criteria.where(key).in(params);
    }
    private Criteria createCriteriaBetween(String key, String firstValue, String secondValue){
        return Criteria.where(key).gte(firstValue).lte(secondValue);
    }

    @Override
    public List<Device> getAllFromCatalogueAndBrand(@NonNull Catalogue catalogue, @NonNull Brand brand) {
        return deviceRepository.findAllByCatalogueAndBrand(catalogue, brand);
    }

    @Override
    public List<Device> getAllWithSameColor(@NonNull String deviceId, @NonNull String color) {
        String deviceSeries = getOne(deviceId).getSpecificationValue("series");
        return deviceRepository.getAllBySeriesAndColor(deviceSeries, color)
                .stream().filter(device -> !device.getId().equals(deviceId)).toList();
    }

    @Override
    public List<Device> getAllWithSameMemory(@NonNull String deviceId, @NonNull String internalMemory) {
        String deviceSeries = getOne(deviceId).getSpecificationValue("series");
        return deviceRepository.getAllBySeriesAndInternalMemory(deviceSeries, internalMemory)
                .stream().filter(device -> !device.getId().equals(deviceId)).toList();
    }

    @Override
    public Device getOne(@NonNull String deviceId) {
        return deviceRepository.findById(deviceId).orElseThrow(()-> new ModelNotFoundException(String.format("Device with id: %s not found", deviceId)));
    }

    @Override
    public Device create(@NonNull DeviceRequest deviceRequest) {
        return null;
    }

    @Override
    public Device update(@NonNull String deviceId, @NonNull DeviceRequest deviceRequest) {
        return null;
    }

    @Override
    public void delete(@NonNull String deviceId) {
        deviceRepository.delete(getOne(deviceId));
    }

    @Override
    public void addReviewToDevices(@NonNull Review review, List<Device> devices) {
        for (Device device :
                devices) {
            device.getReviews().add(review);
        }
        deviceRepository.saveAll(devices);
    }

    @Override
    public void deleteReviewFromDevices(Review review) {
        List<Device> devices = deviceRepository.findAllByReviews(Collections.singletonList(review));
        for (Device device :
                devices) {
            device.getReviews().remove(review);
        }
        deviceRepository.saveAll(devices);
    }

    @Override
    public void addQuestionToDevices(@NonNull Question question, List<Device> devices) {
        for (Device device :
                devices) {
            device.getQuestions().add(question);
        }
        deviceRepository.saveAll(devices);
    }

    @Override
    public void deleteQuestionFromDevices(Question question) {
        List<Device> devices = deviceRepository.findAllByQuestions(Collections.singletonList(question));
        for (Device device :
                devices) {
            device.getQuestions().remove(question);
        }
        deviceRepository.saveAll(devices);
    }

    @Override
    public Map<String, List<Device>> getGroupedDevices() {
        Map<String, List<Device>> resultMap = new HashMap<>();
        List<Device> bestPrices = deviceRepository.findAll(Sort.by(Sort.Order.desc("discount")))
                .stream()
                .limit(10)
                .toList();
        if (!bestPrices.isEmpty()) resultMap.put("The best price offers", bestPrices);
        List<Device> leaders = deviceRepository.findAllByIsLeaderTrue().stream().limit(10).toList();
        if (!leaders.isEmpty()) resultMap.put("Sales leader", leaders);
        List<Device> newProducts = deviceRepository.findAll(Sort.by(Sort.Order.asc("creatingDate")))
                .stream()
                .limit(10)
                .toList();
        if (!newProducts.isEmpty()) resultMap.put("New products", newProducts);
        return resultMap;
    }
}
