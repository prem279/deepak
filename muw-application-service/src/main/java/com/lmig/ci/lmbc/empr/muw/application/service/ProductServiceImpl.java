package com.lmig.ci.lmbc.empr.muw.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lmig.ci.lmbc.empr.muw.application.api.ProductResource;
import com.lmig.ci.lmbc.empr.muw.application.config.MuwApplicationProductConfig;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	MuwApplicationProductConfig muwApplicationProductConfig;

	@Override
	public ResponseEntity<List<ProductResource>> getProducts() {
		Map<String, List<String>> productConfigurationMap = muwApplicationProductConfig.productConfiguration;
		ArrayList<ProductResource> productResources = new ArrayList<ProductResource>();

		if (productConfigurationMap != null) {
			for (String key : productConfigurationMap.keySet()) {
				ProductResource productResource = new ProductResource();
				productResource.setProductCode(key);
				productResource.setApplicantTypeCodes(productConfigurationMap.get(key));
				productResources.add(productResource);
			}
		}

		ResponseEntity<List<ProductResource>> returnResponseEntity = new ResponseEntity<List<ProductResource>>(
				productResources, HttpStatus.OK);
		return returnResponseEntity;
	}
}
