<<<<<<< HEAD
package com.ticketease.te.performance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    Page<Performance> findByNameContaining(String name, Pageable pageable);

}
=======
package com.ticketease.te.performance;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PerformanceRepository extends JpaRepository<Performance, Long> {
}
>>>>>>> 4b8cbb66209fb14f4d97411d810a840771db0d63
