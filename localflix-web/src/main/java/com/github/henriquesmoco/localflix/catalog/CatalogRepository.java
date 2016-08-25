package com.github.henriquesmoco.localflix.catalog;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CatalogRepository extends JpaRepository<Media, Integer> {
}
