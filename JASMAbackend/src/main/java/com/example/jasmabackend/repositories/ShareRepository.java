package com.example.jasmabackend.repositories;

import com.example.jasmabackend.entities.share.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long>  {
}
