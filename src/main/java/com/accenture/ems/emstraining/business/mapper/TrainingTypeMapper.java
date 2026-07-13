package com.accenture.ems.emstraining.business.mapper;

import com.accenture.ems.emstraining.business.repository.dao.TrainingTypeDAO;
import com.accenture.ems.emstraining.model.TrainingType;

import java.util.ArrayList;
import java.util.List;

public class TrainingTypeMapper {
    public static TrainingType mapFromDAO(TrainingTypeDAO TrainingTypeDAO) {
        if (TrainingTypeDAO == null) return null;
        TrainingType TrainingType = new TrainingType();
        TrainingType.setId(TrainingTypeDAO.getId());
        TrainingType.setType(TrainingTypeDAO.getType());
        return TrainingType;
    }
    public static TrainingTypeDAO mapToDAO(TrainingType TrainingType) {
        if (TrainingType == null) return null;
        TrainingTypeDAO TrainingTypeDAO = new TrainingTypeDAO();
        TrainingTypeDAO.setId(TrainingType.getId());
        TrainingTypeDAO.setType(TrainingType.getType());
        return TrainingTypeDAO;
    }
    public static List<TrainingType> mapListFromDAO(List<TrainingTypeDAO> TrainingTypeDAOList){
        if (TrainingTypeDAOList == null) return null;
        List<TrainingType> TrainingTypeList = new ArrayList<>();
        for (TrainingTypeDAO TrainingTypeDAO : TrainingTypeDAOList){
            TrainingTypeList.add(mapFromDAO(TrainingTypeDAO));
        }
        return TrainingTypeList;
    }
}
