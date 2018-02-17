package com.lmig.ci.lmbc.empr.muw.application.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lmig.ci.lmbc.empr.muw.application.EnvironmentConstants;
import com.lmig.ci.lmbc.empr.muw.application.service.ProductService;

@BasePathAwareController
@RequestMapping(value = EnvironmentConstants.BASE_PATH)
public class ProductController {

	@Autowired
	private ProductService service;

	@RequestMapping(value = "/products", method = { RequestMethod.GET })
	ResponseEntity<List<ProductResource>> getProducts() {
		return service.getProducts();
	}

}
