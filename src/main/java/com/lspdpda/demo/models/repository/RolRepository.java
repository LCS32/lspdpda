package com.lspdpda.demo.models.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lspdpda.demo.enums.RolNombre;
import com.lspdpda.demo.models.entity.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
	Optional<Rol> findByRolNombre(RolNombre rolNombre);

	boolean existsByRolNombre(RolNombre rolNombre);
}
