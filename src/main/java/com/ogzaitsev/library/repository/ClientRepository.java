package com.ogzaitsev.library.repository;

import com.ogzaitsev.library.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value = " SELECT * " +
                   " FROM clients " +
                   " WHERE (:lastClientId IS NULL OR client_id > :lastClientId) " +
                   " ORDER BY client_id ASC " +
                   " LIMIT :pageSize",
            nativeQuery = true)
    List<Client> findAll(@Param("lastClientId") Long lastClientId, @Param("pageSize") int pageSize);

    Optional<Client> findClientByName(String name);
}
