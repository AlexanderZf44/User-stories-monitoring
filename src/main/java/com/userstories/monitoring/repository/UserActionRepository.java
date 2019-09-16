package com.userstories.monitoring.repository;

import com.userstories.monitoring.domain.users.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserActionRepository extends JpaRepository<UserAction, Long>, JpaSpecificationExecutor<UserAction> {

}
