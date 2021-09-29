package com.greencommute.organizationservice.dao;

import com.greencommute.organizationservice.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, UUID> {
    @Query(value = "SELECT * from organisations o , addresses a where a.city in :city and o.id = a.org_id",nativeQuery = true)
    List<Organisation> findByCity(@Param("city") List<String> city);
}
