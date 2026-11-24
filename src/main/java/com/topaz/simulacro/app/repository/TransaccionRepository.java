package com.topaz.simulacro.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.topaz.simulacro.app.entity.Transaccion;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long>{

}
