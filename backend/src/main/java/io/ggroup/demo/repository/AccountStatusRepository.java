package io.ggroup.demo.repository;

import io.ggroup.demo.model.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStatusRepository extends JpaRepository<AccountStatus, String> {
}
