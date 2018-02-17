package com.lmig.ci.lmbc.empr.muw.application.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.lmig.ci.lmbc.empr.muw.application.api.ProductResource;

public interface ProductService {

	public ResponseEntity<List<ProductResource>> getProducts();

}
