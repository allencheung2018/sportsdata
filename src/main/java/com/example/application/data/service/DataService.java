package com.example.application.data.service;

import com.example.application.data.entity.E0;
import com.example.application.data.entity.League;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

;

@Service
public class DataService {
    @Autowired
    private EntityManager entityManager;

    public DataService(EntityManager entityManager) {
//        this.entityManager = entityManager;
    }

    public int getCount(Class<? extends League> aClass){
        Session session = entityManager.unwrap(SessionImplementor.class);
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<E0> criteriaQuery = builder.createQuery(E0.class);
        Root<E0> root = criteriaQuery.from(E0.class);
        criteriaQuery.select(root);
        Query query = session.createQuery(criteriaQuery);
        return query.getResultList().size();
    }
}
