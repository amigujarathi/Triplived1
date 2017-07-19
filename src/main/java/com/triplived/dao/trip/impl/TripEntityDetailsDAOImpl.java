package com.triplived.dao.trip.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.triplived.dao.common.GenericHibernateDAO;
import com.triplived.dao.trip.TripEntityDetailsDAO;
import com.triplived.entity.TripEntityDetailsDb;

@Repository
public class TripEntityDetailsDAOImpl extends GenericHibernateDAO<TripEntityDetailsDb, Serializable> implements TripEntityDetailsDAO {

}
